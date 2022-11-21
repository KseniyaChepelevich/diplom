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
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.CreatingClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimEditTest extends BaseTest{
    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    private static CreatingClaimsSteps creatingClaimsSteps = new CreatingClaimsSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    Calendar date = Calendar.getInstance();

    String titleForTheTestClaim = "Заголовок" + " " + DataHelper.generateTitleId();
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
    @DisplayName("Редактирование заявки")
    public void shouldSaveClaimChanges() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int newDay = date.get(Calendar.DAY_OF_MONTH) + 1;
        String dayExpected = TestUtils.getDateToString(newDay);
        String monthExpected = TestUtils.getDateToString(month);
        String planeDate = dayExpected + "." + monthExpected + "." + year;
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int newHour = date.get(Calendar.HOUR_OF_DAY) + 1;
        String hourExpected = TestUtils.getDateToString(newHour);
        String minutesExpected = TestUtils.getDateToString(minutes);
        String planeTime = hourExpected + ":" + minutesExpected;

        //Создать заявку
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Отредактировать заявку
        claimsPageSteps.editClaim(year, month, newDay, newHour, minutes, newTitle, newTitle);
        //Проверить что внесенные изменения сохранились
        claimsPageSteps.isClaimCard(newTitle, planeDate, planeTime, newTitle);

    }

    @Test
    @DisplayName("Смена статуса заявки на In progress")
    public void shouldChangeTheStatusOfTheClaimToInProgress() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявку
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Изменить статус заявки
        claimsPageSteps.setStatusInProcess();
        SystemClock.sleep(2000);
        //Проверить что внесенные изменения сохранились
        claimsPageSteps.getStatusLabel().check(matches(withText("In progress")));
    }

    @Test
    @DisplayName("Смена статуса заявки с In progress на To execute")
    public void shouldChangeTheStatusOfTheClaimToInToExecute() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);


        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);
        String planeTime = hourExpected + ":" + minutesExpected;
        String commentForTheTestClaim = "Выполнено";

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        SystemClock.sleep(3000);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Изменить статус заявки
        claimsPageSteps.setStatusExecute();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(commentForTheTestClaim);
        controlPanelSteps.okButtonClick();
        SystemClock.sleep(3000);
        //Проверить что статус изменился
        claimsPageSteps.getStatusLabel().check(matches(withText("Executed")));
        //Проверяем что у заявки появился комментарий
        claimsPageSteps.getCommentDescriptionText().check(matches(withText(commentForTheTestClaim)));

    }

    @Test
    @DisplayName("Сброс статуса заявки В работе ")
    public void shouldChangeTheStatusOfTheClaimToOpen() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);
        String commentForTheTestClaim = "Уже не нужно";

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        SystemClock.sleep(3000);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Изменить статус заявки
        claimsPageSteps.setStatusOpen();
        claimsPageSteps.isStatusCommentDialog();
        claimsPageSteps.replaceClaimStatusCommentText(commentForTheTestClaim);
        controlPanelSteps.okButtonClick();
        SystemClock.sleep(3000);
        //Проверить что статус изменился
        claimsPageSteps.getStatusLabel().check(matches(withText("Open")));
        //Проверяем что у заявки появился комментарий
        claimsPageSteps.getCommentDescriptionText().check(matches(withText(commentForTheTestClaim)));

    }

    @Test
    @DisplayName("Смена статуса заявки с Open на Canceled")
    public void shouldChangeTheStatusOfTheClaimToCanceled() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявку
        creatingClaimsSteps.creatingAClaimWithStatusOpen(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Изменить статус заявки
        claimsPageSteps.setStatusCanceled();
        SystemClock.sleep(2000);
        //Проверить что внесенные изменения сохранились
        claimsPageSteps.getStatusLabel().check(matches(withText("Canceled")));
    }

    @Test
    @DisplayName("Работа кнопки назад в карточке заявки")
    public void shouldExitTheClaimCardByClickingTheButtonClose() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        SystemClock.sleep(5000);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        SystemClock.sleep(2000);
        //Выйти из карточки заявки, кликнув кнопку Close
        claimsPageSteps.closeImButtonClick();
        SystemClock.sleep(2000);
        //Проверить карточка заявки закрылась
        claimsPageSteps.isClaimsPage();
    }

    //Не удается подобрать проверку
    @Test
    @DisplayName("Сброс статуса заявки В работе с другим исполнителем")
    public void shouldNotChangeTheStatusOfTheClaimIfExecutorSmirnov() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявку
        creatingClaimsSteps.creatingAClaimExecutorSmirnov(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        SystemClock.sleep(5000);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Проверить, что невозможно изменить статус заявки
        TestUtils.waitView(claimsPageSteps.statusProcessingImBut).perform(click());
        TestUtils.waitView(claimsPageSteps.throwOffMenuItem).check(doesNotExist());

    }

    @Test
    @DisplayName("Редактирование заявки со статусом In progress")
    public void shouldShowMessageTheClaimCanBeEditedOnlyInTheOpenStatus() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        SystemClock.sleep(3000);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Тапнуть кнопку редактировать
        claimsPageSteps.editClaimButClick();
        //Проверить появление сообщения "The Claim can be edited only in the Open status."
        controlPanelSteps.checkToast("The Claim can be edited only in the Open status.", true);
    }


}
