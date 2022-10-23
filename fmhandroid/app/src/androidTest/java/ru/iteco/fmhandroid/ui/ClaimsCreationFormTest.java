package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

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
import ru.iteco.fmhandroid.ui.page.ClaimsPageElements;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.CreatingClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimsCreationFormTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    ClaimsPageElements claimsPageElements = new ClaimsPageElements();
    ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    CreatingClaimsSteps creatingClaimsSteps = new CreatingClaimsSteps();

    Calendar date = Calendar.getInstance();

    String titleForTheTestClaim = "Заголовок" + " " + DataHelper.generateTitleId();
    String titleStartsWithASpace = " Заголовок" + " " + DataHelper.generateTitleId();
    String title50Characters = "Заголовок" + " " + DataHelper.generateTitleId() + " " + "50з";
    String title49Characters = "Заголовок" + " " + DataHelper.generateTitleId() + " " + "49";
    String title51Characters = "Заголовок" + " " + DataHelper.generateTitleId() + " " + "51зн";
    String titleExecutorNotListed = "Заголовок" + " " + DataHelper.generateTitleId();







    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            authSteps.isAuthScreen();
        } catch (NoMatchingViewException e) {
            SystemClock.sleep(8000);
            mainPageSteps.clickLogOutBut();
        }
        authSteps.authWithValidData(authInfo());
        mainPageSteps.openClaimsPageThroughTheMainMenu();
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.addNewClaimBut);

    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("Создание заявки")
    public void shouldCreateAClaim() {
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageElements.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(0,0,0,0,0, titleForTheTestClaim, titleForTheTestClaim);
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.saveClaimBut);
        SystemClock.sleep(3000);
        //Проверка что отображается заявка
        claimsPageElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForTheTestClaim)))
                )).check(matches(isDisplayed()));

    }


    @Test
    @DisplayName("Сохранение пустой заявки")
    public void shouldNotCreateEmptyClaim() {
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.saveClaimBut);
        creatingClaimsSteps.isFillEmptyFieldsMessage();
        claimsPageElements.okBut.perform(click());
        creatingClaimsSteps.isWrongEmptyFormClaim();

    }

    @Test
    @DisplayName("Сохранение заявки без исполнителя")
    public void shouldCreateAClaimWithoutChoosingExecutor() {
        //Создать заявку
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(0,0,0,0,0, titleForTheTestClaim, titleForTheTestClaim);
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.saveClaimBut);
        SystemClock.sleep(3000);
        //Проверка что отображается заявка
        claimsPageElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForTheTestClaim)))
                )).check(matches(isDisplayed()));

    }

    //Выбирает вчерашнюю дату. При ручном тестировании невозможно выбрать вчерашнюю дату

    @Test
    @DisplayName("Выбор вчерашней даты в заявке")
    public void shouldNotChoosePublicationDateYesterday() {
        String publicationDate = DataHelper.getValidDate(0, 0, 0);
        //Выбираем в календаре вчерашнюю дату
        creatingClaimsSteps.setDateToDatePicker(0, 0, -1);
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.okBut);
        //Проверяем, что в поле Дата отображается сегодняшняя дата
        claimsPageElements.dateClaimField.check(matches(withText(publicationDate)));
    }

    //Выбирает вчерашнюю дату. При ручном тестировании невозможно выбрать вчерашнюю дату

    @Test
    @DisplayName("Выбор даты год назад в заявке")
    public void shouldNotChoosePublicationDateYearAgo() {
        String publicationDate = DataHelper.getValidDate(0, 0, 0);
        //Выбираем в календаре дату год назад
        creatingClaimsSteps.setDateToDatePicker(-1, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.okBut);
        //Проверяем, что в поле Дата отображается сегодняшняя дата
        claimsPageElements.dateClaimField.check(matches(withText(publicationDate)));
    }

    @Test
    @DisplayName("Выбор даты завтра в заявке")
    public void shouldChoosePublicationDateTomorrow() {
        String publicationDate = DataHelper.getValidDate(0, 0, 1);
        //Выбираем в календаре дату завтра
        creatingClaimsSteps.setDateToDatePicker(0, 0, 1);
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.okBut);
        //Проверяем, что в поле Дата отображается выбранная дата
        claimsPageElements.dateClaimField.check(matches(withText(publicationDate)));
    }

    @Test
    @DisplayName("Выбор даты через год в заявке")
    public void shouldChoosePublicationDateInAYear() {
        String publicationDate = DataHelper.getValidDate(1, 0, 0);
        //Выбираем в календаре дату через год
        creatingClaimsSteps.setDateToDatePicker(1, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.okBut);
        //Проверяем, что в поле Дата отображается выбранная дата
        claimsPageElements.dateClaimField.check(matches(withText(publicationDate)));
    }

    //Нестабильный тест. время  ожидаемое от фактического может отличаться в минуту и тест будет падать

    @Test
    @DisplayName("Создание заявки. В поле Время  на час больше текущего")
    public void shouldChoosePublicationTimeInOneHour() {
        int hour = date.get(Calendar.HOUR_OF_DAY)+1;
        int minutes = date.get(Calendar.MINUTE);
        //Выбираем в часах время на час больше текущего
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.timeClaimField);
        claimsPageElements.timePicker.check(matches(isDisplayed()));
        claimsPageElements.timePicker.perform(setTime(hour, minutes));
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.okBut);
        //Проверяем, что в поле Время отображается выбранное время
        claimsPageElements.timeClaimField.check(matches(withText(hour + ":" + minutes)));
    }

    //Нестабильный тест. время  ожидаемое от фактического может отличаться в минуту и тест будет падать

    @Test
    @DisplayName("Создание заявки. В поле Время  на час меньше текущего")
    public void shouldChoosePublicationTimeHourAgo() {
        int hour = date.get(Calendar.HOUR_OF_DAY)-1;
        int minutes = date.get(Calendar.MINUTE);
        //Выбираем в часах время на час меньше текущего
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.timeClaimField);
        claimsPageElements.timePicker.check(matches(isDisplayed()));
        claimsPageElements.timePicker.perform(setTime(hour, minutes));
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.okBut);
        //Проверяем, что в поле Время отображается выбранное время
        claimsPageElements.timeClaimField.check(matches(withText(hour + ":" + minutes)));
    }

    //Нестабильный тест. время  ожидаемое от фактического может отличаться в минуту и тест будет падать

    @Test
    @DisplayName("Создание заявки. В поле Время  на минуту больше текущего")
    public void shouldChoosePublicationTimeInOneMinute() {
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minutes = date.get(Calendar.MINUTE)+1;
        //Выбираем в часах время на минуту больше текущего
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.timeClaimField);
        claimsPageElements.timePicker.check(matches(isDisplayed()));
        claimsPageElements.timePicker.perform(setTime(hour, minutes));
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.okBut);
        //Проверяем, что в поле Время отображается выбранное время
        claimsPageElements.timeClaimField.check(matches(withText(hour + ":" + minutes)));
    }

    //Нестабильный тест. время  ожидаемое от фактического может отличаться в минуту и тест будет падать

    @Test
    @DisplayName("Создание заявки. В поле Время  на минуту меньше текущего")
    public void shouldChoosePublicationTimeMinuteAgo() {
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minutes = date.get(Calendar.MINUTE)-1;

        //Выбираем в часах время на минуту меньше текущего
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.timeClaimField);
        claimsPageElements.timePicker.check(matches(isDisplayed()));
        claimsPageElements.timePicker.perform(setTime(hour, minutes));
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.okBut);
        //Проверяем, что в поле Время отображается выбранное время
        claimsPageElements.timeClaimField.check(matches(withText(hour + ":" + minutes)));
    }
    @Ignore
    @Test
    @DisplayName("Создание заявки. В поле Время  нереальное время")
    public void shouldShowTextEnteredInvalidValue() {
        int hour = 99;
        int minute = 99;
        //Вводим в часах нереальное время
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.timeClaimField);
        claimsPageElements.timePicker.check(matches(isDisplayed()));
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.timePickerToggleMode);
        claimsPageElements.timePicker.check(matches(isDisplayed()));
        claimsPageElements.timePicker.perform(setTime(hour, minute));
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.okBut);
        //Проверяем, что отображается текст "Enter a valid time"
        claimsPageElements.labelError.check(matches(isDisplayed()));

    }

    @Test
    @DisplayName("Создание заявки с заголовком начинающимся с пробела")
    public void shouldCreateAClaimWithATitleStartsWithoutASpace() {
        String titleWithoutSpace = titleStartsWithASpace.substring(1);
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageElements.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(0,0,0,0,0, titleStartsWithASpace, titleStartsWithASpace);
        //claimsPageElements.titleClaimField.check(matches(withText(titleWithoutSpace)));
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.saveClaimBut);
        SystemClock.sleep(3000);
        //Проверка что отображается заявка
        claimsPageElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleWithoutSpace)))
                )).check(matches(isDisplayed()));

    }

    @Test
    @DisplayName("Создание заявки с заголовком из 50 знаков")
    public void shouldCreateAClaimWithATitleWith50Characters() {
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageElements.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(0,0,0,0,0, title50Characters, title50Characters);
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.saveClaimBut);
        SystemClock.sleep(3000);
        //Проверка что отображается заявка
        claimsPageElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(title50Characters)))
                )).check(matches(isDisplayed()));

    }

    @Test
    @DisplayName("Создание заявки с заголовком из 49 знаков")
    public void shouldCreateAClaimWithATitleWith49Characters() {
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageElements.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(0,0,0,0,0, title49Characters, title49Characters);
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.saveClaimBut);
        SystemClock.sleep(3000);
        //Проверка что отображается заявка
        claimsPageElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(title49Characters)))
                )).check(matches(isDisplayed()));

    }

    @Test
    @DisplayName("Создание заявки с заголовком из 51 знаков")
    public void shouldCreateAClaimWithATitleWith51Characters() {

        String titleWhichToBeKept = title51Characters.substring(0,50);
        //Создать заявку
        creatingClaimsSteps.selectAClaimExecutorFromTheList(claimsPageElements.executorSmirnov);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(0,0,0,0,0, title51Characters, title51Characters);
        //Проверка, что в поле Title отображается только 50 символов
        claimsPageElements.titleClaimField.check(matches(withText(titleWhichToBeKept)));
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.saveClaimBut);
        SystemClock.sleep(3000);
        //Проверка что отображается заявка
        claimsPageElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleWhichToBeKept)))
                )).check(matches(isDisplayed()));

    }

    //Тест падает. Espresso не позволяет ввести текст в поле Executor
    @Ignore
    @Test
    @DisplayName("Создание заявки. Исполнитель  не из списка")
    public void shouldCreateAClaimWithoutAnExecutor() {
        String MayExecutor = "Козлов Константин Анатольевич";
        //Создать заявку
        creatingClaimsSteps.replaceTextClaimExecutor(MayExecutor);
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(0,0,0,0,0, titleExecutorNotListed, titleExecutorNotListed);
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.saveClaimBut);
        SystemClock.sleep(3000);
        //Проверка что отображается заявка. Открытие ее
        claimsPageElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleExecutorNotListed)), hasDescendant(allOf(withId(R.id.executor_name_material_text_view), withText(""))))
                )).check(matches(isDisplayed()));

        //Проверка что исполнитель не назначен


    }
}
