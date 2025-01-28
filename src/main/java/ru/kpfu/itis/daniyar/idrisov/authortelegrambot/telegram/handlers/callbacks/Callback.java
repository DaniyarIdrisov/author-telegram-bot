package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.callbacks;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Callback {

    SendMessage apply(Update update);
}
