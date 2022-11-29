package ru.iteco.fmhandroid.ui;


import static ru.iteco.fmhandroid.ui.data.DeviceHelper.executeBash;

import android.os.RemoteException;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.RuleChain;

import io.qameta.allure.android.rules.LogcatRule;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

public class BaseTest {

    private UiDevice device;

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Rule
    public RuleChain ruleChain = RuleChain.outerRule(new io.qameta.allure.android.rules.ScreenshotRule()).around(new io.qameta.allure.android.rules.ScreenshotRule());

    @Rule
    public io.qameta.allure.android.rules.LogcatRule logcatRule = new LogcatRule();

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();

    /**
     * Отключение анимаций на эмуляторе чтобы не лагало
     */
    private static void disableAnimationOnEmulator() {
        executeBash("adb -s shell settings put global transition_animation_scale 0.0");
        executeBash("adb -s shell settings put global window_animation_scale 0.0");
        executeBash("adb -s shell settings put global animator_duration_scale 0.0");
    }

    /*@BeforeClass
    public void beforeEachTest() throws RemoteException {
        disableAnimationOnEmulator();

    }*/


}
