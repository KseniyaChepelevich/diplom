package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
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
import ru.iteco.fmhandroid.ui.page.ClaimsPageElements;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.CreatingClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class ClaimsPageTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    CreatingClaimsSteps creatingClaimsSteps = new CreatingClaimsSteps();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    Calendar date = Calendar.getInstance();

    String titleForTheTestClaim = "Заголовок" + " " + DataHelper.generateTitleId();
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
    @DisplayName("Открытие фильтра заявок по кнопке Filter")
    public void shouldOpenTheClaimsFilterSettingsForm() {
        TestUtils.waitView(claimsPageSteps.claimsFiltersButton).perform(click());
        claimsPageSteps.isClaimsFilteringDialog();
    }

    @Test
    @DisplayName("Открытие формы Создания заявки в разделе Заявки")
    public void shouldOpenTheCreateClaimForm() {
        TestUtils.waitView(claimsPageSteps.addNewClaimBut).perform(click());
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
        TestUtils.waitView(claimsPageSteps.addCommentBut).perform(click());
        claimsPageSteps.isCommentForm();

        TestUtils.waitView(claimsPageSteps.commentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверить что комментарий сохранился
        TestUtils.waitView(claimsPageSteps.commentDescriptionText).check(matches(withText(commentForTheTestClaim)));
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
        TestUtils.waitView(claimsPageSteps.addCommentBut).perform(click());
        claimsPageSteps.isCommentForm();

        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
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
        TestUtils.waitView(claimsPageSteps.addCommentBut).perform(click());
        claimsPageSteps.isCommentForm();
        //SystemClock.sleep(3000);
        TestUtils.waitView(claimsPageSteps.commentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.cancelBut).perform(click());
        //Проверить что комментарий сохранился
        TestUtils.waitView(claimsPageSteps.claimCommentsListRecyclerView).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.comment_description_text_view, withText(commentForTheTestClaim))));
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
        TestUtils.waitView(claimsPageSteps.addCommentBut).perform(click());
        claimsPageSteps.isCommentForm();
        //SystemClock.sleep(3000);
        TestUtils.waitView(claimsPageSteps.commentTextInputField).perform(replaceText(commentForTheTestClaim));
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Редактируем комменарий
        TestUtils.waitView(claimsPageSteps.editCommentImBut).perform(click());
        claimsPageSteps.isCommentForm();
        TestUtils.waitView(claimsPageSteps.commentTextInputField).perform(replaceText(newComment));
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверяем,что сохранился новый комментарий
        TestUtils.waitView(claimsPageSteps.commentDescriptionText).check(matches(withText(newComment)));

    }

}
