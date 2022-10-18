package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.CustomRecyclerViewActions;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.ControlPanelElements;
import ru.iteco.fmhandroid.ui.page.FilterNewsPageElements;
import ru.iteco.fmhandroid.ui.page.NewsPageElements;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

//@RunWith(AllureAndroidJUnit4.class)
@RunWith(AndroidJUnit4.class)
public class FilterNewsPageTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();
    NewsPageElements newsPageElements =  new NewsPageElements();
    FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    FilterNewsPageElements filterNewsPageElements = new FilterNewsPageElements();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();
    ControlPanelElements controlPanelElements = new ControlPanelElements();

    static String newsAnnouncement1 = "Объявление" + " " + DataHelper.generateTitleId();
    static String newsAnnouncement2 = "Объявление" + " " + DataHelper.generateTitleId();
    static String newsAnnouncement3 = "Объявление" + " " + DataHelper.generateTitleId();
    static String newsBirthday1 = "День рождения" + " " + DataHelper.generateTitleId();
    static String newsBirthday2 = "День рождения" + " " + DataHelper.generateTitleId();
    static String newsBirthday3 = "День рождения" + " " + DataHelper.generateTitleId();
    static String newsSalary1 = "Зарплата" + " " + DataHelper.generateTitleId();
    static String newsSalary2 = "Зарплата" + " " + DataHelper.generateTitleId();
    static String newsSalary3 = "Зарплата" + " " + DataHelper.generateTitleId();
    static String newsTradeUnion1 = "Профсоюз" + " " + DataHelper.generateTitleId();
    static String newsTradeUnion2 = "Профсоюз" + " " + DataHelper.generateTitleId();
    static String newsTradeUnion3 = "Профсоюз" + " " + DataHelper.generateTitleId();
    static String newsHoliday1 = "Праздник" + " " + DataHelper.generateTitleId();
    static String newsHoliday2 = "Праздник" + " " + DataHelper.generateTitleId();
    static String newsHoliday3 = "Праздник" + " " + DataHelper.generateTitleId();
    static String newsGratitude1 = "Благодарность" + " " + DataHelper.generateTitleId();
    static String newsGratitude2 = "Благодарность" + " " + DataHelper.generateTitleId();
    static String newsGratitude3 = "Благодарность" + " " + DataHelper.generateTitleId();
    static String newsMassage1 = "Массаж" + " " + DataHelper.generateTitleId();
    static String newsMassage2 = "Массаж" + " " + DataHelper.generateTitleId();
    static String newsMassage3 = "Массаж" + " " + DataHelper.generateTitleId();
    static String newsNeedHelp1 = "Нужна помощь" + " " + DataHelper.generateTitleId();
    static String newsNeedHelp2 = "Нужна помощь" + " " + DataHelper.generateTitleId();
    static String newsNeedHelp3 = "Нужна помощь" + " " + DataHelper.generateTitleId();




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
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);


    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("Фильтракция новостей по Категории Обьявление")
    public void shouldFilterTheNewsWithCategoryAnnouncement() {
        //Создание новостей с категорией объявление для фильтрации
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement1, newsAnnouncement1);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement2, newsAnnouncement2);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement3, newsAnnouncement3);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday1, newsBirthday1);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday2, newsBirthday2);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday3, newsBirthday3);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categoryAnnouncement,0, -1, 0, 0, 0, 0);
        SystemClock.sleep(3000);
        //Проверка, что отображаются новости с категорией Объявление
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsAnnouncement1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsAnnouncement2))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsAnnouncement3))))).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewisNotExist(R.id.news_item_title_text_view, withText(newsBirthday1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewisNotExist(R.id.news_item_title_text_view, withText(newsBirthday2))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewisNotExist(R.id.news_item_title_text_view, withText(newsBirthday3))));

        //Удаляем созданные новости
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.deleteItemNews(newsAnnouncement1);
        controlPanelSteps.deleteItemNews(newsAnnouncement2);
        controlPanelSteps.deleteItemNews(newsAnnouncement3);
        controlPanelSteps.deleteItemNews(newsBirthday1);
        controlPanelSteps.deleteItemNews(newsBirthday2);
        controlPanelSteps.deleteItemNews(newsBirthday3);

    }

    @Test
    @DisplayName("Фильтракция новостей по Категории День рождения")
    public void shouldFilterTheNewsWithCategoryBirthday() {
        //Создание новостей с категорией объявление для фильтрации
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement1, newsAnnouncement1);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement2, newsAnnouncement2);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement3, newsAnnouncement3);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday1, newsBirthday1);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday2, newsBirthday2);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday3, newsBirthday3);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categoryBirthday,0, -1, 0, 0, 0, 0);
        SystemClock.sleep(3000);
        //Проверка, что отображаются новости с категорией Объявление
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsBirthday1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsBirthday2))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsBirthday3))))).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewisNotExist(R.id.news_item_title_text_view, withText(newsAnnouncement1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewisNotExist(R.id.news_item_title_text_view, withText(newsAnnouncement1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewisNotExist(R.id.news_item_title_text_view, withText(newsAnnouncement1))));

        //Удаляем созданные новости
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.deleteItemNews(newsAnnouncement1);
        controlPanelSteps.deleteItemNews(newsAnnouncement2);
        controlPanelSteps.deleteItemNews(newsAnnouncement3);
        controlPanelSteps.deleteItemNews(newsBirthday1);
        controlPanelSteps.deleteItemNews(newsBirthday2);
        controlPanelSteps.deleteItemNews(newsBirthday3);

    }
}
