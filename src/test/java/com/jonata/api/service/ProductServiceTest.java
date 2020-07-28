package com.jonata.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.jonata.api.model.Product;
import com.jonata.api.repositories.ProductRepository;
import com.jonata.api.services.ProductService;
import com.jonata.api.services.exception.ObjectNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository productRepository;

    private String mockedId;

    private Product mockedProduct;

    @BeforeEach
	public void before() {
		mockedId = "ID";
		mockedProduct = new Product();
		mockedProduct.setName("Cerveja");
		mockedProduct.setDescription("Cerveja puro malte 600ml");
		mockedProduct.setPrice(15);
		mockedProduct.setBrand("Brhama");
	}

	@Test
	public void listAllProduct() {
		List<Product> list = service.findAll();
		assertThat(list).isNotNull();
	}

	@Test
	public void insertValidProduct() throws Exception {
		when(productRepository.save(mockedProduct)).thenReturn(mockedProduct);
		Product newPlanet = service.insert(mockedProduct);
		assertThat(newPlanet).isNotNull();
	}

	@Test
	public void findPlanetById() throws Exception {
		when(productRepository.findById(mockedId)).thenReturn(Optional.of(mockedProduct));
		Product planetFound = service.findById(mockedId);
		assertThat(planetFound).isNotNull();
	}

	@Test
	public void findInexistentIdPlanet() throws Exception {
		Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            service.findById(mockedId);
          });
	}

	@Test
	public void findPlanetByName() throws Exception {
		when(productRepository.findByNameIgnoreCase(mockedProduct.getName())).thenReturn(mockedProduct);
		Product planet = service.findByName(mockedProduct.getName());
		assertThat(planet).isNotNull();
		assertThat(planet.getName()).isEqualTo(mockedProduct.getName());
	}

	@Test
	public void deleteProduct() throws Exception {
		when(productRepository.findById(mockedId)).thenReturn(Optional.of(mockedProduct));
		doNothing().when(productRepository).delete(mockedProduct);
		service.delete(mockedId);
	}

	@Test
	public void tryDeletePlanetNotFoundException() throws Exception {
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            service.delete(mockedId);
          });
	}
}