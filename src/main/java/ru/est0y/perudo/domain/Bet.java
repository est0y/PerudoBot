package ru.est0y.perudo.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bet {
    private int diceCount;
    private int diceValue;
}
