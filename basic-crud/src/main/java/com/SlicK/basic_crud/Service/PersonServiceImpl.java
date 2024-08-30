package com.SlicK.basic_crud.Service;

import com.SlicK.basic_crud.Entity.Person;
import com.SlicK.basic_crud.Entity.PersonDto;
import com.SlicK.basic_crud.Entity.PersonResponse;
import com.SlicK.basic_crud.ExceptionHandler.EmailExistsExc;
import com.SlicK.basic_crud.ExceptionHandler.PersonNotFoundExc;
import com.SlicK.basic_crud.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public PersonDto createPerson(PersonDto personDto){
        if(repository.existsByEmail(personDto.getEmail())){
            throw new EmailExistsExc("This Email already exists, plz add another email");
        }
        Person person = mapToPerson(personDto);
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        return mapToDto(repository.save(person));
    }

    @Override
    public PersonResponse findAllPersons(int pageNum, int pageSize, String name) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        Specification<Person> spec = PersonSpecification.nameExistsOrStartsWith(name);
        Page<Person> personPage = repository.findAll(spec, pageable);
        List<PersonDto> personDtos = personPage.stream().map(this::mapToDto).toList();

        PersonResponse response = new PersonResponse();
        response.setData(personDtos);
        response.setPageNum(pageNum);
        response.setPageSize(pageSize);
        response.setTotalPages(personPage.getTotalPages());
        response.setTotalElements((int) personPage.getTotalElements());
        response.setLast(personPage.isLast());
        return response;
    }

    @Override
    public List<PersonDto> findAll() {
        List<Person> personList = repository.findAll();
        return personList.stream().map(this::mapToDto).toList();
    }

    @Override
    public PersonDto findPersonById(int id) {
        Person person = repository.findById(id).
                orElseThrow(() -> new PersonNotFoundExc("No person by this ID!"));

        return mapToDto(person);
    }

    @Override
    public List<PersonDto> findAllByName(String name) {
        List<Person> personList = repository.findAllByName(name)
                .orElseThrow(() -> new PersonNotFoundExc("No person by this name!"));
        return personList.stream().map(this::mapToDto).toList();
    }

    @Override
    public PersonDto updatePerson(PersonDto personDto, int id) {
        Person person = repository.findById(id).
                orElseThrow(() -> new PersonNotFoundExc("No person by this ID!"));

        if(!(person.getEmail().equalsIgnoreCase(personDto.getEmail()))
                && repository.existsByEmail(personDto.getEmail())){
            throw new EmailExistsExc("This Email already exists, plz add another email");
        }

        person.setName(personDto.getName());
        person.setEmail(personDto.getEmail());
        person.setPhoneNum(personDto.getPhoneNum());
        person.setPassword(bCryptPasswordEncoder.encode(personDto.getPassword()));
        return mapToDto(repository.save(person));
    }

    @Override
    public String deletePersonById(int id) {
        repository.findById(id).orElseThrow(() -> new PersonNotFoundExc("No person by this ID!"));
        repository.deleteById(id);
        return "Deleted!";
    }

    @Override
    public String deleteAll() {
        repository.deleteAll();
        return "Deleted all records!";
    }


    //Mapping****************************************************************
    public PersonDto mapToDto(Person person){
        PersonDto dto = new PersonDto();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setEmail(person.getEmail());
        dto.setPhoneNum(person.getPhoneNum());
        dto.setPassword(person.getPassword());
        return dto;
    }

    public Person mapToPerson(PersonDto dto){
        Person person = new Person();
        person.setName(dto.getName());
        person.setEmail(dto.getEmail());
        person.setPhoneNum(dto.getPhoneNum());
        person.setPassword(dto.getPassword());
        return person;
    }
}
