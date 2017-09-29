package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 提供客户数据服务
 */
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    /**
     * 获取客户列表
     */
    public List<Customer> getCustomerList() {
        //此处使用了ThreadLocal后，获取连接被隐藏
        /* Connection conn = null;
        List<Customer> customerList = new ArrayList<Customer>();*/
        /*try {*/
            String sql = "SELECT * from customer";
            /*添加DbUtils后，需要在此创建连接*/
            //此处使用了ThreadLocal后，获取连接被隐藏，无需创建了
            /*conn = DatabaseHelper.getConnection();*/
            //conn无需传递//
            return DatabaseHelper.queryEntityList(Customer.class,/*conn,*/sql);
/*            添加DbUtils后，可简化以下代码
            conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setContact(rs.getString("contact"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setRemark(rs.getString("remark"));
                customerList.add(customer);
            }*/
       /* }*/ /*catch (SQLException e) {
            LOGGER.error("execute sql failure", e);
        }*/ /*finally {
            DatabaseHelper.closeConnection(conn);
        }*/
        /*return customerList;*/
    }

    /**
     * 获取客户
     */
    public Customer getCustomer(long id) {
        //TODO
        return null;
    }

    /**
     * 创建客户
     */
    public boolean createCustomer(Map<String, Object> fieldMap) {
        //TODO
        return false;
    }

    /**
     * 更新客户
     */
    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        //TODO
        return false;
    }

    /**
     * 删除客户
     */
    public boolean deleteCustomer(long id) {
        //TODO
        return false;
    }
}
