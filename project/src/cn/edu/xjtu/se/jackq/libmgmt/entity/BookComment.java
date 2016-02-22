package cn.edu.xjtu.se.jackq.libmgmt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class BookComment implements Serializable{

    private static final long serialVersionUID = 4241484351518832841L;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "BookId")
    private Book book;

    @Column(name = "DateOfComment")
    private Date dateOfComment;

    @Column(name = "Content")
    private String content;

    @Column(name = "Stars")
    private int stars = 0;
}
