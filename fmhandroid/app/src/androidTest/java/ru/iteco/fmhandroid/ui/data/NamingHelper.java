package ru.iteco.fmhandroid.ui.data;

import java.util.UUID;

public class NamingHelper {


    public static String generateTitleId() {
        String titleId = UUID.randomUUID().toString();
        return titleId;
    }

    public String getNewsAnnouncementName() {
        return "Объявление" + " " + generateTitleId();
    }

    public String getNewsBirthdayName() {
        return "День рождения" + " " + generateTitleId();
    }

    public String getNewsSalaryName() {
        return "Зарплата" + " " + generateTitleId();
    }

    public String getNewsTradeUnionName() {
        return "Профсоюз" + " " + generateTitleId();
    }

    public String getNewsHolidayName() {
        return "Праздник" + " " + generateTitleId();
    }

    public String getNewsGratitudeName() {
        return "Благодарность" + " " + generateTitleId();
    }

    public String getNewsMassageName() {
        return "Массаж" + " " + generateTitleId();
    }

    public String getNewsNeedHelpName() {
        return "Нужна помощь" + " " + generateTitleId();
    }

    public String getNewsMyCategoryName() {
        return "Моя категория" + " " + generateTitleId();
    }

    public String getClaimOpenName() {
        return "Открытая" + " " + generateTitleId();
    }

    public String getClaimInProgressName() {
        return "В работе" + " " + generateTitleId();
    }

    public String getClaimExecutedName() {
        return "Выполнено" + " " + generateTitleId();
    }

    public String getClaimCanceledName() {
        return "Закрыта" + " " + generateTitleId();
    }

    public String getComment() {
        return "Комментарий" + " " + DataHelper.generateTitleId();
    }

    public String getClaimTitleWithASpace() {
        return " Заголовок" + " " + generateTitleId();
    }
    public String getClaimTitle50Characters() {
        return "Заголовок" + " " + DataHelper.generateTitleId() + " " + "50з";
    }
    public String getClaimTitle49Characters() {
        return "Заголовок" + " " + DataHelper.generateTitleId() + " " + "49";
    }
    public String getClaimTitle51Characters() {
        return "Заголовок" + " " + DataHelper.generateTitleId() + " " + "51зн";
    }









}
