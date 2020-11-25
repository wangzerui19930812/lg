package com.lg.mybatis.mybatisdemo;

public interface SqlSession {
    <T> T selectOne(String mappedStatementId, Object parameter);
}
