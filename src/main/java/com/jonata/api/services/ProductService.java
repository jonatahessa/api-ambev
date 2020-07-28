package com.jonata.api.services;

import java.util.List;
import java.util.Optional;

import com.jonata.api.consumers.HttpConsumer;
import com.jonata.api.dto.ProductDTO;
import com.jonata.api.model.Product;
import com.jonata.api.repositories.ProductRepository;
import com.jonata.api.services.exception.ObjectNotFoundException;
import com.jonata.api.services.exception.ProductInvalidAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  HttpConsumer httpConsumer;

  public List<Product> findAll() {
    return productRepository.findAll();
  }

  public List<Product> findAllOrdered() {
    return productRepository.findAllByOrderByNameAsc();
  }

  public Product findById(String id) {
    Optional<Product> obj = productRepository.findById(id);
    return obj.orElseThrow(() -> new ObjectNotFoundException("Product not found"));
  }

  public Product insert(final Product obj) throws ProductInvalidAttribute {
    if (obj.getName() == null || obj.getName().isEmpty()) {
      throw new ProductInvalidAttribute("The Product name cannot be empty");
    }

    obj.setId(null);
    return productRepository.save(obj);
  }

  public Product update(Product obj) {
    Product newObj = findById(obj.getId());
    updateData(newObj, obj);
    return productRepository.save(newObj);
  }

  private void updateData(Product newObj, Product obj) {
    newObj.setName(obj.getName());
    newObj.setDescription(obj.getDescription());
    newObj.setPrice(obj.getPrice());
    newObj.setBrand(obj.getBrand());
  }

  public void delete(String id) {
    findById(id);
    productRepository.deleteById(id);
  }

  public Product findByName(String name) {
    return productRepository.findByNameIgnoreCase(name);
  }

  public Product fromDTO(ProductDTO objDto) {
    return new Product(objDto.getId(), objDto.getName(), objDto.getDescription(), objDto.getPrice(), objDto.getBrand());
  }
}