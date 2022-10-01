package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;

import ru.iteco.fmhandroid.ui.page.MainPage;
import ru.iteco.fmhandroid.ui.page.MainPageElements;

public class MainPageSteps {
    private MainPageElements mainPageElements;

    public void isMainPage() {
        mainPageElements.newsBlockHeader.check(matches(isDisplayed()));
        mainPageElements.newsBlock.check(matches(isDisplayed()));
        mainPageElements.claimsBlockHeader.check(matches(isDisplayed()));
        mainPageElements.claimsBlock.check(matches(isDisplayed()));
    }

    public void clickLogOutBut() {
        mainPageElements.authImBut.perform(click());
        mainPageElements.logOutBut.perform(click());

    }
}
