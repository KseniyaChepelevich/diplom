package ru.iteco.fmhandroid.ui;

import android.content.Intent;
import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import io.qameta.allure.kotlin.junit4.DisplayName;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.AboutPageElements;
import ru.iteco.fmhandroid.ui.page.MainPageElements;
import ru.iteco.fmhandroid.ui.steps.AboutPageSteps;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.LayoutMatchers.hasMultilineText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

@RunWith(AndroidJUnit4.class)
public class AboutPageTest {

    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();

    AboutPageSteps aboutPageSteps = new AboutPageSteps();
    MainPageElements mainPageElements = new MainPageElements();
    AboutPageElements aboutPageElements = new AboutPageElements();

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
        authSteps.authWithValidData(authInfo());
        mainPageSteps.openAboutPageThroughTheMainMenu();
    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("Переход по ссылке на Политику конфиденциальности")
    public void shouldOpenPrivacyPolicyDetailsPage() {
        Intents.init();
        aboutPageSteps.openPrivacyPolicy();
        SystemClock.sleep(3000);
        intended(allOf(hasData("https://vhospice.org/#/privacy-policy/"), hasAction(Intent.ACTION_VIEW)));
        Intents.release();

        //Проверка что загрузилась страница с Политикой конфиденциальности. Не работает. Страница не загружается.
        /*ViewInteraction mainText = onView(allOf(
                withText(containsString("Privacy Policy")), hasMultilineText()));*/
        //onView(withText("ERR_CONNECTION_TIMED_OUT")) .check(doesNotExist());

    }

    @Test
    @DisplayName("Переход по ссылке на Пользовательское соглашение")
    public void shouldOpenTermsOfUseDetailsPage() {
        Intents.init();
        aboutPageSteps.openTermsOfUse();
        SystemClock.sleep(3000);
        intended(allOf(hasData("https://vhospice.org/#/terms-of-use"), hasAction(Intent.ACTION_VIEW)));
        Intents.release();
        //Проверка что загрузилась страница с Пользовательским соглашением. Не работает. Страница не загружается.
        ViewInteraction mainText = onView(allOf(
                withText(containsString("Terms of use")), hasMultilineText()));

    }

    @Test
    @DisplayName("Выход из раздела About по кнопке Назад в AppBar")
    public void shouldGoBackBut() {
        DataHelper.EspressoBaseTest.clickButton(aboutPageElements.aboutBackImageBut);
        mainPageSteps.isMainPage();


    }

}
