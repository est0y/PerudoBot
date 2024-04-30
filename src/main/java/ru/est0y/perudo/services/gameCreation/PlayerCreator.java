package ru.est0y.perudo.services.gameCreation;

import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Service
public class PlayerCreator {
    public List<Player> create(List<Member> members) {
        var mutableMembers = new ArrayList<>(members);
        Collections.shuffle(mutableMembers);
        AtomicInteger count = new AtomicInteger(0);
        return mutableMembers.stream().map(member -> new Player(
                member.getIdLong(),
                Optional.ofNullable(member.getNickname()).orElse(member.getEffectiveName()),
                count.incrementAndGet(),
                rollDice(5),
                true
        )).toList();
    }

    private List<Integer> rollDice(int count) {
        return IntStream.range(1, count + 1).map(i -> getRandomNumber(1, 7)).boxed().toList();
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
