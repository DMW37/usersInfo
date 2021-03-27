package com.dmw.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class JDBCUtils {
    private static DataSource dataSource;

    static {
        //创建配置文件
        Properties properties = new Properties();
        //根据路径获取资源
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            //加载进去
            properties.load(is);
            //转化数据源
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取资源
    public static DataSource getDataSource() {
        return dataSource;
    }
}
