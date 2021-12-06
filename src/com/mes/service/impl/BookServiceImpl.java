package com.mes.service.impl;

import com.mes.dao.BookDao;
import com.mes.dao.impl.BookDaoImpl;
import com.mes.pojo.Book;
import com.mes.pojo.Page;
import com.mes.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        // 返回page对象 创建page对象
        Page<Book> page = new Page<Book>();
        // 设置五个属性的值

        //  设置每页显示数量
        page.setPageSize(pageSize);
        // 设置总记录数
            // 先获得总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCount();

        page.setPageTotalCount(pageTotalCount);
        // 设置总页码
        Integer pageTotal = pageTotalCount / pageSize;
        if(pageTotalCount % pageSize > 0){
            pageTotal++;
        }
        page.setPageTotal(pageTotal);
        /*
        数据边界的有效检查
         */
        if(pageNo<1){
            pageNo = 1;
        }else if (pageNo > pageTotal){
            pageNo = pageTotal;
        }
        // 设置当前页码
        page.setPageNo(pageNo);
        // 设置当前页数据
        int begin = (page.getPageNo()-1)*pageSize;
        List<Book> items  = bookDao.queryForPageItems(begin,pageSize);
        page.setItems(items);
        return page;
    }
    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max) {
        // 返回page对象 创建page对象
        Page<Book> page = new Page<Book>();
        // 设置五个属性的值

        //  设置每页显示数量
        page.setPageSize(pageSize);
        // 设置总记录数
        // 先获得总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCountByPrice(min,max);

        page.setPageTotalCount(pageTotalCount);
        // 设置总页码
        Integer pageTotal = pageTotalCount / pageSize;
        if(pageTotalCount % pageSize > 0){
            pageTotal++;
        }
        page.setPageTotal(pageTotal);
        /*
        数据边界的有效检查
         */
        if(pageNo<1){
            pageNo = 1;
        }else if (pageNo > pageTotal){
            pageNo = pageTotal;
        }
        // 设置当前页码
        page.setPageNo(pageNo);
        // 设置当前页数据
        int begin = (page.getPageNo()-1)*pageSize;
        List<Book> items  = bookDao.queryForPageItemsByPrice(begin,pageSize,min,max);
        page.setItems(items);
        return page;
    }
}
