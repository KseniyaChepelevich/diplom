package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
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
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.CreatingClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class ClaimsPageTest extends BaseTest{
    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    private static CreatingClaimsSteps creatingClaimsSteps = new CreatingClaimsSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    Calendar date = Calendar.getInstance();

    String titleForTheTestClaim = "Заголовок" + " " + DataHelper.generateTitleId();
    String commentForTheTestClaim = "Комментарий" + " " + DataHelper.generateTitleId();


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
    @DisplayName("Открытие фильтра заявок по кнопке Filter")
    public void shouldOpenTheClaimsFilterSettingsForm() {
        claimsPageSteps.openClaimsFilter();
        claimsPageSteps.isClaimsFilteringDialog();
    }

    @Test
    @DisplayName("Открытие формы Создания заявки в разделе Заявки")
    public void shouldOpenTheCreateClaimForm() {
        claimsPageSteps.openCreatingClaimsCard();
        claimsPageSteps.isClaimsForm();
    }

    @Test
    @DisplayName("Окрытие Заявки с помощью кнопки со стрелкой")
    public void shouldOpenTheClaimCard() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String dayExpected = TestUtils.getDateToString(day);
        String monthExpected = TestUtils.getDateToString(month);
        String planeDate = dayExpected + "." + monthExpected + "." + year;
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY)+1;
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);
        String planeTime = hourExpected + ":" + minutesExpected;

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);

        SystemClock.sleep(3000);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(titleForTheTestClaim);

        //Проверить, что отображается карточка созданной заявки
        claimsPageSteps.isClaimCard(titleForTheTestClaim,planeDate,planeTime, titleForTheTestClaim);
    }

    @Test
    @DisplayName("Добавление комментария к заявке")
    public void shouldAddACommentToTheClaim() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        //Создать заявку
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);

        SystemClock.sleep(3000);
        //Открываем заявку для редактирования
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Добавить комментарий
        claimsPageSteps.openCreatingCommentForm();
        claimsPageSteps.isCommentForm();

        claimsPageSteps.replaceCommentTextInputText(commentForTheTestClaim);
        controlPanelSteps.saveNewsButtonClick();
        //Проверить что комментарий сохранился
        claimsPageSteps.getCommentDescriptionText().check(matches(withText(commentForTheTestClaim)));
    }

    @Test
    @DisplayName("Добавление пустого комментария")
    public void shouldShowMessageFieldCannotBeEmpty() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        SystemClock.sleep(3000);
        //Открываем заявку для редактирования
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Добавить пустой комментарий
        claimsPageSteps.openCreatingCommentForm();
        claimsPageSteps.isCommentForm();

       controlPanelSteps.saveNewsButtonClick();
        //Проверить что отображается сообщение
        controlPanelSteps.checkToast("The field cannot be empty.", true);
    }

    @Test
    @DisplayName("Отмена добавления комментария")
    public void shouldNotSaveCommentWhenCancelButtonIsClicked() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        SystemClock.sleep(3000);
        //Открываем заявку для редактирования
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Добавить комментарий
        claimsPageSteps.openCreatingCommentForm();
        claimsPageSteps.isCommentForm();
        //SystemClock.sleep(3000);
        claimsPageSteps.replaceCommentTextInputText(commentForTheTestClaim);
        controlPanelSteps.cancelButtonClick();
        //Проверить что комментарий сохранился
        claimsPageSteps.getClaimCommentsListRecyclerView()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher
                        .matchChildViewIsNotExist(claimsPageSteps.commentDescriptionTextView, withText(commentForTheTestClaim))));
    }

    @Test
    @DisplayName("Редактирование комментария")
    public void shouldEditTheComment() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String newComment = "Отредактированный" + " " + DataHelper.generateTitleId();
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        SystemClock.sleep(3000);
        //Открываем заявку для редактирования
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Добавить комментарий
        claimsPageSteps.openCreatingCommentForm();
        claimsPageSteps.isCommentForm();
        //SystemClock.sleep(3000);
        claimsPageSteps.replaceCommentTextInputText(commentForTheTestClaim);
        controlPanelSteps.saveNewsButtonClick();
        //Редактируем комменарий
        claimsPageSteps.editComment(newComment);
        //Проверяем,что сохранился новый комментарий
        claimsPageSteps.getCommentDescriptionText().check(matches(withText(newComment)));

    }

    @Test
    @DisplayName("Небуквенные и нецифровые знаки в поле Комментарий при редактировании заявки")
    public void shouldShowWarningMessageClaimCommentFieldIsIncorrect() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);


        String nonLetterComment = ";&&";

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        SystemClock.sleep(3000);
        //Открываем заявку для редактирования
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Добавить комментарий
        claimsPageSteps.openCreatingCommentForm();
        claimsPageSteps.isCommentForm();
        //SystemClock.sleep(3000);
        claimsPageSteps.replaceCommentTextInputText(nonLetterComment);

        controlPanelSteps.saveNewsButtonClick();
        controlPanelSteps.checkToast("The field must not contain \";&&\" characters.", true);

    }

    @Test
    @DisplayName("Отображение только что закрытой заявки на экране в списке заявок")
    public void shouldFindOnTheScreenJustClosedClaim() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY);

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(year, month, day, hour, minutes, titleForTheTestClaim, titleForTheTestClaim);
        SystemClock.sleep(3000);
        //Находим заявку в списке и отркываем ее
        claimsPageSteps.openClaimCard(titleForTheTestClaim);
        //Закрываем заявку
        claimsPageSteps.closeImButtonClick();
        //Проверяем, что только-что закрытая заявка видна на экране
        claimsPageSteps.getItemClaimCompatImView(titleForTheTestClaim).check(matches(isDisplayed()));

    }

}
