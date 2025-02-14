package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.handlers.callbacks;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.services.DisputeService;
import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.constants.TelegramConstants;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class GetDisputePerformingCurrentFileCallback implements Callback {

    DisputeService disputeService;

    @Override
    public SendDocument apply(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        var file = disputeService.createXlsDisputePerformingCurrent();
        InputStream fileInputStream = new ByteArrayInputStream(file);

        var sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(new InputFile(fileInputStream,  TelegramConstants.DISCUSSION_PERFORMING_CURRENT_CALLBACK_DATA + ".xls"));
        return sendDocument;
    }
}
