package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.LibraryRepository;
import ru.ancndz.libraryBase.content.jobs.Job;
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
        checkEmpty();
        return libraryRepository.findAll();
    }

    private void checkEmpty() {
        if (this.libraryRepository.findAll().isEmpty()) {
            Library library = new Library();
            library.setId(1);
            library.setAddress("none");
            library.setName("none");
            this.libraryRepository.save(library);
        }
    }

    public List<Library> libraryListBusiness(){
        checkEmpty();
        return libraryRepository.findAllByIdIsNot(1);
    }

    public Library get(Integer id) {
        checkEmpty();
        return libraryRepository.getOne(id);
    }

    public void delete(Integer id) {
        libraryRepository.deleteById(id);
    }

}
