package com.example.demo;

public class Example {
	private final String name;
	private final Long timestamp;

	public Example(final String name, final Long timestamp) {
		this.name = name;
		this.timestamp = timestamp;
	}

	public String getName() {
		return this.name;
	}

	public Long getTimestamp() {
		return this.timestamp;
	}
}
