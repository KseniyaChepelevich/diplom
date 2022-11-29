package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.RemoteException;
import android.os.SystemClock;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.CustomRecyclerViewActions;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.NamingHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.data.ViewActions;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.CreatingClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.FilterClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

@RunWith(AllureAndroidJUnit4.class)
public class FilterClaimsPageTest extends BaseTest{
    private UiDevice device;
    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    private static CreatingClaimsSteps creatingClaimsSteps = new CreatingClaimsSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();
    private static FilterClaimsPageSteps filterClaimsPageSteps = new FilterClaimsPageSteps();
    private static NamingHelper namingHelper = new NamingHelper();

    @Before
    public void logoutCheck() throws RemoteException {
        device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.setOrientationNatural();
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
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimOpenName();
        String thirdName = namingHelper.getClaimOpenName();
        String forthName = namingHelper.getClaimInProgressName();
        String fifthName = namingHelper.getClaimInProgressName();
        String sixthName = namingHelper.getClaimInProgressName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                        DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen(DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen(DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);
        creatingClaimsSteps.creatingAClaim(DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), forthName, forthName);
        creatingClaimsSteps.creatingAClaim(DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), fifthName, fifthName);
        creatingClaimsSteps.creatingAClaim(DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), sixthName, sixthName);

        //Фильтруем заявки со статусом Open
        filterClaimsPageSteps.filterClaims(true, false, false, false);

        //Проверяем, что отфильтровались только заявки со статусом Open
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(firstName);
        filterClaimsPageSteps.checkClaimIsExist(secondName);
        filterClaimsPageSteps.checkClaimIsExist(thirdName);
        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(forthName);
        filterClaimsPageSteps.checkClaimIsNotExist(fifthName);
        filterClaimsPageSteps.checkClaimIsNotExist(sixthName);
    }

    @Test
    @DisplayName("Фильтрация заявок со статусом В работе")
    public void shouldFilterClaimsWithStatusInProgress() {
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimOpenName();
        String thirdName = namingHelper.getClaimOpenName();
        String forthName = namingHelper.getClaimInProgressName();
        String fifthName = namingHelper.getClaimInProgressName();
        String sixthName = namingHelper.getClaimInProgressName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), forthName, forthName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), fifthName, fifthName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), sixthName, sixthName);

        //Фильтруем заявки со статусом In progress
        filterClaimsPageSteps.filterClaims(false, true, false, false);

        //Проверяем, что отфильтровались только заявки со статусом In progress
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(forthName);
        filterClaimsPageSteps.checkClaimIsExist(fifthName);
        filterClaimsPageSteps.checkClaimIsExist(sixthName);
        //Проверяем, что заявки со статусом Open не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(firstName);
        filterClaimsPageSteps.checkClaimIsNotExist(secondName);
        filterClaimsPageSteps.checkClaimIsNotExist(thirdName);
    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Выполнена")
    public void shouldFilterClaimsWithStatusExecuted() {
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimOpenName();
        String thirdName = namingHelper.getClaimOpenName();
        String forthName = namingHelper.getClaimExecutedName();
        String fifthName = namingHelper.getClaimExecutedName();
        String sixthName = namingHelper.getClaimExecutedName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), forthName, forthName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), fifthName, fifthName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), sixthName, sixthName);
        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(forthName);
        //Изменить статус заявки
        claimsPageSteps.setStatusExecute();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(namingHelper.getComment());
        controlPanelSteps.okButtonClick();
        claimsPageSteps.closeImButtonClick();

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(fifthName);
        //Изменить статус заявки
        claimsPageSteps.setStatusExecute();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(namingHelper.getComment());
        controlPanelSteps.okButtonClick();
        claimsPageSteps.closeImButtonClick();

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(sixthName);
        //Изменить статус заявки
        claimsPageSteps.setStatusExecute();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(namingHelper.getComment());
        controlPanelSteps.okButtonClick();
        claimsPageSteps.closeImButtonClick();

        //Фильтруем заявки со статусом Executed
        filterClaimsPageSteps.filterClaims(false, false, true, false);

        //Проверяем, что отфильтровались только заявки со статусом Executed
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(forthName);
        filterClaimsPageSteps.checkClaimIsExist(fifthName);
        filterClaimsPageSteps.checkClaimIsExist(sixthName);

        //Проверяем, что заявки со статусом Open не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(firstName);
        filterClaimsPageSteps.checkClaimIsNotExist(secondName);
        filterClaimsPageSteps.checkClaimIsNotExist(thirdName);
    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Отменена")
    public void shouldFilterClaimsWithStatusCanceled() {
        String firstName = namingHelper.getClaimCanceledName();
        String secondName = namingHelper.getClaimCanceledName();
        String thirdName = namingHelper.getClaimCanceledName();
        String forthName = namingHelper.getClaimInProgressName();
        String fifthName = namingHelper.getClaimInProgressName();
        String sixthName = namingHelper.getClaimInProgressName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), forthName, forthName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), fifthName, fifthName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), sixthName, sixthName);
        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(firstName);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        claimsPageSteps.closeImButtonClick();

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(secondName);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        claimsPageSteps.closeImButtonClick();

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(thirdName);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        claimsPageSteps.closeImButtonClick();

        //Фильтруем заявки со статусом Canceled
        filterClaimsPageSteps.filterClaims(false, false, false, true);

        //Проверяем, что отфильтровались только заявки со статусом Canceled
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(firstName);
        filterClaimsPageSteps.checkClaimIsExist(secondName);
        filterClaimsPageSteps.checkClaimIsExist(thirdName);
        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(forthName);
        filterClaimsPageSteps.checkClaimIsNotExist(fifthName);
        filterClaimsPageSteps.checkClaimIsNotExist(sixthName);
    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Открыта и В работе")
    public void shouldFilterClaimsWithStatusOpenAndInProgress() {
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimCanceledName();
        String thirdName = namingHelper.getClaimInProgressName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(secondName);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        claimsPageSteps.closeImButtonClick();

        //Фильтруем заявки со статусом Open и In progress
        filterClaimsPageSteps.filterClaims(true, true, false, false);

        //Проверяем, что отфильтровались только заявки со статусом Open и In progress
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(firstName);
        filterClaimsPageSteps.checkClaimIsExist(thirdName);

        //Проверяем, что заявки со статусом Canceled не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(secondName);
    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Выполнена и Отменена")
    public void shouldFilterClaimsWithStatusExecutedAndCanceled() {
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimExecutedName();
        String thirdName = namingHelper.getClaimCanceledName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName,firstName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(secondName);
        //Изменить статус заявки
        claimsPageSteps.setStatusExecute();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(namingHelper.getComment());
        controlPanelSteps.okButtonClick();
        claimsPageSteps.closeImButtonClick();

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(thirdName);
        //Изменить статус заявки
        claimsPageSteps.closeImButtonClick();

        //Фильтруем заявки со статусом Canceled и Executed
        filterClaimsPageSteps.filterClaims(false, false, true, true);
        SystemClock.sleep(5000);

        //Проверяем, что отфильтровались только заявки со статусом Canceled и Executed
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(secondName);
        filterClaimsPageSteps.checkClaimIsExist(thirdName);

        //Проверяем, что заявки со статусом Open не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(firstName);
    }

    @Test
    @DisplayName("Фильтрация заявок со статусом В работе и Отменена")
    public void shouldFilterClaimsWithStatusOpenAndInProgressAndCanceled() {
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimCanceledName();
        String thirdName = namingHelper.getClaimInProgressName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(secondName);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        claimsPageSteps.closeImButtonClick();

        //Фильтруем заявки со статусом In progress и Canceled
        filterClaimsPageSteps.filterClaims(false, true, false, true);

        //Проверяем, что отфильтровались только заявки со статусом In progress и Canceled
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(secondName);
        filterClaimsPageSteps.checkClaimIsExist(thirdName);

        //Проверяем, что заявки со статусом Open не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(firstName);

    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Открыта и Отменена")
    public void shouldFilterClaimsWithStatusOpenAndOpenAndCanceled() {
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimCanceledName();
        String thirdName = namingHelper.getClaimInProgressName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(secondName);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        claimsPageSteps.closeImButtonClick();

        //Фильтруем заявки со статусом Open и Canceled
        filterClaimsPageSteps.filterClaims(false, true, false, true);

        //Проверяем, что отфильтровались только заявки со статусом Open и Canceled
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(firstName);
        filterClaimsPageSteps.checkClaimIsExist(secondName);

        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(thirdName);
    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Открыта и Выполнена")
    public void shouldFilterClaimsWithStatusOpenAndExecuted() {
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimExecutedName();
        String thirdName = namingHelper.getClaimCanceledName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(secondName);
        //Изменить статус заявки
        claimsPageSteps.setStatusExecute();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(namingHelper.getComment());
        controlPanelSteps.okButtonClick();
        claimsPageSteps.closeImButtonClick();

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(thirdName);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        claimsPageSteps.closeImButtonClick();

        //Фильтруем заявки со статусом Open и Executed
        filterClaimsPageSteps.filterClaims(true, false, true, false);

        //Проверяем, что отфильтровались только заявки со статусом Open и Executed
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(firstName);
        filterClaimsPageSteps.checkClaimIsExist(secondName);

        //Проверяем, что заявки со статусом Canceled не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(thirdName);
    }

    @Test
    @DisplayName("Фильтрация заявок со статусом В работе и Выполнена")
    public void shouldFilterClaimsWithStatusInProgressAndExecuted() {
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimExecutedName();
        String thirdName = namingHelper.getClaimInProgressName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(secondName);
        //Изменить статус заявки
        claimsPageSteps.setStatusExecute();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(namingHelper.getComment());
        controlPanelSteps.okButtonClick();
        claimsPageSteps.closeImButtonClick();

        //Фильтруем заявки со статусом In progress, Executed
        filterClaimsPageSteps.filterClaims(false, true, true, false);

        //Проверяем, что отфильтровались только заявки со статусом In progress, Executed
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(secondName);
        filterClaimsPageSteps.checkClaimIsExist(thirdName);

        //Проверяем, что заявки со статусом Open не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(firstName);
    }

    @Test
    @DisplayName("Фильтрация заявок со статусом В работе, Открыта и Выполнена")
    public void shouldFilterClaimsWithStatusInProgressOpenAndExecuted() {
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimExecutedName();
        String thirdName = namingHelper.getClaimInProgressName();
        String forthName = namingHelper.getClaimCanceledName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), forthName, forthName);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(secondName);
        //Изменить статус заявки
        claimsPageSteps.setStatusExecute();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(namingHelper.getComment());
        controlPanelSteps.okButtonClick();
        claimsPageSteps.closeImButtonClick();

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(forthName);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        claimsPageSteps.closeImButtonClick();

        //Фильтруем заявки со статусом In progress, Open, Executed
        filterClaimsPageSteps.filterClaims(true, true, true, false);

        //Проверяем, что отфильтровались только заявки со статусом In progress, Open, Executed
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(firstName);
        filterClaimsPageSteps.checkClaimIsExist(secondName);
        filterClaimsPageSteps.checkClaimIsNotExist(thirdName);

        //Проверяем, что заявки со статусом Canceled не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(forthName);
    }

    @Test
    @DisplayName("Фильтрация заявок со статусом Отменена, Открыта и Выполнена")
    public void shouldFilterClaimsWithStatusCanceledOpenAndExecuted() {
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimExecutedName();
        String thirdName = namingHelper.getClaimInProgressName();
        String forthName = namingHelper.getClaimCanceledName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), forthName, forthName);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(secondName);
        //Изменить статус заявки
        claimsPageSteps.setStatusExecute();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(namingHelper.getComment());
        controlPanelSteps.okButtonClick();
        claimsPageSteps.closeImButtonClick();

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(forthName);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        claimsPageSteps.closeImButtonClick();

        //Фильтруем заявки со статусом Open, Executed, Canceled
        filterClaimsPageSteps.filterClaims(true, false, true, true);

        //Проверяем, что отфильтровались только заявки со статусом Open, Executed, Canceled
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(firstName);
        filterClaimsPageSteps.checkClaimIsExist(secondName);
        filterClaimsPageSteps.checkClaimIsNotExist(forthName);

        //Проверяем, что заявки со статусом In progress не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(thirdName);
    }

    @Test
    @DisplayName("Фильтрация заявок со статусом В работе, Выполнена и Отменена")
    public void shouldFilterClaimsWithStatusInProgressExecutedAndCanceled() {
        String firstName = namingHelper.getClaimOpenName();
        String secondName = namingHelper.getClaimExecutedName();
        String thirdName = namingHelper.getClaimInProgressName();
        String forthName = namingHelper.getClaimCanceledName();
        //Создать заявки для теста
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), firstName, firstName);
        creatingClaimsSteps.creatingAClaim
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), secondName, secondName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), thirdName, thirdName);
        creatingClaimsSteps.creatingAClaimWithStatusOpen
                (DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth(),
                DataHelper.getValidDate().getHour(), DataHelper.getValidDate().getMinute(), forthName, forthName);

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(secondName);
        //Изменить статус заявки
        claimsPageSteps.setStatusExecute();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(namingHelper.getComment());
        controlPanelSteps.okButtonClick();
        claimsPageSteps.closeImButtonClick();

        //Открыть карточку заявки со статусом
        claimsPageSteps.openClaimCard(forthName);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        claimsPageSteps.closeImButtonClick();

        //Фильтруем заявки со статусом In progress Executed, Canceled
        filterClaimsPageSteps.filterClaims(false, true, true, true);

        //Проверяем, что отфильтровались только заявки со статусом In progress Executed, Canceled
        //Поиск элементов в RecyclerView работает нестабильно
        filterClaimsPageSteps.checkClaimIsExist(secondName);
        filterClaimsPageSteps.checkClaimIsExist(thirdName);
        filterClaimsPageSteps.checkClaimIsNotExist(forthName);

        //Проверяем, что заявки со статусом Open не отображаются
        filterClaimsPageSteps.checkClaimIsNotExist(firstName);
    }

    @Test
    @DisplayName("Кнопка отмены фильтрации")
    public void shouldCloseTheClaimsFilterSettingsForm() {
        claimsPageSteps.openClaimsFilter();
        claimsPageSteps.claimFilterCancelButtonClick();
        claimsPageSteps.isClaimsPage();
    }
}
