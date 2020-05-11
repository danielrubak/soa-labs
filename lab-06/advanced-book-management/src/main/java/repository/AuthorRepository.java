package repository;

import model.Author;

import java.util.List;

public interface AuthorRepository {

    List<Author> getAllAuthors();
    void addAuthor(Author author);
    void addAuthor(String name, String surname);
    List<Author> findByNameAndSurname(String name, String surname);
    void deleteAuthor(int id);
    void updateAuthor(int id, Author author);
}
