package repository;

import model.Author;

import java.util.List;

public interface AuthorRepository {

    List<Author> getAllAuthors();
    void addAuthor(Author author);
    void deleteAuthor(int id);
    void updateAuthor(int id, Author author);
}
