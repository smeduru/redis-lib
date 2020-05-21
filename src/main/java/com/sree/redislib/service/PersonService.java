package com.sree.redislib.service;

import com.sree.redislib.model.Person;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(AppMessage.class)
public class PersonService {
    private final AppMessage appMessage;
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, Person> personOps;

    public PersonService(final AppMessage appMessage,
                         final ReactiveRedisConnectionFactory factory,
                         final ReactiveRedisOperations<String, Person> personOps) {
        this.appMessage = appMessage;
        this.factory = factory;
        this.personOps = personOps;
    }

    public Boolean save(Person person) {
        return personOps.opsForValue().set(person.getId(), person).block();
    }

    public Person get(String id) {
        return personOps.opsForValue().get(id).block();
    }

    public Boolean delete(String id) {
        return personOps.opsForValue().delete(id).block();
    }

    public String message() {
        return this.appMessage.getMessage();
    }
}
