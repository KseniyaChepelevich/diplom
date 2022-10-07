package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.ControlPanelElements;
import ru.iteco.fmhandroid.ui.page.FilterNewsPageElements;
import ru.iteco.fmhandroid.ui.page.NewsPageElements;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

@RunWith(AndroidJUnit4.class)
public class ControlPanelTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();
    NewsPageElements newsPageElements =  new NewsPageElements();
    FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    FilterNewsPageElements filterNewsPageElements = new FilterNewsPageElements();
    ControlPanelElements controlPanelElements = new ControlPanelElements();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    String titleNewsForEditing = "Новость для редактирования";
    String titleNewsToDelete = "Новость для удаления";
    String titleNewsWillNotBeDeleted = "Новость не будет удалена";
    String titleNewsWillNotBeEditing = "Новость не будет изменена";
    String titleNewsSavedWithoutChanges = "Новость сохранена без изменений";
    String titleNewsTurnOffActiveStatus = "Новость для выключения активного статуса";

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            authSteps.isAuthScreen();
        } catch (NoMatchingViewException e) {
            mainPageSteps.clickLogOutBut();
        }
        authSteps.authWithValidData(authInfo());
        mainPageSteps.openNewsPageThroughTheMainMenu();
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);

    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("Открытие формы создания новости")
    public void shouldOpenCreatNewsForm() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.isCreatingNewsForm();
    }

    @Test
    @DisplayName("Отмена удаления новости во вкладке Control panel")
    public void shouldNotRemoveTheNewsItem(){
        controlPanelSteps.creatingTestNews(titleNewsWillNotBeDeleted, titleNewsWillNotBeDeleted);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWillNotBeDeleted)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsDeleteElement(titleNewsWillNotBeDeleted).perform(click());
        controlPanelElements.messageAboutDelete.check(matches(isDisplayed()));
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.cancelDeleteBut);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWillNotBeDeleted)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.deleteItemNews(titleNewsWillNotBeDeleted);
    }



    @Test
    @DisplayName("Открытие и закрытие Новости для редактирования без внесения изменений")
    public void shouldNotEditTheNews(){
        controlPanelSteps.creatingTestNews(titleNewsWillNotBeEditing, titleNewsWillNotBeEditing);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWillNotBeEditing)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsWillNotBeEditing).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsWillNotBeEditing);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.cancelBut);
        controlPanelElements.messageChangesWonTBeSaved.check(matches(isDisplayed()));
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWillNotBeEditing)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.deleteItemNews(titleNewsWillNotBeEditing);
    }

    @Test
    @DisplayName("Открытие и сохранение Новости для редактирования без внесения изменений")
    public void shouldKeepTheNewsUnchanged(){
        controlPanelSteps.creatingTestNews(titleNewsSavedWithoutChanges, titleNewsSavedWithoutChanges);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsSavedWithoutChanges)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsSavedWithoutChanges).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsSavedWithoutChanges);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsSavedWithoutChanges)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.deleteItemNews(titleNewsSavedWithoutChanges);
    }

    @Test
    @DisplayName("Выключение Активного статуса у Новости")
    public void shouldTurnOffActiveStatus(){
        controlPanelSteps.creatingTestNews(titleNewsTurnOffActiveStatus, titleNewsTurnOffActiveStatus);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsTurnOffActiveStatus)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsTurnOffActiveStatus).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsTurnOffActiveStatus);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.switcherActive);
        controlPanelElements.switcherNotActive.check(matches(isDisplayed()));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsTurnOffActiveStatus)), hasDescendant(withText("NOT ACTIVE")))))
                .check(matches(isDisplayed()));

        controlPanelSteps.deleteItemNews(titleNewsSavedWithoutChanges);
    }
}
