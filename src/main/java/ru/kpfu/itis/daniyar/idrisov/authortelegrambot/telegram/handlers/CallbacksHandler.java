package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models.enums.DisputeType;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.constants.TelegramConstants;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.callbacks.Callback;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.callbacks.GetDisputeFileByTypeCallback;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CallbacksHandler {

    private final Map<String, Callback> callbacks;

    public CallbacksHandler(@Autowired GetDisputeFileByTypeCallback getDisputeFileByTypeCallback) {
        this.callbacks = new HashMap<>();
        for (var disputeType : DisputeType.values()) {
            callbacks.put(disputeType.name(), getDisputeFileByTypeCallback);
        }
    }

    public SendMessage handleCallbacks(Update update) {
        var disputeType = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        var callback = callbacks.get(disputeType);
        if (callback != null) {
            return callback.apply(update);
        } else {
            return new SendMessage(String.valueOf(chatId), TelegramConstants.UNKNOWN_CALLBACK);
        }
    }
}
