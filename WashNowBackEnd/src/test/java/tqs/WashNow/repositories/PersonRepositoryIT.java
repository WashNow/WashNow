package tqs.WashNow.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tqs.WashNow.entities.Person;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PersonRepositoryIT {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void testSavePerson() {
        Person person = new Person();
        person.setName("John Doe");
        person.setEmail("john.doe@example.com");

        Person savedPerson = personRepository.save(person);

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo("John Doe");
        assertThat(savedPerson.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindPersonById() {
        Person person = new Person();
        person.setName("Jane Doe");
        person.setEmail("jane.doe@example.com");
        person = personRepository.save(person);

        Optional<Person> retrievedPerson = personRepository.findById(person.getId());

        assertThat(retrievedPerson).isPresent();
        assertThat(retrievedPerson.get().getName()).isEqualTo("Jane Doe");
        assertThat(retrievedPerson.get().getEmail()).isEqualTo("jane.doe@example.com");
    }

    @Test
    void testDeletePerson() {
        Person person = new Person();
        person.setName("Mark Smith");
        person.setEmail("mark.smith@example.com");
        person = personRepository.save(person);

        personRepository.deleteById(person.getId());

        Optional<Person> deletedPerson = personRepository.findById(person.getId());
        assertThat(deletedPerson).isNotPresent();
    }
}