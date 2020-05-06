package api;

import model.Book;
import java.util.List;

public interface BookAPI {
    List<Book> getAllBooks();
    void addBook(Book book);
    void deleteBookById(int toDeleteId);
    void updateBook(int bookID, Book book);
}
