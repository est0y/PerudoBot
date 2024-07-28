package ru.est0y.perudo.services.buttonListeners;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ButtonListenersManager {
    private final Map<String, ButtonListener> buttonListeners;

    public ButtonListener getListener(String name) {
        return buttonListeners.get(name);
    }
}
