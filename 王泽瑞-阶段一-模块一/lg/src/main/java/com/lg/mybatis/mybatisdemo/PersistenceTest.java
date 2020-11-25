package com.lg.mybatis.mybatisdemo;

import com.lg.mybatis.bean.User;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class PersistenceTest {
    @Test
    public void test01() throws IOException, SQLException {
        InputStream in = Resources.getResourceAsStream("mySqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(1);
        user.setName("1");
        User o = sqlSession.selectOne("com.lg.mybatis.bean.User.selectOne",user);
        System.out.println(o);
    }
}
