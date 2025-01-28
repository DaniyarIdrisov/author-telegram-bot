package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DisputeType {

    NOTIFICATION_SHOWN("Оповещения"),

    DISCUSSION_PERFORMING("Экспозиция"),

    DISCUSSION_FAKE("Обсуждения"),

    RESULT_PREPARING("Заключения"),

    ARCHIVED("Архив");

    public final String typeValue;
}
