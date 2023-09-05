package ru.est0y.perudo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.est0y.perudo.domain.User;
import ru.est0y.perudo.repositories.UserRepository;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Transactional
    public void updateIsPlayingByIds(List<Long> ids, boolean isPlaying) {
        mongoTemplate.updateMulti(
                Query.query(Criteria.where("_id").in(ids)),
                new Update().set("isPlaying", isPlaying),
                User.class
        );
    }

    @Transactional
    public Flux<User> updateOrSave(List<Long> ids) {
        log.info("start updateOrSave");
        Mono<Map<Long, User>> userMapMono = userRepository.findAllById(ids)
                .collectMap(User::getId, user -> user);

        return userMapMono.flatMapMany(userMap -> {
            log.info(userMap.toString());
            var newUsers = new ArrayList<User>();
            for (var id : ids) {
                if (!userMap.containsKey(id)) {
                    newUsers.add(new User(id, true));
                }
            }

            if (userMap.values().stream().noneMatch(User::isPlaying)) {
                userMap.values().forEach(user -> user.setPlaying(true));
            } else {
                return Flux.error(new RuntimeException("Cannot process user IDs. Some users are already playing."));
            }
            log.info("end updateOrSave");
            return Flux.concat(
                    userRepository.insert(newUsers),                // Insert new users
                    userRepository.saveAll(userMap.values())       // Save existing users
            );
        });
    }


}
