package ru.est0y.perudo.config.props;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties("app.player")
public class PlayerProps {
    private final long startingBalance;

    @ConstructorBinding
    public PlayerProps(long startingBalance) {
        this.startingBalance=startingBalance;
    }


}
