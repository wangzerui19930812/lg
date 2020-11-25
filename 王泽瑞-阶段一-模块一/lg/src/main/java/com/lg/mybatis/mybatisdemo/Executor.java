package com.lg.mybatis.mybatisdemo;

public interface Executor {

    <T> T  query(Configuration configuration, String mappedStatementId, Object parameter);
}
