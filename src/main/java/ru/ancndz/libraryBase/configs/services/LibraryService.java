package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.LibraryRepository;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;

import java.util.List;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public void save(Library library) {
        libraryRepository.save(library);
    }

    public List<Library> libraryList() {
        return (List<Library>) libraryRepository.findAll();
    }

    public Library get(Integer id) {
        return libraryRepository.findById(id).get();
    }

    public void delete(Integer id) {
        libraryRepository.deleteById(id);
    }

}
