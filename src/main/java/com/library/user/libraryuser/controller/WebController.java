package com.library.user.libraryuser.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.user.libraryuser.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebController {

	private final BookService bookService;

	@Autowired
	public WebController(BookService bookService) {
		this.bookService = bookService;
	}

	@RequestMapping("/welcomePage")
	public String securedPage(Model model, Principal principal) {
		model.addAttribute("books", bookService.getAllBooks(0, 10));
		return "welcomePage";
	}

	@RequestMapping("/")
	public String index(Model model, Principal principal) {
		return "index";
	}

	@GetMapping(value = "/take/{book_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String takeBook(@PathVariable(value = "book_id", required = true) int bookId, HttpServletRequest request) {

		log.info("BookIssueController : issueBook ID {} ", bookId);
		return "welcomePage";
	}
}
