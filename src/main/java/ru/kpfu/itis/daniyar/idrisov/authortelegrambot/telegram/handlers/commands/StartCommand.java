package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models.enums.DisputeType;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.constants.TelegramConstants;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class StartCommand implements Command {

    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(TelegramConstants.START_MESSAGE);
        addKeyboard(sendMessage);
        return sendMessage;
    }

    private void addKeyboard(SendMessage sendMessage) {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        var keyboardButtonsRow = new ArrayList<InlineKeyboardButton>();
        for (var disputeType : DisputeType.values()) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(disputeType.getTypeValue());
            inlineKeyboardButton.setCallbackData(disputeType.name());
            keyboardButtonsRow.add(inlineKeyboardButton);
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }
}