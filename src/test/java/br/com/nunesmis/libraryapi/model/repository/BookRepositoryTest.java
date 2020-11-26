package br.com.nunesmis.libraryapi.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.nunesmis.libraryapi.model.entity.Book;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	BookRepository repository;
	
	@Test
	@DisplayName("Deve retornar verdadeiro quando existir um livro na base com o isbn informado")
	public void returnTrueWhenIsbnExists() {
		//cenario
		String isbn = "121514";
		Book book = Book.builder().title("Key Keyblade").author("Misael N.").isbn(isbn).build();
		entityManager.persist(book);
		
		//execucao
		boolean isExists = repository.existsByIsbn(isbn);
		
		//verificacao
		Assertions.assertThat(isExists).isTrue();
	}
	
	@Test
	@DisplayName("Deve retornar falso quando não existir um livro na base com o isbn informado")
	public void returnFalseWhenIsbnDoesntExists() {
		//cenario
		String isbn = "121514";
//		Book book = Book.builder().title("Key Keyblade").author("Misael N.").isbn("121515").build();
//		entityManager.persist(book);
		
		//execucao
		boolean isExists = repository.existsByIsbn(isbn);
		
		//verificacao
		Assertions.assertThat(isExists).isFalse();
	}
}
