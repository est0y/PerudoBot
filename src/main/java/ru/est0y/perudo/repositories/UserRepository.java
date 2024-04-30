package ru.est0y.perudo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.est0y.perudo.domain.User;

public interface UserRepository extends MongoRepository<User,Long> {


}
