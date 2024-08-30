package com.SlicK.basic_crud.Service;

import com.SlicK.basic_crud.Entity.Person;
import org.springframework.data.jpa.domain.Specification;

public class PersonSpecification {
    public static Specification<Person> nameStartsWith(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), name + "%");
    }

    public static Specification<Person> nameExistsOrStartsWith(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name != null && !name.isEmpty()) {
                return criteriaBuilder.like(root.get("name"), name + "%");
            }
            return criteriaBuilder.conjunction();
        };
    }
}
