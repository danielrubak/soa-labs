package beans;

import repository.CategoryRepository;
import model.Category;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "CategoryBean")
@SessionScoped
public class CategoryBean {
    @EJB
    CategoryRepository categoryRepository;

    String name;
    Integer selectedCategoryId;

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public String addCategory() {
        Category category = new Category(this.name);
        categoryRepository.addCategory(category);
        this.setEmptyValues();

        return "/categories/categories";
    }

    public String updateCategory() {
        Category category = new Category(this.name);
        categoryRepository.updateCategory(this.selectedCategoryId, category);
        this.setEmptyValues();

        return "/categories/categories";
    }

    public String deleteCategory() {
        categoryRepository.deleteCategory(this.selectedCategoryId);
        this.setEmptyValues();

        return "/categories/categories";
    }

    public Map<String, Integer> getCategoriesMap() {
        Map<String, Integer> categoriesMap = new LinkedHashMap<>();

        List <Category> categories = categoryRepository.getAllCategories();
        for (Category category : categories) {
            categoriesMap.put(category.getName(), category.getId());
        }

        return categoriesMap;
    }

    public void onCategorySelection (AjaxBehaviorEvent ajaxBehaviorEvent) {
        List<Category> authors = categoryRepository.getAllCategories();

        if ( this.selectedCategoryId == null ) {
            this.setEmptyValues();
        } else {
            for (Category category : authors) {
                if ( this.selectedCategoryId == category.getId() ) {
                    this.name = category.getName();
                }
            }
        }
    }

    public String onBackButton () {
        this.setEmptyValues();

        return "/categories/categories";
    }

    public void setEmptyValues () {
        this.name = null;
        this.selectedCategoryId = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSelectedCategoryId() {
        return selectedCategoryId;
    }

    public void setSelectedCategoryId(Integer selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }
}
