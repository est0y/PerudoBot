package ru.est0y.perudo.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document
@ToString
public class User {
    @Id
    private Long  id;
   private boolean isPlaying;
    //skins
    //selected skin
    //isPlaying?
}
