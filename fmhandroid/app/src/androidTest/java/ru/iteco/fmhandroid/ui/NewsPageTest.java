package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.FilterNewsPageElements;
import ru.iteco.fmhandroid.ui.page.NewsPageElements;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

//@RunWith(AllureAndroidJUnit4.class)
@RunWith(AndroidJUnit4.class)

public class NewsPageTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();
    NewsPageElements newsPageElements =  new NewsPageElements();
    FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    FilterNewsPageElements filterNewsPageElements = new FilterNewsPageElements();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

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

    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("Открытие фильтра новостей по кнопке Filter")
    public void shouldOpenTheNewsFilterSettingsForm() {
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        SystemClock.sleep(3000);
        filterNewsPageSteps.isFilterNewsForm();
    }
//Дублируется с тестом на Главной странице "Разворот описания заявки на Главной странице". Оставить элементы только в NewsPageElements
    @Test
    @DisplayName("Разворачиване описания новости")
    public void shouldOpenTheNewsDescription() {
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.newsItemMaterialCardView1);
        SystemClock.sleep(3000);
        newsPageElements.newsItemDescriptionTextView1.check(matches(isDisplayed()));
    }



    @Test
    @DisplayName("Переход по кнопке Edit News")
    public void shouldOpenTheControlPanel() {
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        SystemClock.sleep(3000);
        controlPanelSteps.isControlPanel();
    }



}

