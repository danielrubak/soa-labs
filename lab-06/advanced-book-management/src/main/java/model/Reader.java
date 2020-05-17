package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "library", name = "reader")
public class Reader {
    private int id;
    private String name;
    private String surname;
    private List<Borrowing> borrowing;

    public Reader() {
    }

    public Reader(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "reader")
    public List<Borrowing> getBorrowing() {
        return borrowing;
    }
    public void setBorrowing(List<Borrowing> borrowing) {
        this.borrowing = borrowing;
    }

    @Column(name = "surname", nullable = false)
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "'" + this.name + " " + this.surname + "'";
    }
}
