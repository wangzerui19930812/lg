package com.lg.mybatis.mybatisdemo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private DataSource dateSource;
    private Map<String,MappedStatement> mappedStatements = new HashMap<String,MappedStatement>();

    public Configuration() {
    }

    public DataSource getDateSource() {
        return dateSource;
    }

    public void setDateSource(DataSource dateSource) {
        this.dateSource = dateSource;
    }

    public void addMappedStatement(String mappedStatementId, MappedStatement mappedStatement){
        mappedStatements.put(mappedStatementId,mappedStatement);
    }

    public MappedStatement getMappedStatement(String mappedStatementId){
        return mappedStatements.get(mappedStatementId);
    }
}
