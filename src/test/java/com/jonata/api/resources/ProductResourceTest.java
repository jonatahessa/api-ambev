package com.jonata.api.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonata.api.model.Product;
import com.jonata.api.services.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductService planetService;

    private Product mockedProduct;

    @BeforeEach
	public void before() {
		mockedProduct = new Product();
		mockedProduct.setName("Cerveja");
		mockedProduct.setDescription("Cerveja puro malte 600ml");
		mockedProduct.setPrice(15);
		mockedProduct.setBrand("Brhama");

	}

	@Test
	public void insertProductValid201() throws Exception {
		mvc.perform(post("/products").content(toJson(mockedProduct)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void insertProductUnsupportedMediaType415() throws Exception {
		mvc.perform(post("/products").content(toJson(mockedProduct))).andExpect(status().isUnsupportedMediaType());
	}

	@Test
	public void insertEmptyProduct400() throws Exception {
		mvc.perform(post("/products").content("").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void insertProductWithoutName400() throws Exception {
		mockedProduct.setName(null);
		mvc.perform(post("/products").content(toJson(mockedProduct)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
    }

    @Test
	public void mustGetProductById() throws Exception {
		Product obj = planetService.findByName(mockedProduct.getName());
        mvc.perform(get("/products/" +  obj.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
    
    @Test
	public void mustDeleteProduct() throws Exception {
		Product obj = planetService.findByName(mockedProduct.getName());
        mvc.perform(delete("/products/" +  obj.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    }

	private String toJson(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}