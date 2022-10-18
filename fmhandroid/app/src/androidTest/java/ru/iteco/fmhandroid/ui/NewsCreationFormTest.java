package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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
public class NewsCreationFormTest {
    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();
    NewsPageElements newsPageElements = new NewsPageElements();
    FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    FilterNewsPageElements filterNewsPageElements = new FilterNewsPageElements();
    ControlPanelElements controlPanelElements = new ControlPanelElements();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    String titleForNewsAnnouncement = "Тест новость Объявление" + " " + DataHelper.generateTitleId();
    String titleForNewsBirthday = "Тест новость День рождения" + " " + DataHelper.generateTitleId();
    String titleForNewsSalary = "Тест новость Зарплата" + " " + DataHelper.generateTitleId();
    String titleForNewsTradeUnion = "Тест новость Профсоюз" + " " + DataHelper.generateTitleId();
    String titleForNewsHoliday = "Тест новость Праздник" + " " + DataHelper.generateTitleId();
    String titleForNewsGratitude = "Тест новость Благодарность" + " " + DataHelper.generateTitleId();
    String titleForNewsMassage = "Тест новость Массаж" + " " + DataHelper.generateTitleId();
    String titleForNewsNeedHelp = "Тест новость Нужна помощь" + " " + DataHelper.generateTitleId();
    String titleForNewsShouldNotBeKept = "Новость не должна сохраниться" + " " + DataHelper.generateTitleId();
    String titleForNewsPublicationDateTomorrow = "Дата публикации сегодня" + " " + DataHelper.generateTitleId();
    String titleForNewsPublicationDateInAMonth = "Дата публикации через месяц" + " " + DataHelper.generateTitleId();
    String titleForNewsPublicationTimeHourAgo = "Время публикации час назад" + " " + DataHelper.generateTitleId();
    String titleForNewsPublicationTimeInOneHour = "Время публикации через час" + " " + DataHelper.generateTitleId();

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
        controlPanelSteps.openCreatingNewsForm();

    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }


    @Test
    @DisplayName("Автоподставление в поле Titel из поля Category")
    public void shouldSubstituteInTheTitleFieldTheValueOfTheCategoryField() {

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryAnnouncement);
        controlPanelElements.newsItemTitleField.check(matches(withText("Объявление")));

    }


    @Test
    @DisplayName("Создание Новости с категорией Объявление")
    public void shouldCreateANewsItemWithCategoryAnnouncement() {
        String publicationDate = DataHelper.getValidDate(0, 0);
        //String publicationTime = DataHelper.getValidTime(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, titleForNewsAnnouncement, titleForNewsAnnouncement);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        //controlPanelElements.newsItemPublishTimeField.check(matches(withText(publicationTime)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);

        //Проверка что отображаются новости с заголовком "Тест новость Объявление"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsAnnouncement)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsAnnouncement);
    }

    @Test
    @DisplayName("Создание Новости с категорией День рождения")
    public void shouldCreateANewsItemWithCategoryBirthday() {
        String publicationDate = DataHelper.getValidDate(0, 0);
        //String publicationTime = DataHelper.getValidTime(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryBirthday);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, titleForNewsBirthday, titleForNewsBirthday);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        //controlPanelElements.newsItemPublishTimeField.check(matches(withText(publicationTime)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);

        //Проверка что отображаются новости с заголовком "Тест новость День рождения"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsBirthday)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsBirthday);
    }

    @Test
    @DisplayName("Создание Новости с категорией Зарплата")
    public void shouldCreateANewsItemWithCategorySalagy() {
        String publicationDate = DataHelper.getValidDate(0, 0);
        //String publicationTime = DataHelper.getValidTime(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, titleForNewsSalary, titleForNewsSalary);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        //controlPanelElements.newsItemPublishTimeField.check(matches(withText(publicationTime)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);

        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsSalary)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsSalary);
    }

    @Test
    @DisplayName("Создание Новости с категорией Профсоюз")
    public void shouldCreateANewsItemWithCategoryTradeUnion() {
        String publicationDate = DataHelper.getValidDate(0, 0);
        //String publicationTime = DataHelper.getValidTime(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryTradeUnion);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, titleForNewsTradeUnion, titleForNewsTradeUnion);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        //controlPanelElements.newsItemPublishTimeField.check(matches(withText(publicationTime)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);

        //Проверка что отображаются новости с заголовком "Тест новость Профсоюз"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsTradeUnion)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsTradeUnion);
    }

    @Test
    @DisplayName("Создание Новости с категорией Праздник")
    public void shouldCreateANewsItemWithCategoryHoliday() {
        String publicationDate = DataHelper.getValidDate(0, 0);
        //String publicationTime = DataHelper.getValidTime(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryHoliday);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, titleForNewsHoliday, titleForNewsHoliday);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        //controlPanelElements.newsItemPublishTimeField.check(matches(withText(publicationTime)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);

        //Проверка что отображаются новости с заголовком "Тест новость Праздник"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsHoliday)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsHoliday);
    }

    @Test
    @DisplayName("Создание Новости с категорией Благодарность")
    public void shouldCreateANewsItemWithCategoryGratitude() {
        String publicationDate = DataHelper.getValidDate(0, 0);
        //String publicationTime = DataHelper.getValidTime(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryGratitude);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, titleForNewsGratitude, titleForNewsGratitude);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        //controlPanelElements.newsItemPublishTimeField.check(matches(withText(publicationTime)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);

        //Проверка что отображаются новости с заголовком "Тест новость Благодарность"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsGratitude)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsGratitude);
    }

    @Test
    @DisplayName("Создание Новости с категорией Массаж")
    public void shouldCreateANewsItemWithCategoryMassage() {
        String publicationDate = DataHelper.getValidDate(0, 0);
        //String publicationTime = DataHelper.getValidTime(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryMassage);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, titleForNewsMassage, titleForNewsMassage);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        //controlPanelElements.newsItemPublishTimeField.check(matches(withText(publicationTime)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);

        //Проверка что отображаются новости с заголовком "Тест новость Массаж"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsMassage)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsMassage);
    }

    @Test
    @DisplayName("Создание Новости с категорией Нужна помощь")
    public void shouldCreateANewsItemWithCategoryNeedHelp() {
        String publicationDate = DataHelper.getValidDate(0, 0);
        //String publicationTime = DataHelper.getValidTime(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryNeedHelp);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, titleForNewsNeedHelp, titleForNewsNeedHelp);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        //controlPanelElements.newsItemPublishTimeField.check(matches(withText(publicationTime)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);

        //Проверка что отображаются новости с заголовком "Тест новость Массаж"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsNeedHelp)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsNeedHelp);
    }

   //Как проверить отсутствие новости в списке?
    @Test
    @DisplayName("Отмена создания новости")
    public void shouldNotCreateNews() {
        //int item = CustomRecyclerViewActions.getItemCount(controlPanelElements.newsRecyclerList);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryNeedHelp);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, titleForNewsShouldNotBeKept, titleForNewsShouldNotBeKept);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.cancelBut);
        controlPanelElements.messageChangesWonTBeSaved.check(matches(isDisplayed()));
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
        SystemClock.sleep(3000);
        //Проверить отсутствие в списке новостей новости с заголовком "Новость не должна сохраниться"
        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewisNotExist(R.id.news_item_title_text_view, withText(titleForNewsShouldNotBeKept))));

    }

    @Test
    @DisplayName("Создание новости с категорией не из списка")
    public void shouldShowAWrongMessageWithTextSelectACategoryFromTheList() {

        controlPanelSteps.replaceTextNewsCategory("Тест");
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, titleForNewsShouldNotBeKept, titleForNewsShouldNotBeKept);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelSteps.checkToast("Wrong category selected. Select a category from the list.", true);
    }

    @Test
    @DisplayName("Сохранение пустой формы новости")
    public void shouldNotSaveEmptyNews() {

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelSteps.isWrongEmptyFormNews();

    }

    @Test
    @DisplayName("Создание Новости со статусом Не активна")
    public void shouldToggleTurnOffSwitchActive() {

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryMassage);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, titleForNewsMassage, titleForNewsMassage);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.switcherActive);
        controlPanelElements.switcherNotActive.check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с датой публикации завтра")
    public void shouldCreateANewsItemWithPublishDateTomorrow() {
        String publicationDate = DataHelper.getValidDate(0, 1);
        //String publicationTime = DataHelper.getValidTime(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 1, 0, 0, titleForNewsPublicationDateTomorrow, titleForNewsPublicationDateTomorrow);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        //controlPanelElements.newsItemPublishTimeField.check(matches(withText(publicationTime)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);


        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsPublicationDateTomorrow)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationDateTomorrow);
    }

    @Test
    @DisplayName("Создание Новости с датой публикации через месяц")
    public void shouldCreateANewsItemWithPublicationDateInAMonth() {
        String publicationDate = DataHelper.getValidDate(1, 0);
        //String publicationTime = DataHelper.getValidTime(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 1, 0, 0, 0, titleForNewsPublicationDateInAMonth, titleForNewsPublicationDateInAMonth);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        //controlPanelElements.newsItemPublishTimeField.check(matches(withText(publicationTime)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);

        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsPublicationDateInAMonth)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationDateInAMonth);
    }

    //При ручном тестировании этот кейс проходит без ошибки
    @Test
    @DisplayName("Создание Новости с датой публикации вчера")
    public void shouldCreateANewsItemWithPublicationDateYesterday() {
        String publicationDate = DataHelper.getValidDate(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categorySalary);
        controlPanelSteps.setDateToDatePicker(0, 0, -1);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
    }

    //При ручном тестировании этот кейс проходит без ошибки
    @Test
    @DisplayName("Создание Новости с датой публикации год назад")
    public void shouldCreateANewsItemWithPublicationDateOneYearAgo() {
        String publicationDate = DataHelper.getValidDate(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categorySalary);
        controlPanelSteps.setDateToDatePicker(-1, 0, 0);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
    }

    @Test
    @DisplayName("Создание Новости с датой публикации час назад")
    public void shouldCreateANewsItemWithPublicationTimeHourAgo() {
        String timeHourAgo = DataHelper.getValidTime(-1, 0);
        String publicationDate = DataHelper.getValidDate(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, -1, 0, titleForNewsPublicationTimeHourAgo, titleForNewsPublicationTimeHourAgo);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        controlPanelElements.newsItemPublishTimeField.check(matches(withText(timeHourAgo)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);

        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsPublicationTimeHourAgo)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationTimeHourAgo);
    }

    @Test
    @DisplayName("Создание Новости с датой публикации через час")
    public void shouldCreateANewsItemWithPublicationTimeInOneHour() {
        String timeInOneHour = DataHelper.getValidTime(+1, 0);
        String publicationDate = DataHelper.getValidDate(0, 0);


        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, +1, 0, titleForNewsPublicationTimeInOneHour, titleForNewsPublicationTimeInOneHour);

        controlPanelElements.newsItemPublishDateField.check(matches(withText(publicationDate)));
        controlPanelElements.newsItemPublishTimeField.check(matches(withText(timeInOneHour)));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);

        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleForNewsPublicationTimeInOneHour)), hasDescendant(withText(publicationDate)), hasDescendant(withText("Иванов Д.Д.")))
                )).check(matches(isDisplayed()));

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationTimeInOneHour);
    }

    @Test
    @DisplayName("Выставление времени публикации Новости вводом цифр")
    public void shouldSetTheTimeByEnteringNumbers() {
        String timeInOneHour = DataHelper.getValidTime(1, 0);


        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.newsItemPublishTimeField);
        controlPanelElements.timePicker.check(matches(isDisplayed()));
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.timePickerToggleMode);
        controlPanelSteps.setTimeToTimePicker(1, 0);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);

        //controlPanelElements.newsItemPublishTimeField.check(matches(withText(timeInOneHour)));
    }

    @Test
    @DisplayName("Отмена ввода времени в Форме для создания Новости")
    public void shouldNotSetTime() {


        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.newsItemPublishTimeField);
        controlPanelElements.timePicker.check(matches(isDisplayed()));

        controlPanelSteps.setTimeToTimePicker(1, 0);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.cancelDeleteBut);

        controlPanelElements.newsItemPublishTimeField.check(matches(withText("")));
    }

    @Test
    @DisplayName("Отмена ввода даты в Форме для создания Новости")
    public void shouldNotSetDate() {

        controlPanelSteps.setDateToDatePicker(1, 0, 0);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.cancelDeleteBut);
        controlPanelElements.newsItemPublishDateField.check(matches(withText("")));
    }

    @Test
    @DisplayName("Отмена Создания Новости и отмена выхода из формы")
    public void shouldNotGetOutOfNewsForm() {

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.cancelBut);
        controlPanelElements.messageChangesWonTBeSaved.check(matches(isDisplayed()));
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.cancelDeleteBut);
        controlPanelSteps.isCreatingNewsForm();


    }


}

