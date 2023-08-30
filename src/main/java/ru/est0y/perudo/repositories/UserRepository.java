package ru.est0y.perudo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.est0y.perudo.domain.User;

public interface UserRepository extends ReactiveMongoRepository<User,Long> {

}
