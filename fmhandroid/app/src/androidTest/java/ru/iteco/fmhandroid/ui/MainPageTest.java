package ru.iteco.fmhandroid.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
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
import org.junit.Ignore;
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

public class MainPageTest extends BaseTest{
    private UiDevice device;

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static NewsPageSteps newsPageSteps = new NewsPageSteps();
    private static ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    private static AboutPageSteps aboutPageSteps = new AboutPageSteps();
    private static OurMissionPageSteps ourMissionPageSteps = new OurMissionPageSteps();

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
    }

    @Test
    @DisplayName("Переход по кнопке ALL NEWS")
    public void shouldOpenNewsPageByButAllNews() {
        mainPageSteps.clickAllNewsBut();
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
        mainPageSteps.clickAllClaimsBut();
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
        mainPageSteps.openOurMissionPage();
        ourMissionPageSteps.isOurMissionPage();
    }

    @Test
    @DisplayName("Разворот описания новости на Главной странице")
    public void shouldOpenNewsItemDescription() {
        int positionNews = 1;
        newsPageSteps.openNewsOnNewsPage(positionNews);
        newsPageSteps.getNewsItemDescription(positionNews).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Сворачивание списка новостей на Главной странице")
    public void shouldCollapseTheListOfNews() {
        mainPageSteps.newsExpandMaterialButtonClick();
        mainPageSteps.isNewsBlockCollapsed();
    }

    @Test
    @DisplayName("Сворачивание списка заявок на Главной странице")
    public void shouldCollapseTheListOfClaims() {
        mainPageSteps.claimsExpandMaterialButtonClick();
        mainPageSteps.isClaimsBlockCollapsed();
    }

    @Test
    @DisplayName("Разворот описания заявки на Главной странице")
    public void shouldOpenClaimsItemDescription() {
        int claimPosition = 4;
        mainPageSteps.openClaimItemDescription(claimPosition);
        claimsPageSteps.getClaimItemDescription().check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Добавление заявки с главной страницы")
    public void shouldOpenTheClaimsForm() {
        mainPageSteps.addNewClaimButtonClick();
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

    @Ignore//Ненадежная проверка
    @Test
    @DisplayName("Переход по кнопке Home")
    public void shouldCloseTheApp() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressHome();

        //Проверка, что приложение отправлено в фоновый режим.
        //Придумать как проверить что работает проверка
        CustomViewAssertion.isApplicationSentToBackground(appContext);

    }


}
