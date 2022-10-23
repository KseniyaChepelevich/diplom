package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static ru.iteco.fmhandroid.ui.data.CustomViewAssertion.itemViewMatches;
import static ru.iteco.fmhandroid.ui.data.CustomViewAssertion.notListed;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;
import io.qameta.allure.kotlin.junit4.DisplayName;
import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import io.qameta.allure.android.runners.AllureAndroidJUnit4;
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
public class ControlPanelTest {
    AuthSteps authSteps = new AuthSteps();
    static MainPageSteps mainPageSteps = new MainPageSteps();
    NewsPageSteps newsPageSteps = new NewsPageSteps();
    static NewsPageElements newsPageElements =  new NewsPageElements();
    FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    FilterNewsPageElements filterNewsPageElements = new FilterNewsPageElements();
    ControlPanelElements controlPanelElements = new ControlPanelElements();
    static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    String titleNewsForEditing = "Новость для редактирования" + " " + DataHelper.generateTitleId();
    String titleNewsToDelete = "Новость для удаления" + " " + DataHelper.generateTitleId();
    static String titleNewsWillNotBeDeleted = "Новость не будет удалена" + " " + DataHelper.generateTitleId();
    static String titleNewsWillNotBeEditing = "Новость не будет изменена" + " " + DataHelper.generateTitleId();
    static String titleNewsSavedWithoutChanges = "Новость сохранена без изменений" + " " + DataHelper.generateTitleId();
    static String titleNewsTurnOffActiveStatus = "Новость для выключения активного статуса" + " " + DataHelper.generateTitleId();
    static String titleNewsWithModifiedPublicationDate = "Новость с измененной датой публикации" + " " + DataHelper.generateTitleId();
    static String titleNewsWithModifiedDescription = "Новость с измененным описанием публикации" + " " + DataHelper.generateTitleId();
    static String titleNewsWithModifiedPublicationTime = "Новсость с измененным временем публикации" + " " + DataHelper.generateTitleId();
    static String titleNewsWithModifiedTitle = "Новость с измененным заголовком" + " " + DataHelper.generateTitleId();
    static String titleNewsWithModifiedCategory = "Новость с измененной категорией" + " " + DataHelper.generateTitleId();
    static String titleNewsForOpenDescription = "Новость для просмотра описания" + " " + DataHelper.generateTitleId();



    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheckAndOpenControlPanelPage() {
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

    /*@AfterClass
    public static void deleteTestNews() {
        mainPageSteps.isMainPage();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        DataHelper.EspressoBaseTest.clickButton(newsPageElements.editNewsMaterialBut);

        controlPanelSteps.deleteItemNews(titleNewsWillNotBeDeleted);
        controlPanelSteps.deleteItemNews(titleNewsWillNotBeEditing);
        controlPanelSteps.deleteItemNews(titleNewsSavedWithoutChanges);
        controlPanelSteps.deleteItemNews(titleNewsTurnOffActiveStatus);
        controlPanelSteps.deleteItemNews(titleNewsWithModifiedPublicationDate);
        controlPanelSteps.deleteItemNews(titleNewsWithModifiedDescription);
        controlPanelSteps.deleteItemNews(titleNewsWithModifiedPublicationTime);
        controlPanelSteps.deleteItemNews(titleNewsWithModifiedTitle);
        controlPanelSteps.deleteItemNews(titleNewsWithModifiedCategory);
        controlPanelSteps.deleteItemNews(titleNewsForOpenDescription);


    }*/

    @Test
    @DisplayName("Открытие формы создания новости")
    public void shouldOpenCreatNewsForm() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.isCreatingNewsForm();

    }

    @Test
    @DisplayName("Отмена удаления новости во вкладке Панель управления")
    public void shouldNotRemoveTheNewsItem(){
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, titleNewsWillNotBeDeleted, titleNewsWillNotBeDeleted, 0, 0, 0);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWillNotBeDeleted)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsDeleteElement(titleNewsWillNotBeDeleted).perform(click());
        controlPanelElements.messageAboutDelete.check(matches(isDisplayed()));
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.cancelDeleteBut);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWillNotBeDeleted)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.deleteItemNews(titleNewsWillNotBeDeleted);
    }



    @Test
    @DisplayName("Открытие и закрытие Новости для редактирования без внесения изменений")
    public void shouldNotEditTheNews(){
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, titleNewsWillNotBeEditing, titleNewsWillNotBeEditing, 0, 0, 0);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWillNotBeEditing)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsWillNotBeEditing).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsWillNotBeEditing);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.cancelBut);
        controlPanelElements.messageChangesWonTBeSaved.check(matches(isDisplayed()));
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWillNotBeEditing)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.deleteItemNews(titleNewsWillNotBeEditing);
    }

    @Test
    @DisplayName("Открытие и сохранение Новости для редактирования без внесения изменений")
    public void shouldKeepTheNewsUnchanged(){
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, titleNewsSavedWithoutChanges, titleNewsSavedWithoutChanges, 0, 0, 0);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsSavedWithoutChanges)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsSavedWithoutChanges).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsSavedWithoutChanges);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsSavedWithoutChanges)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.deleteItemNews(titleNewsSavedWithoutChanges);
    }

    @Test
    @DisplayName("Выключение Активного статуса у Новости")
    public void shouldTurnOffActiveStatus(){
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, titleNewsTurnOffActiveStatus, titleNewsTurnOffActiveStatus, 0, 0, 0);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsTurnOffActiveStatus)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsTurnOffActiveStatus).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsTurnOffActiveStatus);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.switcherActive);
        controlPanelElements.switcherNotActive.check(matches(isDisplayed()));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsTurnOffActiveStatus)), hasDescendant(withText("NOT ACTIVE")))))
                .check(matches(isDisplayed()));

        controlPanelSteps.deleteItemNews(titleNewsTurnOffActiveStatus);
    }

    @Test
    @DisplayName("Редактирование даты публикации")
    public void shouldChangeThePublicationDate() {
        String publishDateTomorrow = DataHelper.getValidDate(0, 0, 1);

        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, titleNewsWithModifiedPublicationDate, titleNewsWithModifiedPublicationDate, 0, 0, 0);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWithModifiedPublicationDate)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedPublicationDate).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsWithModifiedPublicationDate);
        controlPanelSteps.setDateToDatePicker(0, 0, 1);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWithModifiedPublicationDate)),

                        hasDescendant(withText(publishDateTomorrow)))))
                .check(matches(isDisplayed()));

        controlPanelSteps.deleteItemNews(titleNewsWithModifiedPublicationDate);
    }

    @Test
    @DisplayName("Редактирование описания Новости")
    public void shouldChangeTheDescription() {
        String newDescription = titleNewsWithModifiedDescription + " проверка";
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, titleNewsWithModifiedDescription, titleNewsWithModifiedDescription, 0, 0, 0);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWithModifiedDescription)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedDescription).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsWithModifiedDescription);
        controlPanelElements.newsItemDescriptionField.perform(replaceText(titleNewsWithModifiedDescription + " проверка"));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWithModifiedDescription))))).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsButViewElement(titleNewsWithModifiedDescription).perform(click());
        controlPanelSteps.getItemNewsDescriptionElement(newDescription).check(matches(isDisplayed()));

        controlPanelSteps.deleteItemNews(titleNewsWithModifiedDescription);
    }

    @Test
    @DisplayName("Редактирование времени публикации Новости")
    public void shouldChangeThePublicationTime() {
        String newPublicationTime = DataHelper.getValidTime(1, 0);

        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, titleNewsWithModifiedPublicationTime, titleNewsWithModifiedPublicationTime, 0, 0, 0);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWithModifiedPublicationTime)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedPublicationTime).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsWithModifiedPublicationTime);
        controlPanelSteps.setTimeToTimeField(1, 0);

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWithModifiedPublicationTime))))).check(matches(isDisplayed()));
        //Открываем новость для проверки сохранилось ли измененное время публикации
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedPublicationTime).perform(click());
        controlPanelElements.newsItemPublishTimeField.check(matches(withText(newPublicationTime)));

        pressBack();

        controlPanelSteps.deleteItemNews(titleNewsWithModifiedPublicationTime);
    }

    @Test
    @DisplayName("Редактирование заголовка Новости")
    public void shouldChangeNewsTitle() {
        String newTitle = titleNewsWithModifiedTitle + " проверка";
        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, titleNewsWithModifiedTitle, titleNewsWithModifiedTitle, 0, 0, 0);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWithModifiedTitle)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedTitle).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsWithModifiedTitle);
        controlPanelElements.newsItemTitleField.perform(replaceText(titleNewsWithModifiedTitle + " проверка"));

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(newTitle))))).check(matches(isDisplayed()));

        controlPanelSteps.deleteItemNews(titleNewsWithModifiedTitle);
    }

    @Test
    @DisplayName("Редактирование Категории Новости")
    public void shouldChangeNewsCategory() {
        String newCategory = "День рождения";

        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, titleNewsWithModifiedCategory, titleNewsWithModifiedCategory, 0, 0, 0);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWithModifiedCategory)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedCategory).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsWithModifiedCategory);
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelElements.categoryBirthday);

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWithModifiedCategory))))).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedCategory).perform(click());
        controlPanelElements.newsItemCategoryField.check(matches(withText(newCategory)));

        pressBack();

        controlPanelSteps.deleteItemNews(titleNewsWithModifiedCategory);
    }

    @Test
    @DisplayName("Просмотр описания новости из вкладки Панель управления раздела Новости")
    public void shouldOpenNewsDescription() {

        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, titleNewsForOpenDescription, titleNewsForOpenDescription, 0, 0, 0);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsForOpenDescription)))
                )).check(matches(isDisplayed()));

        controlPanelSteps.getItemNewsButViewElement(titleNewsForOpenDescription).perform(click());
        controlPanelSteps.getItemNewsDescriptionElement(titleNewsForOpenDescription).check(matches(isDisplayed()));

        controlPanelSteps.deleteItemNews(titleNewsForOpenDescription);
    }

    @Test
    @DisplayName("Удаление новости во вкладке Панель управления")
    public void shouldDeleteNewsItem() {

        controlPanelSteps.creatingTestNews(controlPanelElements.categoryAnnouncement, titleNewsToDelete, titleNewsToDelete, 0, 0, 0);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsToDelete)))
                )).check(matches(isDisplayed()));

        controlPanelSteps.deleteItemNews(titleNewsToDelete);

        controlPanelElements.newsRecyclerList.check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.news_item_title_text_view, withText(titleNewsToDelete))));


    }

    @Test
    @DisplayName("Открытие фильтра новостей по кнопке Filter во вкладке Control panel")
    public void shouldOpenTheNewsFilterSettingsForm() {
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.filterNewsBut);
        SystemClock.sleep(3000);
        filterNewsPageSteps.isFilterNewsFormControlPanel();
    }

}
