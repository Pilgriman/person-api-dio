package one.digitalinnovation.personapi.service;



import one.digitalinnovation.personapi.dto.PersonDto;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Long createNewPerson(PersonDto personDto) {
        Person person = new Person();

        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setBirthDate(personDto.getBirthDate());
        person.setCpf(personDto.getCpf());
        person.setRg(personDto.getRg());
        person.setPhone(personDto.getPhone());


        person = personRepository.save(person);

        return person.getId();
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        Optional<Person> requestedPerson = personRepository.findById(id);
        if (requestedPerson.isEmpty()) {
            throw new PersonNotFoundException(String.format("Person with id: '%s' not found" , id));

        }

        return requestedPerson.get();
    }

    @Transactional
    public Person updatePerson(Long id, PersonDto personToUpdateRequest){
        Optional<Person> personFromDatabase = personRepository.findById(id);
        if (personFromDatabase.isEmpty()){
            throw new PersonNotFoundException(String.format("Person with id: '%s' not found" , id));
        }

        Person personToUpdate = personFromDatabase.get();

        personToUpdate.setFirstName(personToUpdateRequest.getFirstName());
        personToUpdate.setLastName(personToUpdateRequest.getLastName());
        personToUpdate.setBirthDate(personToUpdateRequest.getBirthDate());
        personToUpdate.setCpf(personToUpdateRequest.getCpf());
        personToUpdate.setRg(personToUpdateRequest.getRg());
        personToUpdate.setPhone(personToUpdateRequest.getPhone());

        return  personToUpdate;

    }

    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }
}




