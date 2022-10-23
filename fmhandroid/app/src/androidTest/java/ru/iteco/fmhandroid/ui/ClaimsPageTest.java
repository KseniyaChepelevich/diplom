package ru.iteco.fmhandroid.ui;

import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.ClaimsPageElements;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimsPageTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    ClaimsPageElements claimsPageElements = new ClaimsPageElements();
    ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            authSteps.isAuthScreen();
        } catch (NoMatchingViewException e) {
            mainPageSteps.clickLogOutBut();
        }
        authSteps.authWithValidData(authInfo());
        mainPageSteps.openClaimsPageThroughTheMainMenu();

    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("Открытие фильтра заявок по кнопке Filter")
    public void shouldOpenTheClaimsFilterSettingsForm() {
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.claimsFiltersButton);
        SystemClock.sleep(3000);
        claimsPageSteps.isClaimsFilteringDialog();
    }

    @Test
    @DisplayName("Открытие формы Создания заявки в разделе Заявки")
    public void shouldOpenTheCreateClaimForm() {
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.addNewClaimBut);
        claimsPageSteps.isClaimsForm();

    }

    @Test
    @DisplayName("Окрытие Заявки с помощью кнопки со стрелкой")
    public void shouldOpenTheClaimCard() {
        //Создать заявку для теста

        //Открыть карточку заявки

        //Проверить, что отображается карточка созданной заявки



    }





}
