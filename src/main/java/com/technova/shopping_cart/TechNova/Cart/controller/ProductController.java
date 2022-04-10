package com.technova.shopping_cart.TechNova.Cart.controller;


import com.technova.shopping_cart.TechNova.Cart.dto.ProductRequest;
import com.technova.shopping_cart.TechNova.Cart.dto.ProductResponse;
import com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/v2")
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/products")
    public ResponseEntity<Object> getAllProducts() {
        ResponseEntity<ProductResponse> products = restTemplate.getForEntity("http://localhost:8081/api/products", ProductResponse.class);

        if (products.getStatusCode() == HttpStatus.OK) {
            ProductResponse productResponse = products.getBody();
            //   Map<String,Object> resMap = new HashMap<>();
            //  resMap.put("order", orderResponse.getData());
//            JsonObject jsonObject= new JsonObject(jsonValue);
//            log.info("orders list is: {}",orders.getBody());
            return ApiResponse.generateResponse(productResponse.getStatusCode(), productResponse.getMessage(), productResponse.getData(), productResponse.getErrors());

        }
        return ApiResponse.generateResponse((HttpStatus.BAD_REQUEST.value()), "Product failed", null, "Something went wrong");
    }

    @PostMapping("/products")
    public ResponseEntity<Object> createProducts(@RequestBody ProductRequest productRequest) {
        ResponseEntity<ProductResponse> product = restTemplate.postForEntity("http://localhost:8081/api/products", productRequest, ProductResponse.class);

        if (product.getStatusCode() == HttpStatus.OK) {
            ProductResponse productResponse = product.getBody();
//            JsonObject jsonObject= new JsonObject(jsonValue);
            //Object data = jsonObject.get("data");
//            log.info("orders list is: {}",orders.getBody());
            return ApiResponse.generateResponse(productResponse.getStatusCode(), "Product created successfully", productResponse.getData(), productResponse.getErrors());

        }
        return ApiResponse.generateResponse((HttpStatus.BAD_REQUEST.value()), "Product failed", null, "Something went wrong");
    }


    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProducts(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        HttpEntity<ProductRequest> requestEntity = new HttpEntity<ProductRequest>(productRequest, null);
        ResponseEntity<ProductResponse> products = restTemplate.exchange("http://localhost:8081/api/products/" + id, HttpMethod.PUT, requestEntity, ProductResponse.class);

        if (products.getStatusCode() == HttpStatus.OK) {
            ProductResponse productResponse = products.getBody();
//            JsonObject jsonObject= new JsonObject(jsonValue);
            //Object data = jsonObject.get("data");
//            log.info("orders list is: {}",orders.getBody());
            return ApiResponse.generateResponse(productResponse.getStatusCode(), "Order updated successfully", productResponse.getData(), productResponse.getErrors());

        }
        return ApiResponse.generateResponse((HttpStatus.BAD_REQUEST.value()), "Order failed", null, "Something went wrong");
    }
}