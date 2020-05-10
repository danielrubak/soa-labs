package beans;

import repository.AuthorRepository;
import model.Author;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "AuthorBean")
@SessionScoped
public class AuthorBean {
    @EJB
    AuthorRepository authorRepository;

    String name;
    String surname;

    Integer selectedAuthorId;

    public List<Author> getAllAuthors() {
        return authorRepository.getAllAuthors();
    }

    public String addAuthor() {
        Author author = new Author(this.name, this.surname);
        authorRepository.addAuthor(author);
        this.setEmptyValues();

        return "/authors/authors";
    }

    public String updateAuthor() {
        Author author = new Author(this.name, this.surname);
        authorRepository.updateAuthor(this.selectedAuthorId, author);
        this.setEmptyValues();

        return "/authors/authors";
    }

    public String deleteAuthor() {
        authorRepository.deleteAuthor(this.selectedAuthorId);
        this.setEmptyValues();

        return "/authors/authors";
    }

    public Map<String, Integer> getAuthorsMap() {
        Map<String, Integer> authorsMap = new LinkedHashMap<>();

        String label = "";
        List <Author> authors = authorRepository.getAllAuthors();
        for (Author author : authors) {
            label = author.getName() + " " + author.getSurname();
            authorsMap.put(label, author.getId());
        }

        return authorsMap;
    }

    public void onAuthorSelection (AjaxBehaviorEvent ajaxBehaviorEvent) {
        List<Author> authors = authorRepository.getAllAuthors();

        if ( this.selectedAuthorId == null ) {
            this.setEmptyValues();
        } else {
            for (Author author : authors) {
                if ( this.selectedAuthorId == author.getId() ) {
                    this.name = author.getName();
                    this.surname = author.getSurname();
                }
            }
        }
    }

    public String onBackButton () {
        this.setEmptyValues();

        return "/authors/authors";
    }

    public void setEmptyValues () {
        this.name = null;
        this.surname = null;
        this.selectedAuthorId = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getSelectedAuthorId() {
        return selectedAuthorId;
    }

    public void setSelectedAuthorId(Integer selectedAuthorId) {
        this.selectedAuthorId = selectedAuthorId;
    }
}
