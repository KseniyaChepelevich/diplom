package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
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
public class ClaimEditTest extends BaseTest {
    private UiDevice device;
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
    @DisplayName("Редактирование заявки")
    public void shouldSaveClaimChanges() {
        LocalDateTime date = today.plusHours(1);
        String planeDate = TestUtils.getDateToString(date);
        String planeTime = TestUtils.getTimeToString(date);
        String title = namingHelper.getClaimOpenName();
        String newTitle = namingHelper.getClaimOpenName();

        //Создать заявку
        creatingClaimsSteps.creatingAClaimWithStatusOpen(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(title);
        //Отредактировать заявку
        claimsPageSteps.editClaim(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                date.getHour(), date.getMinute(), newTitle, newTitle);
        //Проверить что внесенные изменения сохранились
        claimsPageSteps.isClaimCard(newTitle, planeDate, planeTime, newTitle);
    }

    @Test
    @DisplayName("Смена статуса заявки на In progress")
    public void shouldChangeTheStatusOfTheClaimToInProgress() {
        String title = namingHelper.getClaimOpenName();
        //Создать заявку
        creatingClaimsSteps.creatingAClaimWithStatusOpen(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(title);
        //Изменить статус заявки
        claimsPageSteps.setStatusInProcess();
        //Проверить что внесенные изменения сохранились
        claimsPageSteps.getStatusLabel().check(matches(withText("In progress")));
    }

    @Test
    @DisplayName("Смена статуса заявки с In progress на To execute")
    public void shouldChangeTheStatusOfTheClaimToInToExecute() {
        String title = namingHelper.getClaimInProgressName();
        String comment = namingHelper.getComment();

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(title);
        //Изменить статус заявки
        claimsPageSteps.setStatusExecute();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(comment);
        controlPanelSteps.okButtonClick();
        //Проверить что статус изменился
        claimsPageSteps.getStatusLabel().check(matches(withText("Executed")));
        //Проверяем что у заявки появился комментарий
        claimsPageSteps.getCommentDescriptionText().check(matches(withText(comment)));
    }

    @Test
    @DisplayName("Сброс статуса заявки В работе ")
    public void shouldChangeTheStatusOfTheClaimToOpen() {
        String title = namingHelper.getClaimInProgressName();
        String comment = namingHelper.getComment();

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        SystemClock.sleep(3000);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(title);
        //Изменить статус заявки
        claimsPageSteps.setStatusOpen();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(comment);
        controlPanelSteps.okButtonClick();
        //Проверить что статус изменился
        claimsPageSteps.getStatusLabel().check(matches(withText("Open")));
        //Проверяем что у заявки появился комментарий
        claimsPageSteps.getCommentDescriptionText().check(matches(withText(comment)));
    }

    @Test
    @DisplayName("Смена статуса заявки с Open на Canceled")
    public void shouldChangeTheStatusOfTheClaimToCanceled() {
        String title = namingHelper.getClaimOpenName();
        //Создать заявку
        creatingClaimsSteps.creatingAClaimWithStatusOpen(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(title);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        //Проверить что внесенные изменения сохранились
        claimsPageSteps.getStatusLabel().check(matches(withText("Canceled")));
    }

    @Test
    @DisplayName("Работа кнопки назад в карточке заявки")
    public void shouldExitTheClaimCardByClickingTheButtonClose() {
        String title = namingHelper.getClaimInProgressName();
        //Создать заявку
        creatingClaimsSteps.creatingAClaim(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(title);
        //Выйти из карточки заявки, кликнув кнопку Close
        claimsPageSteps.closeImButtonClick();
        //Проверить карточка заявки закрылась
        claimsPageSteps.isClaimsPage();
    }

    //Не удается подобрать проверку
    @Test
    @DisplayName("Сброс статуса заявки В работе с другим исполнителем")
    public void shouldNotChangeTheStatusOfTheClaimIfExecutorSmirnov() {
        String title = namingHelper.getClaimInProgressName();
        //Создать заявку
        creatingClaimsSteps.creatingAClaimExecutorSmirnov(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(title);
        //Проверить, что невозможно изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.throwOffMenuItem).check(doesNotExist());
    }

    @Test
    @DisplayName("Редактирование заявки со статусом In progress")
    public void shouldShowMessageTheClaimCanBeEditedOnlyInTheOpenStatus() {
        String title = namingHelper.getClaimInProgressName();
        //Создать заявку
        creatingClaimsSteps.creatingAClaim(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                today.getHour(), today.getMinute(), title, title);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(title);
        //Тапнуть кнопку редактировать
        claimsPageSteps.editClaimButClick();
        //Проверить появление сообщения "The Claim can be edited only in the Open status."
        controlPanelSteps.checkToast("The Claim can be edited only in the Open status.", true);
    }
}
