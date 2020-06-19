package application.data.service;

import application.data.model.ProductImage;
import application.data.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;

    @Transactional
    public void addNewListProductImage(List<ProductImage> productImages) {
        productImageRepository.save(productImages);
    }

    public void addNewProductImage(ProductImage productImage) {
        productImageRepository.save(productImage);
    }

    public ProductImage findOne(int productImageId) {
        return productImageRepository.findOne(productImageId);
    }

    public boolean updateProductImage(ProductImage productImage) {
        try {
            productImageRepository.save(productImage);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public List<ProductImage> getListAllProductImages() {
        return productImageRepository.findAll();
    }

    public boolean deleteProductImage(int productImageId) {

        try {
            productImageRepository.delete(productImageId);
            return true;
        } catch (Exception e) {
        }
        return false;

    }
    public long getTotalProductImages() {
        return productImageRepository.getTotalProductImages();

    }
}
