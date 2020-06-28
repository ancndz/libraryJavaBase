package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ancndz.libraryBase.content.entity.LibraryUser;

public interface UserRepository extends JpaRepository<LibraryUser, Integer> {
    LibraryUser getByUserExtras_Id(int id);

    void deleteByUserExtras_Id(int id);

    LibraryUser findByEmail(String email);
}
