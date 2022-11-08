package ru.iteco.fmhandroid.ui;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.page.AuthPageElements;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;
import static ru.iteco.fmhandroid.ui.data.DataHelper.invalidAuthInfo;

@RunWith(AllureAndroidJUnit4.class)

public class AuthPageInvalidTest {

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
    @DisplayName("Вход с невалидными данными")
    public void shouldNotLogInWithInvalidData() {
        authSteps.authWithInvalidData(invalidAuthInfo());
        authSteps.checkToast(R.string.wrong_login_or_password, true);
    }

    @Test
    @DisplayName("Вход с пустыми полями")
    public void shouldNotLogInWithEmptyFields() {
        authSteps.authWithEmptyFields();
        authSteps.checkToast(R.string.empty_login_or_password, true);
    }
}
