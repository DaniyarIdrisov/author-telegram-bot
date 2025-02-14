package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.constants.TelegramConstants;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.commands.Command;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.commands.StartCommand;

import java.util.Map;

@Slf4j
@Component
public class CommandsHandler {

    private final Map<String, Command> commands;

    public CommandsHandler(@Autowired StartCommand startCommand) {
        this.commands = Map.of(TelegramConstants.START_COMMAND, startCommand);
    }

    public SendMessage handleCommands(Update update) {
        var command = update.getMessage().getText().split(" ")[0];
        var chatId = update.getMessage().getChatId();

        var commandHandler = commands.get(command);
        if (commandHandler != null) {
            return commandHandler.apply(update);
        } else {
            return new SendMessage(String.valueOf(chatId), TelegramConstants.UNKNOWN_COMMAND);
        }
    }
}
