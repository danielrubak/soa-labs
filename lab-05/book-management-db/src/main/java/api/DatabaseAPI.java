package api;

import database.BookEntity;
import java.util.List;

public interface DatabaseAPI {
    List<BookEntity> getAllBooks();
    void addBook(BookEntity book);
    void deleteBookById(int toDeleteId);
    void updateBook(int bookID, BookEntity book);
}
