package tqs.WashNow.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.WashNow.entities.Person;
import tqs.WashNow.entities.Role;
import tqs.WashNow.repositories.PersonRepository;
import tqs.WashNow.services.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    private Person person;

    @BeforeEach
    void setup() {
        person = new Person(
            1L,
            "Alice",
            "alice@example.com",
            Role.DRIVER,
            100.0
        );
    }

    @Test
    void whenCreate_thenSaveAndReturn() {
        Person toCreate = new Person(
            null,
            "Bob",
            "bob@example.com",
            Role.OWNER,
            250.0
        );
        Person saved = new Person(
            2L,
            "Bob",
            "bob@example.com",
            Role.OWNER,
            250.0
        );

        when(repository.save(toCreate)).thenReturn(saved);

        Person result = service.createPerson(toCreate);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Bob", result.getName());
        assertEquals(Role.OWNER, result.getRole());
        verify(repository, times(1)).save(toCreate);
    }

    @Test
    void whenGetByIdExists_thenReturnPerson() {
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        Person result = service.getPersonById(1L);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals("alice@example.com", result.getEmail());
        assertEquals(100.0, result.getBalance());
        verify(repository).findById(1L);
    }

    @Test
    void whenGetByIdNotExists_thenReturnNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Person result = service.getPersonById(99L);

        assertNull(result);
        verify(repository).findById(99L);
    }

    @Test
    void whenUpdateExists_thenSetIdAndSave() {
        Long idToUpdate = 3L;
        Person incoming = new Person(
            null,
            "Charlie",
            "charlie@example.com",
            Role.DRIVER,
            75.0
        );
        Person updated = new Person(
            idToUpdate,
            "Charlie",
            "charlie@example.com",
            Role.DRIVER,
            75.0
        );

        when(repository.existsById(idToUpdate)).thenReturn(true);
        when(repository.save(any(Person.class))).thenReturn(updated);

        Person result = service.updatePersonById(idToUpdate, incoming);

        assertNotNull(result);
        assertEquals(idToUpdate, result.getId());
        assertEquals("Charlie", result.getName());

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(repository).save(captor.capture());
        assertEquals(idToUpdate, captor.getValue().getId());
        assertEquals(75.0, captor.getValue().getBalance());
    }

    @Test
    void whenUpdateNotExists_thenReturnNullAndDontSave() {
        Long idToUpdate = 50L;
        Person incoming = new Person(
            null,
            "Diana",
            "diana@example.com",
            Role.OWNER,
            0.0
        );

        when(repository.existsById(idToUpdate)).thenReturn(false);

        Person result = service.updatePersonById(idToUpdate, incoming);

        assertNull(result);
        verify(repository, never()).save(any());
    }

    @Test
    void whenDelete_thenRepositoryDeleteById() {
        service.deletePersonById(5L);
        verify(repository, times(1)).deleteById(5L);
    }

    @Test
    void whenGetAll_thenReturnListOfPersons() {
        Person p1 = new Person(1L, "Eve", "eve@example.com", Role.DRIVER, 10.0);
        Person p2 = new Person(2L, "Frank", "frank@example.com", Role.OWNER, 500.0);
        List<Person> list = Arrays.asList(p1, p2);

        when(repository.findAll()).thenReturn(list);

        List<Person> result = service.getAllPersons();

        assertEquals(2, result.size());
        assertTrue(result.contains(p1));
        assertTrue(result.contains(p2));
        verify(repository).findAll();
    }
}
