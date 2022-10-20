package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
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

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.CustomRecyclerViewActions;
import ru.iteco.fmhandroid.ui.data.CustomViewAssertion;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.ControlPanelElements;
import ru.iteco.fmhandroid.ui.page.FilterNewsPageElements;
import ru.iteco.fmhandroid.ui.page.NewsPageElements;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

@RunWith(AllureAndroidJUnit4.class)
//@RunWith(AndroidJUnit4.class)
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
    static String myCategory = "Моя категория" + " " + DataHelper.generateTitleId();
    static String newsPublicationDateInTheFuture = "Новость из будущего" + " " + DataHelper.generateTitleId();




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
    @DisplayName("Фильтрация новостей по Категории Обьявление")
    public void shouldFilterTheNewsWithCategoryAnnouncement() {
        //Создание новостей с категорией объявление для фильтрации
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement1, newsAnnouncement1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement2, newsAnnouncement2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement3, newsAnnouncement3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday1, newsBirthday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday2, newsBirthday2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday3, newsBirthday3, 0, 0, 0);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categoryAnnouncement,0, -1, 0, 0, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
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
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsBirthday1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsBirthday2))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsBirthday3))));

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
    @DisplayName("Фильтрация новостей по Категории День рождения")
    public void shouldFilterTheNewsWithCategoryBirthday() {
        //Создание новостей с категорией объявление для фильтрации
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement1, newsAnnouncement1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement2, newsAnnouncement2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement3, newsAnnouncement3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday1, newsBirthday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday2, newsBirthday2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsBirthday3, newsBirthday3, 0, 0, 0);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categoryBirthday,0, -1, 0, 0, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
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
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsAnnouncement1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsAnnouncement1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsAnnouncement1))));

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
    @DisplayName("Фильтрация новостей по Категории Зарплата")
    public void shouldFilterTheNewsWithCategorySalary() {
        //Создание новостей с категорией объявление для фильтрации
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categorySalary, newsSalary1, newsSalary1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categorySalary, newsSalary2, newsSalary2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categorySalary, newsSalary3, newsSalary3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryTradeUnion, newsTradeUnion1, newsTradeUnion1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryTradeUnion, newsTradeUnion2, newsTradeUnion2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryTradeUnion, newsTradeUnion3, newsTradeUnion3, 0, 0, 0);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categorySalary,0, -1, 0, 0, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
        SystemClock.sleep(3000);
        //Проверка, что отображаются новости с категорией Объявление
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsSalary1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsSalary2))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsSalary3))))).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsTradeUnion1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsTradeUnion2))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsTradeUnion3))));

        //Удаляем созданные новости
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.deleteItemNews(newsSalary1);
        controlPanelSteps.deleteItemNews(newsSalary2);
        controlPanelSteps.deleteItemNews(newsSalary3);
        controlPanelSteps.deleteItemNews(newsTradeUnion1);
        controlPanelSteps.deleteItemNews(newsTradeUnion2);
        controlPanelSteps.deleteItemNews(newsTradeUnion3);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Профсоюз")
    public void shouldFilterTheNewsWithCategoryTradeUnion() {
        //Создание новостей с категорией объявление для фильтрации
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categorySalary, newsSalary1, newsSalary1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categorySalary, newsSalary2, newsSalary2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categorySalary, newsSalary3, newsSalary3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryTradeUnion, newsTradeUnion1, newsTradeUnion1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryTradeUnion, newsTradeUnion2, newsTradeUnion2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryTradeUnion, newsTradeUnion3, newsTradeUnion3, 0, 0, 0);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categoryTradeUnion,0, -1, 0, 0, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
        SystemClock.sleep(3000);
        //Проверка, что отображаются новости с категорией Объявление
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsTradeUnion1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsTradeUnion2))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsTradeUnion3))))).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsSalary1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsSalary2))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsSalary3))));

        //Удаляем созданные новости
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.deleteItemNews(newsSalary1);
        controlPanelSteps.deleteItemNews(newsSalary2);
        controlPanelSteps.deleteItemNews(newsSalary3);
        controlPanelSteps.deleteItemNews(newsTradeUnion1);
        controlPanelSteps.deleteItemNews(newsTradeUnion2);
        controlPanelSteps.deleteItemNews(newsTradeUnion3);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Праздник")
    public void shouldFilterTheNewsWithCategoryHoliday() {
        //Создание новостей с категорией объявление для фильтрации
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryHoliday, newsHoliday1, newsHoliday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryHoliday, newsHoliday2, newsHoliday2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryHoliday, newsHoliday3, newsHoliday3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryMassage, newsMassage1, newsMassage1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryMassage, newsMassage2, newsMassage2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryMassage, newsMassage3, newsMassage3, 0, 0, 0);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categoryHoliday,0, -1, 0, 0, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
        SystemClock.sleep(3000);
        //Проверка, что отображаются новости с категорией Объявление
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsHoliday1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsHoliday2))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsHoliday3))))).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsMassage1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsMassage2))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsMassage3))));

        //Удаляем созданные новости
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.deleteItemNews(newsHoliday1);
        controlPanelSteps.deleteItemNews(newsHoliday2);
        controlPanelSteps.deleteItemNews(newsHoliday3);
        controlPanelSteps.deleteItemNews(newsMassage1);
        controlPanelSteps.deleteItemNews(newsMassage2);
        controlPanelSteps.deleteItemNews(newsMassage3);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Массаж")
    public void shouldFilterTheNewsWithCategoryMassage() {
        //Создание новостей с категорией объявление для фильтрации
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryHoliday, newsHoliday1, newsHoliday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryHoliday, newsHoliday2, newsHoliday2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryHoliday, newsHoliday3, newsHoliday3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryMassage, newsMassage1, newsMassage1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryMassage, newsMassage2, newsMassage2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryMassage, newsMassage3, newsMassage3, 0, 0, 0);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categoryMassage,0, -1, 0, 0, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
        SystemClock.sleep(3000);
        //Проверка, что отображаются новости с категорией Объявление
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsMassage1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsMassage2))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsMassage3))))).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsHoliday1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsHoliday2))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsHoliday3))));

        //Удаляем созданные новости
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.deleteItemNews(newsHoliday1);
        controlPanelSteps.deleteItemNews(newsHoliday2);
        controlPanelSteps.deleteItemNews(newsHoliday3);
        controlPanelSteps.deleteItemNews(newsMassage1);
        controlPanelSteps.deleteItemNews(newsMassage2);
        controlPanelSteps.deleteItemNews(newsMassage3);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Благодарность")
    public void shouldFilterTheNewsWithCategoryGratitude() {
        //Создание новостей с категорией объявление для фильтрации
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryGratitude, newsGratitude1, newsGratitude1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryGratitude, newsGratitude2, newsGratitude2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryGratitude, newsGratitude3, newsGratitude3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryNeedHelp, newsNeedHelp1, newsNeedHelp1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryNeedHelp, newsNeedHelp2, newsNeedHelp2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryNeedHelp, newsNeedHelp3, newsNeedHelp3, 0, 0, 0);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categoryGratitude,0, -1, 0, 0, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
        SystemClock.sleep(3000);
        //Проверка, что отображаются новости с категорией Объявление
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsGratitude1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsGratitude2))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsGratitude3))))).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsNeedHelp1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsNeedHelp2))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsNeedHelp3))));

        //Удаляем созданные новости
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.deleteItemNews(newsGratitude1);
        controlPanelSteps.deleteItemNews(newsGratitude2);
        controlPanelSteps.deleteItemNews(newsGratitude3);
        controlPanelSteps.deleteItemNews(newsNeedHelp1);
        controlPanelSteps.deleteItemNews(newsNeedHelp2);
        controlPanelSteps.deleteItemNews(newsNeedHelp3);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Нужна помощь")
    public void shouldFilterTheNewsWithCategoryNeedHelp() {
        //Создание новостей с категорией объявление для фильтрации
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryGratitude, newsGratitude1, newsGratitude1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryGratitude, newsGratitude2, newsGratitude2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryGratitude, newsGratitude3, newsGratitude3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryNeedHelp, newsNeedHelp1, newsNeedHelp1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryNeedHelp, newsNeedHelp2, newsNeedHelp2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryNeedHelp, newsNeedHelp3, newsNeedHelp3, 0, 0, 0);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categoryNeedHelp,0, -1, 0, 0, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
        SystemClock.sleep(3000);
        //Проверка, что отображаются новости с категорией Объявление
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsNeedHelp1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsNeedHelp2))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsNeedHelp3))))).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsGratitude1))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsGratitude2))));
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsGratitude3))));

        //Удаляем созданные новости
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.deleteItemNews(newsGratitude1);
        controlPanelSteps.deleteItemNews(newsGratitude2);
        controlPanelSteps.deleteItemNews(newsGratitude3);
        controlPanelSteps.deleteItemNews(newsNeedHelp1);
        controlPanelSteps.deleteItemNews(newsNeedHelp2);
        controlPanelSteps.deleteItemNews(newsNeedHelp3);

    }

    @Test
    @DisplayName("Отмена филтрации новостей")
    public void shouldNotFilterNewsByCategoryAnnouncement() {
        //Создание новостей с категорией объявление для фильтрации
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement1, newsAnnouncement1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsBirthday1, newsBirthday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsHoliday1, newsHoliday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsMassage1, newsMassage1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsGratitude1, newsGratitude1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryBirthday, newsTradeUnion1, newsTradeUnion1, 0, 0, 0);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categoryAnnouncement,0, -1, 0, 0, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.cancelBut);
        SystemClock.sleep(3000);
        //Проверка, что фильтр не включился и отображаются все новости
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsAnnouncement1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsBirthday1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsHoliday1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsMassage1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsGratitude1))))).check(matches(isDisplayed()));
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newsTradeUnion1))))).check(matches(isDisplayed()));


        //Удаляем созданные новости
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.deleteItemNews(newsAnnouncement1);
        controlPanelSteps.deleteItemNews(newsBirthday1);
        controlPanelSteps.deleteItemNews(newsHoliday1);
        controlPanelSteps.deleteItemNews(newsMassage1);
        controlPanelSteps.deleteItemNews(newsGratitude1);
        controlPanelSteps.deleteItemNews(newsTradeUnion1);

    }

    @Test
    @DisplayName("Фильтрация новостей по несуществующей категории")
    public void shouldShowAMessageSelectACategoryFromTheList() {

        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.replaceTextNewsCategory(myCategory);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageElements.newsItemPublishDateStartField, 0, -1, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.okBut);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageElements.newsItemPublishDateEndField, 0, 0,0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.okBut);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
        SystemClock.sleep(3000);
        //Проверка, что появляется сообщение
        filterNewsPageSteps.checkToast("Invalid category. Select a category from the list.", true);
    }

    @Test
    @DisplayName("Фильрация актуальных новостей за период из будущего")
    public void shouldShowATextThereIsNothingHere() {
        //Создаем новость с датой публикации в будущем
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsPublicationDateInTheFuture, newsPublicationDateInTheFuture, 0, 1, 0);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(filterNewsPageElements.categoryAnnouncement,0, 0, 1, 1, 0, 1);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
        SystemClock.sleep(3000);
        //Проверка, что отображается кнопка REFRESH, текст и картинка пустого списка новостей
        filterNewsPageSteps.isEmptyNewsList();
        //Удаляем созданные новости
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.deleteItemNews(newsPublicationDateInTheFuture);
    }

    @Test
    @DisplayName("Фильтрация новостей без заданного периода")
    public void shouldShowAllActualNewsCategoryAnnouncement() {
        //Создаем новость с датой публикации в будущем
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement1, newsAnnouncement1, 0, -1, 0);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement2, newsAnnouncement2, 0, 0, -10);
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, newsAnnouncement3, newsAnnouncement3, 0, 0, 0);
        //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        SystemClock.sleep(3000);
        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.selectANewsCategoryFromTheList(filterNewsPageElements.categoryAnnouncement);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
        SystemClock.sleep(3000);
        //Проверка, что отображаются все актуальные новости категории Объявление
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

        //Удаляем созданные новости
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);

        controlPanelSteps.deleteItemNews(newsAnnouncement1);
        controlPanelSteps.deleteItemNews(newsAnnouncement2);
        controlPanelSteps.deleteItemNews(newsAnnouncement3);
    }

    @Test
    @DisplayName("Фильтрация новости с заданным периодом от и незаданным периодом до")
    public void shouldShowMessageWrongPeriodPublishDateEndField() {

        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.selectANewsCategoryFromTheList(filterNewsPageElements.categoryAnnouncement);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageElements.newsItemPublishDateStartField, 0, -1, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.okBut);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
        SystemClock.sleep(3000);
        //Проверка сообщения "Wrong period"
        filterNewsPageSteps.isMessageWrongPeriod();
    }

    @Test
    @DisplayName("Фильтрация новости с заданным периодом до и незаданным периодом от")
    public void shouldShowMessageWrongPeriodWithEmptyPublishDateStartField() {

        //Открываем форму фильтра и заполняем её
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.filterNewsMaterialBut);
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.selectANewsCategoryFromTheList(filterNewsPageElements.categoryAnnouncement);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageElements.newsItemPublishDateEndField, 0, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.okBut);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
        SystemClock.sleep(3000);
        //Проверка сообщения "Wrong period"
        filterNewsPageSteps.isMessageWrongPeriod();
    }


}
