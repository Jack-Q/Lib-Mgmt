package cn.edu.xjtu.se.jackq.libmgmt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Book implements Serializable {
    private static final long serialVersionUID = -2943826404696834910L;
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "BookCode", unique = true, nullable = false)
    private String bookCode;

    @Column(name = "Isbn", nullable = true)
    private String isbn;


    @Column(name = "BookName", nullable = false)
    private String bookName;

    @Column(name = "BookNote")
    private String bookNote;

    @Column(name = "Publisher")
    private String publisher;

    @Column(name = "Author")
    private String author;


    @Column(name = "Description", length = 5000)
    private String description;

    @Column(name = "YearOfPublish")
    private int yearOfPublish;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<BookCopy> bookCopies;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<BookComment> bookComments;

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookNote() {
        return bookNote;
    }

    public void setBookNote(String bookNote) {
        this.bookNote = bookNote;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public Set<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(Set<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public Set<BookComment> getBookComments() {
        return bookComments;
    }

    public void setBookComments(Set<BookComment> bookComments) {
        this.bookComments = bookComments;
    }
}
