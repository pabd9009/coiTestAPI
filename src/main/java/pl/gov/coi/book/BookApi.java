package pl.gov.coi.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/books", produces = {MediaType.APPLICATION_JSON_VALUE})
public class BookApi {

    private List<Book> bookList;

    public BookApi() {
        this.bookList = new ArrayList<>();
        bookList.add(new Book(1L, "Quo Vadis"));
        bookList.add(new Book(2L, "Ogniem i mieczem"));
        bookList.add(new Book(3L, "Potop"));
        bookList.add(new Book(4L, "Lalka"));
        bookList.add(new Book(5L, "Kamizelka"));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBooks(@PathVariable long isbn) {

        Optional<Book> bookFound = bookList.stream().filter(book -> book.getIsbn() == isbn).findFirst();
        if (bookFound.isPresent()) {
            return new ResponseEntity<>(bookFound.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> getBooks(@PathVariable String title) {

        List<Book> booksFound = bookList.stream().filter(book -> book.getTitle().matches(".*" + title + ".*")).collect(Collectors.toList());

        return new ResponseEntity<>(booksFound, HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity addBooks(@RequestBody Book book) {
//        boolean add = bookList.add(book);
//        if (add) {
//            return new ResponseEntity<>(bookList, HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @PutMapping
//    public ResponseEntity<Book> modBooks(@RequestBody Book newBook) {
//
//        Optional<Book> first = bookList.stream().filter(book -> book.getIsbn() == newBook.getIsbn()).findFirst();
//
//        if (first.isPresent()) {
//            bookList.remove(first.get());
//            bookList.add(newBook);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Book> removeBooks(@PathVariable Long isbn) {
//
//        Optional<Book> first = bookList.stream().filter(book -> book.getIsbn() == isbn).findFirst();
//
//        if (first.isPresent()) {
//            bookList.remove(first.get());
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

}
