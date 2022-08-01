
package com.library.user.libraryuser.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.user.libraryuser.dto.BookDto;
import com.library.user.libraryuser.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

	private static HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(20)).build();

	@Override
	public List<BookDto> getAllBooks(int pageNo, int pageLimit) {
		try {

			StringBuilder uri = new StringBuilder("http://localhost:9090/api/book/getAll?").append("pageNo=")
					.append(pageNo).append("&pageLimit=").append(pageLimit);
			return bookDtoList(client, uri);
		} catch (URISyntaxException | IOException | InterruptedException ex) {
			log.debug("getAllBooks, Exception - " + ex.getMessage() + " - "
					+ (null != ex.getCause() ? ex.getCause().getCause() : ex));
			throw new ValidationException("getAllBooks Api call failed !");
		}
	}

	@Override
	public List<BookDto> getBookByName(String name) {
		try {

			StringBuilder uri = new StringBuilder("http://localhost:9090/api/book/get/").append(name);
			return bookDtoList(client, uri);
		} catch (URISyntaxException | IOException | InterruptedException ex) {
			log.debug("getBookByName, Exception - " + ex.getMessage() + " - "
					+ (null != ex.getCause() ? ex.getCause().getCause() : ex));
			throw new ValidationException("getBookByName Api call failed !");
		}
	}

	private List<BookDto> bookDtoList(HttpClient client, StringBuilder uri) throws URISyntaxException, IOException,
			InterruptedException, JsonProcessingException, JsonMappingException {
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(uri.toString())).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		ObjectMapper mapper = new ObjectMapper();
		List<BookDto> bookList = mapper.readValue(response.body(),
				mapper.getTypeFactory().constructCollectionType(List.class, BookDto.class));

		return bookList;
	}
}
