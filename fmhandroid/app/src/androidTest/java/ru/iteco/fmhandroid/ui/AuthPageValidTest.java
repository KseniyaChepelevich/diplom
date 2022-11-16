package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.RemoteException;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
//import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;


@RunWith(AllureAndroidJUnit4.class)

public class AuthPageValidTest {
    private UiDevice device;

    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

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

    @Test
    @DisplayName("Разрыв соединения во время авторизации")
    public void shouldShowAToastSomethingWrong() throws UiObjectNotFoundException {
        //Вводим логин и пароль
        authSteps.enterAValidUsernameAndPassword(authInfo());
        //Включаем режим В самолете
        authSteps.turnOnAirplaneMode();
        //Нажимаем кнопку signIn
        TestUtils.waitView(authSteps.signBtn).perform(click());
        //Проверяем, что отображается сообщение
        controlPanelSteps.checkToast("Something went wrong. Try again later.", true);
        //Отключаем режим в самолете
        authSteps.turnOffAirplaneMode();
    }

    @Test
    @DisplayName("Поворот экрана во время авторизации")
    public void shouldSaveDataOnTheAuthPageOnScreenRotation() throws UiObjectNotFoundException, RemoteException {
        //Вводим логин и пароль
        authSteps.enterAValidUsernameAndPassword(authInfo());
        device.setOrientationLeft();
        //Проверяем, что введенные данные сохранились
        TestUtils.waitView(authSteps.loginField).check(matches(withText("login2")));
        TestUtils.waitView(authSteps.passField).check(matches(withText("•••••••••")));

    }
}
