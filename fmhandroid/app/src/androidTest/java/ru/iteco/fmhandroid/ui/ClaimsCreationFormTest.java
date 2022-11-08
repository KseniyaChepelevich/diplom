package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.page.ClaimsPageElements;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.CreatingClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class ClaimsCreationFormTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    CreatingClaimsSteps creatingClaimsSteps = new CreatingClaimsSteps();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    Calendar date = Calendar.getInstance();

    String titleForTheTestClaim = "Заголовок" + " " + DataHelper.generateTitleId();
    String titleStartsWithASpace = " Заголовок" + " " + DataHelper.generateTitleId();
    String title50Characters = "Заголовок" + " " + DataHelper.generateTitleId() + " " + "50з";
    String title49Characters = "Заголовок" + " " + DataHelper.generateTitleId() + " " + "49";
    String title51Characters = "Заголовок" + " " + DataHelper.generateTitleId() + " " + "51зн";
    String titleExecutorNotListed = "Заголовок" + " " + DataHelper.generateTitleId();



    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        try {
            authSteps.isAuthScreen();
        } catch (PerformException e) {
            mainPageSteps.clickLogOutBut();
        }
        authSteps.authWithValidData(authInfo());
        mainPageSteps.isMainPage();
        mainPageSteps.openClaimsPageThroughTheMainMenu();
        TestUtils.waitView(claimsPageSteps.addNewClaimBut).perform(click());
    }

    @Test
    @DisplayName("Создание заявки")
    public void shouldCreateAClaim() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку

        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
                //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaim).check(matches(isDisplayed()));
    }


    @Test
    @DisplayName("Сохранение пустой заявки")
    public void shouldNotCreateEmptyClaim() {
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        creatingClaimsSteps.isFillEmptyFieldsMessage();
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        creatingClaimsSteps.isWrongEmptyFormClaim();
    }

    @Test
    @DisplayName("Сохранение заявки без исполнителя")
    public void shouldCreateAClaimWithoutChoosingExecutor() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaim).check(matches(isDisplayed()));
    }

    //Выбирает вчерашнюю дату. При ручном тестировании невозможно выбрать вчерашнюю дату

    @Test
    @DisplayName("Выбор вчерашней даты в заявке")
    public void shouldNotChoosePublicationDateYesterday() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);
        //Выбираем в календаре вчерашнюю дату
        creatingClaimsSteps.setDateToDatePicker(year, month, day-1);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверяем, что в поле Дата отображается сегодняшняя дата
        TestUtils.waitView(claimsPageSteps.dateClaimField).check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }

    //Выбирает вчерашнюю дату. При ручном тестировании невозможно выбрать вчерашнюю дату

    @Test
    @DisplayName("Выбор даты год назад в заявке")
    public void shouldNotChoosePublicationDateYearAgo() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day+1);
        //Выбираем в календаре дату год назад
        creatingClaimsSteps.setDateToDatePicker(year-1, month, day);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверяем, что в поле Дата отображается сегодняшняя дата
        TestUtils.waitView(claimsPageSteps.dateClaimField).check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }

    @Test
    @DisplayName("Выбор даты завтра в заявке")
    public void shouldChoosePublicationDateTomorrow() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH)+1;
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);
        //Выбираем в календаре дату завтра
        creatingClaimsSteps.setDateToDatePicker(year, month, day);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверяем, что в поле Дата отображается выбранная дата
        TestUtils.waitView(claimsPageSteps.dateClaimField).check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }

    @Test
    @DisplayName("Выбор даты через год в заявке")
    public void shouldChoosePublicationDateInAYear() {
        int year = date.get(Calendar.YEAR)+1;
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);
        //Выбираем в календаре дату через год
        creatingClaimsSteps.setDateToDatePicker(year, month, day);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверяем, что в поле Дата отображается выбранная дата
        TestUtils.waitView(claimsPageSteps.dateClaimField).check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }



    @Test
    @DisplayName("Создание заявки. В поле Время  на час больше текущего")
    public void shouldChoosePublicationTimeInOneHour() {
        int hour = date.get(Calendar.HOUR_OF_DAY)+1;
        int minutes = date.get(Calendar.MINUTE);
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);

        //Выбираем в часах время на час больше текущего
        TestUtils.waitView(claimsPageSteps.timeClaimField).perform(click());
        TestUtils.waitView(controlPanelSteps.timePicker).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.timePicker).perform(setTime(hour, minutes));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверяем, что в поле Время отображается выбранное время
        TestUtils.waitView(claimsPageSteps.timeClaimField).check(matches(withText(hourExpected + ":" + minutesExpected)));
    }



    @Test
    @DisplayName("Создание заявки. В поле Время  на час меньше текущего")
    public void shouldChoosePublicationTimeHourAgo() {
        int hour = date.get(Calendar.HOUR_OF_DAY)-1;
        int minutes = date.get(Calendar.MINUTE);
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);
        //Выбираем в часах время на час меньше текущего
        TestUtils.waitView(claimsPageSteps.timeClaimField).perform(click());
        TestUtils.waitView(controlPanelSteps.timePicker).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.timePicker).perform(setTime(hour, minutes));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверяем, что в поле Время отображается выбранное время
        TestUtils.waitView(claimsPageSteps.timeClaimField).check(matches(withText(hourExpected + ":" + minutesExpected)));
    }



    @Test
    @DisplayName("Создание заявки. В поле Время  на минуту больше текущего")
    public void shouldChoosePublicationTimeInOneMinute() {
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minutes = date.get(Calendar.MINUTE)+1;
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);
        //Выбираем в часах время на минуту больше текущего
        TestUtils.waitView(claimsPageSteps.timeClaimField).perform(click());
        TestUtils.waitView(controlPanelSteps.timePicker).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.timePicker).perform(setTime(hour, minutes));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверяем, что в поле Время отображается выбранное время
        TestUtils.waitView(claimsPageSteps.timeClaimField).check(matches(withText(hourExpected + ":" + minutesExpected)));
    }

    //Нестабильный тест. время  ожидаемое от фактического может отличаться в минуту и тест будет падать

    @Test
    @DisplayName("Создание заявки. В поле Время  на минуту меньше текущего")
    public void shouldChoosePublicationTimeMinuteAgo() {
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minutes = date.get(Calendar.MINUTE)-1;
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);

        //Выбираем в часах время на минуту меньше текущего
        TestUtils.waitView(claimsPageSteps.timeClaimField).perform(click());
        TestUtils.waitView(controlPanelSteps.timePicker).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.timePicker).perform(setTime(hour, minutes));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверяем, что в поле Время отображается выбранное время
        TestUtils.waitView(claimsPageSteps.timeClaimField).check(matches(withText(hourExpected + ":" + minutesExpected)));
    }

    @Test
    @DisplayName("Создание заявки. В поле Время  нереальное время")
    public void shouldShowTextEnteredInvalidValue() {
        String hour = "99";
        String  minutes = "99";
        //Вводим в часах нереальное время
        TestUtils.waitView(claimsPageSteps.timeClaimField).perform(click());
        TestUtils.waitView(controlPanelSteps.timePicker).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.timePickerToggleMode).perform(click());
        TestUtils.waitView(controlPanelSteps.timePicker).check(matches(isDisplayed()));
        //Espresso.p
        TestUtils.waitView(controlPanelSteps.inputHour).check(matches(isDisplayed())).perform(replaceText(hour));
        TestUtils.waitView(controlPanelSteps.inputMinute).check(matches(isDisplayed())).perform(replaceText(minutes));
        //TestUtils.waitView(controlPanelSteps.timePicker).perform(setTime(hour, minutes));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверяем, что отображается текст "Enter a valid time"
        TestUtils.waitView(claimsPageSteps.labelError).check(matches(isDisplayed()));

    }

    @Test
    @DisplayName("Создание заявки с заголовком начинающимся с пробела")
    public void shouldCreateAClaimWithATitleStartsWithoutASpaceAtTheBeginning() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String titleWithoutSpace = titleStartsWithASpace.substring(1);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, titleStartsWithASpace, titleStartsWithASpace);
        //claimsPageElements.titleClaimField.check(matches(withText(titleWithoutSpace)));
       TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(titleWithoutSpace).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание заявки с заголовком из 50 знаков")
    public void shouldCreateAClaimWithATitleWith50Characters() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, title50Characters, title50Characters);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(title50Characters).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание заявки с заголовком из 49 знаков")
    public void shouldCreateAClaimWithATitleWith49Characters() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, title49Characters, title49Characters);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(title49Characters).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание заявки с заголовком из 51 знаков")
    public void shouldCreateAClaimWithATitleWith51Characters() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String titleWhichToBeKept = title51Characters.substring(0,50);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageSteps.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, title51Characters, title51Characters);
        //Проверка, что в поле Title отображается только 50 символов
        TestUtils.waitView(claimsPageSteps.titleClaimField).check(matches(withText(titleWhichToBeKept)));
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображается заявка
        claimsPageSteps.scrollToElementInRecyclerList(titleWhichToBeKept).check(matches(isDisplayed()));
    }



    @Test
    @DisplayName("Создание заявки. Исполнитель  не из списка")
    public void shouldCreateAClaimWithoutAnExecutor() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String MayExecutor = "Козлов Константин Анатольевич";
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.replaceTextClaimExecutor(MayExecutor);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, titleExecutorNotListed, titleExecutorNotListed);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        SystemClock.sleep(3000);
        //Проверка что отображается заявка. Открытие ее
        claimsPageSteps.scrollToElementInRecyclerList(titleExecutorNotListed).check(matches(isDisplayed()));
        //Проверка что исполнитель не назначен
        claimsPageSteps.getItemClaimExecutorName(titleExecutorNotListed).check(matches(withText("")));




    }
}
