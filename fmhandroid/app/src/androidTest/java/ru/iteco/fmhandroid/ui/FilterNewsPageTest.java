package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.PerformException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.CustomRecyclerViewActions;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class FilterNewsPageTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();
    FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

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

    private MockWebServer mockWebServer = new MockWebServer();

    @Before
    public void logoutCheck() {
        try {
            authSteps.isAuthScreen();
        } catch (PerformException e) {
            mainPageSteps.clickLogOutBut();
        }
        authSteps.authWithValidData(authInfo());
        mainPageSteps.isMainPage();
        mainPageSteps.openNewsPageThroughTheMainMenu();
    }


    @Test
    @DisplayName("Фильтрация новостей по Категории Обьявление")
    public void shouldFilterTheNewsWithCategoryAnnouncement() {
        //Создание новостей с категорией объявление для фильтрации
        /*
        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch (RecordedRequest request) throws InterruptedException {

                switch (request.getPath()) {
                    case "/v1/login/auth/":
                        return new MockResponse().setResponseCode(200);
                    case "/v1/check/version/":
                        return new MockResponse().setResponseCode(200).setBody("version=9");
                    case "/v1/profile/info":
                        return new MockResponse().setResponseCode(200).setBody("{\\\"info\\\":{\\\"name\":\"Lucas Albuquerque\",\"age\":\"21\",\"gender\":\"male\"}}");
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mockWebServer.setDispatcher(dispatcher);
        */
                TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsAnnouncement1, newsAnnouncement1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsAnnouncement2, newsAnnouncement2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsAnnouncement3, newsAnnouncement3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, newsBirthday1, newsBirthday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, newsBirthday2, newsBirthday2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, newsBirthday3, newsBirthday3, 0, 0, 0);
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryAnnouncement, 0, -1, 0, 0, 0, 0);
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка, что отображаются новости с категорией Объявление
        newsPageSteps.isNewsPage();
        //SystemClock.sleep(3000);
        controlPanelSteps.scrollToElementInRecyclerList(newsAnnouncement1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsAnnouncement2).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsAnnouncement3).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsBirthday1))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsBirthday2))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsBirthday3))));

        //Удаляем созданные новости
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());

        controlPanelSteps.deleteItemNews(newsAnnouncement1);

        controlPanelSteps.deleteItemNews(newsAnnouncement2);

        controlPanelSteps.deleteItemNews(newsAnnouncement3);
        //SystemClock.sleep(5000);
        controlPanelSteps.deleteItemNews(newsBirthday1);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsBirthday2);

        controlPanelSteps.deleteItemNews(newsBirthday3);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории День рождения")
    public void shouldFilterTheNewsWithCategoryBirthday() {
        //Создание новостей с категорией объявление для фильтрации
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsAnnouncement1, newsAnnouncement1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsAnnouncement2, newsAnnouncement2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsAnnouncement3, newsAnnouncement3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, newsBirthday1, newsBirthday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, newsBirthday2, newsBirthday2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, newsBirthday3, newsBirthday3, 0, 0, 0);
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryBirthday, 0, -1, 0, 0, 0, 0);
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка, что отображаются новости с категорией День рождения
        newsPageSteps.isNewsPage();
        //SystemClock.sleep(3000);
        controlPanelSteps.scrollToElementInRecyclerList(newsBirthday1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsBirthday2).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsBirthday3).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsAnnouncement1))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsAnnouncement2))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsAnnouncement3))));

        //Удаляем созданные новости
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());

        controlPanelSteps.deleteItemNews(newsAnnouncement1);

        controlPanelSteps.deleteItemNews(newsAnnouncement2);

        controlPanelSteps.deleteItemNews(newsAnnouncement3);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsBirthday1);

        controlPanelSteps.deleteItemNews(newsBirthday2);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsBirthday3);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Зарплата")
    public void shouldFilterTheNewsWithCategorySalary() {
        //Создание новостей с категорией объявление для фильтрации
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, newsSalary1, newsSalary1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, newsSalary2, newsSalary2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, newsSalary3, newsSalary3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, newsTradeUnion1, newsTradeUnion1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, newsTradeUnion2, newsTradeUnion2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, newsTradeUnion3, newsTradeUnion3, 0, 0, 0);
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categorySalary, 0, -1, 0, 0, 0, 0);
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка, что отображаются новости с категорией Объявление
        newsPageSteps.isNewsPage();
        //SystemClock.sleep(3000);
        controlPanelSteps.scrollToElementInRecyclerList(newsSalary1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsSalary2).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsSalary3).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsTradeUnion1))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsTradeUnion2))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsTradeUnion3))));

        //Удаляем созданные новости
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());

        controlPanelSteps.deleteItemNews(newsSalary1);

        controlPanelSteps.deleteItemNews(newsSalary2);

        controlPanelSteps.deleteItemNews(newsSalary3);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsTradeUnion1);

        controlPanelSteps.deleteItemNews(newsTradeUnion2);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsTradeUnion3);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Профсоюз")
    public void shouldFilterTheNewsWithCategoryTradeUnion() {
        //Создание новостей с категорией объявление для фильтрации
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, newsSalary1, newsSalary1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, newsSalary2, newsSalary2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, newsSalary3, newsSalary3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, newsTradeUnion1, newsTradeUnion1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, newsTradeUnion2, newsTradeUnion2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, newsTradeUnion3, newsTradeUnion3, 0, 0, 0);
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryTradeUnion, 0, -1, 0, 0, 0, 0);
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка, что отображаются новости с категорией Объявление
        newsPageSteps.isNewsPage();
        //SystemClock.sleep(3000);
        controlPanelSteps.scrollToElementInRecyclerList(newsTradeUnion1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsTradeUnion2).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsTradeUnion3).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsSalary1))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsSalary2))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsSalary3))));

        //Удаляем созданные новости
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());

        controlPanelSteps.deleteItemNews(newsSalary1);

        controlPanelSteps.deleteItemNews(newsSalary2);

        controlPanelSteps.deleteItemNews(newsSalary3);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsTradeUnion1);

        controlPanelSteps.deleteItemNews(newsTradeUnion2);

        controlPanelSteps.deleteItemNews(newsTradeUnion3);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Праздник")
    public void shouldFilterTheNewsWithCategoryHoliday() {
        //Создание новостей с категорией объявление для фильтрации
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, newsHoliday1, newsHoliday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, newsHoliday2, newsHoliday2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, newsHoliday3, newsHoliday3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, newsMassage1, newsMassage1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, newsMassage2, newsMassage2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, newsMassage3, newsMassage3, 0, 0, 0);
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryHoliday, 0, -1, 0, 0, 0, 0);
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка, что отображаются новости с категорией Объявление
        newsPageSteps.isNewsPage();
        //SystemClock.sleep(3000);
        controlPanelSteps.scrollToElementInRecyclerList(newsHoliday1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsHoliday2).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsHoliday3).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsMassage1))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsMassage2))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsMassage3))));

        //Удаляем созданные новости
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());

        controlPanelSteps.deleteItemNews(newsHoliday1);

        controlPanelSteps.deleteItemNews(newsHoliday2);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsHoliday3);

        controlPanelSteps.deleteItemNews(newsMassage1);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsMassage2);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsMassage3);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Массаж")
    public void shouldFilterTheNewsWithCategoryMassage() {
        //Создание новостей с категорией объявление для фильтрации
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, newsHoliday1, newsHoliday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, newsHoliday2, newsHoliday2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, newsHoliday3, newsHoliday3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, newsMassage1, newsMassage1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, newsMassage2, newsMassage2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, newsMassage3, newsMassage3, 0, 0, 0);
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryMassage, 0, -1, 0, 0, 0, 0);
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка, что отображаются новости с категорией Объявление
        newsPageSteps.isNewsPage();
        //SystemClock.sleep(3000);
        controlPanelSteps.scrollToElementInRecyclerList(newsMassage1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsMassage2).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsMassage3).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsHoliday1))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsHoliday2))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsHoliday3))));

        //Удаляем созданные новости
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());

        controlPanelSteps.deleteItemNews(newsHoliday1);

        controlPanelSteps.deleteItemNews(newsHoliday2);

        controlPanelSteps.deleteItemNews(newsHoliday3);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsMassage1);

        controlPanelSteps.deleteItemNews(newsMassage2);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsMassage3);
    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Благодарность")
    public void shouldFilterTheNewsWithCategoryGratitude() {
        //Создание новостей с категорией объявление для фильтрации
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, newsGratitude1, newsGratitude1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, newsGratitude2, newsGratitude2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, newsGratitude3, newsGratitude3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, newsNeedHelp1, newsNeedHelp1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, newsNeedHelp2, newsNeedHelp2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, newsNeedHelp3, newsNeedHelp3, 0, 0, 0);
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryGratitude, 0, -1, 0, 0, 0, 0);
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка, что отображаются новости с категорией Объявление
        newsPageSteps.isNewsPage();
        //SystemClock.sleep(3000);
        controlPanelSteps.scrollToElementInRecyclerList(newsGratitude1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsGratitude2).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsGratitude3).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsNeedHelp1))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsNeedHelp2))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsNeedHelp3))));

        //Удаляем созданные новости
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());

        controlPanelSteps.deleteItemNews(newsGratitude1);

        controlPanelSteps.deleteItemNews(newsGratitude2);

        controlPanelSteps.deleteItemNews(newsGratitude3);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsNeedHelp1);

        controlPanelSteps.deleteItemNews(newsNeedHelp2);

        controlPanelSteps.deleteItemNews(newsNeedHelp3);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Нужна помощь")
    public void shouldFilterTheNewsWithCategoryNeedHelp() {
        //Создание новостей с категорией объявление для фильтрации
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, newsGratitude1, newsGratitude1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, newsGratitude2, newsGratitude2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, newsGratitude3, newsGratitude3, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, newsNeedHelp1, newsNeedHelp1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, newsNeedHelp2, newsNeedHelp2, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, newsNeedHelp3, newsNeedHelp3, 0, 0, 0);
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryNeedHelp, 0, -1, 0, 0, 0, 0);
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка, что отображаются новости с категорией Объявление
        newsPageSteps.isNewsPage();
        //SystemClock.sleep(3000);
        controlPanelSteps.scrollToElementInRecyclerList(newsNeedHelp1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsNeedHelp2).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsNeedHelp3).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsGratitude1))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsGratitude2))));
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(newsGratitude3))));

        //Удаляем созданные новости
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());

        controlPanelSteps.deleteItemNews(newsGratitude1);

        controlPanelSteps.deleteItemNews(newsGratitude2);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsGratitude3);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsNeedHelp1);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsNeedHelp2);

        controlPanelSteps.deleteItemNews(newsNeedHelp3);

    }

    @Test
    @DisplayName("Отмена филтрации новостей")
    public void shouldNotFilterNewsByCategoryAnnouncement() {
        //Создание новостей с категорией объявление для фильтрации
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsAnnouncement1, newsAnnouncement1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsBirthday1, newsBirthday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsHoliday1, newsHoliday1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, newsMassage1, newsMassage1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, newsGratitude1, newsGratitude1, 0, 0, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, newsTradeUnion1, newsTradeUnion1, 0, 0, 0);
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryAnnouncement, 0, -1, 0, 0, 0, 0);
        TestUtils.waitView(controlPanelSteps.cancelBut).perform(click());

        //Проверка, что фильтр не включился и отображаются все новости
        newsPageSteps.isNewsPage();
        //SystemClock.sleep(5000);
        controlPanelSteps.scrollToElementInRecyclerList(newsAnnouncement1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsBirthday1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsHoliday1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsMassage1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsGratitude1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsTradeUnion1).check(matches(isDisplayed()));


        //Удаляем созданные новости
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());

        controlPanelSteps.deleteItemNews(newsAnnouncement1);

        controlPanelSteps.deleteItemNews(newsBirthday1);

        controlPanelSteps.deleteItemNews(newsHoliday1);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsMassage1);

        controlPanelSteps.deleteItemNews(newsGratitude1);
        //SystemClock.sleep(3000);
        controlPanelSteps.deleteItemNews(newsTradeUnion1);

    }

    @Test
    @DisplayName("Фильтрация новостей по несуществующей категории")
    public void shouldShowAMessageSelectACategoryFromTheList() {

        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        TestUtils.waitView(controlPanelSteps.newsItemCategoryField).perform(replaceText(myCategory));

        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateStartField, 0, -1, 0);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateEndField, 0, 0, 0);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка, что появляется сообщение
        controlPanelSteps.checkToast("Invalid category. Select a category from the list.", true);
    }

    @Test
    @DisplayName("Фильрация актуальных новостей за период из будущего")
    public void shouldShowATextThereIsNothingHere() {
        //Создаем новость с датой публикации в будущем
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsPublicationDateInTheFuture, newsPublicationDateInTheFuture, 0, 1, 0);
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryAnnouncement, 0, 0, 1, 1, 0, 1);
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка, что отображается кнопка REFRESH, текст и картинка пустого списка новостей
        newsPageSteps.isEmptyNewsList();
        //Удаляем созданные новости
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.deleteItemNews(newsPublicationDateInTheFuture);
    }

    @Test
    @DisplayName("Фильтрация новостей без заданного периода")
    public void shouldShowAllActualNewsCategoryAnnouncement() {
        //Создаем новость с датой публикации в будущем
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsAnnouncement1, newsAnnouncement1, 0, -1, 0);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsAnnouncement2, newsAnnouncement2, 0, 0, -10);
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, newsAnnouncement3, newsAnnouncement3, 0, 0, 0);
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка, что отображаются все актуальные новости категории Объявление
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList(newsAnnouncement1).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsAnnouncement2).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(newsAnnouncement3).check(matches(isDisplayed()));

        //Удаляем созданные новости
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());

        controlPanelSteps.deleteItemNews(newsAnnouncement1);

        controlPanelSteps.deleteItemNews(newsAnnouncement2);

        controlPanelSteps.deleteItemNews(newsAnnouncement3);
    }

    @Test
    @DisplayName("Фильтрация новости с заданным периодом от и незаданным периодом до")
    public void shouldShowMessageWrongPeriodPublishDateEndField() {

        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateStartField, 0, -1, 0);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка сообщения "Wrong period"
        filterNewsPageSteps.isMessageWrongPeriod();
    }

    @Test
    @DisplayName("Фильтрация новости с заданным периодом до и незаданным периодом от")
    public void shouldShowMessageWrongPeriodWithEmptyPublishDateStartField() {

        //Открываем форму фильтра и заполняем её
        TestUtils.waitView(newsPageSteps.filterNewsMaterialBut).perform(click());
        filterNewsPageSteps.isFilterNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateEndField, 0, 0, 0);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(filterNewsPageSteps.filterBut).perform(click());

        //Проверка сообщения "Wrong period"
        filterNewsPageSteps.isMessageWrongPeriod();
    }


}
