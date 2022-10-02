package ru.iteco.fmhandroid.ui;

import android.os.SystemClock;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import io.qameta.allure.kotlin.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.AuthPageElements;
import ru.iteco.fmhandroid.ui.page.ClaimsPageElements;
import ru.iteco.fmhandroid.ui.page.MainPageElements;
import ru.iteco.fmhandroid.ui.steps.*;

import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

//@RunWith(AllureAndroidJUnit4.class)
@RunWith(AndroidJUnit4.class)

public class MainPageTest {
    AuthSteps authSteps = new AuthSteps();
    AuthPageElements authsPageElements = new AuthPageElements();
    MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();

    ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();

    AboutPageSteps aboutPageSteps = new AboutPageSteps();

    OurMissionPageSteps ourMissionPageSteps = new OurMissionPageSteps();

    MainPageElements mainPageElements = new MainPageElements();

    ClaimsPageElements claimsPageElements = new ClaimsPageElements();

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
    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("Переход по кнопке ALL NEWS")
    public void shouldOpenNewsPageByButAllNews() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.allNewsBut);
        SystemClock.sleep(3000);
        newsPageSteps.isNewsPage();
    }

    @Test
    @DisplayName("Переход в раздел News через главное меню")
    public void shouldOpenNewsPageByButNewsInTheMainMenu() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.mainMenuImBut);
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.newsInTheMainMenu);
        SystemClock.sleep(3000);
        newsPageSteps.isNewsPage();
    }

    @Test
    @DisplayName("Переход в раздел Claims через главное меню")
    public void shouldOpenClaimsPageByButClaimsInTheMainMenu() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.mainMenuImBut);
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.claimsInTheMainMenu);
        SystemClock.sleep(3000);
        claimsPageSteps.isClaimsPage();

    }

    @Test
    @DisplayName("Переход по кнопке ALL CLAIMS")
    public void shouldOpenClaimsPageByButAllClaims() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.allClaimsBut);
        SystemClock.sleep(3000);
        claimsPageSteps.isClaimsPage();
    }

    @Test
    @DisplayName("Переход в раздел About через главное меню")
    public void shouldOpenAboutPage() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.mainMenuImBut);
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.aboutInTheMainMenu);
        SystemClock.sleep(3000);
        aboutPageSteps.isAboutPage();


    }

    @Test
    @DisplayName("Переход в раздел Our mission по кнопке в AppBar")
    public void shouldOpenOurMissionPage() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.ourMissionImBut);
        SystemClock.sleep(3000);
        ourMissionPageSteps.isOurMissionPage();

    }

    @Test
    @DisplayName("Разворот описания новости на Главной странице")
    public void shouldOpenNewsItemDescription() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.newsItemMaterialCardList1);
        SystemClock.sleep(3000);
        mainPageElements.newsItemDescription1.check(matches(isDisplayed()));

    }

    @Test
    @DisplayName("Сворачивание списка новостей на Главной странице")
    public void shouldCollapseTheListOfNews() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.newsExpandMaterialBut);
        SystemClock.sleep(3000);
        mainPageSteps.isNewsBlockCollapsed();

    }

    @Test
    @DisplayName("Сворачивание списка заявок на Главной странице")
    public void shouldCollapseTheListOfClaims() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.claimsExpandMaterialBut);
        SystemClock.sleep(3000);
        mainPageSteps.isClaimsBlockCollapsed();

    }

    /*@Test
    @DisplayName("Разворот описания заявки на Главной странице")
    public void shouldOpenClaimsItemDescription() {
        SystemClock.sleep(3000);
        mainPageElements.scrollView.check(matches(isEnabled()));
        mainPageElements.scrollView.perform(scrollTo());
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.claimListCard1);
        SystemClock.sleep(3000);
        //claimsPageElements.claimsItemDescription.check(matches(isDisplayed()));

    }*/

    @Test
    @DisplayName("Добавление заявки с главной страницы")
    public void shouldOpenTheClaimsForm() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.addNewClaimBut);
        SystemClock.sleep(3000);
        claimsPageSteps.isClaimsForm();


    }






}
