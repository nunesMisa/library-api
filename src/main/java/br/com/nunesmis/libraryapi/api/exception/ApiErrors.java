package br.com.nunesmis.libraryapi.api.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;

import br.com.nunesmis.libraryapi.exception.BusinessException;
import lombok.Getter;

@Getter
public class ApiErrors {
	
	private List<String> errors;
	
	public ApiErrors(BindingResult bindingResult) {
		this.errors = new ArrayList<>();
		bindingResult.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
	}

	public ApiErrors(BusinessException ex) {
		this.errors = Arrays.asList(ex.getMessage());
	}
}
