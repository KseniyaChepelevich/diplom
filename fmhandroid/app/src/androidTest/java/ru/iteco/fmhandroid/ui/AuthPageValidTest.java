package ru.iteco.fmhandroid.ui;

import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.page.AuthPageElements;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


//@RunWith(AllureAndroidJUnit4.class)
@RunWith(AndroidJUnit4.class)

public class AuthPageValidTest {



        private AuthSteps authSteps = new AuthSteps();
        private AuthPageElements authsScreenElements = new AuthPageElements();
        private MainPageSteps mainPageSteps = new MainPageSteps();

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
        }

        @After
        public void setUp() {
            SystemClock.sleep(3000);
        }



        @Test
        @DisplayName("Проверка элементов экрана авторизации")
        public void shouldCheckAuthScreenElements() {
            authSteps.isAuthScreen();
        }

        @Test
        @DisplayName("Вход с валидными данными")
        public void shouldLogInWithValidData() {
            authSteps.authWithValidData(authInfo());
            SystemClock.sleep(3000);
            mainPageSteps.isMainPage();
        }
}
