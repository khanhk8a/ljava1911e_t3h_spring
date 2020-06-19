package application.controller.api;

import application.data.model.Category;
import application.data.service.CategoryService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryApiController {

    @Autowired
    private CategoryService categoryService;

    @Transactional
    @GetMapping("/fake")
    public BaseApiResult fakeCategory() {
        BaseApiResult result = new BaseApiResult();
        try {
            long totalCategories = categoryService.getTotalCategories();
            List<Category> categoryList = new ArrayList<>();
            for (long i = totalCategories + 1; i <= totalCategories + 5; i++) {
                Category category = new Category();
                category.setName("Category" + i);
                category.setShortDesc("Category" + i + " short desc");
                category.setCreatedDate(new Date());
                categoryList.add(category);
            }
            categoryService.addNewListCategory(categoryList);
            result.setSuccess(true);
            result.setMessage("Fake list category success !");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @PostMapping("/create")
    public BaseApiResult createCategory(@RequestBody CategoryDTO dto) {
        DataApiResult result = new DataApiResult();
        try {
            Category category = new Category();
            category.setName(dto.getName());
            category.setShortDesc(dto.getShortDesc());
            category.setCreatedDate(new Date());
            categoryService.addNewCategory(category);
            result.setData(category.getId());
            result.setMessage("save category success " + category.getId());
            result.setSuccess(true);

        } catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }
    @PostMapping("/update/{categoryId}")
    public BaseApiResult updateCategory(@PathVariable int categoryId,@RequestBody CategoryDTO dto) {
        DataApiResult result = new DataApiResult();
        try {

            Category category = categoryService.findOne(categoryId);
            if(category == null){
                result.setMessage("cannot find id category");
                result.setSuccess(false);
            } else {
                category.setName(dto.getName());
                category.setShortDesc(dto.getShortDesc());
                category.setCreatedDate(category.getCreatedDate());
                categoryService.updateCategrory(category);
                result.setData(category.getId());
                result.setMessage("save category success " + category.getId());
                result.setSuccess(true);
            }

        } catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }

    @GetMapping("/detail/{categoryId}")
    public DataApiResult detailCategory(@PathVariable int categoryId) {
        DataApiResult result = new DataApiResult();
        try {
            Category categoryEntity = categoryService.findOne(categoryId);
            if (categoryEntity == null) {
                result.setSuccess(false);
                result.setMessage("Can't find the category");
            } else {
                CategoryDTO dto = new CategoryDTO();
                dto.setId(categoryEntity.getId());
                dto.setName(categoryEntity.getName());
                dto.setShortDesc(categoryEntity.getShortDesc());
                dto.setCreatedDate(categoryEntity.getCreatedDate());
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
}
