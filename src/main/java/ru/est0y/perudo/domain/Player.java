package ru.est0y.perudo.domain;

import lombok.*;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.requests.restaction.CacheRestAction;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Document
@ToString
public class Player {
  //  @Id
    private Long  id;
    private String name;
    private int number;
    private List<Integer> dice;
}
