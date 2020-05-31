package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ancndz.libraryBase.content.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User getByUserExtras_Id(int id);
    void deleteByUserExtras_Id(int id);
    User findByEmail(String email);
}
