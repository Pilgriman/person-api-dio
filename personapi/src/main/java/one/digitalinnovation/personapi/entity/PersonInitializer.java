package one.digitalinnovation.personapi.entity;


import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class PersonInitializer implements CommandLineRunner {
        @Autowired
        private PersonRepository personRepository;

        @Override
        public void run(String... args) throws Exception {
            log.info("Starting to initializer sample data...");

            Faker faker = new Faker();

            for (int i = 0; i < 10; i++){

                Person person = new Person();
                person.setFirstName(faker.name().firstName());
                person.setLastName(faker.name().lastName());
                person.setBirthDate(faker.date().birthday().toString());
                person.setCpf(UUID.randomUUID().toString());
                person.setRg(UUID.randomUUID().toString());
                person.setPhone(faker.phoneNumber().phoneNumber());


                personRepository.save(person);




            }


            log.info("...finished with data initialization");
        }
}
