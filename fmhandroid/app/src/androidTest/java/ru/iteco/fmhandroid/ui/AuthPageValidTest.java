package ru.iteco.fmhandroid.ui;

import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
//import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
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


@RunWith(AllureAndroidJUnit4.class)

public class AuthPageValidTest {

    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();

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
    }

    @Test
    @DisplayName("Проверка элементов экрана авторизации")
    public void shouldCheckAuthPageElements() {
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Вход с валидными данными")
    public void shouldLogInWithValidData() {
        authSteps.authWithValidData(authInfo());
        mainPageSteps.isMainPage();
    }
}
