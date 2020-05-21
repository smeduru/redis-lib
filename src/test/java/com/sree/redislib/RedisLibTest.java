package com.sree.redislib;

import com.sree.redislib.model.Person;
import com.sree.redislib.service.PersonService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

import static org.assertj.core.api.Assertions.assertThat;

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

    @BeforeAll
    public static void startRedisServer() throws IOException {
        personList = Flux.just("John", "Mark", "Anthony", "Tylor")
                         .map(name -> new Person(UUID.randomUUID().toString(), name))
                         .collectList()
                         .block();

        LOG.info("->>" + personList);
        personList.stream().forEach(System.out::println);
    }

    @Test
    @Order(1)
    public void savePersons() {
        personList.stream().forEach(personService::save);
    }

    @Test
    @Order(2)
    public void getPersons() {
        Assertions.assertThat(personList.size()).isEqualTo(4);
        Assertions.assertThat(personList.stream()
                             .filter(person -> personService.get(person.getId()) == null)
                             .count()).isEqualTo(0);
    }

    @Test
    public void contextLoads() {
        assertThat(personService.message()).isNotNull();
    }
}