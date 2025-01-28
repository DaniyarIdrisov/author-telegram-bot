package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.configs.properties.BotProperties;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.constants.TelegramConstants;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.CallbacksHandler;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.CommandsHandler;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class TelegramBot extends TelegramLongPollingBot {

    BotProperties botProperties;
    CommandsHandler commandsHandler;
    CallbacksHandler callbacksHandler;

    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chatId = update.getMessage().getChatId().toString();
            if (update.getMessage().getText().startsWith("/")) {
                sendMessage(commandsHandler.handleCommands(update));
            } else {
                sendMessage(new SendMessage(chatId, TelegramConstants.INCORRECT_COMMAND));
            }
        } else if (update.hasCallbackQuery()) {
            sendMessage(callbacksHandler.handleCallbacks(update));
        }
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
