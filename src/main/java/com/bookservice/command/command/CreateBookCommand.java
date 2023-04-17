package com.bookservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookCommand {
	@TargetAggregateIdentifier
	private String bookId;
	private String name;
	private String author;
	private Boolean isReady;
}
