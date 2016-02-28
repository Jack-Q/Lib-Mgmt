package cn.edu.xjtu.se.jackq.libmgmt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class BookLoan implements Serializable {
    private static final long serialVersionUID = -2652971802397360982L;
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @Column(name = "DateOfBorrowing")
    private Date dateOfBorrowing;

    @ManyToOne
    @JoinColumn(name = "BookCopyId")
    private BookCopy bookCopy;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    @Column(name = "LoanPeriod")
    private int loanPeriod;

    @Column(name = "DeadlineOfReturning")
    private Date deadlineOfReturning;

    @Column(name = "DateOfReturning")
    private Date dateOfReturning;

    @Column(name = "FinedAmount")
    private double finedAmount;

    @Column(name = "Finished")
    private boolean finished;

    @Column(name = "Note")
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateOfBorrowing() {
        return dateOfBorrowing;
    }

    public void setDateOfBorrowing(Date dateOfBorrowing) {
        this.dateOfBorrowing = dateOfBorrowing;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public Date getDeadlineOfReturning() {
        return deadlineOfReturning;
    }

    public void setDeadlineOfReturning(Date deadlineOfReturning) {
        this.deadlineOfReturning = deadlineOfReturning;
    }

    public Date getDateOfReturning() {
        return dateOfReturning;
    }

    public void setDateOfReturning(Date dateOfReturning) {
        this.dateOfReturning = dateOfReturning;
    }

    public double getFinedAmount() {
        return finedAmount;
    }

    public void setFinedAmount(double finedAmount) {
        this.finedAmount = finedAmount;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public double calcLoanFinedAmount(Date returnDate) {
        if (returnDate == null) returnDate = new Date();
        return getDeadlineOfReturning().before(returnDate) ? 0.2 * (returnDate.getTime() - getDeadlineOfReturning().getTime()) / 86400000 : 0;
    }
}
