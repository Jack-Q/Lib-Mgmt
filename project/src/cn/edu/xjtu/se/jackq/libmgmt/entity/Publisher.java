package cn.edu.xjtu.se.jackq.libmgmt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
public class Publisher implements Serializable {
    private static final long serialVersionUID = -6968006543405937832L;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "publisher")
    private List<Book> bookVersions;

    @Column(name = "Description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBookVersions() {
        return bookVersions;
    }

    public void setBookVersions(List<Book> bookVersions) {
        this.bookVersions = bookVersions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


