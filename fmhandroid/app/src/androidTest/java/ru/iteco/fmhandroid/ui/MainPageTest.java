package ru.iteco.fmhandroid.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.data.CustomViewAssertion;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.*;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
//import static ru.iteco.fmhandroid.ui.ControlPanelTest.mainPageSteps;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

@RunWith(AllureAndroidJUnit4.class)

public class MainPageTest {
    private UiDevice device;

    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();
    ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    AboutPageSteps aboutPageSteps = new AboutPageSteps();
    OurMissionPageSteps ourMissionPageSteps = new OurMissionPageSteps();

    private static final String APPS_PACKAGE = "com.google.android.apps.nexuslauncher";

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
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
        mainPageSteps.openAboutPageThroughTheMainMenu();
        aboutPageSteps.isAboutPage();
    }

    @Test
    @DisplayName("Переход в раздел Our mission по кнопке в AppBar")
    public void shouldOpenOurMissionPage() {
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

    @Test
    @DisplayName("Выход из личного кабинета")
    public void shouldOpenTheLoginPage() {
        mainPageSteps.clickLogOutBut();
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Переход по кнопке Back")
    public void shouldOpenTheNewsPage() {
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        newsPageSteps.openMainPage();
        mainPageSteps.isMainPage();
        pressBack();
        mainPageSteps.isMainPage();
    }

    @Test
    @DisplayName("Переход по кнопке Home")
    public void shouldCloseTheApp() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        //device.pressHome();

        //Проверка, что приложение отправлено в фоновый режим.
        //Придумать как проверить что работает проверка
        CustomViewAssertion.isApplicationSentToBackground(appContext);

    }


}
