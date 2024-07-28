package ru.est0y.perudo.services;

import java.util.List;

public interface UserService {
    void updateIsPlayingByIds(List<Long>ids,boolean isPlaying);
}
