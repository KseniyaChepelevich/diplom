package ru.iteco.fmhandroid.ui.steps;

import ru.iteco.fmhandroid.ui.page.OurMissionPageElements;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

public class OurMissionPageSteps {
    OurMissionPageElements ourMissionPageElements = new OurMissionPageElements();
    public void isOurMissionPage() {
        ourMissionPageElements.ourMissionPageTitle.check(matches(isDisplayed()));
        ourMissionPageElements.ourMissionItemList.check(matches(isDisplayed()));
    }
}
