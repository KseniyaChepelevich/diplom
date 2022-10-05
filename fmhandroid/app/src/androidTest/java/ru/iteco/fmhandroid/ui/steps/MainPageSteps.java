package ru.iteco.fmhandroid.ui.steps;

import android.os.SystemClock;

import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.MainPageElements;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.ViewInteraction;

public class MainPageSteps {
    MainPageElements mainPageElements = new MainPageElements();

    public void isMainPage() {
        mainPageElements.newsBlockHeader.check(matches(isDisplayed()));
        mainPageElements.newsBlock.check(matches(isDisplayed()));
        mainPageElements.claimsBlockHeader.check(matches(isDisplayed()));
        mainPageElements.claimsBlock.check(matches(isDisplayed()));
    }

    public void clickLogOutBut() {
        mainPageElements.authImBut.check(matches(isDisplayed()));
        mainPageElements.authImBut.perform(click());
        SystemClock.sleep(3000);
        mainPageElements.logOutBut.check(matches(isDisplayed()));
        mainPageElements.logOutBut.perform(click());

    }

    public void openNewsPageThroughTheMainMenu() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.mainMenuImBut);
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.newsInTheMainMenu);
        SystemClock.sleep(3000);

    }

    public void openClaimsPageThroughTheMainMenu() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.mainMenuImBut);
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.claimsInTheMainMenu);
        SystemClock.sleep(3000);

    }

    public void openAboutPageThroughTheMainMenu() {
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.mainMenuImBut);
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(mainPageElements.aboutInTheMainMenu);
        SystemClock.sleep(3000);

    }









    public void clickAboutInTheMainMenu() {
        SystemClock.sleep(3000);
        mainPageElements.mainMenuImBut.check(matches(isDisplayed()));
        mainPageElements.mainMenuImBut.check(matches(isClickable()));
        mainPageElements.mainMenuImBut.perform(click());
        SystemClock.sleep(3000);
        mainPageElements.aboutInTheMainMenu.check(matches(isDisplayed()));
        mainPageElements.aboutInTheMainMenu.perform(click());
    }



    /*public void clickExpandNewsCardBut() {
        SystemClock.sleep(3000);
        mainPageElements.expandNewsCardBut.check(matches(isDisplayed()));
        mainPageElements.expandNewsCardBut.perform(click());
    }*/



    public void isNewsBlockCollapsed() {
        mainPageElements.newsBlockHeader.check(matches(isDisplayed()));
        mainPageElements.allNewsBut.check(matches(not(isDisplayed())));
        mainPageElements.allNewsCardsBlockConstraintLayout.check(matches(not(isDisplayed())));

    }



    public void isClaimsBlockCollapsed() {
        mainPageElements.claimsBlockHeader.check(matches(isDisplayed()));
        mainPageElements.allClaimsBut.check(matches(not(isDisplayed())));
        mainPageElements.allClaimsCardsBlockConstraintLayout.check(matches(not(isDisplayed())));

    }

}
