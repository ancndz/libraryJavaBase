package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.BookRepository;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void save(Book book) {
        Book bookInStore = this.bookRepository.findByNameAndAuthorAndPubYearAndLibrary_Id(
                book.getName(), book.getAuthor(), book.getPubYear(), book.getLibrary().getId());
        if (bookInStore != null) {
            if (bookInStore.getId() != book.getId()) {
                bookInStore.setCount(bookInStore.getCount() + book.getCount());
            }
            bookRepository.save(bookInStore);
        } else {
            bookRepository.save(book);
        }
    }

    public List<Book> findByCriteria(String name, String author, int pubYear, String genre, int lib_id){
        return this.bookRepository.findAll((Specification<Book>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(name != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"), "%"+name+"%")));
            }
            if(author != null){
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("author"), author)));
            }
            if(pubYear != 0){
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("pub_year"), pubYear)));
            }
            if(genre != null){
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("genre"), genre)));
            }
            if(lib_id != 0){
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("library_id"), lib_id)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }

    public List<Book> booksByAuthor(String author) {
        return this.bookRepository.findAllByAuthor(author);
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
