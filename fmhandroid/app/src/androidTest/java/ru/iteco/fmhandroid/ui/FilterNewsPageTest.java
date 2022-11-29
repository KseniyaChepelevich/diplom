package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.RemoteException;
import android.os.SystemClock;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.qameta.allure.android.rules.LogcatRule;
import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.CustomRecyclerViewActions;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.NamingHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class FilterNewsPageTest extends BaseTest {
    private UiDevice device;

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static NewsPageSteps newsPageSteps = new NewsPageSteps();
    private static FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();
    private static NamingHelper namingHelper = new NamingHelper();

    LocalDateTime today = LocalDateTime.now();

    @Before
    public void logoutCheck() throws RemoteException {
        device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.setOrientationNatural();
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
        LocalDateTime beginningOfPeriod = today.minusDays(1);
        LocalDateTime endOfPeriod = today.plusDays(369);
        String first = namingHelper.getNewsAnnouncementName();
        String second = namingHelper.getNewsAnnouncementName();
        String third = namingHelper.getNewsAnnouncementName();
        String forth = namingHelper.getNewsBirthdayName();
        String fifth = namingHelper.getNewsBirthdayName();
        String sixth = namingHelper.getNewsBirthdayName();
        String categoryForFilter = "Объявление";
        String publishDateStartExpected = TestUtils.getDateToString(beginningOfPeriod);
        String publishDateEndExpected = TestUtils.getDateToString(endOfPeriod);

        //Создание новостей с категорией объявление для фильтрации
        newsPageSteps.openControlPanel();
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, first, first,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, second, second,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, third, third,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, forth, forth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, fifth, fifth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, sixth, sixth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryAnnouncement,
                beginningOfPeriod.getYear(), beginningOfPeriod.getMonthValue(), beginningOfPeriod.getDayOfMonth(),
                endOfPeriod.getYear(), endOfPeriod.getMonthValue(), endOfPeriod.getDayOfMonth());
        //Проверяем, что в полях формы отображаются введенные данные, а не другие
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryForFilter)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Включаем фильтрацию
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что отображаются новости с категорией Объявление
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList(first).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(second).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(third).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(forth))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(fifth))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(sixth))));
    }

    @Test
    @DisplayName("Фильтрация новостей по Категории День рождения")
    public void shouldFilterTheNewsWithCategoryBirthday() {
        LocalDateTime beginningOfPeriod = today.minusDays(1);
        LocalDateTime endOfPeriod = today.plusDays(369);
        String first = namingHelper.getNewsAnnouncementName();
        String second = namingHelper.getNewsAnnouncementName();
        String third = namingHelper.getNewsAnnouncementName();
        String forth = namingHelper.getNewsBirthdayName();
        String fifth = namingHelper.getNewsBirthdayName();
        String sixth = namingHelper.getNewsBirthdayName();
        String categoryForFilter = "День рождения";
        String publishDateStartExpected = TestUtils.getDateToString(beginningOfPeriod);
        String publishDateEndExpected = TestUtils.getDateToString(endOfPeriod);
        //Создание новостей с категорией объявление для фильтрации
        newsPageSteps.openControlPanel();
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, first, first,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, second, second,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, third, third,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, forth, forth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, fifth, fifth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, sixth, sixth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryBirthday,
                beginningOfPeriod.getYear(), beginningOfPeriod.getMonthValue(), beginningOfPeriod.getDayOfMonth(),
                endOfPeriod.getYear(), endOfPeriod.getMonthValue(), endOfPeriod.getDayOfMonth());
        //Проверяем, что в полях формы отображаются введенные данные, а не другие
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryForFilter)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Включаем фильтрацию
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что отображаются новости с категорией День рождения
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList(forth).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(fifth).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(sixth).check(matches(isDisplayed()));
        //Проверка, что новости с категорией День рождения не отображаются
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(first))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(second))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(third))));
    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Зарплата")
    public void shouldFilterTheNewsWithCategorySalary() {
        LocalDateTime beginningOfPeriod = today.minusDays(1);
        LocalDateTime endOfPeriod = today.plusDays(369);
        String first = namingHelper.getNewsSalaryName();
        String second = namingHelper.getNewsSalaryName();
        String third = namingHelper.getNewsSalaryName();
        String forth = namingHelper.getNewsTradeUnionName();
        String fifth = namingHelper.getNewsTradeUnionName();
        String sixth = namingHelper.getNewsTradeUnionName();
        String categoryForFilter = "Зарплата";
        String publishDateStartExpected = TestUtils.getDateToString(beginningOfPeriod);
        String publishDateEndExpected = TestUtils.getDateToString(endOfPeriod);
        //Создание новостей с категорией объявление для фильтрации
        newsPageSteps.openControlPanel();
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, first, first,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, second, second,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, third, third,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, forth, forth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, fifth, fifth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, sixth, sixth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categorySalary,
                beginningOfPeriod.getYear(), beginningOfPeriod.getMonthValue(), beginningOfPeriod.getDayOfMonth(),
                endOfPeriod.getYear(), endOfPeriod.getMonthValue(), endOfPeriod.getDayOfMonth());
        //Проверяем, что в полях формы отображаются введенные данные, а не другие
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryForFilter)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Включаем фильтрацию
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что отображаются новости с категорией Зарплата
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList(first).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(second).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(third).check(matches(isDisplayed()));
        //Проверка, что новости с категорией Профсоюз не отображаются
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(forth))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(fifth))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(sixth))));
    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Профсоюз")
    public void shouldFilterTheNewsWithCategoryTradeUnion() {
        LocalDateTime beginningOfPeriod = today.minusDays(1);
        LocalDateTime endOfPeriod = today.plusDays(369);
        String first = namingHelper.getNewsSalaryName();
        String second = namingHelper.getNewsSalaryName();
        String third = namingHelper.getNewsSalaryName();
        String forth = namingHelper.getNewsTradeUnionName();
        String fifth = namingHelper.getNewsTradeUnionName();
        String sixth = namingHelper.getNewsTradeUnionName();
        String categoryForFilter = "Профсоюз";
        String publishDateStartExpected = TestUtils.getDateToString(beginningOfPeriod);
        String publishDateEndExpected = TestUtils.getDateToString(endOfPeriod);
        //Создание новостей с категорией объявление для фильтрации
        newsPageSteps.openControlPanel();
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, first, first,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, second, second,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categorySalary, third, third,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, forth, forth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, fifth, fifth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, sixth, sixth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryTradeUnion,
                beginningOfPeriod.getYear(), beginningOfPeriod.getMonthValue(), beginningOfPeriod.getDayOfMonth(),
                endOfPeriod.getYear(), endOfPeriod.getMonthValue(), endOfPeriod.getDayOfMonth());
        //Проверяем, что в полях формы отображаются введенные данные, а не другие
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryForFilter)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Включаем фильтрацию
        //Проверяем, что в полях формы отображаются введенные данные, а не другие
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryForFilter)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Включаем фильтрацию
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что отображаются новости с категорией Профсоюз
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList(forth).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(fifth).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(sixth).check(matches(isDisplayed()));
        //Проверка, что новости с категорией Зарплата не отображаются
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(first))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(second))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(third))));
    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Праздник")
    public void shouldFilterTheNewsWithCategoryHoliday() {
        LocalDateTime beginningOfPeriod = today.minusDays(1);
        LocalDateTime endOfPeriod = today.plusDays(369);
        String first = namingHelper.getNewsHolidayName();
        String second = namingHelper.getNewsHolidayName();
        String third = namingHelper.getNewsHolidayName();
        String forth = namingHelper.getNewsMassageName();
        String fifth = namingHelper.getNewsMassageName();
        String sixth = namingHelper.getNewsMassageName();
        String categoryForFilter = "Праздник";
        String publishDateStartExpected = TestUtils.getDateToString(beginningOfPeriod);
        String publishDateEndExpected = TestUtils.getDateToString(endOfPeriod);
        //Создание новостей с категорией объявление для фильтрации
        newsPageSteps.openControlPanel();
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, first, first,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, second, second,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, third, third,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, forth, forth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, fifth, fifth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, sixth, sixth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryHoliday,
                beginningOfPeriod.getYear(), beginningOfPeriod.getMonthValue(), beginningOfPeriod.getDayOfMonth(),
                endOfPeriod.getYear(), endOfPeriod.getMonthValue(), endOfPeriod.getDayOfMonth());
        //Проверяем, что в полях формы отображаются введенные данные, а не другие
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryForFilter)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Включаем фильтрацию
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что отображаются новости с категорией Праздник
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList(first).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(second).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(third).check(matches(isDisplayed()));
        //Проверка, что новости с категорией Массаж не отображаются
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(forth))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(fifth))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(sixth))));
    }


    @Test
    @DisplayName("Фильтрация новостей по Категории Массаж")
    public void shouldFilterTheNewsWithCategoryMassage() {
        LocalDateTime beginningOfPeriod = today.minusDays(1);
        LocalDateTime endOfPeriod = today.plusDays(369);
        String first = namingHelper.getNewsHolidayName();
        String second = namingHelper.getNewsHolidayName();
        String third = namingHelper.getNewsHolidayName();
        String forth = namingHelper.getNewsMassageName();
        String fifth = namingHelper.getNewsMassageName();
        String sixth = namingHelper.getNewsMassageName();
        String categoryForFilter = "Массаж";
        String publishDateStartExpected = TestUtils.getDateToString(beginningOfPeriod);
        String publishDateEndExpected = TestUtils.getDateToString(endOfPeriod);
        //Создание новостей с категорией объявление для фильтрации
        newsPageSteps.openControlPanel();
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, first, first,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, second, second,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, third, third,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, forth, forth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, fifth, fifth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, sixth, sixth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryMassage,
                beginningOfPeriod.getYear(), beginningOfPeriod.getMonthValue(), beginningOfPeriod.getDayOfMonth(),
                endOfPeriod.getYear(), endOfPeriod.getMonthValue(), endOfPeriod.getDayOfMonth());
        //Проверяем, что в полях формы отображаются введенные данные, а не другие
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryForFilter)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Включаем фильтрацию
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что отображаются новости с категорией Массаж
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList(forth).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(fifth).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(sixth).check(matches(isDisplayed()));
        //Проверка, что новости с категорией Праздник не отображаются
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(first))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(second))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(third))));
    }


    @Test
    @DisplayName("Фильтрация новостей по Категории Благодарность")
    public void shouldFilterTheNewsWithCategoryGratitude() {
        LocalDateTime beginningOfPeriod = today.minusDays(1);
        LocalDateTime endOfPeriod = today.plusDays(369);
        String first = namingHelper.getNewsGratitudeName();
        String second = namingHelper.getNewsGratitudeName();
        String third = namingHelper.getNewsGratitudeName();
        String forth = namingHelper.getNewsNeedHelpName();
        String fifth = namingHelper.getNewsNeedHelpName();
        String sixth = namingHelper.getNewsNeedHelpName();
        String categoryForFilter = "Благодарность";
        String publishDateStartExpected = TestUtils.getDateToString(beginningOfPeriod);
        String publishDateEndExpected = TestUtils.getDateToString(endOfPeriod);
        //Создание новостей с категорией объявление для фильтрации
        newsPageSteps.openControlPanel();
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, first, first,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, second, second,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, third, third,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, forth, forth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, fifth, fifth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, sixth, sixth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryGratitude,
                beginningOfPeriod.getYear(), beginningOfPeriod.getMonthValue(), beginningOfPeriod.getDayOfMonth(),
                endOfPeriod.getYear(), endOfPeriod.getMonthValue(), endOfPeriod.getDayOfMonth());
        //Проверяем, что в полях формы отображаются введенные данные, а не другие
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryForFilter)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Включаем фильтрацию
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что отображаются новости с категорией Благодарность
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList(first).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(second).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(third).check(matches(isDisplayed()));
        //Проверка, что новости с категорией Нужна помощь не отображаются
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(forth))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(fifth))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(sixth))));
    }


    @Test
    @DisplayName("Фильтрация новостей по Категории Нужна помощь")
    public void shouldFilterTheNewsWithCategoryNeedHelp() {
        LocalDateTime beginningOfPeriod = today.minusDays(1);
        LocalDateTime endOfPeriod = today.plusDays(369);
        String first = namingHelper.getNewsGratitudeName();
        String second = namingHelper.getNewsGratitudeName();
        String third = namingHelper.getNewsGratitudeName();
        String forth = namingHelper.getNewsNeedHelpName();
        String fifth = namingHelper.getNewsNeedHelpName();
        String sixth = namingHelper.getNewsNeedHelpName();
        String categoryForFilter = "Нужна помощь";
        String publishDateStartExpected = TestUtils.getDateToString(beginningOfPeriod);
        String publishDateEndExpected = TestUtils.getDateToString(endOfPeriod);
        //Создание новостей с категорией объявление для фильтрации
        newsPageSteps.openControlPanel();
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, first, first,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, second, second,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, third, third,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, forth, forth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, fifth, fifth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryNeedHelp, sixth, sixth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryNeedHelp,
                beginningOfPeriod.getYear(), beginningOfPeriod.getMonthValue(), beginningOfPeriod.getDayOfMonth(),
                endOfPeriod.getYear(), endOfPeriod.getMonthValue(), endOfPeriod.getDayOfMonth());
        //Проверяем, что в полях формы отображаются введенные данные, а не другие
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryForFilter)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Включаем фильтрацию
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что отображаются новости с категорией Нужна помощь
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList(forth).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(fifth).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(sixth).check(matches(isDisplayed()));
        //Проверка, что новости с категорией Благодарность не отображаются
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(first))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(second))));
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(third))));
    }


    @Test
    @DisplayName("Отмена филтрации новостей")
    public void shouldNotFilterNewsByCategoryAnnouncement() {
        LocalDateTime beginningOfPeriod = today.minusDays(1);
        LocalDateTime endOfPeriod = today.plusDays(369);
        String first = namingHelper.getNewsAnnouncementName();
        String second = namingHelper.getNewsBirthdayName();
        String third = namingHelper.getNewsHolidayName();
        String forth = namingHelper.getNewsMassageName();
        String fifth = namingHelper.getNewsGratitudeName();
        String sixth = namingHelper.getNewsTradeUnionName();
        String categoryForFilter = "Объявление";
        String publishDateStartExpected = TestUtils.getDateToString(beginningOfPeriod);
        String publishDateEndExpected = TestUtils.getDateToString(endOfPeriod);
        //Создание новостей с категорией объявление для фильтрации
        newsPageSteps.openControlPanel();
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, first, first,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryBirthday, second, second,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryHoliday, third, third,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryMassage, forth, forth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryGratitude, fifth, fifth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryTradeUnion, sixth, sixth,
                DataHelper.getValidDate().getYear(), DataHelper.getValidDate().getMonthValue(), DataHelper.getValidDate().getDayOfMonth());
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryAnnouncement,
                beginningOfPeriod.getYear(), beginningOfPeriod.getMonthValue(), beginningOfPeriod.getDayOfMonth(),
                endOfPeriod.getYear(), endOfPeriod.getMonthValue(), endOfPeriod.getDayOfMonth());
        //Проверяем, что в полях формы отображаются введенные данные, а не другие
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryForFilter)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Отменяем фильтрацию
        filterNewsPageSteps.cancelFilterNewsButtonClick();
        //Проверка, что фильтр не включился и отображаются все новости
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList(first).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(second).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(third).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(forth).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(fifth).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(sixth).check(matches(isDisplayed()));
    }


    @Test
    @DisplayName("Фильтрация новостей по несуществующей категории")
    public void shouldShowAMessageSelectACategoryFromTheList() {
        LocalDateTime beginningOfPeriod = today.minusDays(1);
        LocalDateTime endOfPeriod = today.plusDays(369);
        String myCategoryTitle = namingHelper.getNewsMyCategoryName();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        controlPanelSteps.replaceNewsCategoryText(myCategoryTitle);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateStartField,
                beginningOfPeriod.getYear(), beginningOfPeriod.getMonthValue(), beginningOfPeriod.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateEndField,
                endOfPeriod.getYear(), endOfPeriod.getMonthValue(), endOfPeriod.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что появляется сообщение
        controlPanelSteps.checkToast("Invalid category. Select a category from the list.", true);
    }


    @Test
    @DisplayName("Фильрация актуальных новостей за период из будущего")
    public void shouldShowATextThereIsNothingHere() {
        LocalDateTime date = DataHelper.getValidDate().plusMonths(1);
        LocalDateTime endOfPeriod = date.plusYears(1);
        String titlePublicationDateInTheFuture = namingHelper.getNewsAnnouncementName();
        String categoryForFilter = "Объявление";
        String publishDateStartExpected = TestUtils.getDateToString(date);
        String publishDateEndExpected = TestUtils.getDateToString(endOfPeriod);
        //Создаем новость с датой публикации в будущем
        newsPageSteps.openControlPanel();
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titlePublicationDateInTheFuture, titlePublicationDateInTheFuture,
                date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryAnnouncement,
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                endOfPeriod.getYear(), endOfPeriod.getMonthValue(), endOfPeriod.getDayOfMonth());
        //Проверяем, что в полях формы отображаются введенные данные, а не другие
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryForFilter)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Включаем фильтрацию
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что отображается кнопка REFRESH, текст и картинка пустого списка новостей
        newsPageSteps.isEmptyNewsList();
    }


    @Test
    @DisplayName("Фильтрация новостей без заданного периода")
    public void shouldShowAllActualNewsCategoryAnnouncement() {
        LocalDateTime dateMonthAgo = DataHelper.getDateOneMonthAgo();
        LocalDateTime dateTenDaysAgo = DataHelper.getDateTenDaysAgo();
        LocalDateTime dateToday = DataHelper.getDateToday();
        String first = namingHelper.getNewsAnnouncementName();
        String second = namingHelper.getNewsAnnouncementName();
        String third = namingHelper.getNewsAnnouncementName();

        //Создаем новость
        newsPageSteps.openControlPanel();
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, first, first,
                dateMonthAgo.getYear(), dateMonthAgo.getMonthValue(), dateMonthAgo.getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, second, second,
                dateTenDaysAgo.getYear(), dateTenDaysAgo.getMonthValue(), dateTenDaysAgo.getDayOfMonth());
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, third, third,
                dateToday.getYear(), dateToday.getMonthValue(), dateToday.getDayOfMonth());
        //Переходим в раздел Новости
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что отображаются все актуальные новости категории Объявление
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList(first).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(second).check(matches(isDisplayed()));
        controlPanelSteps.scrollToElementInRecyclerList(third).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Фильтрация новости с заданным периодом от и незаданным периодом до")
    public void shouldShowMessageWrongPeriodPublishDateEndField() {
        LocalDateTime dateMonthAgo = DataHelper.getDateOneMonthAgo();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateStartField,
                dateMonthAgo.getYear(), dateMonthAgo.getMonthValue(), dateMonthAgo.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка сообщения "Wrong period"
        filterNewsPageSteps.isMessageWrongPeriod();
    }

    @Test
    @DisplayName("Фильтрация новости с заданным периодом до и незаданным периодом от")
    public void shouldShowMessageWrongPeriodWithEmptyPublishDateStartField() {
        LocalDateTime dateToday = DataHelper.getDateToday();
        //Открываем форму фильтра и заполняем её
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateEndField,
                dateToday.getYear(), dateToday.getMonthValue(), dateToday.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка сообщения "Wrong period"
        filterNewsPageSteps.isMessageWrongPeriod();
    }


}
