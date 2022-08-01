
package com.library.user.libraryuser.service;

import java.util.List;

import com.library.user.libraryuser.dto.BookDto;

public interface BookService {

	List<BookDto> getAllBooks(int pageNo, int pageLimit);

	List<BookDto> getBookByName(String name);

}
