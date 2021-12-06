package com.mes.test;

import com.mes.pojo.Book;
import com.mes.service.BookService;
import com.mes.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

public class BookServiceTest {
    private BookService bookService = new BookServiceImpl();
    @Test
    public void addBook() {
        bookService.addBook(new Book(null,"name",new BigDecimal(456),"456",465318,0,null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(24);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(24,"名字",new BigDecimal(456),"456",465318,0,null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(24));
    }

    @Test
    public void queryBooks() {
        for (Book queryBook : bookService.queryBooks()) {
            System.out.println(queryBook);
        }
    }
    @Test
    public void page(){
        System.out.println(bookService.page(1,4));
    }
    @Test
    public void pageByPrice(){
        System.out.println(bookService.pageByPrice(1,4,10,50));
    }

}