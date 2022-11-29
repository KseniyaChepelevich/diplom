package ru.iteco.fmhandroid.ui;

import android.content.Intent;
import android.os.RemoteException;
import android.os.SystemClock;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import io.qameta.allure.kotlin.junit4.DisplayName;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AboutPageSteps;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.LayoutMatchers.hasMultilineText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

@RunWith(AndroidJUnit4.class)

public class AboutPageTest extends BaseTest{
    private UiDevice device;

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static AboutPageSteps aboutPageSteps = new AboutPageSteps();

    @Before
    public void logoutCheck() throws RemoteException {
        device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.setOrientationNatural();
        try {
            authSteps.isAuthScreen();
        } catch (PerformException e) {
            mainPageSteps.clickLogOutBut();
        }
        authSteps.authWithValidData(authInfo());
        mainPageSteps.isMainPage();
        mainPageSteps.openAboutPageThroughTheMainMenu();
    }

    @Test
    @DisplayName("Переход по ссылке Политика конфиденциальности")
    public void shouldOpenPrivacyPolicyDetailsPage() {
        String uriPrivacyPolicy = "https://vhospice.org/#/privacy-policy/";
        String headerPrivacyPolicyPage = "Privacy policy";
        Intents.init();

        aboutPageSteps.openPrivacyPolicy();
        intended(allOf(hasData(uriPrivacyPolicy), hasAction(Intent.ACTION_VIEW)));
        Intents.release();

        //Проверка что загрузилась страница с Политикой конфиденциальности.
        aboutPageSteps.getHeaderPrivacyPolicyPage().check(matches(withText(headerPrivacyPolicyPage)));

    }

    @Test
    @DisplayName("Переход по ссылке Пользовательское соглашение")
    public void shouldOpenTermsOfUseDetailsPage() {
        String uriTermsOfUse = "https://vhospice.org/#/terms-of-use";
        String headerTermsOfUsePege = "Terms of use";
        Intents.init();

        aboutPageSteps.openTermsOfUse();
        SystemClock.sleep(3000);
        intended(allOf(hasData(uriTermsOfUse), hasAction(Intent.ACTION_VIEW)));
        Intents.release();
        //Проверка что загрузилась страница с Пользовательским соглашением.
        aboutPageSteps.getHeaderTermsOfUsePage().check(matches(withText(headerTermsOfUsePege)));

    }

    @Test
    @DisplayName("Переход по кнопке Back в AppBar")
    public void shouldGoBackBut() {
        aboutPageSteps.aboutBackImageButClick();
        mainPageSteps.isMainPage();
    }

}
