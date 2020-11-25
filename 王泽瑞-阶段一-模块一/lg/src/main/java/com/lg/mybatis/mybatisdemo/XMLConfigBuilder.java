package com.lg.mybatis.mybatisdemo;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {
    private InputStream in;

    public XMLConfigBuilder(InputStream in) {
        this.in = in;
    }

    public Configuration parse() {
        Document document = null;
        try {
            document = new SAXReader().read(this.in);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        List<Element> propertys = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element property:propertys) {
            String name = property.attributeValue("name");
            String value = property.attributeValue("value");
            properties.setProperty(name,value);
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
            comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
            comboPooledDataSource.setUser(properties.getProperty("username"));
            comboPooledDataSource.setPassword(properties.getProperty("password"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        Configuration configuration = new Configuration();
        configuration.setDateSource(comboPooledDataSource);
        List<Element> mappers = rootElement.selectNodes("//mapper");
        for (Element mapper:mappers) {
            String resource = mapper.attributeValue("resource");
            InputStream resourceIn = Resources.getResourceAsStream(resource);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resourceIn, configuration);
            xmlMapperBuilder.parse();
        }
        return configuration;
    }
}
