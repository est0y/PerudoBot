package ru.est0y.perudo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
@ToString
public class Game {
    @Id
    private String id;

    private  Long guildId;

    private   Player turnHolder;

    private List<Player>players;

    //вынести в класс Round
    private Bet lastBet;

    private  int believersCount;

    private int roundNumber;

    private  boolean isSpecialRound;

}
