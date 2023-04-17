package com.bookservice.command.event;

import com.bookservice.command.data.Book;
import com.bookservice.command.data.BookRepository;
import com.bookservice.exceptions.BookExceptions;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Transient;


@Component
public class BookEventsHandler {
    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreatedEvent event) {
       Book book = new Book();
       BeanUtils.copyProperties(event,book);
       bookRepository.save(book);
    }
    @EventHandler
    public void  on(BookUpdateEvent event) throws BookExceptions {
        try {
            Book book = bookRepository.getById(event.getBookId());
            if (book != null) {
                book.setName(event.getName());
                book.setAuthor(event.getAuthor());
                book.setIsReady(event.getIsReady());
                bookRepository.save(book);
            } else {
                throw new BookExceptions("Save not success");
            }
        }catch (BookExceptions e){
            throw  new BookExceptions("Not have Book");
        }
    }
    @EventHandler
    public void on(DeleteBookEvent event){
        bookRepository.deleteById(event.getBookId());
    }
}
