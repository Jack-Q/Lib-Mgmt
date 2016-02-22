package cn.edu.xjtu.se.jackq.libmgmt.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Book implements Serializable {
    private static final long serialVersionUID = -2943826404696834910L;
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NaturalId
    @Column(name = "BookCode", unique = true, nullable = false)
    private String bookCode;

    @NaturalId
    @Column(name = "Isbn", unique = true, nullable = true)
    private String isbn;


    @Column(name = "BookName", nullable = false)
    private String bookName;

    @Column(name = "BookNote")
    private String bookNote;

    @ManyToOne
    @JoinColumn(name="PublisherId")
    private Publisher publisher;

    @Column(name = "Author")
    private String author;

    @Column(name = "Description")
    private String description;

    @Column(name = "YearOfPublish")
    private int yearOfPublish;

    @OneToMany(mappedBy = "book")
    private List<BookCopy> bookCopies;

    @OneToMany(mappedBy = "book")
    private List<BookComment> bookComments;

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


    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
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

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public List<BookComment> getBookComments() {
        return bookComments;
    }

    public void setBookComments(List<BookComment> bookComments) {
        this.bookComments = bookComments;
    }
}
