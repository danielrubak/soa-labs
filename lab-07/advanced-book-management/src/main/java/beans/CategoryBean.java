package beans;

import model.BookCategory;
import repository.BookCategoryRepository;

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
    BookCategoryRepository bookCategoryRepository;

    String name;
    Integer selectedCategoryId;

    public List<BookCategory> getAllCategories() {
        return bookCategoryRepository.getAllCategories();
    }

    public String addCategory() {
        BookCategory category = new BookCategory(this.name);
        bookCategoryRepository.addCategory(category);
        this.setEmptyValues();

        return "/categories/categories";
    }

    public String updateCategory() {
        BookCategory category = new BookCategory(this.name);
        bookCategoryRepository.updateCategory(this.selectedCategoryId, category);
        this.setEmptyValues();

        return "/categories/categories";
    }

    public String deleteCategory() {
        bookCategoryRepository.deleteCategory(this.selectedCategoryId);
        this.setEmptyValues();

        return "/categories/categories";
    }

    public Map<String, Integer> getCategoriesMap() {
        Map<String, Integer> categoriesMap = new LinkedHashMap<>();

        List <BookCategory> categories = bookCategoryRepository.getAllCategories();
        for (BookCategory category : categories) {
            categoriesMap.put(category.getName(), category.getId());
        }

        return categoriesMap;
    }

    public void onCategorySelection (AjaxBehaviorEvent ajaxBehaviorEvent) {
        List<BookCategory> authors = bookCategoryRepository.getAllCategories();

        if ( this.selectedCategoryId == null ) {
            this.setEmptyValues();
        } else {
            for (BookCategory category : authors) {
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

    public String getMessage(int mode) {
        switch (mode) {
            case 1:
                return "New category has been added: '" + getName() + "'";
            case 2:
                return "Category has been updated: '" + getName() + "'";
            case 3:
                return "Category with id '" + getSelectedCategoryId() + "' has been removed";
            default:
                return null;
        }
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
