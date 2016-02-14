package cn.edu.xjtu.se.jackq.libmgmt.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Book implements Serializable {
    private static final long serialVersionUID = -2428191795524513404L;

    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NaturalId
    @Column(name = "BookCode", length = 30, unique = true, nullable = false)
    private String bookCode;

    @Column(name = "LoginDate")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date loginDate;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @Column(name = "BookName")
    private String bookName;

    @OneToMany(mappedBy = "book")
    private List<BookVersion> bookVersions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public List<BookVersion> getBookVersions() {
        return bookVersions;
    }

    public void setBookVersions(List<BookVersion> bookVersions) {
        this.bookVersions = bookVersions;
    }
}
