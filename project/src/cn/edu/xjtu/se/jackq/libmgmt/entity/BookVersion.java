package cn.edu.xjtu.se.jackq.libmgmt.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class BookVersion implements Serializable {
    private static final long serialVersionUID = -2943826404696834910L;
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NaturalId
    @Column(name = "VersionCode", unique = true, nullable = false)
    private String versionCode;

    @Column(name = "VersionNote")
    private String versionNote;


    @JoinColumn(name = "BookId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Book book;

    @ManyToOne
    @JoinColumn(name="PublisherId")
    private Publisher publisher;

    @Column(name = "Author")
    private String author;

    @Column(name = "Description")
    private String description;

    @Column(name = "YearOfPublish")
    private int yearOfPublish;

    @OneToMany(mappedBy = "bookVersion")
    private List<BookCopy> bookCopies;

    @OneToMany(mappedBy = "bookVersion")
    private List<BookComment> bookComments;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionNote() {
        return versionNote;
    }

    public void setVersionNote(String versionNote) {
        this.versionNote = versionNote;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
