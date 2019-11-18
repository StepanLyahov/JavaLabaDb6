package com.labaDb.Repository;

import com.labaDb.TodoApplication;
import com.labaDb.model.Product;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepo {
    private static Logger LOG = Logger.getLogger(TodoApplication.class);

    private static String url = "jdbc:mysql://localhost:3306/laba3";
    private static String username = "root";
    private static String password = "QweAsdZxc12345678";

    private static Connection coon;
    private static Statement statement;

    public ProductRepo () {
        BasicConfigurator.configure();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            coon = DriverManager.getConnection(url,username, password);
            statement = coon.createStatement();
        } catch (SQLException e) {
            LOG.info("Отсутствует подключение к БД");
        }

    }

    public void addProduct(Product pr) {
        String query = "";
        try {
            int id_measure = 0;
            if (pr.getMeasure().equals("килограмм"))
                id_measure = 2;
            else if (pr.getMeasure().equals("штук"))
                id_measure = 1;
            else if (pr.getMeasure().equals("литров"))
                id_measure = 3;
            else if (pr.getMeasure().equals("метры"))
                id_measure = 5;

            query = String.format("insert into product(price, name, count, measure_id, stock_id) values (%d, '%s', %d, %d, %d)", pr.getPrice(), pr.getName(), pr.getCount(), id_measure, pr.getId_stock());
            statement.execute(query);
        } catch (SQLException ex) {
            LOG.info("Ошибка SQL ..." + query);
        }
    }

    public List<Product> getAllProduct(String settings) {
        List<Product> list = new ArrayList<>();
        String query = "select product.id, product.price, product.name, product.count, product.stock_id, measure.name from" +
                " product join measure on product.measure_id = measure.id" +
                settings;

        try {
            ResultSet resSet = statement.executeQuery(query);
            while (resSet.next()) {
                String product_id = resSet.getString("product.id");
                String product_price = resSet.getString("product.id");
                String product_name = resSet.getString("product.name");
                String product_count = resSet.getString("product.count");
                String product__stock_id = resSet.getString("product.stock_id");
                String product_measure = resSet.getString("measure.name");

                Integer id = Integer.parseInt(product_id);
                Integer d_price = Integer.parseInt(product_price);
                Integer d_count = Integer.parseInt(product_count);
                Integer d_stock_id = Integer.parseInt(product__stock_id);
                list.add(new Product(
                        id,
                        product_name,
                        product_measure,
                        d_price,
                        d_count,
                        d_stock_id
                ));
            }

        } catch (SQLException e) {
            LOG.info("Не верно введён запрос");
        }
        return list;
    }
}
