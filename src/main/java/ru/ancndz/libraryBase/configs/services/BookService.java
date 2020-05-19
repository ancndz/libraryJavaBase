package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.BookRepository;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void save(Book book) {
        bookRepository.save(book);
    }
    public List<Book> booksList() {
        return (List<Book>) bookRepository.findAll();
    }
    public List<Book> booksByLibrary(Integer id) {
        return bookRepository.findAllByLibrary_Id(id);
    }
    public Book get(Integer id) {
        return bookRepository.findById(id).get();
    }
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }
}
