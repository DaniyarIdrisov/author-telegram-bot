package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.telegram.constants;

public class TelegramConstants {

    public static final String START_MESSAGE = "Выберете тип для того, чтобы отобразить нужную информацию из системы «Общественные обсуждения»";

    public static final String INCORRECT_COMMAND = "Введите команду, начинающуюся с /";
    public static final String UNKNOWN_COMMAND = "Такой команды не существует";
    public static final String UNKNOWN_CALLBACK = "Такого типа не существует";
    public static final String ERROR = "Внутренняя ошибка";

    public static final String CHOSE_MESSAGE = "Поздравляем, вы выбрали %s вашим городом.\nВыберите тип заведения, " +
            "пин-код которого хотите узнать:";

    public static final String CHOSE_TYPE_MESSAGE = "Поздравляем, вы выбрали заведение типа %s.\nВыберите адрес заведения, " +
            "пин-код которого хотите узнать:";

    public static final String CHOSE_ADDRESS_PIN = "Пин-код выбранного заведения: %s. \nПожалуйста, поделитесь отзывом, был ли верен пин?";

    public static final String CHOSE_ADDRESS_NO_PIN = "К сожалению, у нас нет актуального пин-кода данного заведения. Хотите ли вы добавить актуальный?";

    public static final String CHOSE_ADDRESS_OUTDATED_PIN = "К сожалению, пин-код заведения был помечен, как устаревший. Но можете попробовать, вдруг сработает: %s. Оказался ли он верным?";

    public static final String YES = "Да";

    public static final String NO = "НЕТ";

    public static final String RESTART = "Можете написать /start, чтобы начать заново. Спасибо за использование бота.";
    public static final String PIN_CORRECT_BYE = "Мы рады, что смогли помочь вам. " + RESTART;

    public static final String PIN_INCORRECT_MSG = "Нам жаль, что пин-код не актуален. Хотите ли вы добавить актуальный?";

    public static final String PIN_DONT_ADD_BYE = "Нам жаль, что мы не смогли помочь вам. Расскажите о нас вашим знакомым, возможно, они смогут добавить актуальный пин-код.";

    public static final String PIN_ADD_MSG = "Мы рады, что вы решили нам помочь. Введите пин-код в следующем сообщении, добавив /pin в начале (пример: /pin 1245#)";

    public static final String PIN_ADDED_MSG = "Спасибо, что помогли нам и добавили актуальный пин-код! " + RESTART;

    public static final String PIN_WRONG_ORDER = "Упс, кажется вы еще не выбрали адрес, куда хотите добавить пин-код или что-то пошло не так. " + RESTART;
}

