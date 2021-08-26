package one.digitalinnovation.personapi.controller;


import one.digitalinnovation.personapi.service.PersonService;
import one.digitalinnovation.personapi.dto.PersonDto;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;


    @Captor
    private ArgumentCaptor<PersonDto> argumentCaptor;
    private ArgumentCaptor<PersonDto> personDtoArgumentCaptor;

    @Test
    public void postingANewPersonShouldCreateANewPersonInTheDatabase() throws Exception {
        PersonDto personDto = new PersonDto();

        personDto.setFirstName("Andre");
        personDto.setLastName("Barbosa");
        personDto.setBirthDate("12-10-1990");
        personDto.setCpf("789.456.741.-78");
        personDto.setRg("8495621");
        personDto.setPhone("84759-87459");

        when(personService.createNewPerson(argumentCaptor.capture())).thenReturn(1L);

        this.mockMvc.perform(post("/people")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/people/1"));

        assertThat(argumentCaptor.getValue().getFirstName(), is("Andre"));
        assertThat(argumentCaptor.getValue().getLastName(), is("Barbosa"));
        assertThat(argumentCaptor.getValue().getBirthDate(), is("12-10-1990"));
        assertThat(argumentCaptor.getValue().getCpf(), is("789.456.741.-78"));
        assertThat(argumentCaptor.getValue().getRg(), is("8495621"));
        assertThat(argumentCaptor.getValue().getPhone(), is("84759-87459"));


    }

    @Test
    public void allPersonsEndPointShouldReturnTwoPersons() throws Exception {
        when(personService.getAllPersons()).thenReturn(List.of(createPerson(1L, "Andre", "Barbosa", "12-10-1990","789.456.741.-78","8495621","84759-87459")));
        createPerson(2L,"Andrey", "Barbosa", "15-11-1990","789.456.741.-77","7495621","84759-87459");

        this.mockMvc
                .perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Andre")))
                .andExpect(jsonPath("$[0].lastName", is("Barbosa")))
                .andExpect(jsonPath("$[0].birthdate", is("12-10-1990")))
                .andExpect(jsonPath("$[0].cpf", is("789.456.741.-78")))
                .andExpect(jsonPath("$[0].rg", is("8495621")))
                .andExpect(jsonPath("$[0].phone", is("84759-87459")))
                .andExpect(jsonPath("$[0].id", is(1)));
    }
    @Test
    public void getPersonWithIdOneShouldReturnAPerson() throws Exception {
        when(personService.getPersonById(1L)).thenReturn(createPerson(1L, "Andre",
                "Barbosa", "12-10-1990","789.456.741.-78", "8495621","84759-87459"));


        this.mockMvc
                .perform(get("/people/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName", is("Andre")))
                .andExpect(jsonPath("$.lastName", is("Barbosa")))
                .andExpect(jsonPath("$.birthdate", is("12-10-1990")))
                .andExpect(jsonPath("$.cpf", is("789.456.741.-78")))
                .andExpect(jsonPath("$.rg", is("8495621")))
                .andExpect(jsonPath("$.phone", is("84759-87459")))
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test
    public void getPersonWithUnknownIdShouldReturn404() throws Exception {
        when(personService.getPersonById(1L)).thenThrow(new PersonNotFoundException("Person with id: '1' was not found"));
        this.mockMvc
                .perform(get("/people/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePersonWithKnownIdShouldUpdateThePerson() throws Exception{
        PersonDto personDto = new PersonDto();

        personDto.setFirstName("Carlos");
        personDto.setLastName("Barbosa");
        personDto.setBirthDate("12-10-1990");
        personDto.setCpf("789.456.741.-78");
        personDto.setRg("8495621");
        personDto.setPhone("84759-87459");

        when(personService.updatePerson(eq(1L), personDtoArgumentCaptor.capture()))
                .thenReturn(createPerson(1L, "Carlos", "Barbosa", "12-10-1990","789.456.741.-78","8495621","84759-87459"));
        this.mockMvc.perform(put("/people/1")

                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName", is("Carlos")))
                .andExpect(jsonPath("$.lastName", is("Barbosa")))
                .andExpect(jsonPath("$.birthdate", is("12-10-1990")))
                .andExpect(jsonPath("$.cpf", is("789.456.741.-78")))
                .andExpect(jsonPath("$.rg", is("8495621")))
                .andExpect(jsonPath("$.phone", is("84759-87459")))
                .andExpect(jsonPath("$.id", is(1)));

        assertThat(argumentCaptor.getValue().getFirstName(), is("Carlos"));
        assertThat(argumentCaptor.getValue().getLastName(), is("Barbosa"));
        assertThat(argumentCaptor.getValue().getBirthDate(), is("12-10-1990"));
        assertThat(argumentCaptor.getValue().getCpf(), is("789.456.741.-78"));
        assertThat(argumentCaptor.getValue().getRg(), is("8495621"));
        assertThat(argumentCaptor.getValue().getPhone(), is("84759-87459"));


    }

    @Test
    public void updatePersonWithUnknownIdShouldReturn404() throws Exception{
        PersonDto personDto = new PersonDto();

        personDto.setFirstName("Carlos");
        personDto.setLastName("Barbosa");
        personDto.setBirthDate("12-10-1990");
        personDto.setCpf("789.456.741.-78");
        personDto.setRg("8495621");
        personDto.setPhone("84759-87459");


        when(personService.updatePerson(eq(42L), personDtoArgumentCaptor.capture()))
                .thenThrow(new PersonNotFoundException("Person with id: '42' was not found"));
        this.mockMvc
                .perform(put("/people/42").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(personDto)))
                .andExpect(status().isNotFound());

    }

    private Person createPerson(long id, String firstName, String lastName, String birthdate, String cpf, String rg, String phone) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setId(id);
        person.setBirthDate(birthdate);
        person.setCpf(cpf);
        person.setRg(rg);
        person.setPhone(phone);

        return person;


    }
}

