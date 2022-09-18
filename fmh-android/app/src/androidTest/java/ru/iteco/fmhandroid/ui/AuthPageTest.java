package ru.iteco.fmhandroid.ui;








import static androidx.test.espresso.matcher.ViewMatchers.assertThat;


import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;
import static ru.iteco.fmhandroid.ui.data.DataHelper.invalidAuthInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.MainPage;

import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.TestApplication;

@RunWith(AndroidJUnit4.class)
public class AuthPageTest {
    private UiDevice device;
    public static final String packageName = "ru.iteco.fmhandroid";
    private TestApplication authPageSteps;
    private MainPageSteps mainPageSteps;
    private MainPage mainPage;

    @Before
    public void setUp() {
        authPageSteps = new TestApplication();
    }


    @Test public void authorizationValidData() throws UiObjectNotFoundException {
        authPageSteps.authentication(authInfo().getLogin(), authInfo().getPass());

        assertEquals("News", mainPage.newsHeader().getText());
        //assertEquals("Claims", mainPage.claimsHear().getText());
        //mainPage.isMainPage();
        authPageSteps.closeApp();
    }

    @Test public void authorizationInvalidData() throws UiObjectNotFoundException {
        authPageSteps.authentication(invalidAuthInfo().getLogin(), invalidAuthInfo().getPass());
        boolean isNotAuthorization = DataHelper.waitForToast("Wrong login or password",3000);
        Assert.assertTrue("this toast", isNotAuthorization);

    }

    /*@After
    public void tearDown() throws UiObjectNotFoundException {
        authPageSteps.close();
    }*/

}
