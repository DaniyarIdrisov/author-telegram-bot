package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.callbacks;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Callback {

    SendDocument apply(Update update);
}
