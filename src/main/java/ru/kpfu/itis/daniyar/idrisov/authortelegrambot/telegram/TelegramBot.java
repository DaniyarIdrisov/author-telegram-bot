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

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class TelegramBot extends TelegramLongPollingBot {

    BotProperties botProperties;

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
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String chatId = update.getMessage().getChatId().toString();
//            if (update.getMessage().getText().startsWith("/")) {
//                sendMessage(commandsHandler.handleCommands(update));
//            } else {
//                sendMessage(new SendMessage(chatId, Consts.CANT_UNDERSTAND));
//            }
//        } else {
//            sendMessage(new SendMessage(chatId, Consts.CANT_UNDERSTAND));
//        }
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
