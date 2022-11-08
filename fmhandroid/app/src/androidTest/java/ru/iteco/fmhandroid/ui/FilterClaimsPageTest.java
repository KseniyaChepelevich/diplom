package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.PerformException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
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
import ru.iteco.fmhandroid.ui.data.ViewActions;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.CreatingClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.FilterClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

@RunWith(AllureAndroidJUnit4.class)
public class FilterClaimsPageTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    CreatingClaimsSteps creatingClaimsSteps = new CreatingClaimsSteps();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();
    FilterClaimsPageSteps filterClaimsPageSteps = new FilterClaimsPageSteps();

    Calendar date = Calendar.getInstance();

    String titleForTheTestClaimOpen1 = "Открытая1" + " " + DataHelper.generateTitleId();
    String titleForTheTestClaimOpen2 = "Открытая2" + " " + DataHelper.generateTitleId();
    String titleForTheTestClaimOpen3 = "Открытая3" + " " + DataHelper.generateTitleId();
    String titleForTheTestClaimInProgress1 = "В работе1" + " " + DataHelper.generateTitleId();
    String titleForTheTestClaimInProgress2 = "В работе2" + " " + DataHelper.generateTitleId();
    String titleForTheTestClaimInProgress3 = "В работе3" + " " + DataHelper.generateTitleId();
    String titleForTheTestClaimExecuted1 = "Выполнено1" + " " + DataHelper.generateTitleId();
    String titleForTheTestClaimExecuted2 = "Выполнено2" + " " + DataHelper.generateTitleId();
    String titleForTheTestClaimExecuted3 = "Выполнено3" + " " + DataHelper.generateTitleId();
    String titleForTheTestClaimCanceled1 = "Закрыта1" + " " + DataHelper.generateTitleId();
    String titleForTheTestClaimCanceled2 = "Закрыта2" + " " + DataHelper.generateTitleId();
    String titleForTheTestClaimCanceled3 = "Закрыта3" + " " + DataHelper.generateTitleId();
    String newTitle = "Новый" + " " + DataHelper.generateTitleId();
    String commentForTheTestClaim = "Комментарий" + " " + DataHelper.generateTitleId();

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


    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Открыта")
    public void shouldFilterClaimsWithStatusOpen() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);
        //creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen2, titleForTheTestClaimOpen2);
        //creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen3, titleForTheTestClaimOpen3);
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress1, titleForTheTestClaimInProgress1);
        //creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress2, titleForTheTestClaimInProgress2);
        //creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress3, titleForTheTestClaimInProgress3);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(true, false, false, false);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimOpen1);
        //filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimOpen2);
        //filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimOpen3);
        /*claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimOpen1).check(matches(isDisplayed()));
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimOpen2).check(matches(isDisplayed()));
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimOpen3).check(matches(isDisplayed()));*/
        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimInProgress1);
        //filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimInProgress2);
        //filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimInProgress3);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом В работе")
    public void shouldFilterClaimsWithStatusInProgress() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen2, titleForTheTestClaimOpen2);
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen3, titleForTheTestClaimOpen3);
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress1, titleForTheTestClaimInProgress1);
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress2, titleForTheTestClaimInProgress2);
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress3, titleForTheTestClaimInProgress3);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(false, true, false, false);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimInProgress1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimInProgress2);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimInProgress3);
        /*claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimInProgress1).check(matches(isDisplayed()));
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimInProgress2).check(matches(isDisplayed()));
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimInProgress3).check(matches(isDisplayed()));*/
        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen1);
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen2);
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen3);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Выполнена")
    public void shouldFilterClaimsWithStatusExecuted() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen2, titleForTheTestClaimOpen2);
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen3, titleForTheTestClaimOpen3);
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimExecuted1, titleForTheTestClaimExecuted1);
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimExecuted2, titleForTheTestClaimExecuted2);
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimExecuted3, titleForTheTestClaimExecuted3);
        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimExecuted1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.toExecuteMenuItem).perform(click());
        claimsPageSteps.isStatusCommentDialog();
        TestUtils.waitView(claimsPageSteps.statusCommentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimExecuted2);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.toExecuteMenuItem).perform(click());
        claimsPageSteps.isStatusCommentDialog();
        TestUtils.waitView(claimsPageSteps.statusCommentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimExecuted3);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.toExecuteMenuItem).perform(click());
        claimsPageSteps.isStatusCommentDialog();
        TestUtils.waitView(claimsPageSteps.statusCommentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);


        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(false, false, true, false);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimExecuted1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimExecuted2);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimExecuted3);
        /*claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimInProgress1).check(matches(isDisplayed()));
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimInProgress2).check(matches(isDisplayed()));
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimInProgress3).check(matches(isDisplayed()));*/
        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen1);
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen2);
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen3);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Отменена")
    public void shouldFilterClaimsWithStatusCanceled() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimCanceled1, titleForTheTestClaimCanceled1);
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimCanceled2, titleForTheTestClaimCanceled2);
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimCanceled3, titleForTheTestClaimCanceled3);
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress1, titleForTheTestClaimInProgress1);
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress2, titleForTheTestClaimInProgress2);
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress3, titleForTheTestClaimInProgress3);
        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimCanceled1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.cancelMenuItem).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimCanceled2);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.cancelMenuItem).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimCanceled3);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.cancelMenuItem).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);


        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(false, false, false, true);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimCanceled1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimCanceled2);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimCanceled3);
        /*claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimInProgress1).check(matches(isDisplayed()));
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimInProgress2).check(matches(isDisplayed()));
        claimsPageSteps.scrollToElementInRecyclerList(titleForTheTestClaimInProgress3).check(matches(isDisplayed()));*/
        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimInProgress1);
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimInProgress2);
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimInProgress3);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Открыта и В работе")
    public void shouldFilterClaimsWithStatusOpenAndInProgress() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimCanceled1, titleForTheTestClaimCanceled1);

        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress1, titleForTheTestClaimInProgress1);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimCanceled1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.cancelMenuItem).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(true, true, false, false);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimOpen1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimInProgress1);

        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimCanceled1);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Выполнена и Отменена")
    public void shouldFilterClaimsWithStatusExecutedAndCanceled() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);

        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimExecuted1, titleForTheTestClaimExecuted1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimCanceled1, titleForTheTestClaimCanceled1);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimExecuted1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.toExecuteMenuItem).perform(click());
        claimsPageSteps.isStatusCommentDialog();
        TestUtils.waitView(claimsPageSteps.statusCommentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimCanceled1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.cancelMenuItem).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(false, false, true, true);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimExecuted1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimCanceled1);

        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen1);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом В работе и Отменена")
    public void shouldFilterClaimsWithStatusOpenAndInProgressAndCanceled() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimCanceled1, titleForTheTestClaimCanceled1);

        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress1, titleForTheTestClaimInProgress1);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimCanceled1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.cancelMenuItem).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(false, true, false, true);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimCanceled1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimInProgress1);

        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen1);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Открыта и Отменена")
    public void shouldFilterClaimsWithStatusOpenAndOpenAndCanceled() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimCanceled1, titleForTheTestClaimCanceled1);

        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimInProgress1, titleForTheTestClaimInProgress1);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimCanceled1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.cancelMenuItem).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(false, true, false, true);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimCanceled1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimOpen1);

        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimInProgress1);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Открыта и Выполнена")
    public void shouldFilterClaimsWithStatusOpenAndExecuted() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);

        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimExecuted1, titleForTheTestClaimExecuted1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimCanceled1, titleForTheTestClaimCanceled1);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimExecuted1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.toExecuteMenuItem).perform(click());
        claimsPageSteps.isStatusCommentDialog();
        TestUtils.waitView(claimsPageSteps.statusCommentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimCanceled1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.cancelMenuItem).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(true, false, true, false);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimExecuted1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimOpen1);

        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimCanceled1);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом В работе и Выполнена")
    public void shouldFilterClaimsWithStatusInProgressAndExecuted() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);

        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimExecuted1, titleForTheTestClaimExecuted1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimInProgress1, titleForTheTestClaimInProgress1);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimExecuted1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.toExecuteMenuItem).perform(click());
        claimsPageSteps.isStatusCommentDialog();
        TestUtils.waitView(claimsPageSteps.statusCommentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(false, true, true, false);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimExecuted1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimInProgress1);

        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen1);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом В работе, Открыта и Выполнена")
    public void shouldFilterClaimsWithStatusInProgressOpenAndExecuted() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);

        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimExecuted1, titleForTheTestClaimExecuted1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimInProgress1, titleForTheTestClaimInProgress1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimCanceled1, titleForTheTestClaimCanceled1);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimExecuted1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.toExecuteMenuItem).perform(click());
        claimsPageSteps.isStatusCommentDialog();
        TestUtils.waitView(claimsPageSteps.statusCommentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimCanceled1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.cancelMenuItem).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(true, true, true, false);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimExecuted1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimInProgress1);
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen1);

        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimCanceled1);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Отменена, Открыта и Выполнена")
    public void shouldFilterClaimsWithStatusCanceledOpenAndExecuted() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);

        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimExecuted1, titleForTheTestClaimExecuted1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimInProgress1, titleForTheTestClaimInProgress1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimCanceled1, titleForTheTestClaimCanceled1);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimExecuted1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.toExecuteMenuItem).perform(click());
        claimsPageSteps.isStatusCommentDialog();
        TestUtils.waitView(claimsPageSteps.statusCommentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimCanceled1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.cancelMenuItem).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(true, false, true, true);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimExecuted1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimCanceled1);
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen1);

        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimInProgress1);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом В работе, Выполнена и Отменена")
    public void shouldFilterClaimsWithStatusInProgressExecutedAndCanceled() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimOpen1, titleForTheTestClaimOpen1);

        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaimExecuted1, titleForTheTestClaimExecuted1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimInProgress1, titleForTheTestClaimInProgress1);

        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaimCanceled1, titleForTheTestClaimCanceled1);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimExecuted1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.toExecuteMenuItem).perform(click());
        claimsPageSteps.isStatusCommentDialog();
        TestUtils.waitView(claimsPageSteps.statusCommentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(titleForTheTestClaimCanceled1);
        //Изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.cancelMenuItem).perform(click());
        TestUtils.waitView(claimsPageSteps.closeImBut).perform(click());
        SystemClock.sleep(2000);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(false, true, true, true);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimExecuted1);
        filterClaimsPageSteps.checkClaimIsExist(titleForTheTestClaimCanceled1);
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimInProgress1);

        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(titleForTheTestClaimOpen1);

    }

    @Test
    @DisplayName("Кнопка отмены фильтрации")
    public void shouldCloseTheClaimsFilterSettingsForm() {
        TestUtils.waitView(claimsPageSteps.claimsFiltersButton).perform(click());
        TestUtils.waitView(claimsPageSteps.claimFilterCancelBut).perform(click());
        claimsPageSteps.isClaimsPage();
    }
}
