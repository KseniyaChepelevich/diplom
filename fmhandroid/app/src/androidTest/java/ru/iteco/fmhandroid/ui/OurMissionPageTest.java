package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.OurMissionPageSteps;

@RunWith(AllureAndroidJUnit4.class)
public class OurMissionPageTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    OurMissionPageSteps ourMissionPageSteps = new OurMissionPageSteps();

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
        authSteps.authWithValidData(authInfo());
        mainPageSteps.isMainPage();
        TestUtils.waitView(mainPageSteps.ourMissionImBut).perform(click());
        ourMissionPageSteps.isOurMissionPage();
    }

    @Test
    @DisplayName("Просмотр цитаты")
    public void shouldOpenOurMissionPage() {
        int missionItemPosition = 3;
        TestUtils.waitView(ourMissionPageSteps.ourMissionItemListRecyclerView).perform(actionOnItemAtPosition(missionItemPosition, click()));
        ourMissionPageSteps.getOurMissionItemDescriptionTextView(missionItemPosition).check(matches(isDisplayed()));
    }

}
