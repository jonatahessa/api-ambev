package com.jonata.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.jonata.api.dto.ProductDTO;
import com.jonata.api.model.Product;
import com.jonata.api.resources.util.URL;
import com.jonata.api.services.ProductService;
import com.jonata.api.services.exception.ProductInvalidAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/products")
@Api(value = "API REST AMBEV PRODUCTS")
@CrossOrigin(origins = "*")
public class ProductResource {

    @Autowired
    private ProductService productService;

    @GetMapping
    @ApiOperation(value = "Return a list of all products from database.")
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<Product> list = productService.findAll();
		List<ProductDTO> listDto = list.stream().map(obj -> new ProductDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/ordered")
    @ApiOperation(value = "Return a list of all products from database ordered by name.")
    public ResponseEntity<List<ProductDTO>> findAllOrdered() {
        List<Product> list = productService.findAllOrdered();
		List<ProductDTO> listDto = list.stream().map(obj -> new ProductDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Return a product to database by ID.")
    public ResponseEntity<ProductDTO> findById(@PathVariable String id) {
        ProductDTO objDto = new ProductDTO(productService.findById(id));
        return ResponseEntity.ok().body(objDto);
    }

    @GetMapping("/planet")
    @ApiOperation(value = "Return a product from database by name.")
    public ResponseEntity<Product> findName(@RequestParam(value = "name", defaultValue = "") String name) {
        name = URL.decodeParam(name);
        return ResponseEntity.ok().body(productService.findByName(name));
    }

    @PostMapping
    @ApiOperation(value = "Insert a product to database.")
    public ResponseEntity<Void> insert(@RequestBody ProductDTO objDto) throws ProductInvalidAttribute {
        Product obj = productService.fromDTO(objDto);
        obj = productService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product from database.")
    public ResponseEntity<Void> update(@RequestBody ProductDTO objDto, @PathVariable String id) {
        Product obj = productService.fromDTO(objDto);
        obj.setId(id);
        productService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product from database by ID.")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}