package com.jonata.api.dto;

import java.io.Serializable;

import com.jonata.api.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    String id;
    String name;
    String description;
    double price;
    String brand;

    public ProductDTO(Product obj) {
        id = obj.getId();
        name = obj.getName();
        description = obj.getDescription();
        price = obj.getPrice();
        brand = obj.getBrand();
    }
}