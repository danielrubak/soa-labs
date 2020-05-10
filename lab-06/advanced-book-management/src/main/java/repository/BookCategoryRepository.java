package repository;

import model.BookCategory;

import java.util.List;

public interface BookCategoryRepository {
    List<BookCategory> getAllCategories();
    void addCategory(BookCategory category);
    void updateCategory(int id, BookCategory category);
    void deleteCategory(int id);
}
