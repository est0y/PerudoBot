package ru.est0y.perudo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.est0y.perudo.domain.User;

import java.util.List;

public interface UserRepository extends MongoRepository<User,Long> {


}
