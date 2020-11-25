package com.lg.mybatis.mybatisdemo;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    public SimpleExecutor() {
        super();
    }

    public  <T> T  query(Configuration configuration, String mappedStatementId, Object parameter) {
        DataSource dateSource = configuration.getDateSource();
        try {
            MappedStatement mappedStatement = configuration.getMappedStatement(mappedStatementId);
            String rawSql = mappedStatement.getSql();
            BoundSql boundSql = getBoundSql(rawSql);
            Connection connection = dateSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
            List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
            Class<?> paramterType = mappedStatement.getParamterType();
            for (int i = 0; i < parameterMappingList.size(); i++) {
                ParameterMapping parameterMapping = parameterMappingList.get(i);
                String name = parameterMapping.getContent();
                //反射
                Field declaredField = paramterType.getDeclaredField(name);
                declaredField.setAccessible(true);
                //参数的值
                Object o = declaredField.get(parameter);
                //给占位符赋值
                preparedStatement.setObject(i + 1, o);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            Class<?> resultTypeClass = mappedStatement.getResultType();

            ArrayList<Object> objects = new ArrayList<Object>();

            // 6. 封装返回结果集
            while (resultSet.next()){
                Object o =resultTypeClass.newInstance();
                //元数据
                ResultSetMetaData metaData = resultSet.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {

                    // 字段名
                    String columnName = metaData.getColumnName(i);
                    // 字段的值
                    Object value = resultSet.getObject(columnName);

                    //使用反射或者内省，根据数据库表和实体的对应关系，完成封装
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    writeMethod.invoke(o,value);


                }
                objects.add(o);
            }
            return (T) objects.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private BoundSql getBoundSql(String rawSql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parse = genericTokenParser.parse(rawSql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parse, parameterMappings);
        return boundSql;
    }
}
