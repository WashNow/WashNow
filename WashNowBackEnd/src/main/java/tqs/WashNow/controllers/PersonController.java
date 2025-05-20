package tqs.WashNow.controllers;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tqs.WashNow.services.PersonService;
import tqs.WashNow.entities.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@RestController
@RequestMapping("/api/Persons")
@Tag(name = "Person", description = "Person API")
public class PersonController {

    private final PersonService PersonService;

    public PersonController(PersonService PersonService) {
        this.PersonService = PersonService;
    }

    @PostMapping
    @Operation(summary = "Create a new Person")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Person created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<Person> createPerson(@RequestBody Person Person) {
        Person createdPerson = PersonService.createPerson(Person);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all Persons")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Persons retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> Persons = PersonService.getAllPersons();
        return new ResponseEntity<>(Persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Person by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Person retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person Person = PersonService.getPersonById(id);
        if (Person != null) {
            return new ResponseEntity<>(Person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Person by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Person updated successfully"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person Person) {
        Person updatedPerson = PersonService.updatePersonById(id, Person);
        if (updatedPerson != null) {
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Person by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Person deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (PersonService.getPersonById(id) != null) {
            PersonService.deletePersonById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    
}