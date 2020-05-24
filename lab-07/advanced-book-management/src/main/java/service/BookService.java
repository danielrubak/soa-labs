package service;

import model.Author;
import model.Book;
import model.BookCategory;
import model.Catalog;
import repository.BookRepository;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Stateless
@Local(BookRepository.class)
public class BookService implements BookRepository {
    @PersistenceContext(name = "book-persistence-unit")
    private EntityManager em;

    @Override
    public List<Book> getAllBooks() {
        try {
            Query q = em.createQuery("FROM Book", Book.class);
            List<Book> books = q.getResultList();
            return books;
        }
        catch (Exception e){
            System.err.println("An error occurred during selecting entities: " + e);
        }
        return null;
    }

    @Override
    public void addBook(String title, BigInteger isbn, String authorName, String authorSurname, String category, int quantity) {
        try {
            Book book = new Book(title, isbn);

            // check if author exists in database
            String authorQueryStr = "SELECT a FROM Author a WHERE a.name = :name AND a.surname = :surname";
            Query authorQuery = em.createQuery(authorQueryStr);
            authorQuery.setParameter("name", authorName);
            authorQuery.setParameter("surname", authorSurname);
            List<Author> authors = authorQuery.getResultList();

            //check if book category exists in database
            String categoryQueryStr = "SELECT c FROM BookCategory c WHERE c.name = :name";
            Query categoryQuery = em.createQuery(categoryQueryStr);
            categoryQuery.setParameter("name", category);
            List<BookCategory> categories = categoryQuery.getResultList();

            // if author does not exists then add new one, select existing one otherwise
            if ( authors.isEmpty() ){
                Author newAuthor = new Author(authorName, authorSurname);
                // update books field of author entity
                newAuthor.addBook(book);
                em.persist(newAuthor);
            } else {
                for (Author author : authors) {
                    author.addBook(book);
                }
            }

            // if category does not exists then add new one, select existing one otherwise
            if( categories.isEmpty()){
                BookCategory newCategory = new BookCategory(category);
                // update books field of category entity
                newCategory.addBook(book);
                em.persist(newCategory);
            } else {
                for(BookCategory bookCategory: categories){
                    bookCategory.addBook(book);
                }
            }

            // catalog to book is one to one mapping so new catalog must be created
            Catalog newCatalog = new Catalog(quantity, quantity);
            newCatalog.setBook(book);
            book.setCatalog(newCatalog);
            em.persist(newCatalog);
            em.persist(book);

        } catch ( Exception e ) {
            System.err.println("An error occurred during addition of a book: " + e);
        }
    }

    @Override
    public void updateBook(int id, String title, BigInteger isbn, String authorName, String authorSurname, String category, int quantity) {

    }

    @Override
    public void deleteBook(int id) {
        try {
            Book book = em.find(Book.class, id);

            Author author = book.getAuthor();
            author.removeBook(book);

            BookCategory category = book.getCategory();
            category.removeBook(book);

            Catalog catalog = book.getCatalog();
            em.remove(catalog);

            em.remove(book);

        } catch (Exception e) {
            System.err.println("An error occurred during book deletion. Id: " + id + "\n" + e);
        }
    }
}
