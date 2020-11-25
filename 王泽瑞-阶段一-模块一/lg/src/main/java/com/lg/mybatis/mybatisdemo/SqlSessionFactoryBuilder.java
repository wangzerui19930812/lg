package com.lg.mybatis.mybatisdemo;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream in) {
        XMLConfigBuilder parser = new XMLConfigBuilder(in);
        Configuration  configuration = parser.parse();
        return new DefaultSqlSessionFactory(configuration);
    }
}
