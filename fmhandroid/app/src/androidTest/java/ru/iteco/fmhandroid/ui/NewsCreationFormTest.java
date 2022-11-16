package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.RemoteException;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
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

public class NewsCreationFormTest {
    private UiDevice device;

    AuthSteps authSteps = new AuthSteps();
    MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    Calendar date = Calendar.getInstance();

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
    String nonLetterTitle = ";&&" + " " + DataHelper.generateTitleId();

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        try {
            authSteps.isAuthScreen();
        } catch (PerformException e) {
            mainPageSteps.clickLogOutBut();
        }
        authSteps.authWithValidData(authInfo());
        mainPageSteps.isMainPage();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        TestUtils.waitView(newsPageSteps.editNewsMaterialBut).perform(click());
        TestUtils.waitView(controlPanelSteps.addNewsImBut).perform(click());
    }

    @Test
    @DisplayName("Автоподставление в поле Title из поля Category")
    public void shouldSubstituteInTheTitleFieldTheValueOfTheCategoryField() {
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        TestUtils.waitView(controlPanelSteps.newsItemTitleField).check(matches(withText("Объявление")));
    }

    @Test
    @DisplayName("Создание Новости с категорией Объявление")
    public void shouldCreateANewsItemWithCategoryAnnouncement() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsAnnouncement, titleForNewsAnnouncement);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость Объявление"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsAnnouncement).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsAnnouncement);
    }

    @Test
    @DisplayName("Создание Новости с категорией День рождения")
    public void shouldCreateANewsItemWithCategoryBirthday() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryBirthday);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsBirthday, titleForNewsBirthday);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость День рождения"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsBirthday).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsBirthday);
    }

    @Test
    @DisplayName("Создание Новости с категорией Зарплата")
    public void shouldCreateANewsItemWithCategorySalary() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsSalary, titleForNewsSalary);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsSalary).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsSalary);
    }

    @Test
    @DisplayName("Создание Новости с категорией Профсоюз")
    public void shouldCreateANewsItemWithCategoryTradeUnion() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryTradeUnion);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsTradeUnion, titleForNewsTradeUnion);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость Профсоюз"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsTradeUnion).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsTradeUnion);
    }

    @Test
    @DisplayName("Создание Новости с категорией Праздник")
    public void shouldCreateANewsItemWithCategoryHoliday() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryHoliday);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsHoliday, titleForNewsHoliday);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость Праздник"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsHoliday).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsHoliday);
    }

    @Test
    @DisplayName("Создание Новости с категорией Благодарность")
    public void shouldCreateANewsItemWithCategoryGratitude() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryGratitude);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsGratitude, titleForNewsGratitude);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость Благодарность"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsGratitude).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsGratitude);
    }

    @Test
    @DisplayName("Создание Новости с категорией Массаж")
    public void shouldCreateANewsItemWithCategoryMassage() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryMassage);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsMassage, titleForNewsMassage);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость Массаж"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsMassage).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsMassage);
    }

    @Test
    @DisplayName("Создание Новости с категорией Нужна помощь")
    public void shouldCreateANewsItemWithCategoryNeedHelp() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryNeedHelp);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsNeedHelp, titleForNewsNeedHelp);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость Массаж"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsNeedHelp).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsNeedHelp);
    }

    @Test
    @DisplayName("Отмена создания новости")
    public void shouldNotCreateNews() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryNeedHelp);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsShouldNotBeKept, titleForNewsShouldNotBeKept);
        TestUtils.waitView(controlPanelSteps.cancelBut).perform(click());
        TestUtils.waitView(controlPanelSteps.messageChangesWonTBeSaved).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверить отсутствие в списке новостей новости с заголовком "Новость не должна сохраниться"
        TestUtils.waitView(controlPanelSteps.newsRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(titleForNewsShouldNotBeKept))));
    }

    @Test
    @DisplayName("Создание новости с категорией не из списка")
    public void shouldShowAWrongMessageWithTextSelectACategoryFromTheList() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        TestUtils.waitView(controlPanelSteps.newsItemCategoryField).perform(replaceText("Тест"));
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsShouldNotBeKept, titleForNewsShouldNotBeKept);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        controlPanelSteps.checkToast("Wrong category selected. Select a category from the list.", true);
    }

    @Test
    @DisplayName("Сохранение пустой формы новости")
    public void shouldNotSaveEmptyNews() {
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        controlPanelSteps.isWrongEmptyFormNews();
    }

    @Test
    @DisplayName("Создание Новости со статусом Не активна")
    public void shouldToggleTurnOffSwitchActive() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryMassage);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsMassage, titleForNewsMassage);
        TestUtils.waitView(controlPanelSteps.switcherActive).perform(click());
        TestUtils.waitView(controlPanelSteps.switcherNotActive).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание Новости с датой публикации завтра")
    public void shouldCreateANewsItemWithPublishDateTomorrow() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH)+1;
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsPublicationDateTomorrow, titleForNewsPublicationDateTomorrow);
        //Проверка, что выбранная дата отображается
        TestUtils.waitView(controlPanelSteps.newsItemPublishDateField).check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsPublicationDateTomorrow).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationDateTomorrow);
    }

    @Test
    @DisplayName("Создание Новости с датой публикации через месяц")
    public void shouldCreateANewsItemWithPublicationDateInAMonth() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+2;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsPublicationDateInAMonth, titleForNewsPublicationDateInAMonth);

        TestUtils.waitView(controlPanelSteps.newsItemPublishDateField).check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsPublicationDateInAMonth).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationDateInAMonth);
    }

    //При ручном тестировании этот кейс проходит без ошибки
    @Test
    @DisplayName("Создание Новости с датой публикации вчера")
    public void shouldCreateANewsItemWithPublicationDateYesterday() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.setDateToDatePicker(year, month, day -1);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверяем, что в поле Дата отображается сегодняшняя дата
        TestUtils.waitView(controlPanelSteps.newsItemPublishDateField).check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }

    //При ручном тестировании этот кейс проходит без ошибки
    @Test
    @DisplayName("Создание Новости с датой публикации год назад")
    public void shouldCreateANewsItemWithPublicationDateOneYearAgo() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        controlPanelSteps.setDateToDatePicker(year -1, month, day);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        TestUtils.waitView(controlPanelSteps.newsItemPublishDateField).check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
    }

    @Test
    @DisplayName("Создание Новости с датой публикации час назад")
    public void shouldCreateANewsItemWithPublicationTimeHourAgo() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY)-1;
        int minutes = date.get(Calendar.MINUTE);
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        TestUtils.waitView(controlPanelSteps.newsItemTitleField).perform(replaceText(titleForNewsPublicationTimeHourAgo));
        controlPanelSteps.setDateToDatePicker(year, month, day);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        controlPanelSteps.setTimeToTimeField(hour, minutes);
        //Проверка,что выбранное время отображается
        TestUtils.waitView(controlPanelSteps.newsItemPublishTimeField).check(matches(withText(hourExpected + ":" + minutesExpected)));
        //Ввод описания
        TestUtils.waitView(controlPanelSteps.newsItemDescriptionField).perform(replaceText(titleForNewsPublicationTimeHourAgo));
        //Сохраняем
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsPublicationTimeHourAgo).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationTimeHourAgo);
    }

    @Test
    @DisplayName("Создание Новости с датой публикации через час")
    public void shouldCreateANewsItemWithPublicationTimeInOneHour() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour =  date.get(Calendar.HOUR_OF_DAY)+1;
        int minutes = date.get(Calendar.MINUTE);
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categorySalary);
        TestUtils.waitView(controlPanelSteps.newsItemTitleField).perform(replaceText(titleForNewsPublicationTimeInOneHour));
        controlPanelSteps.setDateToDatePicker(year, month, day);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        controlPanelSteps.setTimeToTimeField(hour, minutes);
        //Проверка,что выбранное время отображается
        TestUtils.waitView(controlPanelSteps.newsItemPublishTimeField).check(matches(withText(hourExpected + ":" + minutesExpected)));
        //Ввод описания
        TestUtils.waitView(controlPanelSteps.newsItemDescriptionField).perform(replaceText(titleForNewsPublicationTimeInOneHour));
        //Сохраняем
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка что отображаются новости с заголовком "Тест новость Зарплата"
        controlPanelSteps.scrollToElementInRecyclerList(titleForNewsPublicationTimeInOneHour).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleForNewsPublicationTimeInOneHour);
    }

    //Не выходит вводом цифр
    @Test
    @DisplayName("Выставление времени публикации Новости вводом цифр")
    public void shouldSetTheTimeByEnteringNumbers() {
        int hour = date.get(Calendar.HOUR_OF_DAY)+1;
        int minutes = date.get(Calendar.MINUTE);
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);

        //Открываем TimePicker и вводим время с клавиатуры
        TestUtils.waitView(controlPanelSteps.newsItemPublishTimeField).perform(click());
        TestUtils.waitView(controlPanelSteps.timePicker).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.timePickerToggleMode).perform(click());
        TestUtils.waitView(controlPanelSteps.inputHour).check(matches(isDisplayed())).perform(replaceText(hourExpected));
        TestUtils.waitView(controlPanelSteps.inputMinute).check(matches(isDisplayed())).perform(replaceText(minutesExpected));
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        //Проверка
        TestUtils.waitView(controlPanelSteps.newsItemPublishTimeField).check(matches(withText(hourExpected + ":" + minutesExpected)));
    }

    @Test
    @DisplayName("Отмена ввода времени в Форме для создания Новости")
    public void shouldNotSetTime() {
        int hour = date.get(Calendar.HOUR_OF_DAY)+1;
        int minutes = date.get(Calendar.MINUTE);

        TestUtils.waitView(controlPanelSteps.newsItemPublishTimeField).perform(click());
        TestUtils.waitView(controlPanelSteps.timePicker).check(matches(isDisplayed()));
        controlPanelSteps.setTimeToTimePicker(hour, minutes);
        TestUtils.waitView(controlPanelSteps.cancelDeleteBut).perform(click());
        //Проверяем, что поле Время пустое
        TestUtils.waitView(controlPanelSteps.newsItemPublishTimeField).check(matches(withText("")));
    }

    @Test
    @DisplayName("Отмена ввода даты в Форме для создания Новости")
    public void shouldNotSetDate() {
        int year = date.get(Calendar.YEAR)+1;
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.setDateToDatePicker(year, month, day);
        TestUtils.waitView(controlPanelSteps.cancelDeleteBut).perform(click());
        TestUtils.waitView(controlPanelSteps.newsItemPublishDateField).check(matches(withText("")));
    }

    @Test
    @DisplayName("Отмена Создания Новости и отмена выхода из формы")
    public void shouldNotGetOutOfNewsForm() {
        TestUtils.waitView(controlPanelSteps.cancelBut).perform(click());
        TestUtils.waitView(controlPanelSteps.messageChangesWonTBeSaved).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.cancelDeleteBut).perform(click());
        controlPanelSteps.isCreatingNewsForm();
    }

    @Test
    @DisplayName("Небуквенные и нецифровые знаки в поле Заголовок при создании новости")
    public void shouldShowWarningMessageNewsTitleFieldIsIncorrect() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, nonLetterTitle, titleForNewsAnnouncement);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка, что отображается сообщение
        controlPanelSteps.checkToast("The field must not contain \";&&\" characters.", true);
    }

    @Test
    @DisplayName("Небуквенные и нецифровые знаки в поле Описание при создании новости")
    public void shouldShowWarningMessageNewsDescriptionFieldIsIncorrect() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsAnnouncement, nonLetterTitle);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        //Проверка, что отображается сообщение
        controlPanelSteps.checkToast("The field must not contain \";&&\" characters.", true);
    }

    @Test
    @DisplayName("Разрыв соединения во время создания новости")
    public void shouldShowWarningMessageWhenTheConnectionIsBrokenDuringTheCreationOfTheNews() throws UiObjectNotFoundException {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsAnnouncement, titleForNewsAnnouncement);
        //Включаем режим В самолете
        authSteps.turnOnAirplaneMode();
        //Пытаемся сохранить новость
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
       //Проверяем, что отображается сообщение
        controlPanelSteps.checkToast("Saving failed. Try again later.", true);
        //Отключаем режим в самолете
        authSteps.turnOffAirplaneMode();
    }

    @Test
    @DisplayName("Поворот экрана при создании новости")
    public void shouldSaveDataInTheNewsCreationFormOnScreenRotation() throws UiObjectNotFoundException, RemoteException {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day);

        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryAnnouncement);
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDateToday(year, month, day, titleForNewsAnnouncement, titleForNewsAnnouncement);
        device.setOrientationLeft();
        //Проверяем, что введенные данные сохранились
        TestUtils.waitView(controlPanelSteps.newsItemTitleField).check(matches(withText(titleForNewsAnnouncement)));
        TestUtils.waitView(controlPanelSteps.newsItemPublishDateField).check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
        TestUtils.waitView(controlPanelSteps.newsItemDescriptionField).check(matches(withText(titleForNewsAnnouncement)));
    }


}

