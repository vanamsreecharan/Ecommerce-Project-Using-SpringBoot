package com.example.demo;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product.getId() > 0) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]>getImageByProductId(@PathVariable int productId){
    	Product product=productService.getProductById(productId);
    	return new ResponseEntity<>(product.getImagedata(),HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        Product savedProduct = null;
        try {
            savedProduct = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
    @PutMapping("product/{id}")
    public ResponseEntity<String>updateProduct(@PathVariable int id,@RequestPart Product product,@RequestPart MultipartFile imageFile) throws IOException{
    	Product updateProduct=null;
    	updateProduct = productService.updateProduct(product, imageFile);
		return new ResponseEntity<>("updated", HttpStatus.OK);
    }
    @DeleteMapping("product/{id}")
    public ResponseEntity<String>deleteProduct(@PathVariable int id){
    	Product product=productService.getProductById(id);
    	if(product!=null) {
    		productService.deleteProduct(id);
    		return new ResponseEntity<>("deleted",HttpStatus.OK);
    	}
    	else
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping()
    public ResponseEntity<List<Product>>searchProducts(@RequestParam String keyword){
    	List<Product> products=productService.searchProducts(keyword);
    	System.out.println("Searching with keyword:"+keyword);
    	return new ResponseEntity<>(products,HttpStatus.OK);
    }
}