package repository;

import java.util.List;

public interface CategoryRepository {
    List<Category> getAllCategories();
    void addCategory(Category category);
    void updateCategory(int id, Category category);
    void deleteCategory(int id);
}
