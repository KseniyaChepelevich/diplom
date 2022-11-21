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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.CreatingClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class ClaimsCreationFormTest extends BaseTest {

    private UiDevice device;
    private static final int LAUNCH_TIMEOUT = 10000;
    private static final String BASIC_PACKAGE = "ru.iteco.fmhandroid";

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    private static CreatingClaimsSteps creatingClaimsSteps = new CreatingClaimsSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    Calendar date = Calendar.getInstance();

    String titleForTheTestClaim = "Заголовок" + " " + DataHelper.generateTitleId();
    String titleStartsWithASpace = " Заголовок" + " " + DataHelper.generateTitleId();
    String title50Characters = "Заголовок" + " " + DataHelper.generateTitleId() + " " + "50з";
    String title49Characters = "Заголовок" + " " + DataHelper.generateTitleId() + " " + "49";
    String title51Characters = "Заголовок" + " " + DataHelper.generateTitleId() + " " + "51зн";
    String titleExecutorNotListed = "Заголовок" + " " + DataHelper.generateTitleId();


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
        mainPageSteps.openClaimsPageThroughTheMainMenu();
        claimsPageSteps.openCreatingClaimsCard();
    }

    @Test
    @DisplayName("Создание заявки")
    public void shouldCreateAClaim() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку

        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaim).check(matches(isDisplayed()));
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
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaim).check(matches(isDisplayed()));
    }

    @Ignore //Выбирает вчерашнюю дату. При ручном тестировании невозможно выбрать вчерашнюю дату
    @Test
    @DisplayName("Выбор вчерашней даты в заявке")
    public void shouldNotChoosePublicationDateYesterday() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);
        //Выбираем в календаре вчерашнюю дату
        creatingClaimsSteps.setDateToDatePicker(year, month, day - 1);
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата отображается сегодняшняя дата
        creatingClaimsSteps.getClaimDateInPlane().check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }

    @Ignore//Выбирает вчерашнюю дату. При ручном тестировании невозможно выбрать вчерашнюю дату
    @Test
    @DisplayName("Выбор даты год назад в заявке")
    public void shouldNotChoosePublicationDateYearAgo() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day + 1);
        //Выбираем в календаре дату год назад
        creatingClaimsSteps.setDateToDatePicker(year - 1, month, day);
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата отображается сегодняшняя дата
        creatingClaimsSteps.getClaimDateInPlane().check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }

    @Test
    @DisplayName("Выбор даты завтра в заявке")
    public void shouldChoosePublicationDateTomorrow() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH) + 1;
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);
        //Выбираем в календаре дату завтра
        creatingClaimsSteps.setDateToDatePicker(year, month, day);
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата отображается выбранная дата
        creatingClaimsSteps.getClaimDateInPlane().check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }

    @Test
    @DisplayName("Выбор даты через год в заявке")
    public void shouldChoosePublicationDateInAYear() {
        int year = date.get(Calendar.YEAR) + 1;
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);
        //Выбираем в календаре дату через год
        creatingClaimsSteps.setDateToDatePicker(year, month, day);
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата отображается выбранная дата
        creatingClaimsSteps.getClaimDateInPlane().check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }


    @Test
    @DisplayName("Создание заявки. В поле Время  на час больше текущего")
    public void shouldChoosePublicationTimeInOneHour() {
        int hour = date.get(Calendar.HOUR_OF_DAY) + 1;
        int minutes = date.get(Calendar.MINUTE);
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);

        //Выбираем в часах время на час больше текущего
        creatingClaimsSteps.setTimeToTimeField(hour, minutes);

        //Проверяем, что в поле Время отображается выбранное время
        creatingClaimsSteps.getClaimTime().check(matches(withText(hourExpected + ":" + minutesExpected)));
    }


    @Test
    @DisplayName("Создание заявки. В поле Время  на час меньше текущего")
    public void shouldChoosePublicationTimeHourAgo() {
        int hourNow = date.get(Calendar.HOUR_OF_DAY);
        int hour = TestUtils.getHourMinus(hourNow) - 1;
        int minutes = date.get(Calendar.MINUTE);
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);
        //Выбираем в часах время на час меньше текущего
        creatingClaimsSteps.setTimeToTimeField(hour, minutes);
        //Проверяем, что в поле Время отображается выбранное время
        creatingClaimsSteps.getClaimTime().check(matches(withText(hourExpected + ":" + minutesExpected)));
    }


    @Test
    @DisplayName("Создание заявки. В поле Время  на минуту больше текущего")
    public void shouldChoosePublicationTimeInOneMinute() {
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minutes = date.get(Calendar.MINUTE) + 1;
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);
        //Выбираем в часах время на минуту больше текущего
        creatingClaimsSteps.setTimeToTimeField(hour, minutes);
        //Проверяем, что в поле Время отображается выбранное время
        creatingClaimsSteps.getClaimTime().check(matches(withText(hourExpected + ":" + minutesExpected)));
    }

    //Нестабильный тест. время  ожидаемое от фактического может отличаться в минуту и тест будет падать

    @Test
    @DisplayName("Создание заявки. В поле Время  на минуту меньше текущего")
    public void shouldChoosePublicationTimeMinuteAgo() {
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minutesNow = date.get(Calendar.MINUTE);
        int minutes = TestUtils.getMinutesMinus(minutesNow) - 1;

        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);

        //Выбираем в часах время на минуту меньше текущего
        creatingClaimsSteps.setTimeToTimeField(hour, minutes);
        //Проверяем, что в поле Время отображается выбранное время
        creatingClaimsSteps.getClaimTime().check(matches(withText(hourExpected + ":" + minutesExpected)));
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
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String titleWithoutSpace = titleStartsWithASpace.substring(1);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, titleStartsWithASpace, titleStartsWithASpace);
        //claimsPageElements.titleClaimField.check(matches(withText(titleWithoutSpace)));
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(titleWithoutSpace).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание заявки с заголовком из 50 знаков")
    public void shouldCreateAClaimWithATitleWith50Characters() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, title50Characters, title50Characters);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(title50Characters).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание заявки с заголовком из 49 знаков")
    public void shouldCreateAClaimWithATitleWith49Characters() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, title49Characters, title49Characters);
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(title49Characters).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание заявки с заголовком из 51 знаков")
    public void shouldCreateAClaimWithATitleWith51Characters() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String titleWhichToBeKept = title51Characters.substring(0, 50);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, title51Characters, title51Characters);
        //Проверка, что в поле Title отображается только 50 символов
        claimsPageSteps.getTitleClaim().check(matches(withText(titleWhichToBeKept)));
        controlPanelSteps.saveNewsButtonClick();
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(titleWhichToBeKept).check(matches(isDisplayed()));
    }


    @Test
    @DisplayName("Создание заявки. Исполнитель  не из списка")
    public void shouldCreateAClaimWithoutAnExecutor() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String MayExecutor = "Козлов Константин Анатольевич";
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.replaceTextClaimExecutor(MayExecutor);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, titleExecutorNotListed, titleExecutorNotListed);
        controlPanelSteps.saveNewsButtonClick();
        SystemClock.sleep(3000);
        //Проверка что отображается заявка. Открытие ее
        claimsPageSteps.scrollToElementInRecyclerList(titleExecutorNotListed).check(matches(isDisplayed()));
        //Проверка что исполнитель не назначен
        claimsPageSteps.getItemClaimExecutorName(titleExecutorNotListed).check(matches(withText("")));
    }

    @Test
    @DisplayName("Создание заявки.  В заголовке небуквенные и нецифровые симовлы")
    public void shouldNotDisplayNonAlphabeticAndNonNumericCharactersInTheFieldTitle() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        String nonLetterTitle = ";&&";
        //Создать заявку
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, nonLetterTitle, titleForTheTestClaim);
        //Проверка, что в поле Заголовок не отображается введенный текст
        claimsPageSteps.getTitleClaim().check(matches(withText("")));
        controlPanelSteps.saveNewsButtonClick();
        //Проверка, что появляется диалоговое окно  с текстом
        creatingClaimsSteps.isFillEmptyFieldsMessage();
    }

    @Test
    @DisplayName("Создание заявки.  Описание заявки небуквенные и нецифровые символы")
    public void shouldNotDisplayNonAlphabeticAndNonNumericCharactersInTheFieldDescription() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        String nonLetterDescription = ";&&";
        //Создать заявку
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, titleForTheTestClaim, nonLetterDescription);
        //Проверка, что в поле Заголовок не отображается введенный текст
        claimsPageSteps.getDescriptionClaimField().check(matches(withText("")));
        controlPanelSteps.saveNewsButtonClick();
        //Проверка, что появляется диалоговое окно  с текстом
        creatingClaimsSteps.isFillEmptyFieldsMessage();
    }

    @Test
    @DisplayName("Сворачивание приложения во время создания заявки")
    public void shouldOpenTheAppOnTheClaimAfterPressHome() throws UiObjectNotFoundException {
        creatingClaimsSteps.replaceTitleClaimText(titleForTheTestClaim);
        device.pressHome();
        TestUtils.waitForPackage(BASIC_PACKAGE);
        //Проверка что отображается заявка, которую начали создавать
        //Тест падает, потому как сначала открывается главная страница и после ожидания отктывается созаваемая заявка
        SystemClock.sleep(10000);
        //claimsPageSteps.isClaimsForm();
        claimsPageSteps.getTitleClaim().check(matches(withText(titleForTheTestClaim)));
    }

    @Test
    @DisplayName("Разрыв соединения во время создания заявки")
    public void shouldShowWarningMessageWhenTheConnectionIsBrokenDuringTheCreationOfTheClaim() throws UiObjectNotFoundException {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        //Включаем режим В самолете
        authSteps.turnOnAirplaneMode();
        //Пытаемся сохранить заявку
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем, что отображается сообщение
        controlPanelSteps.checkToast("Something went wrong. Try again later.", true);
        //Отключаем режим в самолете
        authSteps.turnOffAirplaneMode();
    }

    @Test
    @DisplayName("Поворот экрана при создании заявки")
    public void shouldSaveDataOnScreenRotation() throws RemoteException {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);

        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        //Поворачиваем экран
        device.setOrientationLeft();
        //Проверяем, что введенные данные сохранились
        claimsPageSteps.getTitleClaim().check(matches(withText(titleForTheTestClaim)));
        creatingClaimsSteps.getClaimDateInPlane().check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
        creatingClaimsSteps.getClaimTime().check(matches(withText(hourExpected + ":" + minutesExpected)));
        claimsPageSteps.getDescriptionClaimField().check(matches(withText(titleForTheTestClaim)));
        device.setOrientationNatural();
    }


}
