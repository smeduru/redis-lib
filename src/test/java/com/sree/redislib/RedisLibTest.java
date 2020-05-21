package com.sree.redislib;

import com.sree.redislib.model.Person;
import com.sree.redislib.service.PersonService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = {RedisLibTestApp.class})
@ActiveProfiles("test")
class RedisLibTest {
    private static Logger LOG = LoggerFactory.getLogger(RedisLibTest.class);

    private PersonService personService;

    private static List<Person> personList;

    @Autowired
    public RedisLibTest(PersonService personService) {
        LOG.info("->> personService:" + (personService != null));
        this.personService = personService;
    }

    @Test
    @Order(1)
    @DisplayName("Given a list of personList "
                 + "when the save method is invoked, "
                 + "then person list must be saved to distributed redis cache.")
    public void savePersons() throws IOException {
        personList = Flux.just("John", "Mark", "Anthony", "Tylor")
                         .map(name -> new Person(UUID.randomUUID().toString(), name))
                         .collectList()
                         .block();

        LOG.info("->>" + personList);
        Assertions.assertThat(personList.size()).isEqualTo(4);

        personList.stream().forEach(personService::save);
    }

    @Test
    @Order(2)
    @DisplayName("Given a person id "
                 + "when the get method is invoked, "
                 + "then person must be retrieved from distributed redis cache.")
    public void getPersons() {
        Assertions.assertThat(personList.stream()
                             .filter(person -> !personService.get(person.getId()).getName().equals(person.getName()))
                             .count()).isEqualTo(0);
    }
}