package cn.edu.xjtu.se.jackq.libmgmt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Jack on 2/14/2016.
 */
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
    private List<BookVersion> bookVersions;

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

    public List<BookVersion> getBookVersions() {
        return bookVersions;
    }

    public void setBookVersions(List<BookVersion> bookVersions) {
        this.bookVersions = bookVersions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


