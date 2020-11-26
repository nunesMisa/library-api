package br.com.nunesmis.libraryapi.service.impl;

import br.com.nunesmis.libraryapi.exception.BusinessException;
import br.com.nunesmis.libraryapi.model.entity.Book;
import br.com.nunesmis.libraryapi.model.repository.BookRepository;
import br.com.nunesmis.libraryapi.service.BookService;

public class BookServiceImpl implements BookService {

	private BookRepository repository;
	
	public BookServiceImpl(BookRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Book save(Book book) {
		if(repository.existsByIsbn(book.getIsbn())) {
			throw new BusinessException("Isbn já cadastrado.");
		}
		return repository.save(book);
	}

}
