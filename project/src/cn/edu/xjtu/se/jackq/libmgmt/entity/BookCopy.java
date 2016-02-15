package cn.edu.xjtu.se.jackq.libmgmt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


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
    @JoinColumn(name = "BookVersionId")
    private BookVersion bookVersion;

    @Column(name = "DateOfRecord")
    private Date dateOfRecord;

    @OneToMany(mappedBy = "bookCopy")
    private List<BookLoan> bookLoans;

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

    public BookVersion getBookVersion() {
        return bookVersion;
    }

    public void setBookVersion(BookVersion bookVersion) {
        this.bookVersion = bookVersion;
    }

    public Date getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(Date dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }

    public List<BookLoan> getBookLoans() {
        return bookLoans;
    }

    public void setBookLoans(List<BookLoan> bookLoans) {
        this.bookLoans = bookLoans;
    }
}
