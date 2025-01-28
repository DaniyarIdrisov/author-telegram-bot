package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.callbacks;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.services.DisputeService;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class GetDisputeFileByTypeCallback implements Callback {

    DisputeService disputeService;

    @Override
    public SendMessage apply(Update update) {
        var disputeType = update.getCallbackQuery().getData();
        return null;
    }
}
