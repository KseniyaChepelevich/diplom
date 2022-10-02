package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.page.MainPage;
import ru.iteco.fmhandroid.ui.page.NewsPage;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.TestApplication;

@RunWith(AndroidJUnit4.class)
public class MainPageTest {


    private UiDevice device;
    public static final String packageName = "ru.iteco.fmhandroid";
    private TestApplication authPageSteps;
    private MainPageSteps mainPageSteps;
    private MainPage mainPage;
    private NewsPage newsPage;

    @Before
    public void setUp() {
        authPageSteps = new TestApplication();
    }

    @After
    public void tearDown() throws UiObjectNotFoundException {
        authPageSteps.closeApp();
    }

    @Test
    public void transitionOnTheAllNewsBut() throws UiObjectNotFoundException {
        authPageSteps.authentication(authInfo().getLogin(), authInfo().getPass());
        //mainPage.news().check(matches(isDisplayed()));
        //MatcherAssert.assertThat(mainPage.allNewsBut().isEnabled(), Is.is(true));
        /*UiObject allNews = device.findObject(new UiSelector().resourceId("container_list_news_include_on_fragment_main"));
        allNews.clickBottomRight();*/
        //mainPage.allNewsBut().click();
        //newsPage.newsPageHeader().isEnabled();
        //device.pressBack();
        //mainPage.mainPageName().isEnabled();20
    }

    @Test
    public void transitionOnTheAllClaimsBut() throws UiObjectNotFoundException {
        authPageSteps.authentication(authInfo().getLogin(), authInfo().getPass());
        //MatcherAssert.assertThat(mainPage.allNewsBut().isEnabled(), Is.is(true));
        mainPage.allClaimsBut().click();
        //newsPage.newsPageHeader().isEnabled();
        //device.pressBack();
        //mainPage.mainPageName().isEnabled();20
    }

    @Test
    public void transitionOnTheHomeBut() throws UiObjectNotFoundException {
        authPageSteps.authentication(authInfo().getLogin(), authInfo().getPass());
        device.pressHome();

    }

    @Test
    public void transitionOnTheBackBut() throws UiObjectNotFoundException {
        authPageSteps.authentication(authInfo().getLogin(), authInfo().getPass());
        device.pressBack();

    }

    @Test
    public void transitionOnTheNewsPage() throws UiObjectNotFoundException {
        authPageSteps.authentication(authInfo().getLogin(), authInfo().getPass());
        UiObject mainMenuImBut = device.findObject(new UiSelector().className("android.view.ViewGroup").resourceId("ru.iteco.fmhandroid:id/container_custom_app_bar_include_on_fragment_main").fromParent(new UiSelector().className("android.widget.ImageButton").description("Main menu").resourceId("ru.iteco.fmhandroid:id/main_menu_image_button")));
        mainMenuImBut.click();

    }



}
