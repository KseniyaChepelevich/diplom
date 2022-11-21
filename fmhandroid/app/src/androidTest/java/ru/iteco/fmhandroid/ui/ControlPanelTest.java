package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;
import io.qameta.allure.kotlin.junit4.DisplayName;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.Calendar;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
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

public class ControlPanelTest extends BaseTest{

    private UiDevice device;
    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static NewsPageSteps newsPageSteps = new NewsPageSteps();
    private static FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    Calendar date = Calendar.getInstance();

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

    @Test
    @DisplayName("Открытие формы создания новости")
    public void shouldOpenCreateNewsForm() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.isCreatingNewsForm();
    }

    @Test
    @DisplayName("Отмена удаления новости во вкладке Панель управления")
    public void shouldNotRemoveTheNewsItem(){
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsWillNotBeDeleted, titleNewsWillNotBeDeleted, 0, 0, 0);
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWillNotBeDeleted).check(matches(isDisplayed()));
        //Нажимаем на кнопку удалить в карточке новости
        controlPanelSteps.getItemNewsDeleteElement(titleNewsWillNotBeDeleted).perform(click());
        //Отображается сообщение об удалении
        controlPanelSteps.getMessageAboutDelete().check(matches(isDisplayed()));
        //Отменяем удаление
        controlPanelSteps.cancelDeleteButtonClick();
        //Проверяем, что наша новость осталась в списке
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWillNotBeDeleted).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleNewsWillNotBeDeleted);
    }

    @Test
    @DisplayName("Открытие Новости для редактирования")
    public void shouldOpenTheNewsForEditing(){
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsWillNotBeEditing, titleNewsWillNotBeEditing, 0, 0, 0);
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWillNotBeEditing).check(matches(isDisplayed()));
        //Нажимаем на кнопку Редактировать в карточке новости
        controlPanelSteps.getItemNewsEditElement(titleNewsWillNotBeEditing).perform(click());
        //Проверяем, что октрылась наша новость
        controlPanelSteps.isCardTestNews(titleNewsWillNotBeEditing);
        pressBack();

        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleNewsWillNotBeEditing);
    }



    @Test
    @DisplayName("Открытие и закрытие Новости для редактирования без внесения изменений")
    public void shouldNotEditTheNews(){
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsWillNotBeEditing, titleNewsWillNotBeEditing, 0, 0, 0);
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWillNotBeEditing).check(matches(isDisplayed()));
        //Нажимаем на кнопку Редактировать в карточке новости
        controlPanelSteps.getItemNewsEditElement(titleNewsWillNotBeEditing).perform(click());
        //Проверяем, что октрылась наща новость
        controlPanelSteps.isCardTestNews(titleNewsWillNotBeEditing);
        //Отменяем редактирование новости
        controlPanelSteps.cancelButtonClick();
        //Отображается сообщение, что изменения не будут сохранены
        controlPanelSteps.getMessageChangesWonTBeSaved().check(matches(isDisplayed()));
        controlPanelSteps.okButtonClick();
        //Проверяем, что наша новость есть в списке
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWillNotBeEditing).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleNewsWillNotBeEditing);
    }

    @Test
    @DisplayName("Открытие и сохранение Новости для редактирования без внесения изменений")
    public void shouldKeepTheNewsUnchanged(){
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsSavedWithoutChanges, titleNewsSavedWithoutChanges, 0, 0, 0);
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsSavedWithoutChanges).check(matches(isDisplayed()));
        //Открываем новость для редактирования
        controlPanelSteps.getItemNewsEditElement(titleNewsSavedWithoutChanges).perform(click());
        //Проверяем, что октрылась наща новость
        controlPanelSteps.isCardTestNews(titleNewsSavedWithoutChanges);
        //Сохраняем новость без изменений
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем, что наша новость есть в списке
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsSavedWithoutChanges).check(matches(isDisplayed()));
        //Удаляем новость
        controlPanelSteps.deleteItemNews(titleNewsSavedWithoutChanges);
    }

    @Test
    @DisplayName("Выключение Активного статуса у Новости")
    public void shouldTurnOffActiveStatus(){
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsTurnOffActiveStatus, titleNewsTurnOffActiveStatus, 0, 0, 0);
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsTurnOffActiveStatus).check(matches(isDisplayed()));
        //Открываем новость для редактирования
        controlPanelSteps.getItemNewsEditElement(titleNewsTurnOffActiveStatus).perform(click());
        //Проверяем, что октрылась наща новость
        controlPanelSteps.isCardTestNews(titleNewsTurnOffActiveStatus);
        //Переключаем статус новости из Active в NotActive
        controlPanelSteps.switchNewsStatus();
        controlPanelSteps.getSwitcherNoteActive().check(matches(isDisplayed()));
        //Сохраняем
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем, что наша новость отображается в списке
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsTurnOffActiveStatus).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleNewsTurnOffActiveStatus);
    }

    @Test
    @DisplayName("Редактирование даты публикации")
    public void shouldChangeThePublicationDate() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String monthExpected = TestUtils.getDateToString(month);
        String dayExpected = TestUtils.getDateToString(day+1);
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsWithModifiedPublicationDate, titleNewsWithModifiedPublicationDate, 0, 0, 0);
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWithModifiedPublicationDate).check(matches(isDisplayed()));
        //Открываем новость для редактирования
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedPublicationDate).perform(click());
        //Проверяем, что октрылась наща новость
        controlPanelSteps.isCardTestNews(titleNewsWithModifiedPublicationDate);
       //Редактируем дату публикации
        controlPanelSteps.setDateToDatePicker(year, month, day +1);
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата публикации отображается новая дата
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dayExpected + "." + monthExpected + "." + year)));
        //Сохраняем изменения
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем, что наша новость есть в списке
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWithModifiedPublicationDate).check(matches(isDisplayed()));
        //Удаляем новость
        controlPanelSteps.deleteItemNews(titleNewsWithModifiedPublicationDate);
    }

    @Test
    @DisplayName("Редактирование описания Новости")
    public void shouldChangeTheDescription() {
        String newDescription = titleNewsWithModifiedDescription + " проверка";
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsWithModifiedDescription, titleNewsWithModifiedDescription, 0, 0, 0);
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWithModifiedDescription).check(matches(isDisplayed()));
        //Открываем новость для редактирования
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedDescription).perform(click());
        //Проверяем, что октрылась наща новость
        controlPanelSteps.isCardTestNews(titleNewsWithModifiedDescription);
        //Редактируем описание новости
        controlPanelSteps.getNewsItemDescription().perform(replaceText(titleNewsWithModifiedDescription + " проверка"));
        //Сохраняем изменения
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем, что наша новость есть в списке, что она имеет новое описание
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWithModifiedDescription).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsButViewElement(titleNewsWithModifiedDescription).perform(click());
        controlPanelSteps.getItemNewsDescriptionElement(newDescription).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleNewsWithModifiedDescription);
    }

    @Test
    @DisplayName("Редактирование времени публикации Новости")
    public void shouldChangeThePublicationTime() {
        int minutes = date.get(Calendar.MINUTE);
        int hour = date.get(Calendar.HOUR_OF_DAY)+1;
        String hourExpected = TestUtils.getDateToString(hour);
        String minutesExpected = TestUtils.getDateToString(minutes);


        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsWithModifiedPublicationTime, titleNewsWithModifiedPublicationTime, 0, 0, 0);
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWithModifiedPublicationTime).check(matches(isDisplayed()));
        //Открываем ее для редактирования
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedPublicationTime).perform(click());
        //Проверяем, что открылась наша новость
        controlPanelSteps.isCardTestNews(titleNewsWithModifiedPublicationTime);
        //Редактируем время публикации
        controlPanelSteps.setTimeToTimeField(hour, minutes);
        //Сохраняем изменения
        controlPanelSteps.saveNewsButtonClick();

        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWithModifiedPublicationTime).check(matches(isDisplayed()));
        //Открываем новость для проверки сохранилось ли измененное время публикации
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedPublicationTime).perform(click());
        controlPanelSteps.getNewsItemPublishTime().check(matches(withText(hourExpected + ":" + minutesExpected)));

        pressBack();
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleNewsWithModifiedPublicationTime);
    }

    @Test
    @DisplayName("Редактирование заголовка Новости")
    public void shouldChangeNewsTitle() {
        String newTitle = titleNewsWithModifiedTitle + " проверка";
        //Создаем новость
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsWithModifiedTitle, titleNewsWithModifiedTitle, 0, 0, 0);
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWithModifiedTitle).check(matches(isDisplayed()));
        //Открываем ее для редактирования
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedTitle).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsWithModifiedTitle);
        //Редактируем заголовок
        controlPanelSteps.getNewsItemTitle().perform(replaceText(titleNewsWithModifiedTitle + " проверка"));
        //Сохраняем измененную новость
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем что наша новость с новым заголовком отображается в списке
        controlPanelSteps.scrollToElementInRecyclerList(newTitle).check(matches(isDisplayed()));
        //Удаляем нашу новость
        controlPanelSteps.deleteItemNews(titleNewsWithModifiedTitle);
    }

    //Тест работает нестабильно. В режиме дебага все проходит. Падает при попытке сменить категорию
    @Test
    @DisplayName("Редактирование Категории Новости")
    public void shouldChangeNewsCategory() {
        String newCategory = "День рождения";
        //Создаем новость
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsWithModifiedCategory, titleNewsWithModifiedCategory, 0, 0, 0);
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWithModifiedCategory).check(matches(isDisplayed()));
        //Открываем ее для редактирования
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedCategory).perform(click());
        controlPanelSteps.isCardTestNews(titleNewsWithModifiedCategory);
        //SystemClock.sleep(5000);
        //Меняем категорию
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryBirthday);
        //Сохраняем изменения
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем что наша новость с новой категорией есть в списке
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsWithModifiedCategory).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(titleNewsWithModifiedCategory).perform(click());
        controlPanelSteps.getNewsItemCategory().check(matches(withText(newCategory)));

        pressBack();
        //Удаляем новость
        controlPanelSteps.deleteItemNews(titleNewsWithModifiedCategory);
    }

    @Test
    @DisplayName("Просмотр описания новости из вкладки Панель управления раздела Новости")
    public void shouldOpenNewsDescription() {
        //Создаем новость
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsForOpenDescription, titleNewsForOpenDescription, 0, 0, 0);
        //Находим нашу новость в списке
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsForOpenDescription).check(matches(isDisplayed()));
        //Разворачиваем карточку новости и проверяем, что отображается описание
        controlPanelSteps.getItemNewsButViewElement(titleNewsForOpenDescription).perform(click());
        controlPanelSteps.getItemNewsDescriptionElement(titleNewsForOpenDescription).check(matches(isDisplayed()));
        //Удаляем новость
        controlPanelSteps.deleteItemNews(titleNewsForOpenDescription);
    }

    @Test
    @DisplayName("Удаление новости во вкладке Панель управления")
    public void shouldDeleteNewsItem() {
        //Создаем новость
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, titleNewsToDelete, titleNewsToDelete, 0, 0, 0);
        //Находим новость в списке
        controlPanelSteps.scrollToElementInRecyclerList(titleNewsToDelete).check(matches(isDisplayed()));
        //Удаляем новость
        controlPanelSteps.deleteItemNews(titleNewsToDelete);
        //Проверяем, что новость удалилась
        controlPanelSteps.getNewsRecyclerList().check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(titleNewsToDelete))));


    }

    @Test
    @DisplayName("Открытие фильтра новостей по кнопке Filter во вкладке Control panel")
    public void shouldOpenTheNewsFilterSettingsForm() {
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsFormControlPanel();
    }

    @Test
    @DisplayName("Разрыв соединения в разделе Control panel")
    public void shouldShowDialogWindowSomethingWrong() throws UiObjectNotFoundException {
        //Включаем режим В самолете
        device.openQuickSettings();
        device.findObject(new UiSelector().description("Airplane mode")).click();
        device.pressBack();
        device.pressBack();
        //Нажимаем кнопку добавить новость
        controlPanelSteps.openCreatingNewsForm();
        //Проверяем, что отображается сообщение
        controlPanelSteps.isDialogWindowMessageTryAgainLatter();
        //Отключаем режим в самолете
        device.openQuickSettings();
        device.findObject(new UiSelector().description("Airplane mode")).click();
    }

}
