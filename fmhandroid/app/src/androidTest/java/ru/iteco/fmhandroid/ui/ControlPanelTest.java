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
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
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

    String titleNewsForEditing = "Новость для редактирования";
    String titleNewsToDelete = "Новость для удаления";
    static String titleNewsWillNotBeDeleted = "Новость не будет удалена";
    static String titleNewsWillNotBeEditing = "Новость не будет изменена";
    static String titleNewsSavedWithoutChanges = "Новость сохранена без изменений";
    static String titleNewsTurnOffActiveStatus = "Новость для выключения активного статуса";
    static String titleNewsWithModifiedPublicationDate = "Новость с измененной датой публикации";
    static String titleNewsWithModifiedDescription = "Новость с измененным описанием публикации";
    static String titleNewsWithModifiedPublicationTime = "Новсость с измененным временем публикации";
    static String titleNewsWithModifiedTitle = "Новость с измененным заголовком";
    static String titleNewsWithModifiedCategory = "Новость с измененной категорией";
    static String titleNewsForOpenDescription = "Новость для просмотра описания";



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

    @AfterClass
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


    }

    @Test
    public void shouldOpenCreatNewsForm() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.isCreatingNewsForm();

    }

    @Test

    public void shouldNotRemoveTheNewsItem(){
        controlPanelSteps.creatingTestNews(titleNewsWillNotBeDeleted, titleNewsWillNotBeDeleted);
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
        //controlPanelSteps.deleteItemNews(titleNewsWillNotBeDeleted);
    }



    @Test

    public void shouldNotEditTheNews(){
        controlPanelSteps.creatingTestNews(titleNewsWillNotBeEditing, titleNewsWillNotBeEditing);
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
        //controlPanelSteps.deleteItemNews(titleNewsWillNotBeEditing);
    }

    @Test

    public void shouldKeepTheNewsUnchanged(){
        controlPanelSteps.creatingTestNews(titleNewsSavedWithoutChanges, titleNewsSavedWithoutChanges);
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
        //controlPanelSteps.deleteItemNews(titleNewsSavedWithoutChanges);
    }

    @Test

    public void shouldTurnOffActiveStatus(){
        controlPanelSteps.creatingTestNews(titleNewsTurnOffActiveStatus, titleNewsTurnOffActiveStatus);
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

        //controlPanelSteps.deleteItemNews(titleNewsTurnOffActiveStatus);
    }

    @Test

    public void shouldChangeThePublicationDate() {
        String publishDateTomorrow = DataHelper.getValidDate(0, 1);

        controlPanelSteps.creatingTestNews(titleNewsWithModifiedPublicationDate, titleNewsWithModifiedPublicationDate);
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

        //controlPanelSteps.deleteItemNews(titleNewswithModifiedPublicationDate);
    }

    @Test

    public void shouldChangeTheDescription() {
        String newDescription = titleNewsWithModifiedDescription + " проверка";
        controlPanelSteps.creatingTestNews(titleNewsWithModifiedDescription, titleNewsWithModifiedDescription);
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

        //controlPanelSteps.deleteItemNews(titleNewsWithModifiedDescription);
    }

    @Test

    public void shouldChangeThePublicationTime() {
        String newPublicationTime = DataHelper.getValidTime(1, 0);

        controlPanelSteps.creatingTestNews(titleNewsWithModifiedPublicationTime, titleNewsWithModifiedPublicationTime);
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

        //controlPanelSteps.deleteItemNews(titleNewsWithModifiedPublicationTime);
    }

    @Test

    public void shouldChangeNewsTitle() {
        String newTitel = titleNewsWithModifiedTitle + " проверка";
        controlPanelSteps.creatingTestNews(titleNewsWithModifiedTitle, titleNewsWithModifiedTitle);
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
                        hasDescendant(withText(newTitel))))).check(matches(isDisplayed()));

        //controlPanelSteps.deleteItemNews(titleNewsWithModifiedTitle);
    }

    @Test

    public void shouldChangeNewsCategory() {
        String newCategory = "День рождения";

        controlPanelSteps.creatingTestNews(titleNewsWithModifiedCategory, titleNewsWithModifiedCategory);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWithModifiedCategory)))
                )).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedCategory).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsWithModifiedCategory);
        controlPanelSteps.selectANewsCategoryFromTheList(ControlPanelElements.categoryBirthday);

        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsWithModifiedCategory))))).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedCategory).perform(click());
        controlPanelElements.newsItemCategoryField.check(matches(withText(newCategory)));

        pressBack();

        //controlPanelSteps.deleteItemNews(titleNewsWithModifiedCategory);
    }

    @Test

    public void shouldOpenNewsDescription() {

        controlPanelSteps.creatingTestNews(titleNewsForOpenDescription, titleNewsForOpenDescription);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsForOpenDescription)))
                )).check(matches(isDisplayed()));

        controlPanelSteps.getItemNewsButViewElement(titleNewsForOpenDescription).perform(click());
        controlPanelSteps.getItemNewsDescriptionElement(titleNewsForOpenDescription).check(matches(isDisplayed()));

        //controlPanelSteps.deleteItemNews(titleNewsForOpenDescription);
    }

    @Test

    public void shouldDeleteNewsItem() {

        controlPanelSteps.creatingTestNews(titleNewsToDelete, titleNewsToDelete);
        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsToDelete)))
                )).check(matches(isDisplayed()));

        controlPanelSteps.deleteItemNews(titleNewsToDelete);

        controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(titleNewsToDelete)))
                )).check(doesNotExist());
    }

}
