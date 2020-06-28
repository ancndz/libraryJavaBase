package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ancndz.libraryBase.content.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
}
