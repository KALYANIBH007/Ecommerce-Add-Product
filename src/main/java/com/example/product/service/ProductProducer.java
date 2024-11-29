package com.example.product.service;

import com.example.product.dto.ProductDto;
import com.example.product.exception.ProductNotFoundExcepion;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductProducer {

    @Autowired
    private KafkaTemplate<String, ProductDto> kafkaTemplate;

    @Autowired
    private ProductRepo productRepo;

    private static final String TOPIC = "product_topic";

    public void sendProduct(ProductDto productDto){
        kafkaTemplate.send(TOPIC, productDto);
    }

    public Page<Product> searchProduct(String name, Pageable pageable){
        Page<Product> products = productRepo.findByName(name, pageable);
        if(products.isEmpty()){
            throw new ProductNotFoundExcepion("No product found with name " + name);
        }
        return products;
    }
}
