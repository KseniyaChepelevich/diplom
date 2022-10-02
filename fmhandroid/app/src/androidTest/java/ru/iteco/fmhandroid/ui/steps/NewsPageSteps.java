package ru.iteco.fmhandroid.ui.steps;

import ru.iteco.fmhandroid.ui.page.MainPageElements;
import ru.iteco.fmhandroid.ui.page.NewsPageElements;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

public class NewsPageSteps {
    NewsPageElements newsPageElements = new NewsPageElements();

    public void isNewsPage() {
        newsPageElements.newsPageHeader.check(matches(isDisplayed()));
        newsPageElements.newsListSwipe.check(matches(isDisplayed()));

    }
}
