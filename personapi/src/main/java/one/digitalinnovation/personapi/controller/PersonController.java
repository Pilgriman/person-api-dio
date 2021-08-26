package one.digitalinnovation.personapi.controller;



import one.digitalinnovation.personapi.dto.PersonDto;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    @Autowired
    private PersonService personService;


    @PostMapping
    public ResponseEntity<Void> createNewPerson(@Valid @RequestBody PersonDto personDto, UriComponentsBuilder uriComponentsBuilder) {
        Long primaryKey = personService.createNewPerson(personDto);

        UriComponents uriComponents = uriComponentsBuilder.path("/people/{id}").buildAndExpand(primaryKey);

        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons(){
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id){
        return ResponseEntity.ok(personService.getPersonById(id));

    }
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @Valid @RequestBody PersonDto personDto){
        return ResponseEntity.ok(personService.updatePerson(id, personDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") Long id){
        personService.deletePersonById(id);
        return ResponseEntity.ok().build();
    }

}


