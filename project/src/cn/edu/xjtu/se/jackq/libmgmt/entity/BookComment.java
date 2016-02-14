package cn.edu.xjtu.se.jackq.libmgmt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jack on 2/14/2016.
 */
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
    @JoinColumn(name = "BookVersionId")
    private BookVersion bookVersion;

    @Column(name = "DateOfComment")
    private Date dateOfComment;

    @Column(name = "Content")
    private String content;

    @Column(name = "Stars")
    private int stars = 0;
}
