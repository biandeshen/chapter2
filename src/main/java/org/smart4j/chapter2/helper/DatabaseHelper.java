package org.smart4j.chapter2.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
/**
 * 数据库操作助手类
 */
public final class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final ThreadLocal<Connection> CONNECTION_HOLDER=new ThreadLocal<Connection>();

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
//        Connection conn = null;
/*        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("get Connection failure", e);
        }*/
        //引入ThreadLocal后
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null){
            try {
                conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            } catch (SQLException e) {
                LOGGER.error("get Connection failure",e);
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection(){/*Connection conn 引入后，无需此参数*/
/*        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure",e);
            }
        }*/
        //引入ThreadLocal后
        Connection conn = CONNECTION_HOLDER.get();
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 查询实体列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass,/*Connection conn,*/ String sql, Object...params){
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        }catch (SQLException e){
            LOGGER.error("query entity list failure",e);
            throw new RuntimeException(e);
        }finally {
            closeConnection(/*conn*/);
        }
        return entityList;
    }


}
