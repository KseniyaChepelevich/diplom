package ru.iteco.fmhandroid.ui;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.page.AuthPageElements;
import ru.iteco.fmhandroid.ui.page.ClaimsPageElements;
import ru.iteco.fmhandroid.ui.page.MainPageElements;
import ru.iteco.fmhandroid.ui.steps.*;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
//import static ru.iteco.fmhandroid.ui.ControlPanelTest.mainPageSteps;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

@RunWith(AllureAndroidJUnit4.class)

public class MainPageTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();
    ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    AboutPageSteps aboutPageSteps = new AboutPageSteps();
    OurMissionPageSteps ourMissionPageSteps = new OurMissionPageSteps();


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
    }

    @Test
    @DisplayName("Переход по кнопке ALL NEWS")
    public void shouldOpenNewsPageByButAllNews() {
        TestUtils.waitView(mainPageSteps.allNewsBut).perform(click());
        newsPageSteps.isNewsPage();
    }

    @Test
    @DisplayName("Переход в раздел News через главное меню")
    public void shouldOpenNewsPageByButNewsInTheMainMenu() {
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
    }

    @Test
    @DisplayName("Переход в раздел Claims через главное меню")
    public void shouldOpenClaimsPageByButClaimsInTheMainMenu() {
        mainPageSteps.openClaimsPageThroughTheMainMenu();
        claimsPageSteps.isClaimsPage();
    }

    @Test
    @DisplayName("Переход по кнопке ALL CLAIMS")
    public void shouldOpenClaimsPageByButAllClaims() {
        TestUtils.waitView(mainPageSteps.allClaimsBut).perform(click());
        claimsPageSteps.isClaimsPage();
    }

    @Test
    @DisplayName("Переход в раздел About через главное меню")
    public void shouldOpenAboutPage() {
        //SystemClock.sleep(3000);
        mainPageSteps.openAboutPageThroughTheMainMenu();
        aboutPageSteps.isAboutPage();
    }

    @Test
    @DisplayName("Переход в раздел Our mission по кнопке в AppBar")
    public void shouldOpenOurMissionPage() {
        //SystemClock.sleep(3000);
        TestUtils.waitView(mainPageSteps.ourMissionImBut).perform(click());
        ourMissionPageSteps.isOurMissionPage();
    }

    @Test
    @DisplayName("Разворот описания новости на Главной странице")
    public void shouldOpenNewsItemDescription() {
        TestUtils.waitView(newsPageSteps.newsItemMaterialCardView1).perform(click());
        TestUtils.waitView(newsPageSteps.newsItemDescriptionTextView1).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Сворачивание списка новостей на Главной странице")
    public void shouldCollapseTheListOfNews() {
        TestUtils.waitView(mainPageSteps.newsExpandMaterialBut).perform(click());
        mainPageSteps.isNewsBlockCollapsed();
    }

    @Test
    @DisplayName("Сворачивание списка заявок на Главной странице")
    public void shouldCollapseTheListOfClaims() {
        TestUtils.waitView(mainPageSteps.claimsExpandMaterialBut).perform(click());
        mainPageSteps.isClaimsBlockCollapsed();
    }

    @Test
    @DisplayName("Разворот описания заявки на Главной странице")
    public void shouldOpenClaimsItemDescription() {
        TestUtils.waitView(mainPageSteps.scrollView).check(matches(isEnabled()));
        TestUtils.waitView(mainPageSteps.scrollView).perform(swipeUp());
        SystemClock.sleep(3000);
        TestUtils.waitView(mainPageSteps.claimsListCard4).perform(click());
        TestUtils.waitView(claimsPageSteps.claimsItemDescription).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Добавление заявки с главной страницы")
    public void shouldOpenTheClaimsForm() {
        TestUtils.waitView(mainPageSteps.addNewClaimBut).perform(click());
        claimsPageSteps.isClaimsForm();
    }
}
