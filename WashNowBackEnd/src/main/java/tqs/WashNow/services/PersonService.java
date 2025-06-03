package tqs.WashNow.services;

import org.springframework.stereotype.Service;

import tqs.WashNow.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tqs.WashNow.entities.Person;
import java.util.List;


@Service
public class PersonService {

    private PersonRepository PersonRepository;

    @Autowired
    public PersonService(PersonRepository PersonRepository) {
        this.PersonRepository = PersonRepository;
    }

    // POST
    public Person createPerson(Person person) {
        if (person.getId() != null && PersonRepository.existsById(person.getId())) return null;

        return PersonRepository.save(person);
    }

    // GET
    public Person getPersonById(Long id) {
        return PersonRepository.findById(id).orElse(null);
    }

    // PUT
    public Person updatePersonById(Long id, Person Person) {
        if (PersonRepository.existsById(id)) {
            Person.setId(id);
            return PersonRepository.save(Person);
        }
        return null;
    }

    // DELETE
    public void deletePersonById(Long id) {
        PersonRepository.deleteById(id);
    }

    // GET ALL
    public List<Person> getAllPersons() {
        return PersonRepository.findAll();
    }
    
    
}
