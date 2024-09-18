package com.SlicK.basic_crud;

import com.SlicK.basic_crud.Entity.Person;
import com.SlicK.basic_crud.Entity.PersonDto;
import com.SlicK.basic_crud.Entity.PersonResponse;
import com.SlicK.basic_crud.ExceptionHandler.EmailExistsExc;
import com.SlicK.basic_crud.ExceptionHandler.PersonNotFoundExc;
import com.SlicK.basic_crud.Repository.PersonRepository;
import com.SlicK.basic_crud.Service.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private PersonDto personDto;
    private Person person;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        personDto = new PersonDto();
        personDto.setEmail("test@testing.com");
        personDto.setPassword("password");
        personDto.setName("test");
        personDto.setPhoneNum("12345678910");

        person = new Person();
        person.setId(1);
        person.setEmail("test@testing.com");
        person.setPassword("encodedPassword");
        person.setName("test");
        person.setPhoneNum("12345678910");
    }

    // create*******************************************************************************************
    @Test
    void testCreatePerson_Success() {
        when(personRepository.existsByEmail(personDto.getEmail())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(personDto.getPassword())).thenReturn("encodedPassword");
        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDto result = personService.createPerson(personDto);

        assertEquals(1, result.getId());
        assertEquals(personDto.getName(), result.getName());
        assertEquals(personDto.getEmail(), result.getEmail());
        assertEquals(personDto.getPhoneNum(), result.getPhoneNum());
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    void testCreatePerson_EmailExistsException() {
        when(personRepository.existsByEmail(personDto.getEmail())).thenReturn(true);

        EmailExistsExc thrown = assertThrows(EmailExistsExc.class, () -> {
            personService.createPerson(personDto);
        });
        assertEquals("This Email already exists, plz add another email", thrown.getMessage());
    }

    // findAllPersons*******************************************************************************************
    @Test
    void testFindAllPersons_Success() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Person> personList = List.of(person);
        Page<Person> personPage = mock(Page.class);
        when(personPage.getContent()).thenReturn(personList);
        when(personPage.getTotalPages()).thenReturn(1);
        when(personPage.getTotalElements()).thenReturn(1L);
        when(personPage.isLast()).thenReturn(true);
        when(personRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(personPage);

        PersonResponse response = personService.findAllPersons(0, 5, "test");

        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertEquals(0, response.getPageNum());
        assertEquals(5, response.getPageSize());
        assertTrue(personPage.isLast());
    }

    @Test
    void testFindAllPersons_EmptyResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Person> personPage = mock(Page.class);
        when(personPage.getContent()).thenReturn(List.of());
        when(personPage.getTotalPages()).thenReturn(0);
        when(personPage.getTotalElements()).thenReturn(0L);
        when(personPage.isLast()).thenReturn(true);
        when(personRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(personPage);

        PersonResponse response = personService.findAllPersons(0, 10, "non-existing");

        assertEquals(0, response.getTotalElements());
        assertEquals(0, response.getTotalPages());
        assertEquals(0, response.getData().size());
    }

    // findAll*******************************************************************************************
    @Test
    void testFindAll_Success() {
        when(personRepository.findAll()).thenReturn(List.of(person));

        List<PersonDto> personDtos = personService.findAll();

        assertEquals(1, personDtos.size());
        assertEquals(person.getEmail(), personDtos.get(0).getEmail());
        assertEquals(1, personDtos.size());
    }

    @Test
    void testFindAll_EmptyList() {
        when(personRepository.findAll()).thenReturn(List.of());

        List<PersonDto> personDtos = personService.findAll();

        assertEquals(0, personDtos.size());
    }

    // findPersonById*******************************************************************************************
    @Test
    void testFindPersonById_Success() {
        when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person));

        PersonDto personDto = personService.findPersonById(1);

        assertEquals(person.getId(), personDto.getId());
        assertEquals(person.getEmail(), personDto.getEmail());
    }

    @Test
    void testFindPersonById_PersonNotFound() {
        when(personRepository.findById(1)).thenReturn(java.util.Optional.empty());

        PersonNotFoundExc thrown = assertThrows(PersonNotFoundExc.class, () -> {
            personService.findPersonById(1);
        });

        assertEquals("No person by this ID!", thrown.getMessage());
    }

    // findAllByName*******************************************************************************************
    @Test
    void testFindAllByName_Success() {
        when(personRepository.findAllByName("test")).thenReturn(java.util.Optional.of(List.of(person)));

        List<PersonDto> personDtos = personService.findAllByName("test");

        assertEquals(1, personDtos.size());
        assertEquals(person.getName(), personDtos.get(0).getName());
    }

    @Test
    void testFindAllByName_PersonNotFound() {
        when(personRepository.findAllByName("non-existing")).thenReturn(java.util.Optional.empty());

        PersonNotFoundExc thrown = assertThrows(PersonNotFoundExc.class, () -> {
            personService.findAllByName("non-existing");
        });

        assertEquals("No person by this name!", thrown.getMessage());
    }

    // updatePerson*******************************************************************************************
    @Test
    void testUpdatePerson_Success() {
        when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person));
        when(bCryptPasswordEncoder.encode(personDto.getPassword())).thenReturn("newEncodedPassword");
        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDto updatedPerson = personService.updatePerson(personDto, 1);

        assertEquals(1, updatedPerson.getId());
        assertEquals(personDto.getEmail(), updatedPerson.getEmail());
        assertEquals("newEncodedPassword", updatedPerson.getPassword());
    }

    @Test
    void testUpdatePerson_EmailExistsException() {
        when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person));
        when(personRepository.existsByEmail("newemail@test.com")).thenReturn(true);

        personDto.setEmail("newemail@test.com");

        EmailExistsExc thrown = assertThrows(EmailExistsExc.class, () -> {
            personService.updatePerson(personDto, 1);
        });

        assertEquals("This Email already exists, plz add another email", thrown.getMessage());
    }

    @Test
    void testUpdatePerson_PersonNotFound() {
        when(personRepository.findById(1)).thenReturn(java.util.Optional.empty());

        PersonNotFoundExc thrown = assertThrows(PersonNotFoundExc.class, () -> {
            personService.updatePerson(personDto, 1);
        });

        assertEquals("No person by this ID!", thrown.getMessage());
    }

    // deletePersonById*******************************************************************************************
    @Test
    void testDeletePersonById_Success() {
        when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person));
        doNothing().when(personRepository).deleteById(1);

        String result = personService.deletePersonById(1);

        assertEquals("Deleted!", result);
    }

    @Test
    void testDeletePersonById_PersonNotFound() {
        when(personRepository.findById(1)).thenReturn(java.util.Optional.empty());

        PersonNotFoundExc thrown = assertThrows(PersonNotFoundExc.class, () -> {
            personService.deletePersonById(1);
        });

        assertEquals("No person by this ID!", thrown.getMessage());
    }

    // deleteAll*******************************************************************************************
    @Test
    void testDeleteAll_Success() {
        doNothing().when(personRepository).deleteAll();

        String result = personService.deleteAll();

        assertEquals("Deleted all records!", result);
    }
}