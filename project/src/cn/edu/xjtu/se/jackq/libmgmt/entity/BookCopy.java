package cn.edu.xjtu.se.jackq.libmgmt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
public class BookCopy implements Serializable {
    private static final long serialVersionUID = -4668523476390472345L;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "Loanable")
    private boolean loanable;

    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private BookCopyStatus status;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "BookId")
    private Book book;


    @Column(name = "DateOfRecord")
    private Date dateOfRecord;

    @OneToMany(mappedBy = "bookCopy", fetch = FetchType.EAGER)
    private Set<BookLoan> bookLoans;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLoanable() {
        return loanable;
    }

    public void setLoanable(boolean loanable) {
        this.loanable = loanable;
    }

    public BookCopyStatus getStatus() {
        return status;
    }

    public void setStatus(BookCopyStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(Date dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }

    public Set<BookLoan> getBookLoans() {
        return bookLoans;
    }

    public void setBookLoans(Set<BookLoan> bookLoans) {
        this.bookLoans = bookLoans;
    }
}
