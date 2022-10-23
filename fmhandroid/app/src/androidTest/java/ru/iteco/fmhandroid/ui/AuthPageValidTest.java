package ru.iteco.fmhandroid.ui;

import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
//import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.EspressoIdlingResources;
import ru.iteco.fmhandroid.ui.page.AuthPageElements;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;


//@RunWith(AllureAndroidJUnit4.class)
@RunWith(AndroidJUnit4.class)
public class AuthPageValidTest {




    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);
    //public ActivityScenarioRule rule = new ActivityScenarioRule<>(AppActivity.class);

    AuthSteps authSteps = new AuthSteps();
    //AuthPageElements authsPageElements = new AuthPageElements();
    MainPageSteps mainPageSteps = new MainPageSteps();

    @Before
    public void logoutCheck() {
        //IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource);
        //SystemClock.sleep(8000);
        try {
            authSteps.isAuthScreen();
        } catch (NoMatchingViewException e) {
            SystemClock.sleep(8000);
            mainPageSteps.clickLogOutBut();
        }
    }

    @After
    public void setUp() {


        SystemClock.sleep(3000);
    }


    @Test
    @DisplayName("Проверка элементов экрана авторизации")
    public void shouldCheckAuthPageElements() {
        //ActivityScenario scenario = rule.getScenario();
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Вход с валидными данными")
    public void shouldLogInWithValidData() {
        //ActivityScenario scenario = rule.getScenario();
        authSteps.authWithValidData(authInfo());
        SystemClock.sleep(3000);
        mainPageSteps.isMainPage();
    }
}
