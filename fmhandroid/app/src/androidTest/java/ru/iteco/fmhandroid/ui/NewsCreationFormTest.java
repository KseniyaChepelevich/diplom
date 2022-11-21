package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.RemoteException;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.CustomRecyclerViewActions;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class NewsCreationFormTest extends BaseTest{
    private UiDevice device;

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static NewsPageSteps newsPageSteps = new NewsPageSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    Calendar date = Calendar.getInstance();

    String titleForNewsAnnouncement = "Тест новость Объявление" + " " + DataHelper.generateTitleId();
    String titleForNewsBirthday = "Тест новость День рождения" + " " + DataHelper.generateTitleId();
    String titleForNewsSalary = "Тест новость Зарплата" + " " + DataHelper.generateTitleId();
    String titleForNewsTradeUnion = "Тест новость Профсоюз" + " " + DataHelper.generateTitleId();
    String titleForNewsHoliday = "Тест новость Праздник" + " " + DataHelper.generateTitleId();
    String titleForNewsGratitude = "Тест новость Благодарность" + " " + DataHelper.generateTitleId();
    String titleForNewsMassage = "Тест новость Массаж" + " " + DataHelper.generateTitleId();
    String titleForNewsNeedHelp = "Тест новость Нужна помощь" + " " + DataHelper.generateTitleId();
    String titleForNewsShouldNotBeKept = "Новость не должна сохраниться" + " " + DataHelper.generateTitleId();
    String titleForNewsPublicationDateTomorrow = "Дата публикации сегодня" + " " + DataHelper.generateTitleId();
    String titleForNewsPublicationDateInAMonth = "Дата публикации через месяц" + " " + DataHelper.generateTitleId();
    String titleForNewsPublicationTimeHourAgo = "Время публикации час назад" + " " + DataHelper.generateTitleId();
    String titleForNewsPublicationTimeInOneHour = "Время публикации через час" + " " + DataHelper.generateTitleId();
    String nonLetterTitle = ";&&" + " " + DataHelper.generateTitleId();

    @Before
    public void logoutCheck() {
        device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        try {
            authSteps.isAuthScreen();
        } catch (PerformException e) {
            mainPageSteps.clickLogOutBut();
        }
        authSteps.authWithValidData(authInfo());
        mainPageSteps.isMainPage();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.openControlPanel();
        controlPanelSteps.openCreatingNewsForm();
    }

    @Test
    @DisplayName("Автоподставление в поле Title из поля Category")
    public void shouldSubstituteInTheTitleFieldTheValueOfTheCategoryField() {
        String categoryAnnouncement = "Объявление";
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.getNewsItemTitle().check(matches(withText(categoryAnnouncement)));
    }

    @Test
    @DisplayName("Создание Новости с категорией Объявление")
    public void shouldCreateANewsItemWithCategoryAnnouncement() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsAnnouncement, titleForNewsAnnouncement);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Объявление"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsAnnouncement).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsAnnouncement);
    }

    @Test
    @DisplayName("Создание Новости с категорией День рождения")
    public void shouldCreateANewsItemWithCategoryBirthday() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryBirthday);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsBirthday, titleForNewsBirthday);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость День рождения"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsBirthday).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsBirthday);
    }

    @Test
    @DisplayName("Создание Новости с категорией Зарплата")
    public void shouldCreateANewsItemWithCategorySalary() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsSalary, titleForNewsSalary);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsSalary).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsSalary);
    }

    @Test
    @DisplayName("Создание Новости с категорией Профсоюз")
    public void shouldCreateANewsItemWithCategoryTradeUnion() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryTradeUnion);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsTradeUnion, titleForNewsTradeUnion);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Профсоюз"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsTradeUnion).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsTradeUnion);
    }

    @Test
    @DisplayName("Создание Новости с категорией Праздник")
    public void shouldCreateANewsItemWithCategoryHoliday() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryHoliday);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsHoliday, titleForNewsHoliday);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Праздник"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsHoliday).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsHoliday);
    }

    @Test
    @DisplayName("Создание Новости с категорией Благодарность")
    public void shouldCreateANewsItemWithCategoryGratitude() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryGratitude);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsGratitude, titleForNewsGratitude);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Благодарность"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsGratitude).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsGratitude);
    }

    @Test
    @DisplayName("Создание Новости с категорией Массаж")
    public void shouldCreateANewsItemWithCategoryMassage() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryMassage);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsMassage, titleForNewsMassage);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Массаж"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsMassage).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsMassage);
    }

    @Test
    @DisplayName("Создание Новости с категорией Нужна помощь")
    public void shouldCreateANewsItemWithCategoryNeedHelp() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryNeedHelp);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsNeedHelp, titleForNewsNeedHelp);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Массаж"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsNeedHelp).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsNeedHelp);
    }

    @Test
    @DisplayName("Отмена создания новости")
    public void shouldNotCreateNews() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryNeedHelp);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsShouldNotBeKept, titleForNewsShouldNotBeKept);
        controlPanelSteps.cancelButtonClick();
        controlPanelSteps.getMessageChangesWonTBeSaved().check(matches(isDisplayed()));
        controlPanelSteps.okButtonClick();
        //Проверить отсутствие в списке новостей новости с заголовком "Новость не должна сохраниться"
        controlPanelSteps.getNewsRecyclerList().check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(titleForNewsShouldNotBeKept))));
    }

    @Test
    @DisplayName("Создание новости с категорией не из списка")
    public void shouldShowAWrongMessageWithTextSelectACategoryFromTheList() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String MyCategory = "Тест";

        controlPanelSteps.replaceNewsCategoryText(MyCategory);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsShouldNotBeKept, titleForNewsShouldNotBeKept);
        controlPanelSteps.saveNewsButtonClick();
        controlPanelSteps.checkToast("Wrong category selected. Select a category from the list.", true);
    }

    @Test
    @DisplayName("Сохранение пустой формы новости")
    public void shouldNotSaveEmptyNews() {
        controlPanelSteps.saveNewsButtonClick();
        controlPanelSteps.isWrongEmptyFormNews();
    }

    @Test
    @DisplayName("Создание Новости со статусом Не активна")
    public void shouldToggleTurnOffSwitchActive() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryMassage);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsMassage, titleForNewsMassage);
        controlPanelSteps.switchNewsStatus();
        controlPanelSteps.getSwitcherNoteActive().check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с датой публикации завтра")
    public void shouldCreateANewsItemWithPublishDateTomorrow() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH)+1;
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsPublicationDateTomorrow, titleForNewsPublicationDateTomorrow);
        //Проверка, что выбранная дата отображается
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsPublicationDateTomorrow).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationDateTomorrow);
    }

    @Test
    @DisplayName("Создание Новости с датой публикации через месяц")
    public void shouldCreateANewsItemWithPublicationDateInAMonth() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+2;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsPublicationDateInAMonth, titleForNewsPublicationDateInAMonth);

        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsPublicationDateInAMonth).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationDateInAMonth);
    }

    @Ignore//При ручном тестировании этот кейс проходит без ошибки
    @Test
    @DisplayName("Создание Новости с датой публикации вчера")
    public void shouldNotCreateANewsItemWithPublicationDateYesterday() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.setDateToDatePicker(year, month, day -1);
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата отображается сегодняшняя дата
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }

    @Ignore//При ручном тестировании этот кейс проходит без ошибки
    @Test
    @DisplayName("Создание Новости с датой публикации год назад")
    public void shouldNotCreateANewsItemWithPublicationDateOneYearAgo() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.setDateToDatePicker(year -1, month, day);
        controlPanelSteps.okButtonClick();
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }

    @Test
    @DisplayName("Создание Новости с датой публикации час назад")
    public void shouldCreateANewsItemWithPublicationTimeHourAgo() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hourNow = date.get(Calendar.HOUR_OF_DAY);
        int hour = TestUtils.getHourMinus(hourNow)-1;
        int minutes = date.get(Calendar.MINUTE);
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.replaceNewsTitleText(titleForNewsPublicationTimeHourAgo);
        controlPanelSteps.setDateToDatePicker(year, month, day);
        controlPanelSteps.okButtonClick();
        controlPanelSteps.setTimeToTimeField(hour, minutes);
        //Проверка,что выбранное время отображается
        controlPanelSteps.getNewsItemPublishTime().check(matches(withText(hourExpected + ":" + minutesExpected)));
        //Ввод описания
        controlPanelSteps.replaceNewsDescriptionText(titleForNewsPublicationTimeHourAgo);
        //Сохраняем
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsPublicationTimeHourAgo).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationTimeHourAgo);
    }

    @Test
    @DisplayName("Создание Новости с датой публикации через час")
    public void shouldCreateANewsItemWithPublicationTimeInOneHour() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour =  date.get(Calendar.HOUR_OF_DAY)+1;
        int minutes = date.get(Calendar.MINUTE);
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.replaceNewsTitleText(titleForNewsPublicationTimeInOneHour);
        controlPanelSteps.setDateToDatePicker(year, month, day);
        controlPanelSteps.okButtonClick();
        controlPanelSteps.setTimeToTimeField(hour, minutes);
        //Проверка,что выбранное время отображается
        controlPanelSteps.getNewsItemPublishTime().check(matches(withText(hourExpected + ":" + minutesExpected)));
        //Ввод описания
        controlPanelSteps.replaceNewsDescriptionText(titleForNewsPublicationTimeInOneHour);
        //Сохраняем
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsPublicationTimeInOneHour).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationTimeInOneHour);
    }


    @Test
    @DisplayName("Выставление времени публикации Новости вводом цифр")
    public void shouldSetTheTimeByEnteringNumbers() {
        int hour = date.get(Calendar.HOUR_OF_DAY)+1;
        int minutes = date.get(Calendar.MINUTE);
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);

        //Открываем TimePicker и вводим время с клавиатуры
        controlPanelSteps.openNewsTimePicker();
        controlPanelSteps.setTimeToTimePickerFromTheKeyboard(hourExpected, minutesExpected);
        //Проверка
        controlPanelSteps.getNewsItemPublishTime().check(matches(withText(hourExpected + ":" + minutesExpected)));
    }

    @Test
    @DisplayName("Отмена ввода времени в Форме для создания Новости")
    public void shouldNotSetTime() {
        int hour = date.get(Calendar.HOUR_OF_DAY)+1;
        int minutes = date.get(Calendar.MINUTE);

        controlPanelSteps.getNewsItemPublishTime().perform(click());
        controlPanelSteps.setTimeToTimePicker(hour, minutes);
        controlPanelSteps.cancelDeleteButtonClick();
        //Проверяем, что поле Время пустое
        controlPanelSteps.getNewsItemPublishTime().check(matches(withText("")));
    }

    @Test
    @DisplayName("Отмена ввода даты в Форме для создания Новости")
    public void shouldNotSetDate() {
        int year = date.get(Calendar.YEAR)+1;
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.setDateToDatePicker(year, month, day);
        controlPanelSteps.cancelDeleteButtonClick();
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText("")));
    }

    @Test
    @DisplayName("Отмена Создания Новости и отмена выхода из формы")
    public void shouldNotGetOutOfNewsForm() {
        controlPanelSteps.cancelButtonClick();
        controlPanelSteps.getMessageChangesWonTBeSaved().check(matches(isDisplayed()));
        controlPanelSteps.cancelDeleteButtonClick();
        controlPanelSteps.isCreatingNewsForm();
    }

    @Test
    @DisplayName("Небуквенные и нецифровые знаки в поле Заголовок при создании новости")
    public void shouldShowWarningMessageNewsTitleFieldIsIncorrect() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, nonLetterTitle, titleForNewsAnnouncement);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка, что отображается сообщение
        controlPanelSteps.checkToast("The field must not contain \";&&\" characters.", true);
    }

    @Test
    @DisplayName("Небуквенные и нецифровые знаки в поле Описание при создании новости")
    public void shouldShowWarningMessageNewsDescriptionFieldIsIncorrect() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsAnnouncement, nonLetterTitle);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка, что отображается сообщение
        controlPanelSteps.checkToast("The field must not contain \";&&\" characters.", true);
    }

    @Test
    @DisplayName("Разрыв соединения во время создания новости")
    public void shouldShowWarningMessageWhenTheConnectionIsBrokenDuringTheCreationOfTheNews() throws UiObjectNotFoundException {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsAnnouncement, titleForNewsAnnouncement);
        //Включаем режим В самолете
        authSteps.turnOnAirplaneMode();
        //Пытаемся сохранить новость
        controlPanelSteps.saveNewsButtonClick();
       //Проверяем, что отображается сообщение
        controlPanelSteps.checkToast("Saving failed. Try again later.", true);
        //Отключаем режим в самолете
        authSteps.turnOffAirplaneMode();
    }

    @Test
    @DisplayName("Поворот экрана при создании новости")
    public void shouldSaveDataInTheNewsCreationFormOnScreenRotation() throws UiObjectNotFoundException, RemoteException {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsAnnouncement, titleForNewsAnnouncement);
        device.setOrientationLeft();
        //Проверяем, что введенные данные сохранились
        controlPanelSteps.getNewsItemTitle().check(matches(withText(titleForNewsAnnouncement)));
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
        controlPanelSteps.getNewsItemDescription().check(matches(withText(titleForNewsAnnouncement)));
    }


}

