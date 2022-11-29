package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.SystemClock;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.Calendar;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.NamingHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.CreatingClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class ClaimsCreationFormTest extends BaseTest {

    private UiDevice device;

    private static final String BASIC_PACKAGE = "ru.iteco.fmhandroid";

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    private static CreatingClaimsSteps creatingClaimsSteps = new CreatingClaimsSteps();
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
        mainPageSteps.openClaimsPageThroughTheMainMenu();
        claimsPageSteps.openCreatingClaimsCard();
    }

    @After
    public void disableAirplaneMode() throws RemoteException, UiObjectNotFoundException {
        device.setOrientationNatural();
        TestUtils.disableAirplaneMode();
    }

    @Test
    @DisplayName("Создание заявки")
    public void shouldCreateAClaim() {
        String title = namingHelper.getClaimInProgressName();
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
    }


    @Test
    @DisplayName("Сохранение пустой заявки")
    public void shouldNotCreateEmptyClaim() {
        controlPanelSteps.saveNewsButtonClick();
        creatingClaimsSteps.isFillEmptyFieldsMessage();
        controlPanelSteps.okButtonClick();
        creatingClaimsSteps.isWrongEmptyFormClaim();
    }

    @Test
    @DisplayName("Сохранение заявки без исполнителя")
    public void shouldCreateAClaimWithoutChoosingExecutor() {
        String title = namingHelper.getClaimOpenName();
        //Создать заявку
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
    }

    @Ignore //Выбирает вчерашнюю дату. При ручном тестировании невозможно выбрать вчерашнюю дату
    @Test
    @DisplayName("Выбор вчерашней даты в заявке")
    public void shouldNotChoosePublicationDateYesterday() {
        LocalDateTime date = today.minusDays(1);
        String dateExpected = TestUtils.getDateToString(today);
        //Выбираем в календаре вчерашнюю дату
        creatingClaimsSteps.setDateToDatePicker(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата отображается сегодняшняя дата
        creatingClaimsSteps.getClaimDateInPlane().check(matches(withText(dateExpected)));
    }

    @Ignore//Выбирает вчерашнюю дату. При ручном тестировании невозможно выбрать вчерашнюю дату
    @Test
    @DisplayName("Выбор даты год назад в заявке")
    public void shouldNotChoosePublicationDateYearAgo() {
        LocalDateTime date = today.minusYears(1);
        String dateExpected = TestUtils.getDateToString(today);

        //Выбираем в календаре дату год назад
        creatingClaimsSteps.setDateToDatePicker(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата отображается сегодняшняя дата
        creatingClaimsSteps.getClaimDateInPlane().check(matches(withText(dateExpected)));
    }

    @Test
    @DisplayName("Выбор даты завтра в заявке")
    public void shouldChoosePublicationDateTomorrow() {
        LocalDateTime date = today.plusDays(1);
        String dateExpected = TestUtils.getDateToString(date);

        //Выбираем в календаре дату завтра
        creatingClaimsSteps.setDateToDatePicker(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата отображается выбранная дата
        creatingClaimsSteps.getClaimDateInPlane().check(matches(withText(dateExpected)));
    }

    @Test
    @DisplayName("Выбор даты через год в заявке")
    public void shouldChoosePublicationDateInAYear() {
        LocalDateTime date = today.plusYears(1);
        String dateExpected = TestUtils.getDateToString(date);

        //Выбираем в календаре дату через год
        creatingClaimsSteps.setDateToDatePicker(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата отображается выбранная дата
        creatingClaimsSteps.getClaimDateInPlane().check(matches(withText(dateExpected)));
    }


    @Test
    @DisplayName("Создание заявки. В поле Время  на час больше текущего")
    public void shouldChoosePublicationTimeInOneHour() {
        LocalDateTime date = today.plusHours(1);
        String timeExpected = TestUtils.getTimeToString(date);

        //Выбираем в часах время на час больше текущего
        creatingClaimsSteps.setTimeToTimeField(date.getHour(), date.getMinute());
        //Проверяем, что в поле Время отображается выбранное время
        creatingClaimsSteps.getClaimTime().check(matches(withText(timeExpected)));
    }

    @Test
    @DisplayName("Создание заявки. В поле Время  на час меньше текущего")
    public void shouldChoosePublicationTimeHourAgo() {
        LocalDateTime date = today.minusHours(1);
        String timeExpected = TestUtils.getTimeToString(date);
        //Выбираем в часах время на час меньше текущего
        creatingClaimsSteps.setTimeToTimeField(date.getHour(), date.getMinute());
        //Проверяем, что в поле Время отображается выбранное время
        creatingClaimsSteps.getClaimTime().check(matches(withText(timeExpected)));
    }


    @Test
    @DisplayName("Создание заявки. В поле Время  на минуту больше текущего")
    public void shouldChoosePublicationTimeInOneMinute() {
        LocalDateTime date = today.plusMinutes(1);
        String timeExpected = TestUtils.getTimeToString(date);
        //Выбираем в часах время на минуту больше текущего
        creatingClaimsSteps.setTimeToTimeField(date.getHour(), date.getMinute());
        //Проверяем, что в поле Время отображается выбранное время
        creatingClaimsSteps.getClaimTime().check(matches(withText(timeExpected)));
    }

    @Test
    @DisplayName("Создание заявки. В поле Время  на минуту меньше текущего")
    public void shouldChoosePublicationTimeMinuteAgo() {
        LocalDateTime date = today.minusMinutes(1);
        String timeExpected = TestUtils.getTimeToString(date);

        //Выбираем в часах время на минуту меньше текущего
        creatingClaimsSteps.setTimeToTimeField(date.getHour(), date.getMinute());
        //Проверяем, что в поле Время отображается выбранное время
        creatingClaimsSteps.getClaimTime().check(matches(withText(timeExpected)));
    }

    @Test
    @DisplayName("Создание заявки. В поле Время  нереальное время")
    public void shouldShowTextEnteredInvalidValue() {
        String hour = "99";
        String minutes = "99";
        //Вводим в часах нереальное время
        creatingClaimsSteps.openClaimTimePicker();
        controlPanelSteps.setTimeToTimePickerFromTheKeyboard(hour, minutes);
        //Проверяем, что отображается текст "Enter a valid time"
        claimsPageSteps.getLabelError().check(matches(isDisplayed()));

    }

    @Test
    @DisplayName("Создание заявки с заголовком начинающимся с пробела")
    public void shouldCreateAClaimWithATitleStartsWithoutASpaceAtTheBeginning() {
        String titleStartsWithASpace = namingHelper.getClaimTitleWithASpace();
        String titleWithoutSpace = titleStartsWithASpace.substring(1);

        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), titleStartsWithASpace, titleStartsWithASpace);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(titleWithoutSpace).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание заявки с заголовком из 50 знаков")
    public void shouldCreateAClaimWithATitleWith50Characters() {
        String title50Characters = namingHelper.getClaimTitle50Characters();
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title50Characters, title50Characters);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(title50Characters).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание заявки с заголовком из 49 знаков")
    public void shouldCreateAClaimWithATitleWith49Characters() {
        String title49Characters = namingHelper.getClaimTitle49Characters();
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title49Characters, title49Characters);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(title49Characters).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание заявки с заголовком из 51 знаков")
    public void shouldCreateAClaimWithATitleWith51Characters() {
        String title51Characters = namingHelper.getClaimTitle51Characters();
        String titleWhichToBeKept = title51Characters.substring(0, 50);

        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title51Characters, title51Characters);
        //Проверка, что в поле Title отображается только 50 символов
        claimsPageSteps.getTitleClaim().check(matches(withText(titleWhichToBeKept)));
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(titleWhichToBeKept).check(matches(isDisplayed()));
    }


    @Test
    @DisplayName("Создание заявки. Исполнитель  не из списка")
    public void shouldCreateAClaimWithoutAnExecutor() {
        String title = namingHelper.getClaimInProgressName();
        String MayExecutor = "Козлов Константин Анатольевич";

        //Создать заявку
        creatingClaimsSteps.replaceTextClaimExecutor(MayExecutor);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка. Открытие ее
        claimsPageSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Проверка что исполнитель не назначен
        claimsPageSteps.getItemClaimExecutorName(title).check(matches(withText("")));
    }

    @Test
    @DisplayName("Создание заявки.  В заголовке небуквенные и нецифровые симовлы")
    public void shouldNotDisplayNonAlphabeticAndNonNumericCharactersInTheFieldTitle() {
        String description = namingHelper.getClaimOpenName();
        String nonLetterTitle = ";&&";

        //Создать заявку
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), nonLetterTitle, description);
        //Проверка, что в поле Заголовок не отображается введенный текст
        claimsPageSteps.getTitleClaim().check(matches(withText("")));
        controlPanelSteps.saveNewsButtonClick();
        //Проверка, что появляется диалоговое окно  с текстом
        creatingClaimsSteps.isFillEmptyFieldsMessage();
    }

    @Test
    @DisplayName("Создание заявки.  Описание заявки небуквенные и нецифровые символы")
    public void shouldNotDisplayNonAlphabeticAndNonNumericCharactersInTheFieldDescription() {
        String title = namingHelper.getClaimOpenName();
        String nonLetterDescription = ";&&";

        //Создать заявку
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, nonLetterDescription);
        //Проверка, что в поле Заголовок не отображается введенный текст
        claimsPageSteps.getDescriptionClaimField().check(matches(withText("")));
        controlPanelSteps.saveNewsButtonClick();
        //Проверка, что появляется диалоговое окно  с текстом
        creatingClaimsSteps.isFillEmptyFieldsMessage();
    }

    @Test
    @DisplayName("Сворачивание приложения во время создания заявки")
    public void shouldOpenTheAppOnTheClaimAfterPressHome() throws UiObjectNotFoundException {
        String title = namingHelper.getClaimOpenName();
        creatingClaimsSteps.replaceTitleClaimText(title);
        device.pressHome();
        TestUtils.waitForPackage(BASIC_PACKAGE);
        //Проверка что отображается заявка, которую начали создавать
        //Тест падает, потому как сначала открывается главная страница и после ожидания отктывается создаваемая заявка. Сколько бы не прибавлялось время ожидания элемента, тест падает.
        claimsPageSteps.getTitleClaim().check(matches(withText(title)));
    }

    @Test
    @DisplayName("Разрыв соединения во время создания заявки")
    public void shouldShowWarningMessageWhenTheConnectionIsBrokenDuringTheCreationOfTheClaim() throws UiObjectNotFoundException {
        String title = namingHelper.getClaimInProgressName();
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        //Включаем режим В самолете
        authSteps.turnOnAirplaneMode();
        //Пытаемся сохранить заявку
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем, что отображается сообщение
        controlPanelSteps.checkToast("Something went wrong. Try again later.", true);
        //Отключаем режим в самолете
        TestUtils.disableAirplaneMode();
    }

    @Test
    @DisplayName("Поворот экрана при создании заявки")
    public void shouldSaveDataOnScreenRotation() throws RemoteException {
        String title = namingHelper.getClaimInProgressName();
        String dateExpected = TestUtils.getDateToString(today);
        String timeExpected = TestUtils.getTimeToString(today);

        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        //Поворачиваем экран
        device.setOrientationLeft();
        //Проверяем, что введенные данные сохранились
        claimsPageSteps.getTitleClaim().check(matches(withText(title)));
        creatingClaimsSteps.getClaimDateInPlane().check(matches(withText(dateExpected)));
        creatingClaimsSteps.getClaimTime().check(matches(withText(timeExpected)));
        claimsPageSteps.getDescriptionClaimField().check(matches(withText(title)));
        device.setOrientationNatural();
    }


}
