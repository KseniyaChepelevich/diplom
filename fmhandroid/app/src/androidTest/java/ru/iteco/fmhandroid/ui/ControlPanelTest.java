package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
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
public class ControlPanelTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();
    NewsPageElements newsPageElements =  new NewsPageElements();
    FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    FilterNewsPageElements filterNewsPageElements = new FilterNewsPageElements();
    ControlPanelElements controlPanelElements = new ControlPanelElements();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

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
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);

    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("Открытие формы создания новости")
    public void shouldOpenCreatNewsForm() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.isCreatingNewsForm();
    }


    @Test
    @DisplayName("Создание Новости с категорией Объявление")
    public void shouldCreateANewsItemWithCategoryAnnouncement() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateTomorrow("Тест новость Объявление", "Тест новость Объявление");
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        String today = DataHelper.getValidDate(0, 0);
        String tomorrow = DataHelper.getValidDate(0, 1);

        //Проверка что отображаются новости с заголовком "Тест новость Объявление"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText("Тест новость Объявление")), hasDescendant(withText(today)), hasDescendant(withText(tomorrow)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
       controlPanelSteps.deleteItemNews("Тест новость Объявление");
    }

    @Test
    @DisplayName("Создание Новости с категорией День рождения")
    public void shouldCreateANewsItemWithCategoryBirthday() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryBirthday);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateTomorrow("Тест новость День рождения", "Тест новость День рождения");
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        String today = DataHelper.getValidDate(0, 0);
        String tomorrow = DataHelper.getValidDate(0, 1);

        //Проверка что отображаются новости с заголовком "Тест новость День рождения"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText("Тест новость День рождения")), hasDescendant(withText(today)), hasDescendant(withText(tomorrow)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews("Тест новость День рождения");
    }

    @Test
    @DisplayName("Создание Новости с категорией День рождения")
    public void shouldCreateANewsItemWithCategorySalagy() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateTomorrow("Тест новость Зарплата", "Тест новость Зарплата");
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        String today = DataHelper.getValidDate(0, 0);
        String tomorrow = DataHelper.getValidDate(0, 1);

        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText("Тест новость Зарплата")), hasDescendant(withText(today)), hasDescendant(withText(tomorrow)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews("Тест новость Зарплата");
    }

    @Test
    @DisplayName("Создание Новости с категорией Профсоюз")
    public void shouldCreateANewsItemWithCategoryTradeUnion() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryTradeUnion);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateTomorrow("Тест новость Профсоюз", "Тест новость Профсоюз");
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        String today = DataHelper.getValidDate(0, 0);
        String tomorrow = DataHelper.getValidDate(0, 1);

        //Проверка что отображаются новости с заголовком "Тест новость Профсоюз"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText("Тест новость Профсоюз")), hasDescendant(withText(today)), hasDescendant(withText(tomorrow)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews("Тест новость Профсоюз");
    }

    @Test
    @DisplayName("Создание Новости с категорией Праздник")
    public void shouldCreateANewsItemWithCategoryHoliday() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryHoliday);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateTomorrow("Тест новость Праздник", "Тест новость Праздник");
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        String today = DataHelper.getValidDate(0, 0);
        String tomorrow = DataHelper.getValidDate(0, 1);

        //Проверка что отображаются новости с заголовком "Тест новость Праздник"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText("Тест новость Праздник")), hasDescendant(withText(today)), hasDescendant(withText(tomorrow)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews("Тест новость Праздник");
    }

    @Test
    @DisplayName("Создание Новости с категорией Благодарность")
    public void shouldCreateANewsItemWithCategoryGratitude() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryGratitude);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateTomorrow("Тест новость Благодарность", "Тест новость Благодарность");
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        String today = DataHelper.getValidDate(0, 0);
        String tomorrow = DataHelper.getValidDate(0, 1);

        //Проверка что отображаются новости с заголовком "Тест новость Благодарность"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText("Тест новость Благодарность")), hasDescendant(withText(today)), hasDescendant(withText(tomorrow)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews("Тест новость Благодарность");
    }

    @Test
    @DisplayName("Создание Новости с категорией Массаж")
    public void shouldCreateANewsItemWithCategoryMassage() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryMassage);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateTomorrow("Тест новость Массаж", "Тест новость Массаж");
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        String today = DataHelper.getValidDate(0, 0);
        String tomorrow = DataHelper.getValidDate(0, 1);

        //Проверка что отображаются новости с заголовком "Тест новость Массаж"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText("Тест новость Массаж")), hasDescendant(withText(today)), hasDescendant(withText(tomorrow)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews("Тест новость Массаж");
    }

    @Test
    @DisplayName("Создание Новости с категорией Нужна помощь")
    public void shouldCreateANewsItemWithCategoryNeedHelp() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryNeedHelp);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateTomorrow("Тест новость Нужна помощь", "Тест новость Нужна помощь");
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        String today = DataHelper.getValidDate(0, 0);
        String tomorrow = DataHelper.getValidDate(0, 1);

        //Проверка что отображаются новости с заголовком "Тест новость Массаж"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText("Тест новость Нужна помощь")), hasDescendant(withText(today)), hasDescendant(withText(tomorrow)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews("Тест новость Нужна помощь");
    }

    @Test
    @DisplayName("Отмена создания новости")
    public void shouldNotCreateNews() {
        //Проверить количество новостей до
        //int item = CustomRecyclerViewActions.getItemCount(controlPanelElements.newsRecyclerList);
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryNeedHelp);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateTomorrow("Новость не должна сохраниться", "Новость не должна сохраниться");
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.cancelBut);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
        SystemClock.sleep(3000);
        String today = DataHelper.getValidDate(0, 0);
        String tomorrow = DataHelper.getValidDate(0, 1);

        //Проверить количетво новостей после
        //controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.CustomViewMatcher.recyclerViewSizeMatcher(item)));
                // scrollTo will fail the test if no item matches.



    }

    @Test
    @DisplayName("Создание новости с категорией не из списка")
    public void shouldShowAMessageWithTextSelectACategoryFromTheList() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.replaceTextNewsCategory("Тест");
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateTomorrow("Новость не должна сохраниться", "Новость не должна сохраниться");
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelSteps.checkToast("Wrong category selected. Select a category from the list.", true);
    }

    @Test
    @DisplayName("Сохранение пустой формы новости")
    public void shouldNotSaveEmptyNews() {
        controlPanelSteps.openCreatingNewsForm();
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelSteps.isWrongEmptyFormNews();

    }

    @Test
    @DisplayName("Создание Новости со статусом Не активна")
    public void shouldToggleTurnOffSwitchActive() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryMassage);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateTomorrow("Тест новость Массаж", "Тест новость Массаж");
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.switcherActive);
        controlPanelElements.switcherNotActive.check(matches(isDisplayed()));
    }

}

