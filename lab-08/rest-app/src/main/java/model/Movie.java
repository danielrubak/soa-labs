package model;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return "Movie id: " + id +
                " title: " + title +
                " uri: " + uri;
    }
}
