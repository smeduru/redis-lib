package com.sree.redislib.service;

import com.sree.redislib.model.Person;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, Person> personOps;

    public PersonService(final ReactiveRedisConnectionFactory factory,
                         final ReactiveRedisOperations<String, Person> personOps) {
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
}
