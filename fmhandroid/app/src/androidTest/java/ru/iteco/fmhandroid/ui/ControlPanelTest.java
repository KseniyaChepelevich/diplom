package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.RemoteException;

import io.qameta.allure.kotlin.junit4.DisplayName;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class ControlPanelTest extends BaseTest {

    private UiDevice device;
    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static NewsPageSteps newsPageSteps = new NewsPageSteps();
    private static FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();


    LocalDateTime today = LocalDateTime.now();

    @Before
    public void logoutCheckAndOpenControlPanelPage() throws RemoteException, UiObjectNotFoundException {
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
        newsPageSteps.openControlPanel();
    }

    @After
    public void disableAirplaneMode() throws RemoteException, UiObjectNotFoundException {
        device.setOrientationNatural();
        TestUtils.disableAirplaneMode();
    }

    @Test
    @DisplayName("???????????????? ?????????? ???????????????? ??????????????")
    public void shouldOpenCreateNewsForm() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.isCreatingNewsForm();
    }

    @Test
    @DisplayName("???????????? ???????????????? ?????????????? ???? ?????????????? ???????????? ????????????????????")
    public void shouldNotRemoveTheNewsItem() {
        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(announcementNews);
        //?????????????? ???????? ??????????????
        controlPanelSteps.scrollToElementInRecyclerList(announcementNews.getNewsName()).check(matches(isDisplayed()));
        //???????????????? ???? ???????????? ?????????????? ?? ???????????????? ??????????????
        controlPanelSteps.getItemNewsDeleteElement(announcementNews.getNewsName()).perform(click());
        //???????????????????????? ?????????????????? ???? ????????????????
        controlPanelSteps.getMessageAboutDelete().check(matches(isDisplayed()));
        //???????????????? ????????????????
        controlPanelSteps.cancelDeleteButtonClick();
        //??????????????????, ?????? ???????? ?????????????? ???????????????? ?? ????????????
        controlPanelSteps.checkNewsIsPresent(announcementNews);
    }

    @Test
    @DisplayName("???????????????? ?????????????? ?????? ????????????????????????????")
    public void shouldOpenTheNewsForEditing() {
        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(announcementNews);

        //???????????????? ???? ???????????? ?????????????????????????? ?? ???????????????? ??????????????
        controlPanelSteps.openNewsCard(announcementNews);
        controlPanelSteps.isCardTestNews(announcementNews.getNewsName(), announcementNews.getNewsDescription());
        pressBack();
    }

    @Test
    @DisplayName("???????????????? ?? ???????????????? ?????????????? ?????? ???????????????????????????? ?????? ???????????????? ??????????????????")
    public void shouldNotEditTheNews() {
        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(announcementNews);
        //???????????????? ???? ???????????? ?????????????????????????? ?? ???????????????? ??????????????
        controlPanelSteps.openNewsCard(announcementNews);
        //??????????????????, ?????? ?????????????????? ???????? ??????????????
        controlPanelSteps.isCardTestNews(announcementNews.getNewsName(), announcementNews.getNewsDescription());
        //???????????????? ???????????????????????????? ??????????????
        controlPanelSteps.cancelButtonClick();
        //???????????????????????? ??????????????????, ?????? ?????????????????? ???? ?????????? ??????????????????
        controlPanelSteps.getMessageChangesWonTBeSaved().check(matches(isDisplayed()));
        controlPanelSteps.okButtonClick();
        //??????????????????, ?????? ???????? ?????????????? ???????? ?? ????????????
        controlPanelSteps.checkNewsIsPresent(announcementNews);
    }

    @Test
    @DisplayName("???????????????? ?? ???????????????????? ?????????????? ?????? ???????????????????????????? ?????? ???????????????? ??????????????????")
    public void shouldKeepTheNewsUnchanged() {
        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(announcementNews);
        //???????????????? ???? ???????????? ?????????????????????????? ?? ???????????????? ??????????????
        controlPanelSteps.openNewsCard(announcementNews);
        //??????????????????, ?????? ?????????????????? ???????? ??????????????
        controlPanelSteps.isCardTestNews(announcementNews.getNewsName(), announcementNews.getNewsDescription());
        //?????????????????? ?????????????? ?????? ??????????????????
        controlPanelSteps.saveButtonClick();
        //??????????????????, ?????? ???????? ?????????????? ???????? ?? ????????????
        controlPanelSteps.checkNewsIsPresent(announcementNews);
    }

    @Test
    @DisplayName("???????????????????? ?????????????????? ?????????????? ?? ??????????????")
    public void shouldTurnOffActiveStatus() {
        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(announcementNews);
        //?????????????????? ?????????????? ?????? ????????????????????????????
        controlPanelSteps.openNewsCard(announcementNews);
        //??????????????????, ?????? ?????????????????? ???????? ??????????????
        controlPanelSteps.isCardTestNews(announcementNews.getNewsName(), announcementNews.getNewsDescription());
        //?????????????????????? ???????????? ?????????????? ???? Active ?? NotActive
        controlPanelSteps.switchNewsStatus();
        controlPanelSteps.getSwitcherNoteActive().check(matches(isDisplayed()));
        //??????????????????
        controlPanelSteps.saveButtonClick();
        //??????????????????, ?????? ???????? ?????????????? ???????????????????????? ?? ????????????
        controlPanelSteps.checkNewsIsPresent(announcementNews);
    }

    @Test
    @DisplayName("???????????????????????????? ???????? ????????????????????")
    public void shouldChangeThePublicationDate() {
        String dateExpected = TestUtils.getDateToString(today.plus(1, ChronoUnit.DAYS));

        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(announcementNews);
        //?????????????????? ?????????????? ?????? ????????????????????????????
        controlPanelSteps.openNewsCard(announcementNews);
        //??????????????????, ?????? ?????????????????? ???????? ??????????????
        controlPanelSteps.isCardTestNews(announcementNews.getNewsName(), announcementNews.getNewsDescription());
        //?????????????????????? ???????? ????????????????????
        controlPanelSteps.setDateToDatePicker(today.plus(1, ChronoUnit.DAYS));
        controlPanelSteps.okButtonClick();
        //??????????????????, ?????? ?? ???????? ???????? ???????????????????? ???????????????????????? ?????????? ????????
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dateExpected)));
        //?????????????????? ??????????????????
        controlPanelSteps.saveButtonClick();
        //??????????????????, ?????? ???????? ?????????????? ???????? ?? ????????????
        controlPanelSteps.checkNewsIsPresent(announcementNews);
    }

    @Test
    @DisplayName("???????????????????????????? ???????????????? ??????????????")
    public void shouldChangeTheDescription() {
        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();
        String newDescription = announcementNews.getNewsDescription() + " ????????????????";

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(announcementNews);
        //?????????????????? ?????????????? ?????? ????????????????????????????
        controlPanelSteps.openNewsCard(announcementNews);
        //??????????????????, ?????? ?????????????????? ???????? ??????????????
        controlPanelSteps.isCardTestNews(announcementNews.getNewsName(), announcementNews.getNewsDescription());
        //?????????????????????? ???????????????? ??????????????
        controlPanelSteps.getNewsItemDescription().perform(replaceText(newDescription));
        //?????????????????? ??????????????????
        controlPanelSteps.saveButtonClick();
        //??????????????????, ?????? ???????? ?????????????? ???????? ?? ????????????
        controlPanelSteps.checkNewsIsPresent(announcementNews);
        //?????????????????????????? ???????????????? ?????????????? ?? ??????????????????, ?????? ?????? ?????????? ?????????? ????????????????
        controlPanelSteps.openNewsCard(announcementNews);
        controlPanelSteps.getNewsItemDescription().check(matches(withText(newDescription)));
    }

    @Test
    @DisplayName("???????????????????????????? ?????????????? ???????????????????? ??????????????")
    public void shouldChangeThePublicationTime() {
        String timeExpected = TestUtils.getTimeToString(today.plus(1, ChronoUnit.HOURS));

        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(announcementNews);

        //?????????????????? ?????????????? ?????? ????????????????????????????
        controlPanelSteps.openNewsCard(announcementNews);
        //??????????????????, ?????? ?????????????????? ???????? ??????????????
        controlPanelSteps.isCardTestNews(announcementNews.getNewsName(), announcementNews.getNewsDescription());
        //?????????????????????? ?????????? ????????????????????
        controlPanelSteps.setTimeToTimeField(today.plus(1, ChronoUnit.HOURS));
        //?????????????????? ??????????????????
        controlPanelSteps.saveButtonClick();

        //?????????????????? ?????????????? ?????? ???????????????? ?????????????????????? ???? ???????????????????? ?????????? ????????????????????
        controlPanelSteps.openNewsCard(announcementNews);
        controlPanelSteps.getNewsItemPublishTime().check(matches(withText(timeExpected)));

        pressBack();
    }

    @Test
    @DisplayName("???????????????????????????? ?????????????????? ??????????????")
    public void shouldChangeNewsTitle() {
        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();

        String newTitle = announcementNews.getNewsName() + " ????????????????";

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(announcementNews);
        //?????????????????? ?????????????? ?????? ????????????????????????????
        controlPanelSteps.openNewsCard(announcementNews);
        controlPanelSteps.isCardTestNews(announcementNews.getNewsName(), announcementNews.getNewsDescription());
        //?????????????????????? ??????????????????
        controlPanelSteps.getNewsItemTitle().perform(replaceText(newTitle));
        //?????????????????? ???????????????????? ??????????????
        controlPanelSteps.saveButtonClick();
        //?????????????????? ?????? ???????? ?????????????? ?? ?????????? ???????????????????? ???????????????????????? ?? ????????????
        controlPanelSteps.scrollToElementInRecyclerList(newTitle).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(newTitle).perform(click());
        controlPanelSteps.getNewsItemTitle().check(matches(withText(newTitle)));
    }

    @Ignore
    //???????? ???????????????? ??????????????????????. ?? ???????????? ???????????? ?????? ????????????????. ???????????? ?????? ?????????????? ?????????????? ??????????????????
    @Test
    @DisplayName("???????????????????????????? ?????????????????? ??????????????")
    public void shouldChangeNewsCategory() {
        DataHelper.CreateNews changeCategoryNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();
        DataHelper.CreateNews newChangeCategoryNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryBirthday()).withDueDate(today).build();

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(changeCategoryNews);
        //?????????????????? ?????????????? ?????? ????????????????????????????
        controlPanelSteps.openNewsCard(changeCategoryNews);
        controlPanelSteps.isCardTestNews(changeCategoryNews.getNewsName(), changeCategoryNews.getNewsDescription());
        //???????????? ??????????????????
        controlPanelSteps.selectANewsCategoryFromTheList(DataHelper.getCategoryBirthday());
        //?????????????????? ??????????????????
        controlPanelSteps.saveButtonClick();
        //?????????????????? ?????? ???????? ?????????????? ?? ?????????? ???????????????????? ???????? ?? ????????????
        controlPanelSteps.checkNewsIsPresent(newChangeCategoryNews);
        controlPanelSteps.openNewsCard(changeCategoryNews);
        controlPanelSteps.getNewsItemCategory().check(matches(withText(DataHelper.getCategoryBirthday())));

        pressBack();
    }

    @Test
    @DisplayName("???????????????? ???????????????? ?????????????? ???? ?????????????? ???????????? ???????????????????? ?????????????? ??????????????")
    public void shouldOpenNewsDescription() {
        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(announcementNews);
        //?????????????? ???????? ??????????????
        controlPanelSteps.checkNewsIsPresent(announcementNews);
        //?????????????????????????? ???????????????? ?????????????? ?? ??????????????????, ?????? ???????????????????????? ????????????????
        controlPanelSteps.openNewsDescription(announcementNews);
        controlPanelSteps.getItemNewsDescriptionElement(announcementNews.getNewsDescription()).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("???????????????? ?????????????? ???? ?????????????? ???????????? ????????????????????")
    public void shouldDeleteNewsItem() {
        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();

        //?????????????? ?????????????? ?????? ??????????
        controlPanelSteps.creatingNews(announcementNews);
        //?????????????? ???????? ??????????????
        controlPanelSteps.checkNewsIsPresent(announcementNews);
        //?????????????? ??????????????
        controlPanelSteps.deleteItemNews(announcementNews.getNewsName());
        //??????????????????, ?????? ?????????????? ??????????????????
        controlPanelSteps.checkNewsDoesNotPresent(announcementNews);
    }

    @Test
    @DisplayName("???????????????? ?????????????? ???????????????? ???? ???????????? Filter ???? ?????????????? Control panel")
    public void shouldOpenTheNewsFilterSettingsForm() {
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsFormControlPanel();
    }

    @Test
    @DisplayName("???????????? ???????????????????? ?? ?????????????? Control panel")
    public void shouldShowDialogWindowSomethingWrong() throws UiObjectNotFoundException {
        DataHelper.CreateNews announcementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();
        //???????????????? ???????????? ???????????????? ??????????????
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.fillingOutTheFormCreatingNewsWithDate(announcementNews);
        //???????????????? ?????????? ?? ????????????????
        device.openQuickSettings();
        device.findObject(new UiSelector().description("Airplane mode")).click();
        device.pressBack();
        device.pressBack();
        //?????????????????? ??????????????
        controlPanelSteps.saveButtonClick();
        //??????????????????, ?????? ???????????????????????? ??????????????????
        controlPanelSteps.isDialogWindowMessageSavingFailed();
        //?????????????????? ?????????? ?? ????????????????
        TestUtils.disableAirplaneMode();
    }

}
