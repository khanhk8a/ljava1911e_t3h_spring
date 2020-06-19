package application.controller.api;

import application.data.model.Category;
import application.data.model.Product;
import application.data.model.ProductImage;
import application.data.service.ProductImageService;
import application.data.service.ProductService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.ProductDTO;
import application.model.dto.ProductImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(path = "/api/product-image")
public class ProductImageApiController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    private String[] images = {
            "https://salt.tikicdn.com/cache/550x550/ts/product/0a/fb/75/740106b009f436911a8ea4efdf7edadf.jpg",
            "https://salt.tikicdn.com/cache/550x550/media/catalog/product/a/m/american-edition-5-student-book.jpg",
            "https://salt.tikicdn.com/cache/w1200/ts/product/cc/6f/1a/bddcfae10b1ae4877dee0d85d11a325e.jpg",
            "https://salt.tikicdn.com/cache/w1200/ts/product/00/47/df/b02b462394bc3c59e5876ec0d9cb6ae8.jpg",
            "https://salt.tikicdn.com/cache/550x550/ts/product/dd/28/91/4a7bb0e7be810aade0c4ab45427508a4.jpg"
    };


    @Transactional
    @GetMapping("/fake")
    public BaseApiResult fakeProduct() {
        BaseApiResult result = new BaseApiResult();
        try {
            long totalProductImages = productImageService.getTotalProductImages();
            List<Product> productList = productService.getListAllProducts();
            List<ProductImage> productImages = new ArrayList<>();
            Random random = new Random();
            int n = 0;
            for (long i = totalProductImages + 1; i <= totalProductImages + (productList.size() * 3); i++) {
                ProductImage productImg = new ProductImage();
                productImg.setLink(images[random.nextInt(images.length)]);
                productImg.setProduct(productList.get(n));
                productImg.setCreatedDate(new Date());
                productImages.add(productImg);
                if (i % 3 == 0) n++;
            }
            productImageService.addNewListProductImage(productImages);
            result.setSuccess(true);
            result.setMessage("Fake list product success !");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @PostMapping("/create")
    public BaseApiResult createProductImage(@RequestBody ProductImageDTO dto) {
        DataApiResult result = new DataApiResult();
        try {
            ProductImage productImage = new ProductImage();
            productImage.setLink(dto.getLink());
            productImage.setCreatedDate(new Date());
            Product product = new Product();
            product.setId(dto.getProductId());
            productImage.setProduct(product);
            productImageService.addNewProductImage(productImage);
            result.setData(productImage.getId());
            result.setMessage("save product success " + productImage.getId());
            result.setSuccess(true);

        } catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }
    @PostMapping("/update/{productImageId}")
    public BaseApiResult updateProductImage(@PathVariable int productImageId,@RequestBody ProductImageDTO dto) {
        DataApiResult result = new DataApiResult();
        try {

            ProductImage productImage = productImageService.findOne(productImageId);
            if(productImage == null){
                result.setMessage("cannot find id category");
                result.setSuccess(false);
            } else {
                productImage.setLink(dto.getLink());
                productImage.setCreatedDate(productImage.getCreatedDate());
                Product product = new Product();
                product.setId(dto.getProductId());
                productImage.setProduct(product);
                productImageService.addNewProductImage(productImage);
                result.setData(productImage.getId());
                result.setMessage("save product success " + productImage.getId());
                result.setSuccess(true);
            }

        } catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }

}
