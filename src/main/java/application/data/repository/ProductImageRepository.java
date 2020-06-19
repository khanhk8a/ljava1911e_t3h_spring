package application.data.repository;

import application.data.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    @Query("select count(pi.id) from dbo_product_image pi")
    long getTotalProductImages();
}
