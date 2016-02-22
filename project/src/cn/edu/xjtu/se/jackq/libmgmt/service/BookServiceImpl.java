package cn.edu.xjtu.se.jackq.libmgmt.service;

import cn.edu.xjtu.se.jackq.libmgmt.dao.BookCopyDao;
import cn.edu.xjtu.se.jackq.libmgmt.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookCopyDao bookCopyDao;

}
