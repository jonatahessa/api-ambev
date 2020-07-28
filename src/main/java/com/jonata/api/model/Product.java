package com.jonata.api.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    String id;
    String name;
    String description;
    double price;
    String brand;

    public Product(String id, String name, String description, double price, String brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
    }
}