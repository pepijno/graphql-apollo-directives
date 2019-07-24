package com.example.demo;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Query {

	private String string = "Hello World!";

	@GraphQLQuery
	public String getString() {
		return this.string;
	}

	@GraphQLQuery
	public Example getExample() {
		return new Example(this.string, new Date().getTime());
	}

	@GraphQLMutation
	public String changeString(final String input) {
		this.string = input;
		return this.string;
	}
}
