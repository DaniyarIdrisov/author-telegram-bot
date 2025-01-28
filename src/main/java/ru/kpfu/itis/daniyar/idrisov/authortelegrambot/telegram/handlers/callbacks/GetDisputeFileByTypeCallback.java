package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.callbacks;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models.enums.DisputeType;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.services.DisputeService;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class GetDisputeFileByTypeCallback implements Callback {

    DisputeService disputeService;

    @Override
    public SendDocument apply(Update update) {
        var disputeType = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        var file = disputeService.createXlsByType(DisputeType.valueOf(disputeType));
        var sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
    }
}
