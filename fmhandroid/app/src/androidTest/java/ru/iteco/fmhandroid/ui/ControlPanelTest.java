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

import android.os.RemoteException;

import io.qameta.allure.kotlin.junit4.DisplayName;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.time.LocalDateTime;
import java.util.Calendar;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
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

public class ControlPanelTest extends BaseTest{

    private UiDevice device;
    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static NewsPageSteps newsPageSteps = new NewsPageSteps();
    private static FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();
    private static NamingHelper namingHelper = new NamingHelper();

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
    @DisplayName("Открытие формы создания новости")
    public void shouldOpenCreateNewsForm() {
        controlPanelSteps.openCreatingNewsForm();
        controlPanelSteps.isCreatingNewsForm();
    }

    @Test
    @DisplayName("Отмена удаления новости во вкладке Панель управления")
    public void shouldNotRemoveTheNewsItem(){
        String title = namingHelper.getNewsAnnouncementName();
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Нажимаем на кнопку удалить в карточке новости
        controlPanelSteps.getItemNewsDeleteElement(title).perform(click());
        //Отображается сообщение об удалении
        controlPanelSteps.getMessageAboutDelete().check(matches(isDisplayed()));
        //Отменяем удаление
        controlPanelSteps.cancelDeleteButtonClick();
        //Проверяем, что наша новость осталась в списке
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Открытие Новости для редактирования")
    public void shouldOpenTheNewsForEditing(){
        String title = namingHelper.getNewsAnnouncementName();
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Нажимаем на кнопку Редактировать в карточке новости
        controlPanelSteps.getItemNewsEditElement(title).perform(click());
        //Проверяем, что октрылась наша новость
        controlPanelSteps.isCardTestNews(title);
        pressBack();
    }

    @Test
    @DisplayName("Открытие и закрытие Новости для редактирования без внесения изменений")
    public void shouldNotEditTheNews(){
        String title = namingHelper.getNewsAnnouncementName();
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Нажимаем на кнопку Редактировать в карточке новости
        controlPanelSteps.getItemNewsEditElement(title).perform(click());
        //Проверяем, что октрылась наща новость
        controlPanelSteps.isCardTestNews(title);
        //Отменяем редактирование новости
        controlPanelSteps.cancelButtonClick();
        //Отображается сообщение, что изменения не будут сохранены
        controlPanelSteps.getMessageChangesWonTBeSaved().check(matches(isDisplayed()));
        controlPanelSteps.okButtonClick();
        //Проверяем, что наша новость есть в списке
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Открытие и сохранение Новости для редактирования без внесения изменений")
    public void shouldKeepTheNewsUnchanged(){
        String title = namingHelper.getNewsAnnouncementName();
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Открываем новость для редактирования
        controlPanelSteps.getItemNewsEditElement(title).perform(click());
        //Проверяем, что октрылась наща новость
        controlPanelSteps.isCardTestNews(title);
        //Сохраняем новость без изменений
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем, что наша новость есть в списке
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Выключение Активного статуса у Новости")
    public void shouldTurnOffActiveStatus(){
        String title = namingHelper.getNewsAnnouncementName();
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Открываем новость для редактирования
        controlPanelSteps.getItemNewsEditElement(title).perform(click());
        //Проверяем, что октрылась наща новость
        controlPanelSteps.isCardTestNews(title);
        //Переключаем статус новости из Active в NotActive
        controlPanelSteps.switchNewsStatus();
        controlPanelSteps.getSwitcherNoteActive().check(matches(isDisplayed()));
        //Сохраняем
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем, что наша новость отображается в списке
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Редактирование даты публикации")
    public void shouldChangeThePublicationDate() {
        LocalDateTime date = today.plusDays(1);
        String dateExpected = TestUtils.getDateToString(date);
        String title = namingHelper.getNewsAnnouncementName();

        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Открываем новость для редактирования
        controlPanelSteps.getItemNewsEditElement(title).perform(click());
        //Проверяем, что октрылась наща новость
        controlPanelSteps.isCardTestNews(title);
       //Редактируем дату публикации
        controlPanelSteps.setDateToDatePicker(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        controlPanelSteps.okButtonClick();
        //Проверяем, что в поле Дата публикации отображается новая дата
        controlPanelSteps.getNewsItemPublishDate().check(matches(withText(dateExpected)));
        //Сохраняем изменения
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем, что наша новость есть в списке
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Редактирование описания Новости")
    public void shouldChangeTheDescription() {
        String title = namingHelper.getNewsAnnouncementName();
        String newDescription = title + " проверка";
        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Открываем новость для редактирования
        controlPanelSteps.getItemNewsEditElement(title).perform(click());
        //Проверяем, что октрылась наша новость
        controlPanelSteps.isCardTestNews(title);
        //Редактируем описание новости
        controlPanelSteps.getNewsItemDescription().perform(replaceText(newDescription));
        //Сохраняем изменения
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем, что наша новость есть в списке, что она имеет новое описание
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsButViewElement(title).perform(click());
        controlPanelSteps.getItemNewsDescriptionElement(newDescription).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Редактирование времени публикации Новости")
    public void shouldChangeThePublicationTime() {
        LocalDateTime date = today.plusHours(1);
        String timeExpected = TestUtils.getTimeToString(date);
        String title = namingHelper.getNewsAnnouncementName();

        //Создаем новость для теста
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Открываем ее для редактирования
        controlPanelSteps.getItemNewsEditElement(title).perform(click());
        //Проверяем, что открылась наша новость
        controlPanelSteps.isCardTestNews(title);
        //Редактируем время публикации
        controlPanelSteps.setTimeToTimeField(date.getHour(), date.getMinute());
        //Сохраняем изменения
        controlPanelSteps.saveNewsButtonClick();

        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Открываем новость для проверки сохранилось ли измененное время публикации
        controlPanelSteps.getItemNewsEditElement(title).perform(click());
        controlPanelSteps.getNewsItemPublishTime().check(matches(withText(timeExpected)));

        pressBack();
    }

    @Test
    @DisplayName("Редактирование заголовка Новости")
    public void shouldChangeNewsTitle() {
        String title = namingHelper.getNewsAnnouncementName();
        String newTitle = title + " проверка";
        //Создаем новость
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Открываем ее для редактирования
        controlPanelSteps.getItemNewsEditElement(title).perform(click());
        controlPanelSteps.isCardTestNews(title);
        //Редактируем заголовок
        controlPanelSteps.getNewsItemTitle().perform(replaceText(newTitle));
        //Сохраняем измененную новость
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем что наша новость с новым заголовком отображается в списке
        controlPanelSteps.scrollToElementInRecyclerList(newTitle).check(matches(isDisplayed()));
    }

    //Тест работает нестабильно. В режиме дебага все проходит. Падает при попытке сменить категорию
    @Test
    @DisplayName("Редактирование Категории Новости")
    public void shouldChangeNewsCategory() {
        String title = namingHelper.getNewsAnnouncementName();
        String newCategory = "День рождения";
        //Создаем новость
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим нашу новость
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Открываем ее для редактирования
        controlPanelSteps.getItemNewsEditElement(title).perform(click());
        controlPanelSteps.isCardTestNews(title);
        //Меняем категорию
        controlPanelSteps.selectANewsCategoryFromTheList(controlPanelSteps.categoryBirthday);
        //Сохраняем изменения
        controlPanelSteps.saveNewsButtonClick();
        //Проверяем что наша новость с новой категорией есть в списке
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        controlPanelSteps.getItemNewsEditElement(title).perform(click());
        controlPanelSteps.getNewsItemCategory().check(matches(withText(newCategory)));

        pressBack();
    }

    @Test
    @DisplayName("Просмотр описания новости из вкладки Панель управления раздела Новости")
    public void shouldOpenNewsDescription() {
        String title = namingHelper.getNewsAnnouncementName();
        //Создаем новость
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим нашу новость в списке
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Разворачиваем карточку новости и проверяем, что отображается описание
        controlPanelSteps.getItemNewsButViewElement(title).perform(click());
        controlPanelSteps.getItemNewsDescriptionElement(title).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Удаление новости во вкладке Панель управления")
    public void shouldDeleteNewsItem() {
        String title = namingHelper.getNewsAnnouncementName();
        //Создаем новость
        controlPanelSteps.creatingTestNews(controlPanelSteps.categoryAnnouncement, title, title,
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //Находим новость в списке
        controlPanelSteps.scrollToElementInRecyclerList(title).check(matches(isDisplayed()));
        //Удаляем новость
        controlPanelSteps.deleteItemNews(title);
        //Проверяем, что новость удалилась
        controlPanelSteps.getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(controlPanelSteps.newsItemTitleTextView, withText(title))));
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
        TestUtils.disableAirplaneMode();
    }

}
