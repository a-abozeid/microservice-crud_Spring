package com.SlicK.basic_crud.Service;

import com.SlicK.basic_crud.Entity.PersonDto;
import com.SlicK.basic_crud.Entity.PersonResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PersonService {
    PersonDto createPerson(PersonDto personDto);
    PersonResponse findAllPersons(int pageNum, int pageSize, String name);
    List<PersonDto> findAll();
    PersonDto findPersonById(int id);
    List<PersonDto> findAllByName(String name);
    PersonDto updatePerson(PersonDto personDto, int id);
    String deletePersonById(int id);
    String deleteAll();
}
