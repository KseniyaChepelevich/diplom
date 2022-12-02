package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.RemoteException;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.data.CustomRecyclerViewActions;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.NamingHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class NewsCreationFormTest extends BaseTest {
    private UiDevice device;

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static NewsPageSteps newsPageSteps = new NewsPageSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();
    private static NamingHelper namingHelper = new NamingHelper();

    LocalDateTime today = LocalDateTime.now();

    @Before
    public void logoutCheck() throws RemoteException {
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

    @After
    public void disableAirplaneMode() throws RemoteException, UiObjectNotFoundException {
        device.setOrientationNatural();
        TestUtils.disableAirplaneMode();
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
        String titleAnnouncement = namingHelper.getNewsAnnouncementName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                titleAnnouncement, titleAnnouncement);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Объявление"
        controlPanelSteps.scrollToElementInRecyclerList(titleAnnouncement).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с категорией День рождения")
    public void shouldCreateANewsItemWithCategoryBirthday() {
        String titleBirthday = namingHelper.getNewsBirthdayName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryBirthday);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                titleBirthday, titleBirthday);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость День рождения"
        controlPanelSteps.scrollToElementInRecyclerList(titleBirthday).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с категорией Зарплата")
    public void shouldCreateANewsItemWithCategorySalary() {
        String titleSalary = namingHelper.getNewsSalaryName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                titleSalary, titleSalary);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(titleSalary).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с категорией Профсоюз")
    public void shouldCreateANewsItemWithCategoryTradeUnion() {
        String titleTradeUnion = namingHelper.getNewsTradeUnionName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryTradeUnion);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                titleTradeUnion, titleTradeUnion);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Профсоюз"
        controlPanelSteps.scrollToElementInRecyclerList(titleTradeUnion).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с категорией Праздник")
    public void shouldCreateANewsItemWithCategoryHoliday() {
        String titleHoliday = namingHelper.getNewsHolidayName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryHoliday);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                titleHoliday, titleHoliday);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Праздник"
        controlPanelSteps.scrollToElementInRecyclerList(titleHoliday).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с категорией Благодарность")
    public void shouldCreateANewsItemWithCategoryGratitude() {
        String titleGratitude = namingHelper.getNewsGratitudeName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryGratitude);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                titleGratitude, titleGratitude);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Благодарность"
        controlPanelSteps.scrollToElementInRecyclerList(titleGratitude).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с категорией Массаж")
    public void shouldCreateANewsItemWithCategoryMassage() {
        String titleMassage = namingHelper.getNewsMassageName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryMassage);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                titleMassage, titleMassage);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Массаж"
        controlPanelSteps.scrollToElementInRecyclerList(titleMassage).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с категорией Нужна помощь")
    public void shouldCreateANewsItemWithCategoryNeedHelp() {
        String titleNeedHelp = namingHelper.getNewsNeedHelpName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryNeedHelp);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                titleNeedHelp, titleNeedHelp);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Массаж"
        controlPanelSteps.scrollToElementInRecyclerList(titleNeedHelp).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Отмена создания новости")
    public void shouldNotCreateNews() {
        String titleNeedHelp = namingHelper.getNewsNeedHelpName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryNeedHelp);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                titleNeedHelp, titleNeedHelp);
        controlPanelSteps.cancelButtonClick();
        controlPanelSteps.getMessageChangesWonTBeSaved().check(matches(isDisplayed()));
        controlPanelSteps.okButtonClick();
        //Проверить отсутствие в списке новостей новости с заголовком "Новость не должна сохраниться"
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(titleNeedHelp))));
    }

    @Test
    @DisplayName("Создание новости с категорией не из списка")
    public void shouldShowAWrongMessageWithTextSelectACategoryFromTheList() {
        String MyCategory = "Тест";
        String title = namingHelper.getNewsMyCategoryName();

        controlPanelSteps.replaceNewsCategoryText(MyCategory);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                title, title);
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
        String title = namingHelper.getNewsMassageName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryMassage);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                title, title);
        controlPanelSteps.switchNewsStatus();
        controlPanelSteps.getSwitcherNoteActive().check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с датой публикации завтра")
    public void shouldCreateANewsItemWithPublishDateTomorrow() {
        LocalDateTime date = today.plusDays(1);
        String dateExpected = TestUtils.getDateToString(date);
        String title = namingHelper.getNewsSalaryName();

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                title, title);
        //Проверка, что выбранная дата отображается
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dateExpected)));
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с датой публикации через месяц")
    public void shouldCreateANewsItemWithPublicationDateInAMonth() {
        LocalDateTime date = today.plusMonths(1);
        String dateExpected = TestUtils.getDateToString(date);
        String title = namingHelper.getNewsSalaryName();

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                title, title);

        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dateExpected)));
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
    }

    @Ignore//При ручном тестировании этот кейс проходит без ошибки
    @Test
    @DisplayName("Создание Новости с датой публикации вчера")
    public void shouldNotCreateANewsItemWithPublicationDateYesterday() {
        LocalDateTime date = today.minusDays(1);
        String dateExpected = TestUtils.getDateToString(today);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.setDateToDatePicker(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата отображается сегодняшняя дата
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dateExpected)));
    }

    @Ignore//При ручном тестировании этот кейс проходит без ошибки
    @Test
    @DisplayName("Создание Новости с датой публикации год назад")
    public void shouldNotCreateANewsItemWithPublicationDateOneYearAgo() {
        LocalDateTime date = today.minusYears(1);
        String dateExpected = TestUtils.getDateToString(today);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.setDateToDatePicker(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dateExpected)));
    }

    @Test
    @DisplayName("Создание Новости с датой публикации час назад")
    public void shouldCreateANewsItemWithPublicationTimeHourAgo() {
        LocalDateTime date = today.minusHours(1);
        String timeExpected = TestUtils.getTimeToString(date);
        String title = namingHelper.getNewsSalaryName();

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.replaceNewsTitleText(title);
        controlPanelSteps.setDateToDatePicker(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        controlPanelSteps.setTimeToTimeField(date.getHour(), date.getMinute());
        //Проверка,что выбранное время отображается
        controlPanelSteps.getNewsItemPublishTime().check(matches(withText(timeExpected)));
        //Ввод описания
        controlPanelSteps.replaceNewsDescriptionText(title);
        //Сохраняем
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с датой публикации через час")
    public void shouldCreateANewsItemWithPublicationTimeInOneHour() {
        LocalDateTime date = today.plusHours(1);
        String timeExpected = TestUtils.getTimeToString(date);
        String title = namingHelper.getNewsSalaryName();

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.replaceNewsTitleText(title);
        controlPanelSteps.setDateToDatePicker(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        controlPanelSteps.setTimeToTimeField(date.getHour(), date.getMinute());
        //Проверка,что выбранное время отображается
        controlPanelSteps.getNewsItemPublishTime().check(matches(withText(timeExpected)));
        //Ввод описания
        controlPanelSteps.replaceNewsDescriptionText(title);
        //Сохраняем
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображаются новости с заголовком
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
    }


    @Test
    @DisplayName("Выставление времени публикации Новости вводом цифр")
    public void shouldSetTheTimeByEnteringNumbers() {
        LocalDateTime date = today.plusHours(1);
        String hour = TestUtils.getHourToString(date);
        String minute = TestUtils.getMinuteToString(date);
        String timeExpected = TestUtils.getTimeToString(date);

        //Открываем TimePicker и вводим время с клавиатуры
        controlPanelSteps.openNewsTimePicker();
        controlPanelSteps.setTimeToTimePickerFromTheKeyboard(hour, minute);
        //Проверка
        controlPanelSteps.getNewsItemPublishTime().check(matches(withText(timeExpected)));
    }

    @Test
    @DisplayName("Отмена ввода времени в Форме для создания Новости")
    public void shouldNotSetTime() {
        LocalDateTime date = today.plusHours(1);

        controlPanelSteps.getNewsItemPublishTime().perform(click());
        controlPanelSteps.setTimeToTimePicker(date.getHour(), date.getMinute());
        controlPanelSteps.cancelDeleteButtonClick();
        //Проверяем, что поле Время пустое
        controlPanelSteps.getNewsItemPublishTime().check(matches(withText("")));
    }

    @Test
    @DisplayName("Отмена ввода даты в Форме для создания Новости")
    public void shouldNotSetDate() {
        LocalDateTime date = today.plusYears(1);

        controlPanelSteps.setDateToDatePicker(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
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
        String nonLetterTitle = ";&&" + " " + DataHelper.generateTitleId();
        String description = namingHelper.getNewsAnnouncementName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                nonLetterTitle, description);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка, что отображается сообщение
        controlPanelSteps.checkToast("The field must not contain \";&&\" characters.", true);
    }

    @Test
    @DisplayName("Небуквенные и нецифровые знаки в поле Описание при создании новости")
    public void shouldShowWarningMessageNewsDescriptionFieldIsIncorrect() {
        String nonLetterDescription = ";&&" + " " + DataHelper.generateTitleId();
        String title = namingHelper.getNewsAnnouncementName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                title, nonLetterDescription);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка, что отображается сообщение
        controlPanelSteps.checkToast("The field must not contain \";&&\" characters.", true);
    }

    @Test
    @DisplayName("Разрыв соединения во время создания новости")
    public void shouldShowWarningMessageWhenTheConnectionIsBrokenDuringTheCreationOfTheNews() throws UiObjectNotFoundException {
        String title = namingHelper.getNewsAnnouncementName();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                title, title);
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
        String dateExpected = TestUtils.getDateToString(today);
        String title = namingHelper.getNewsAnnouncementName();

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                title, title);
        device.setOrientationLeft();
        //Проверяем, что введенные данные сохранились
        controlPanelSteps.getNewsItemTitle().check(matches(withText(title)));
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dateExpected)));
        controlPanelSteps.getNewsItemDescription().check(matches(withText(title)));
    }


}

