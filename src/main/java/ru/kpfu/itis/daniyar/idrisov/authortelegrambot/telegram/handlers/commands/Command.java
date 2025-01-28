package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    SendMessage apply(Update update);
}
