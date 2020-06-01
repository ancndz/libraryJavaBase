package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Integer>, JpaSpecificationExecutor<Book> {
    List<Book> findAllByLibrary_Id(int id);

    Book findByNameAndAuthorAndPubYear(String name, String author, int pubYear);

    List<Book> findAllByAuthor(String author);

    Book findByNameAndAuthorAndPubYearAndLibrary_Id(String name, String author, int pubYear, int libraryId);
}
