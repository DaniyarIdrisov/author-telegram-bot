package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.services;

import ru.kpfu.itis.daniyar.idrisov.authortelegrambot.models.enums.DisputeType;

public interface DisputeService {

    byte[] createXlsByType(DisputeType disputeType);
}
