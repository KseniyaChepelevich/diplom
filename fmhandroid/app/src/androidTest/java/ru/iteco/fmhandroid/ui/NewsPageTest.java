package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import androidx.test.espresso.PerformException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

@RunWith(AllureAndroidJUnit4.class)


public class NewsPageTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();
    FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

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
        mainPageSteps.openNewsPageThroughTheMainMenu();
    }

    @Test
    @DisplayName("Открытие фильтра новостей по кнопке Filter")
    public void shouldOpenTheNewsFilterSettingsForm() {
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
    }

    @Test
    @DisplayName("Разворачиване описания новости")
    public void shouldOpenTheNewsDescription() {
        TestUtils.waitView(newsPageSteps.newsItemMaterialCardView1).perform(click());
        TestUtils.waitView(newsPageSteps.newsItemDescriptionTextView1).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Переход по кнопке Edit News")
    public void shouldOpenTheControlPanel() {
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.isControlPanel();
    }

    @Test
    @DisplayName("Открытие Главной страницы из главного меню")
    public void shouldOpenMainPage() {
        newsPageSteps.openMainPage();
        mainPageSteps.isMainPage();
    }
}

