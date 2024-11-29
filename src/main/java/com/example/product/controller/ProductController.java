package com.example.product.controller;

import com.example.product.dto.AddProductDto;
import com.example.product.dto.ProductDto;
import com.example.product.model.Product;
import com.example.product.service.ProductProducer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductProducer productProducer;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addProduct(@Valid @RequestBody AddProductDto addProductDto){
//        for(ProductDto productDto : addProductDto.getProducts()){
//            productProducer.sendProduct(productDto);
//        }
        addProductDto.getProducts().forEach(productProducer::sendProduct);
        return ResponseEntity.ok(Map.of("statusCode","200","statusMessage","Product added to Kafka"));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProduct(@RequestParam String name, @RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productProducer.searchProduct(name, pageable);
        return ResponseEntity.ok(products);
    }
    
}

//POST localhost:9097/product/add
//{
//    "products": [
//    {
//        "price": 30000.00,
//            "quantity": -2,
//            "category": "Electronics"
//    },
//    {
//        "name": "CS",
//            "price": 500.00,
//            "quantity": 5,
//            "category": "Books"
//    }
//    ]
//}

//GET localhost:9097/product/search?name=Laptop&page=0&size=5
