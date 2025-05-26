package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repo.ProductRepo;
import com.example.demo.model.Product;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(int id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImagedata(image.getBytes());

        return productRepo.save(product);
    }

	public Product updateProduct(Product product, MultipartFile image) {
		product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        try {
			product.setImagedata(image.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return productRepo.save(product);
	}

	public void deleteProduct(int id) {
		productRepo.deleteById(id);
		
	}

	public List<Product> searchProducts(String keyword) {
		
		return productRepo.searchProducts(keyword);
	}
	
}