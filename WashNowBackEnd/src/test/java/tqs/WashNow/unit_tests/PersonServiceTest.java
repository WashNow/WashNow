package tqs.WashNow.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private Person person;

    private Long id = 1L;

    @BeforeEach
    void setup() {
        person = new Person(
            id,
            "Alice",
            "alice@example.com",
            Role.DRIVER,
            100.0
        );
    }

    @Test
    void testCreateBooking() {
        when(personRepository.save(person)).thenReturn(person);
        Person created = personService.createPerson(person);
        assertNotNull(created);
        assertEquals(id, created.getId());
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void testCreateBookingWhenExists() {
        when(personRepository.existsById(1L)).thenReturn(true);
        personRepository.save(person);
        assertTrue(personRepository.existsById(1L));

        Person created = personService.createPerson(person);
        assertNull(created);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void testGetBookingById() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        Person found = personService.getPersonById(1L);
        assertNotNull(found);
        assertEquals(person.getId(), found.getId());
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookingByIdWhenNotExists() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        Person found = personService.getPersonById(1L);
        assertNull(found);
        verify(personRepository).findById(1L);
    }    

    @Test
    void testUpdateBookingByIdWhenExists() {
        when(personRepository.existsById(1L)).thenReturn(true);
        when(personRepository.save(person)).thenReturn(person);

        Person updated = personService.updatePersonById(1L, person);
        assertNotNull(updated);
        assertEquals(person.getId(), updated.getId());
        verify(personRepository, times(1)).existsById(1L);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void testUpdateBookingByIdWhenNotExists() {
        when(personRepository.existsById(1L)).thenReturn(false);

        Person updated = personService.updatePersonById(1L, person);
        assertNull(updated);
        verify(personRepository, times(1)).existsById(1L);
        verify(personRepository, never()).save(person);
    }

    @Test
    void testDeleteBookingById() {
        personService.deletePersonById(1L);
        verify(personRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllBookings() {
        List<Person> allBookings = Arrays.asList(person, new Person());
        when(personRepository.findAll()).thenReturn(allBookings);

        List<Person> result = personService.getAllPersons();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(person));
        verify(personRepository, times(1)).findAll();
    }
}
