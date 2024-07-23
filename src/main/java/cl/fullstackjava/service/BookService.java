package cl.fullstackjava.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cl.fullstackjava.model.Book;

@Service
public class BookService {
	private static final Logger LOG = LoggerFactory.getLogger(BookService.class);
	private List<Book> books;
	private int bookIdCounter = 1;
	
	public BookService() {
		books = new ArrayList<Book>();
		books.add(new Book(bookIdCounter++, "El Quijote", "Miguel de Cervantes"));
        books.add(new Book(bookIdCounter++, "Cien Años de Soledad", "Gabriel García Márquez"));
        books.add(new Book(bookIdCounter++, "La Odisea", "Homero"));
	}
	
	public List<Book> getAllBooks() {
		LOG.info("Obteniendo todos los libros");
		return books;
	}
	
	public Book getBookById(long id) {
		LOG.info("Obteniendo el libro con id: {}", id);
		return books.stream()
				.filter(b->Objects.equals(b.getId(), id))
				.findFirst()
				.orElse(null);
	}
	
	public Book addBook(Book book) {
		book.setId(bookIdCounter++);
		books.add(book);
		LOG.info("Añadiendo el libro: {}", book.getTitle());
		return book;
	}
	
	public Book updateBook(Book updatedBook) {
		Book book = getBookById(updatedBook.getId());
		if(book!=null) {
			book.setTitle(updatedBook.getTitle());
			book.setAuthor(updatedBook.getAuthor());
		}
		LOG.info("Actualizando el libro: {}", book.getTitle());
		return book;
	}
	
	public boolean deleteBook(Long id) {
		boolean output = books.removeIf(book->Objects.equals(book.getId(), id));
		if(output) {
			LOG.info("Eliminado el libro id: {}", id);
		}
		return output;
	}

}
