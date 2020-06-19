package application.data.service;

import application.data.model.Category;
import application.data.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void addNewListCategory(List<Category> categories) {
        categoryRepository.save(categories);
    }

    public void addNewCategory(Category category) {
        categoryRepository.save(category);
    }

    public Category findOne(int categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    public boolean updateCategrory(Category category) {
        try {
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public List<Category> getListAllCategories() {
        return categoryRepository.findAll();
    }

    public boolean deleteCategory(int categoryId) {

        try {
            categoryRepository.delete(categoryId);
            return true;
        } catch (Exception e) {
        }
        return false;

    }

    public long getTotalCategories() {
        return categoryRepository.getTotalCategories();

    }


}
