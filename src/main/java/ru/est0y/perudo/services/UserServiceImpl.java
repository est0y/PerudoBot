package ru.est0y.perudo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.est0y.perudo.domain.User;
import ru.est0y.perudo.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<User> updateOrSave(List<Long> ids) {
        log.info("start updateOrSave");
        Map<Long, User> userMap = userRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(User::getId, (u) -> u));

        //return userMapMono.flatMapMany(userMap -> {
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
            throw new RuntimeException("Cannot process user IDs. Some users are already playing.");
        }
        log.info("end updateOrSave");

                userRepository.insert(newUsers);             // Insert new users
              return userRepository.saveAll(userMap.values());     // Save existing users

        // });
    }


}
