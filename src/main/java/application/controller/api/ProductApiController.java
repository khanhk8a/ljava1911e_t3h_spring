package application.controller.api;

import application.data.model.Category;
import application.data.model.Product;
import application.data.service.CategoryService;
import application.data.service.ProductImageService;
import application.data.service.ProductService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.CategoryDTO;
import application.model.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(path = "/api/product")
public class ProductApiController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;
    @Autowired
    CategoryService categoryService;
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
            long totalProducts = productService.getTotalProducts();
            List<Category> categoryList = categoryService.getListAllCategories();
            List<Product> productList = new ArrayList<>();
            Random random = new Random();
            for (long i = totalProducts + 1; i <= totalProducts + 30; i++) {
                Product product = new Product();
                product.setName("Product" + i);
                product.setShortDesc("Product" + i + " short desc");
                product.setMainImage(images[random.nextInt(images.length)]);
                double rangeMin = 4;
                double rangMax = 30;
                double randomPrice = rangeMin+(rangMax-rangeMin)*random.nextDouble();
                product.setPrice(randomPrice);
                product.setCategory(categoryList.get(random.nextInt(categoryList.size())));
                product.setCreatedDate(new Date());
                productList.add(product);
            }
            productService.addNewListProduct(productList);
            result.setSuccess(true);
            result.setMessage("Fake list product success !");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @GetMapping("/detail/{productId}")
    public DataApiResult detailProduct(@PathVariable int productId) {
        DataApiResult result = new DataApiResult();
        try {
            Product productEntity = productService.findOne(productId);
            if (productEntity == null) {
                result.setSuccess(false);
                result.setMessage("Can't find the category");
            } else {
                ProductDTO dto = new ProductDTO();
                dto.setId(productEntity.getId());
                dto.setName(productEntity.getName());
                dto.setShortDesc(productEntity.getShortDesc());
                dto.setCategoryId(productEntity.getCategory().getId());
                dto.setCategoryName(productEntity.getCategory().getName());
                dto.setMainImage(productEntity.getMainImage());
                dto.setPrice(productEntity.getPrice());
                dto.setCreatedDate(productEntity.getCreatedDate());
                result.setSuccess(true);
                result.setData(dto);
                result.setMessage("Success");
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }


        return result;
    }
    @PostMapping("/create")
    public BaseApiResult createProduct(@RequestBody ProductDTO dto) {
        DataApiResult result = new DataApiResult();
        try {
            Product product = new Product();
            product.setName(dto.getName());
            product.setShortDesc(dto.getShortDesc());
            product.setCreatedDate(new Date());
            product.setMainImage(dto.getMainImage());
            product.setPrice(dto.getPrice());
            Category category = new Category();
            category.setId(dto.getCategoryId());
            product.setCategory(category);
            productService.addNewProduct(product);
            result.setData(product.getId());
            result.setMessage("save product success " + product.getId());
            result.setSuccess(true);

        } catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }
    @PostMapping("/update/{productId}")
    public BaseApiResult updateProduct(@PathVariable int productId,@RequestBody ProductDTO dto) {
        DataApiResult result = new DataApiResult();
        try {

            Product product = productService.findOne(productId);
            if(product == null){
                result.setMessage("cannot find id category");
                result.setSuccess(false);
            } else {

                product.setName(dto.getName());
                product.setShortDesc(dto.getShortDesc());
                product.setCreatedDate(product.getCreatedDate());
                product.setMainImage(dto.getMainImage());
                product.setPrice(dto.getPrice());
                Category category = new Category();
                category.setId(dto.getCategoryId());
                product.setCategory(category);
                productService.updateProduct(product);
                result.setData(product.getId());
                result.setMessage("update product success " + product.getId());
                result.setSuccess(true);
            }

        } catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }




}
