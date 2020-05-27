package repository;

import model.Book;

import java.math.BigInteger;
import java.util.List;

public interface BookRepository {

    List<Book> getAllBooks();
    Book getBookById(int id);
    void addBook(String title, BigInteger isbn, String authorName, String authorSurname, String category, int quantity);
    void updateBook(int id, String title, BigInteger isbn, String authorName, String authorSurname, String category,
                        int quantity);
    void deleteBook(int id);
}
