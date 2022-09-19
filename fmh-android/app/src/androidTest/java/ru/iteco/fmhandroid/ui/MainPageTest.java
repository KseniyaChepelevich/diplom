package ru.iteco.fmhandroid.ui;

import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

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

        @Test public void transitionOnTheBackBut() throws UiObjectNotFoundException {
            authPageSteps.authentication(authInfo().getLogin(), authInfo().getPass());
            MatcherAssert.assertThat(mainPage.allNewsBut().isEnabled(), Is.is(true));
            mainPage.allNewsBut().click();
            //newsPage.newsPageHeader().isEnabled();
            //device.pressBack();
            //mainPage.mainPageName().isEnabled();





        }
}
