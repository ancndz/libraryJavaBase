package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer> {
    List<Library> findAllByIdIsNot(int id);
}

