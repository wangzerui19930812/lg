package com.lg.mybatis.mybatisdemo;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {
    private InputStream resourceIn;
    private Configuration configuration;

    public XMLMapperBuilder(InputStream resourceIn, Configuration configuration) {
        this.resourceIn = resourceIn;
        this.configuration = configuration;
    }

    public void parse() {
        Document document = null;
        try {
            document = new SAXReader().read(this.resourceIn);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectE = rootElement.selectNodes("//select");
        for (Element e:selectE) {
            String id = e.attributeValue("id");
            String mappedStatementId = namespace + "." +id;
            String sql = e.getTextTrim();
            String paramterType = e.attributeValue("paramterType");
            String resultType = e.attributeValue("resultType");
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setSql(sql);
            try {
                mappedStatement.setParamterType(Class.forName(paramterType));
                mappedStatement.setResultType(Class.forName(resultType));
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            configuration.addMappedStatement(mappedStatementId,mappedStatement);
        }
    }
}
