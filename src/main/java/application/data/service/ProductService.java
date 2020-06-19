package application.data.service;

import application.data.model.Product;
import application.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void addNewListProduct(List<Product> products) {
        productRepository.save(products);
    }

    public void addNewProduct(Product product) {
        productRepository.save(product);
    }

    public Product findOne(int productId) {
        return productRepository.findOne(productId);
    }

    public boolean updateProduct(Product product) {
        try {
            productRepository.save(product);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public List<Product> getListAllProducts() {
        return productRepository.findAll();
    }

    public boolean deleteProduct(int productId) {

        try {
            productRepository.delete(productId);
            return true;
        } catch (Exception e) {
        }
        return false;

    }
    public long getTotalProducts() {
        return productRepository.getTotalProduct();

    }
}
