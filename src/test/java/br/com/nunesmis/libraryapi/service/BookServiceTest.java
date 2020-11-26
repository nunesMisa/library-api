package br.com.nunesmis.libraryapi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.nunesmis.libraryapi.api.dto.BookDTO;
import br.com.nunesmis.libraryapi.exception.BusinessException;
import br.com.nunesmis.libraryapi.model.entity.Book;
import br.com.nunesmis.libraryapi.model.repository.BookRepository;
import br.com.nunesmis.libraryapi.service.impl.BookServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

	BookService service;

	@MockBean
	BookRepository repository;

	@BeforeEach
	public void setUp() {
		this.service = new BookServiceImpl(repository);
	}

	@Test
	@DisplayName("Deve salvar um livro")
	public void saveBookTest() {
		// cenario
		Book book = createValidBook();
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
		Mockito.when(repository.save(book))
				.thenReturn(Book.builder().id(11).title("Key Keyblade").author("Misael N.").isbn("141512").build());

		// execucao
		Book savadBook = service.save(book);

		// verificação
		Assertions.assertThat(savadBook.getId()).isNotNull();
		Assertions.assertThat(savadBook.getTitle()).isEqualTo("Key Keyblade");
		Assertions.assertThat(savadBook.getAuthor()).isEqualTo("Misael N.");
		Assertions.assertThat(savadBook.getIsbn()).isEqualTo("141512");
	}
	
	@Test
	@DisplayName("Deve lançar erro de negócio ao tentar salvar um livro com isbn duplicado")
	public void shouldNotSaveABookWithDuplicatedISBN() {
		//cenário
		Book book = createValidBook();
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);
		
		
		//execucao
		String msgError = "Isbn já cadastrado.";
		Throwable exception = Assertions.catchThrowable( () -> service.save(book));
		
		//verificacoes
		Assertions.assertThat(exception)
			.isInstanceOf(BusinessException.class)
			.hasMessage(msgError);
		
		Mockito.verify(repository, Mockito.never()).save(book);
	}

	private Book createValidBook() {
		return Book.builder().id(101).title("Key Keyblade").author("Misael N.").isbn("121514").build();
	}
}
