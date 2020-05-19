package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import ru.ancndz.libraryBase.content.entity.Client;

import java.time.LocalDateTime;
import java.util.List;
//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Procedure(procedureName = "unregisterClient")
    int unregisterClient(int id);

    @Procedure(procedureName = "register_client")
    void registerClient(String firstName, String lastName, String email, String status, String hashPin);

}
