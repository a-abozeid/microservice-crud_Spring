package com.SlicK.basic_crud.Controller;

import com.SlicK.basic_crud.Entity.PersonDto;
import com.SlicK.basic_crud.Entity.PersonResponse;
import com.SlicK.basic_crud.Service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("persons")
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping("/create")
    public ResponseEntity<PersonDto> createPerson(@Valid @RequestBody PersonDto personDto){
        return new ResponseEntity<>(service.createPerson(personDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<PersonResponse> getAllPersons(
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
            @RequestParam(value = "name", required = false) String name
    ){
        return new ResponseEntity<>(service.findAllPersons(pageNum, pageSize, name), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<PersonDto>> getAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findPersonByID(@PathVariable int id){
        return new ResponseEntity<>(service.findPersonById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<PersonDto>> findPersonByName(@PathVariable String name){
        return new ResponseEntity<>(service.findAllByName(name), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(
            @Valid @RequestBody PersonDto pokemonDto, @PathVariable int id){
        return new ResponseEntity<>(service.updatePerson(pokemonDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonByID(@PathVariable int id){
        return new ResponseEntity<>(service.deletePersonById(id), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll(){
        return new ResponseEntity<>(service.deleteAll(), HttpStatus.OK);
    }
}
