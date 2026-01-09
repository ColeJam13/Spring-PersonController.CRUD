package io.zipcoder.crudapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {
    
    @Autowired
    private PersonRepository repository;

    @PostMapping("/people")
    public ResponseEntity<Person> createPerson(@RequestBody Person p) {
        Person created = repository.save(p);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/people")
    public ResponseEntity<Iterable<Person>> getPersonList() {
        Iterable<Person> people = repository.findAll();
        return new ResponseEntity<>(people, HttpStatus.OK);
    }
    
    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Integer id) {
        return repository.findById(id)
                .map(person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/people/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Integer id, @RequestBody Person p) {
        boolean exists = repository.existsById(id);
        p.setId(id);
        Person saved = repository.save(p);
        
        if (exists) {
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }
    }
    
    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}