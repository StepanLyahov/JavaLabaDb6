package com.labaDb.model;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String name;
    private String measure;
    private Integer price;
    private Integer count;
    private Integer id_stock;

    public Product(Integer id, String name, String measure, Integer price, Integer count, Integer id_stock) {
        this.id = id;
        this.name = name;
        this.measure = measure;
        this.price = price;
        this.count = count;
        this.id_stock = id_stock;
    }

}
