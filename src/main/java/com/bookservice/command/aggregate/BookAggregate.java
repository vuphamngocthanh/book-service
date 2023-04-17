package com.bookservice.command.aggregate;

import com.bookservice.command.command.CreateBookCommand;
import com.bookservice.command.command.DeleteBookCommand;
import com.bookservice.command.command.UpdateBookCommand;
import com.bookservice.command.event.BookCreatedEvent;
import com.bookservice.command.event.BookUpdateEvent;
import com.bookservice.command.event.DeleteBookEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;


@Aggregate
@NoArgsConstructor
public class BookAggregate {
	@AggregateIdentifier
	private String bookId;
	private String name;
	private String author;
	private Boolean isReady;

	 @CommandHandler
	 public BookAggregate(CreateBookCommand createBookCommand) {
		 BookCreatedEvent bookCreatedEvent
				 = new BookCreatedEvent();
		 BeanUtils.copyProperties(createBookCommand,bookCreatedEvent);
		 AggregateLifecycle.apply(bookCreatedEvent);
	 }
	 @CommandHandler
	 public void handle(UpdateBookCommand updateBookCommand){
		 BookUpdateEvent bookUpdateEvent
				 = new BookUpdateEvent();
		 BeanUtils.copyProperties(updateBookCommand,bookUpdateEvent);
		 AggregateLifecycle.apply(bookUpdateEvent);
	 }
	 @CommandHandler
	 public void handle(DeleteBookCommand deleteBookCommand){
		 DeleteBookEvent deleteBookEvent
				 = new DeleteBookEvent();
		 BeanUtils.copyProperties(deleteBookCommand,deleteBookEvent);
		 AggregateLifecycle.apply(deleteBookEvent);
	 }
	@EventSourcingHandler
	public void on(BookCreatedEvent event) {
		this.bookId = event.getBookId();
		this.author = event.getAuthor();
		this.isReady = event.getIsReady();
		this.name = event.getName();
	}
	@EventSourcingHandler
	public void on(BookUpdateEvent event){
		 this.bookId = event.getBookId();
		 this.author = event.getAuthor();
		 this.isReady = event.getIsReady();
		 this.name = event.getName();
	}
	@EventSourcingHandler
	public void on(DeleteBookEvent event){
		 this.bookId = event.getBookId();
	}
}
