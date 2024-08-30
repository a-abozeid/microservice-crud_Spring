package com.SlicK.basic_crud.Repository;

import com.SlicK.basic_crud.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person> {
    Optional<List<Person>> findAllByName(String name);
    Person findByEmail(String email);
    boolean existsByEmail(String email);
}
