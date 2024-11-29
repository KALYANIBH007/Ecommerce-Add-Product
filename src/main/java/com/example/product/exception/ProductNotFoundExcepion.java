package com.example.product.exception;

public class ProductNotFoundExcepion extends RuntimeException{
    public ProductNotFoundExcepion(String message){
        super(message);
    }
}
