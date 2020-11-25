package com.lg.mybatis.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCTest {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/base?useCharactorEncoding=utf-8&serverTimezone=UTC", "root", "futuredream");
        PreparedStatement pre = connection.prepareStatement("select  * from user where username = ?");
        pre.setObject(1,"admin");
        pre.execute();
        ResultSet resultSet = pre.getResultSet();
        if(resultSet.next()){
            resultSet.getObject(1);
            resultSet.getObject(2);
            resultSet.getObject(3);
            System.out.println();
        }
        resultSet.close();
        pre.close();
        connection.close();
    }
}
