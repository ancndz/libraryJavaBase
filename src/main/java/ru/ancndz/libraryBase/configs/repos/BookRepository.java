package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByLibrary_Id(int id);
    Book findByNameAndAuthorAndPubYear(String name, String author, int pubYear);

    Book findByNameAndAuthorAndPubYearAndLibrary_Id(String name, String author, int pubYear, int libraryId);
}
