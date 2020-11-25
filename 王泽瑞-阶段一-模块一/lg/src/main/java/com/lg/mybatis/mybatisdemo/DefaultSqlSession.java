package com.lg.mybatis.mybatisdemo;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public  <T> T  selectOne(String mappedStatementId, Object parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement(mappedStatementId);
        Executor executor = new SimpleExecutor();
        return executor.query(configuration,mappedStatementId,parameter);
    }
}
