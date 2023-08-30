package ru.est0y.perudo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.est0y.perudo.domain.User;
import ru.est0y.perudo.repositories.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;

    public Flux<User> updateOrSave(List<Long> ids) {
        Mono<Map<Long, User>> userMapMono = userRepository.findAllById(ids)
                .collectMap(User::getId, user -> user);

        return userMapMono.flatMapMany(userMap -> {
            var newUsers = new ArrayList<User>();

            for (var id : ids) {
                if (!userMap.containsKey(id)) {
                    newUsers.add(new User(id, true));
                }
            }

            if (userMap.values().stream().noneMatch(User::isPlaying)) {
                userMap.values().forEach(user -> user.setPlaying(true));
            }

            if (userMap.values().stream().anyMatch(User::isPlaying)) {
                return Flux.error(new RuntimeException("Cannot process user IDs. Some users are already playing."));
            }

            return Flux.concat(
                    userRepository.insert(newUsers),                // Insert new users
                    userRepository.saveAll(userMap.values())       // Save existing users
            );/*.map(user -> {
                if (newUsers.contains(user)) {
                    return new User(user.getId(), false);      // Return new user with isPlaying set to false
                }
                return user;                                   // Return existing user
            });*/
        });
    }




 /*   Flux<User> updateOrSave(List<Long> ids) {
        var map = userRepository.findAllById(ids).collectMap(User::getId, user -> user).block();
        if (map==null)map=new HashMap<>();
        var newUsers = new ArrayList<User>();
        for (var id : ids) {
            if (map.get(id) == null) {
                newUsers.add(new User(id, true));
            }
            if (map.values().stream().anyMatch(User::isPlaying))throw new RuntimeException();
            userRepository.insert(newUsers);
            map.values().forEach(user -> user.setPlaying(true));
            userRepository.saveAll(map.values());
        }
        var list=new ArrayList<>(map.values().stream().toList());
        list.addAll(newUsers);
        return Flux.fromIterable(list);
    }
}*/
}
