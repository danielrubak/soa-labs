package api;

import database.BookEntity;
import java.util.List;

public interface DataBaseAPI {
    List<BookEntity> getAllBooks();
    void addBook(BookEntity book);
    void deleteBookById(int toDeleteId);
    void updateBook(int bookID, BookEntity book);
}
