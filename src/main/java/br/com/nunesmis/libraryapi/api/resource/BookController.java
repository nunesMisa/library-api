package br.com.nunesmis.libraryapi.api.resource;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.nunesmis.libraryapi.api.dto.BookDTO;
import br.com.nunesmis.libraryapi.api.exception.ApiErrors;
import br.com.nunesmis.libraryapi.exception.BusinessException;
import br.com.nunesmis.libraryapi.model.entity.Book;
import br.com.nunesmis.libraryapi.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private BookService service;
	private ModelMapper modelMapper;

	public BookController(BookService service, ModelMapper mapper) {
		this.service = service;
		this.modelMapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody @Valid BookDTO dto) {
		Book entity = modelMapper.map(dto, Book.class);

		entity = service.save(entity);
		
		return modelMapper.map(entity, BookDTO.class);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult bindResult = ex.getBindingResult();
		List<ObjectError> allErrors = bindResult.getAllErrors();
		
		return new ApiErrors(bindResult);				
	}
	
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleBusinessException(BusinessException ex) {
		return new ApiErrors(ex);
	}
}
