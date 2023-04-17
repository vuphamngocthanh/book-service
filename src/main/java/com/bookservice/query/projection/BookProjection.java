package com.bookservice.query.projection;

import com.bookservice.command.data.Book;
import com.bookservice.command.data.BookRepository;
import com.bookservice.exceptions.BookExceptions;
import com.bookservice.query.model.BookResponseModel;
import com.bookservice.query.queries.GetAllBookQuery;
import com.bookservice.query.queries.GetBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public BookResponseModel handle(GetBookQuery getBookQuery) throws BookExceptions{
        BookResponseModel bookResponseModel = new BookResponseModel();
        try {
            Book book = bookRepository.getById(getBookQuery.getBookId());
            BeanUtils.copyProperties(book,bookResponseModel);
        }catch (NullPointerException e){
            throw new BookExceptions("Don't have book");
        }
        return bookResponseModel;
    }
    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery getAllBookQuery){
        List<Book> bookList = bookRepository.findAll();
        List<BookResponseModel> bookResponseModelList = new ArrayList<>();
        bookList.forEach(book -> {
            BookResponseModel bookResponseModel = new BookResponseModel();
            BeanUtils.copyProperties(book,bookResponseModel);
            bookResponseModelList.add(bookResponseModel);
        });
        return bookResponseModelList;
    }
}
