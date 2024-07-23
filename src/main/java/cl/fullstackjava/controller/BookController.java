package cl.fullstackjava.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.fullstackjava.model.Book;
import cl.fullstackjava.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
	private static final Logger LOG = LoggerFactory.getLogger(BookController.class);
	private final BookService bookService;

	
	// Inyeccion de dependencias a traves del constructor
	@Autowired
	public BookController(BookService bookService) {
		this.bookService=bookService;
	}
	
	// Obtener todos los libros
	@GetMapping
	public String getAllBooks(Model model) {
		List<Book> books = bookService.getAllBooks();
		model.addAttribute("books",books);
		model.addAttribute("book", new Book());
		model.addAttribute("isNew", true);
		LOG.info("Listando todos los libros");
		return "index";
	}
	
	// Obtener todos los libros + libro editar
	@GetMapping("/{id}")
	public String editBook(@PathVariable Long id, Model model) {
		List<Book> books = bookService.getAllBooks();
		Book book = bookService.getBookById(id);
		model.addAttribute("books",books);
		model.addAttribute("book",book);
		model.addAttribute("isNew", false);
		LOG.info("Mostrando libro con id: {}", id);
		return "index";
	}
	
	// Guardar cambios de libro o nuevo libro
	@PostMapping("/save")
	public String saveBook(@ModelAttribute Book book) {
		if (Objects.equals(book.getId(), null) || book.getId() == 0) {
			LOG.info("Creando un nuevo libro"); 
				bookService.addBook(book);
		} else {
			LOG.info("Actualizando libro existente");
			bookService.updateBook(book);
		}
		return "redirect:/books";		
	}
	
	// Borrar un libro
	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		LOG.info("Eliminado el libro con id: {}", id);
		return "redirect:/books"; 
	}
	
}
