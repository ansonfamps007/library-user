package com.library.user.libraryuser.dto;

import lombok.Data;

@Data
public class BookDto {

	private Integer id;

	private String title;

	private String description;

	private String author;

	private String language;

	private String category;
}
