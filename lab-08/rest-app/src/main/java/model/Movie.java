package model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "rest", name = "movies")
public class Movie {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "uri", nullable = false)
    private String uri;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favouriteMovies")
    @JsonBackReference
    private List<User> users = new ArrayList<>();

    public Movie() {
    }

    public Movie(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Movie id: " + id +
                " title: " + title +
                " uri: " + uri;
    }
}
