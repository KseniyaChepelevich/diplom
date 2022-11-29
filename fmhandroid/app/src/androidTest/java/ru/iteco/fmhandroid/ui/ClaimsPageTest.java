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
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.CreatingClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class ClaimsPageTest extends BaseTest {
    private UiDevice device;
    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    private static CreatingClaimsSteps creatingClaimsSteps = new CreatingClaimsSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();
    private static NamingHelper namingHelper = new NamingHelper();

    LocalDateTime date = LocalDateTime.now();

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
        String title = namingHelper.getClaimInProgressName();
        String planeDate = TestUtils.getDateToString(date);
        String planeTime = TestUtils.getTimeToString(date);

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                date.getHour(), date.getMinute(), title, title);
        //Открыть карточку заявки
        claimsPageSteps.openClaimCard(title);
        //Проверить, что отображается карточка созданной заявки
        claimsPageSteps.isClaimCard(title, planeDate, planeTime, title);
    }

    @Test
    @DisplayName("Добавление комментария к заявке")
    public void shouldAddACommentToTheClaim() {
        String title = namingHelper.getClaimInProgressName();
        String comment = namingHelper.getComment();
        //Создать заявку
        creatingClaimsSteps.creatingAClaim(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                date.getHour(), date.getMinute(), title, title);
        //Открываем заявку для редактирования
        claimsPageSteps.openClaimCard(title);
        //Добавить комментарий
        claimsPageSteps.openCreatingCommentForm();
        claimsPageSteps.isCommentForm();
        claimsPageSteps.replaceCommentTextInputText(comment);
        controlPanelSteps.saveNewsButtonClick();
        //Проверить что комментарий сохранился
        claimsPageSteps.getCommentDescriptionText().check(matches(withText(comment)));
    }

    @Test
    @DisplayName("Добавление пустого комментария")
    public void shouldShowMessageFieldCannotBeEmpty() {
        String title = namingHelper.getClaimInProgressName();
        //Создать заявку
        creatingClaimsSteps.creatingAClaim(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                date.getHour(), date.getMinute(), title, title);
        //Открываем заявку для редактирования
        claimsPageSteps.openClaimCard(title);
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
        String title = namingHelper.getClaimInProgressName();
        String comment = namingHelper.getComment();
        //Создать заявку
        creatingClaimsSteps.creatingAClaim(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                date.getHour(), date.getMinute(), title, title);
        //Открываем заявку для редактирования
        claimsPageSteps.openClaimCard(title);
        //Добавить комментарий
        claimsPageSteps.openCreatingCommentForm();
        claimsPageSteps.isCommentForm();
        claimsPageSteps.replaceCommentTextInputText(comment);
        controlPanelSteps.cancelButtonClick();
        //Проверить что комментарий не сохранился
        claimsPageSteps.getClaimCommentsListRecyclerView()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher
                        .matchChildViewIsNotExist(claimsPageSteps.commentDescriptionTextView, withText(comment))));
    }

    @Test
    @DisplayName("Редактирование комментария")
    public void shouldEditTheComment() {
        String title = namingHelper.getClaimInProgressName();
        String comment = namingHelper.getComment();
        String newComment = namingHelper.getComment();;

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                date.getHour(), date.getMinute(), title, title);
        //Открываем заявку для редактирования
        claimsPageSteps.openClaimCard(title);
        //Добавить комментарий
        claimsPageSteps.openCreatingCommentForm();
        claimsPageSteps.isCommentForm();
        claimsPageSteps.replaceCommentTextInputText(comment);
        controlPanelSteps.saveNewsButtonClick();
        //Редактируем комменарий
        claimsPageSteps.editComment(newComment);
        //Проверяем,что сохранился новый комментарий
        claimsPageSteps.getCommentDescriptionText().check(matches(withText(newComment)));
    }

    @Test
    @DisplayName("Небуквенные и нецифровые знаки в поле Комментарий при редактировании заявки")
    public void shouldShowWarningMessageClaimCommentFieldIsIncorrect() {
        String title = namingHelper.getClaimInProgressName();
        String nonLetterComment = ";&&";

        //Создать заявку
        creatingClaimsSteps.creatingAClaim(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                date.getHour(), date.getMinute(), title, title);
        //Открываем заявку для редактирования
        claimsPageSteps.openClaimCard(title);
        //Добавить комментарий
        claimsPageSteps.openCreatingCommentForm();
        claimsPageSteps.isCommentForm();
        claimsPageSteps.replaceCommentTextInputText(nonLetterComment);
        controlPanelSteps.saveNewsButtonClick();
        controlPanelSteps.checkToast("The field must not contain \";&&\" characters.", true);
    }

    @Test
    @DisplayName("Отображение только что закрытой заявки на экране в списке заявок")
    public void shouldFindOnTheScreenJustClosedClaim() {
        String title = namingHelper.getClaimInProgressName();
        //Создать заявку
        creatingClaimsSteps.creatingAClaim(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                date.getHour(), date.getMinute(), title, title);
        //Находим заявку в списке и отркываем ее
        claimsPageSteps.openClaimCard(title);
        //Закрываем заявку
        claimsPageSteps.closeImButtonClick();
        //Проверяем, что только-что закрытая заявка видна на экране
        claimsPageSteps.getItemClaimCompatImView(title).check(matches(isDisplayed()));
    }

}
