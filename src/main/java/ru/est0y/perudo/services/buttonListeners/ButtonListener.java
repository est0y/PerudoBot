package ru.est0y.perudo.services.buttonListeners;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface ButtonListener {
    void click(ButtonInteractionEvent event);
}
