package ru.est0y.perudo.messaging;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiceService {
    String getDice(List<Integer> dice){
        String result="";
        for (var die:dice){
          result=  result.concat(die +",");
        }
        return result;
    }
}
