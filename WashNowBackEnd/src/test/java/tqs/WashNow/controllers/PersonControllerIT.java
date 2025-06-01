package tqs.WashNow.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.WashNow.entities.Person;
import tqs.WashNow.services.PersonService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PersonControllerIT {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePerson() {
        Person person = new Person();
        when(personService.createPerson(person)).thenReturn(person);

        ResponseEntity<Person> response = personController.createPerson(person);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        verify(personService, times(1)).createPerson(person);
    }

    @Test
    void testGetAllPersons() {
        List<Person> persons = Arrays.asList(new Person(), new Person());
        when(personService.getAllPersons()).thenReturn(persons);

        ResponseEntity<List<Person>> response = personController.getAllPersons();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        verify(personService, times(1)).getAllPersons();
    }

    @Test
    void testGetPersonById() {
        Long id = 1L;
        Person person = new Person();
        when(personService.getPersonById(id)).thenReturn(person);

        ResponseEntity<Person> response = personController.getPersonById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(personService, times(1)).getPersonById(id);
    }

    @Test
    void testUpdatePerson() {
        Long id = 1L;
        Person updatedPerson = new Person();
        when(personService.updatePersonById(id, updatedPerson)).thenReturn(updatedPerson);

        ResponseEntity<Person> response = personController.updatePerson(id, updatedPerson);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(personService, times(1)).updatePersonById(id, updatedPerson);
    }

    @Test
    void testDeletePerson() {
        Long id = 1L;
        Person person = new Person();
        when(personService.getPersonById(id)).thenReturn(person);
        doNothing().when(personService).deletePersonById(id);

        ResponseEntity<Void> response = personController.deletePerson(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(personService, times(1)).deletePersonById(id);
    }
}