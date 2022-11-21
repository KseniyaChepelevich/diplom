package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import java.security.PublicKey;
import java.util.Calendar;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;


public class ControlPanelSteps {
    Calendar date = Calendar.getInstance();

    public Matcher<View> addNewsImBut = withId(R.id.add_news_image_view);
    public Matcher<View> filterNewsBut = withId(R.id.filter_news_material_button);
    //Форма создания новости
    public Matcher<View> newsItemCategoryField = withId(R.id.news_item_category_text_auto_complete_text_view);
    public Matcher<View> newsItemTitleField = withId(R.id.news_item_title_text_input_edit_text);
    public Matcher<View> newsItemPublishDateField = withId(R.id.news_item_publish_date_text_input_edit_text);
    public Matcher<View> newsItemPublishTimeField = withId(R.id.news_item_publish_time_text_input_edit_text);
    public Matcher<View> newsItemDescriptionField = withId(R.id.news_item_description_text_input_edit_text);
    public Matcher<View> switcherActive = withId(R.id.switcher);
    public Matcher<View> switcherNotActive = withText("Not active");
    public Matcher<View> saveBut = withId(R.id.save_button);
    public Matcher<View> cancelBut = withId(R.id.cancel_button);
    //Категории новостей
    public ViewInteraction categoryAnnouncement = onView(withText("Объявление")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryBirthday = onView(withText("День рождения")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categorySalary = onView(withText("Зарплата")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryTradeUnion = onView(withText("Профсоюз")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryMassage = onView(withText("Массаж")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryGratitude = onView(withText("Благодарность")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryNeedHelp = onView(withText("Нужна помощь")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryHoliday = onView(withText("Праздник")).inRoot((RootMatchers.isPlatformPopup()));
    //Календарь и часы
    public Matcher<View> datePicker = isAssignableFrom(DatePicker.class);
    public Matcher<View> okBut = withId(android.R.id.button1);
    public Matcher<View> cancelDeleteBut = withId(android.R.id.button2);
    public Matcher<View> timePicker = isAssignableFrom(TimePicker.class);
    public Matcher<View> timePickerToggleMode = withContentDescription("Switch to text input mode for the time input.");
    public Matcher<View> inputHour = Matchers.allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
            TestUtils.childAtPosition(
                    Matchers.allOf(withClassName(is("android.widget.RelativeLayout")),
                            TestUtils.childAtPosition(
                                    withClassName(is("android.widget.TextInputTimePickerView")),
                                    1)),
                    0));
    public Matcher<View> inputMinute = Matchers.allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
            TestUtils.childAtPosition(
                    Matchers.allOf(withClassName(is("android.widget.RelativeLayout")),
                            TestUtils.childAtPosition(
                                    withClassName(is("android.widget.TextInputTimePickerView")),
                                    1)),
                    3));

    public Matcher<View> messageAboutDelete = withText("Are you sure you want to permanently delete the document? These changes cannot be reversed in the future.");
    public Matcher<View> newsRecyclerList = withId(R.id.news_list_recycler_view);
    public Matcher<View> categoryTextInputStartIcon = allOf(withId(R.id.text_input_start_icon), withParent(withParent(withParent(withId(R.id.news_item_category_text_input_layout)))));
    public Matcher<View> titleTextInputEndIcon = allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_title_text_input_layout))))));
    public Matcher<View> createDateTextInputEndIcon = allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_create_date_text_input_layout))))));
    public Matcher<View> createTimeInputEndIcon = allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_publish_time_text_input_layout))))));
    public Matcher<View> descriptionTextInputEndIcon = allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_description_text_input_layout))))));
    public Matcher<View> messageChangesWonTBeSaved = withText("The changes won't be saved, do you really want to log out?");

    public int newsItemTitleTextView=(R.id.news_item_title_text_view);

    public ViewInteraction wrongСategoryToast(String text) {
        return onView(withText(text)).inRoot(new DataHelper.ToastMatcher());
    }

    public ViewInteraction getCategory(String text) {
        return onView(withText("text")).inRoot((RootMatchers.isPlatformPopup()));
    }

    public void isControlPanel() {
        TestUtils.waitView(withText("Control panel")).check(matches(isDisplayed()));
        TestUtils.waitView(addNewsImBut).check(matches(isDisplayed()));
    }

    public void isCreatingNewsForm() {
        TestUtils.waitView(newsItemCategoryField).check(matches(isDisplayed()));
        TestUtils.waitView(newsItemTitleField).check(matches(isDisplayed()));
        TestUtils.waitView(newsItemPublishDateField).check(matches(isDisplayed()));
        TestUtils.waitView(newsItemPublishTimeField).check(matches(isDisplayed()));
        TestUtils.waitView(newsItemDescriptionField).check(matches(isDisplayed()));
        TestUtils.waitView(saveBut).check(matches(isDisplayed()));
        TestUtils.waitView(cancelBut).check(matches(isDisplayed()));
        TestUtils.waitView(switcherActive).check(matches(isDisplayed()));
    }

    public void isCardTestNews(String title) {
        TestUtils.waitView(newsItemCategoryField).check(matches(withText("Объявление")));
        TestUtils.waitView(newsItemTitleField).check(matches(withText(title)));
        TestUtils.waitView(newsItemDescriptionField).check(matches(withText(title)));
        TestUtils.waitView(saveBut).check(matches(isDisplayed()));
        TestUtils.waitView(cancelBut).check(matches(isDisplayed()));
        TestUtils.waitView(switcherActive).check(matches(isDisplayed()));
    }

    public void selectANewsCategoryFromTheList(ViewInteraction nameCategory) {
        TestUtils.waitView(newsItemCategoryField).perform(click());
        Espresso.closeSoftKeyboard();
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(nameCategory);
    }



    public void selectANewsCategoryFromTheList1(Matcher<View> nameCategory) {
        TestUtils.waitView(newsItemCategoryField).perform(click());
        Espresso.closeSoftKeyboard();
        TestUtils.waitPopupView(nameCategory).perform(click());
    }

    public void setDateToDatePicker(int year, int month, int day) {
        TestUtils.waitView(newsItemPublishDateField).perform(click());
        TestUtils.waitView(datePicker).check(matches(isDisplayed()));
        TestUtils.waitView(datePicker).perform(setDate(year, month, day));
    }

    public void setTimeToTimePicker(int hour, int minute) {
        TestUtils.waitView(timePicker).check(matches(isDisplayed()));
        TestUtils.waitView(timePicker).perform(setTime(hour, minute));
    }

    public void setTimeToTimePickerFromTheKeyboard(String hour, String minutes) {
        TestUtils.waitView(timePicker).check(matches(isDisplayed()));
        TestUtils.waitView(timePickerToggleMode).perform(click());
        TestUtils.waitView(inputHour).check(matches(isDisplayed())).perform(replaceText(hour));
        TestUtils.waitView(inputMinute).check(matches(isDisplayed())).perform(replaceText(minutes));
        okButtonClick();
    }

    public void openNewsTimePicker() {
        TestUtils.waitView(newsItemPublishTimeField).perform(click());
    }

    public void setTimeToTimeField(int hour, int minute) {
        TestUtils.waitView(newsItemPublishTimeField).perform(click());
        setTimeToTimePicker(hour, minute);
        okButtonClick();
    }

    public void fillingOutTheFormCreatingNewsWithDateToday(int year, int month, int day, String title, String description) {
        TestUtils.waitView(newsItemTitleField).perform(replaceText(title));
        setDateToDatePicker(year, month, day);
        TestUtils.waitView(okBut).perform(click());
        setTimeToTimeField(date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
        TestUtils.waitView(newsItemDescriptionField).perform(replaceText(description));
    }

    public void replaceNewsTitleText(String title) {
        TestUtils.waitView(newsItemTitleField).perform(replaceText(title));
    }

    public void replaceNewsDescriptionText(String description) {
        TestUtils.waitView(newsItemDescriptionField).perform(replaceText(description));
    }

    public ViewInteraction getItemNewsDeleteElement(String title) {
        return TestUtils.waitView(allOf(withId(R.id.delete_news_item_image_view), withParent(withParent(allOf(withId(R.id.news_item_material_card_view), withChild(withChild(withText(title))))))));
    }

    public ViewInteraction getItemNewsEditElement(String title) {
        return onView(allOf(withId(R.id.edit_news_item_image_view), withParent(withParent(allOf(withId(R.id.news_item_material_card_view), withChild(withChild(withText(title))))))));
    }

    public ViewInteraction getItemNewsPublicationDateElement(String title) {
        return (onView(allOf(withId(R.id.news_item_publication_date_text_view), withParent(withParent(allOf(withId(R.id.news_item_material_card_view), withChild(withChild(withText(title)))))))));
    }

    public ViewInteraction getItemNewsButViewElement(String title) {
        return onView(allOf(withId(R.id.view_news_item_image_view), withParent(withParent(allOf(withId(R.id.news_item_material_card_view), withChild(withChild(withText(title))))))));
    }

    public ViewInteraction getItemNewsStatusElement(String title) {
        return onView(allOf(withId(R.id.news_item_published_text_view), withParent(withParent(allOf(withId(R.id.news_item_material_card_view), withChild(withChild(withText(title))))))));
    }

    public ViewInteraction getItemNewsDescriptionElement(String title) {
        return onView(allOf(withId(R.id.news_item_description_text_view), withParent(withParent(allOf(withId(R.id.news_item_material_card_view), withChild(withChild(withText(title))))))));
    }

    public ViewInteraction getItemNewsTitleElement(String title) {
        return onView(allOf(withId(R.id.news_item_title_text_view), withParent(withParent(allOf(withId(R.id.news_item_material_card_view), withChild(withChild(withText(title))))))));
    }

    public ViewInteraction getItemNewsCard(String title) {
        return onView(allOf(withId(R.id.news_item_material_card_view), withChild(withChild(withText(title)))));

    }

    public void deleteItemNews(String description) {
        scrollToElementInRecyclerList(description);
        getItemNewsDeleteElement(description).check(matches(isDisplayed())).perform(click());
        TestUtils.waitView(messageAboutDelete).check(matches(isDisplayed()));
        TestUtils.waitView(okBut).perform(click());
    }

    public ViewInteraction scrollToElementInRecyclerList(String description) {
        return TestUtils.waitView(newsRecyclerList).check(matches(isDisplayed()))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(description)))));
    }


    public void checkToast(String text, boolean visible) {
        if (visible) {
            wrongСategoryToast(text).check(matches(isDisplayed()));
        } else {
            wrongСategoryToast(text).check(matches(not(isDisplayed())));
        }
    }

    public void isWrongEmptyFormNews() {
        TestUtils.waitView(categoryTextInputStartIcon).check(matches(isDisplayed()));
        TestUtils.waitView(titleTextInputEndIcon).check(matches(isDisplayed()));
        TestUtils.waitView(createDateTextInputEndIcon).check(matches(isDisplayed()));
        TestUtils.waitView(createTimeInputEndIcon).check(matches(isDisplayed()));
        TestUtils.waitView(descriptionTextInputEndIcon).check(matches(isDisplayed()));
    }

    public void creatingTestNews(ViewInteraction newsItemCategory, String title, String description, int plusYear, int plusMonth, int plusDay) {
        TestUtils.waitView(addNewsImBut).perform(click());
        selectANewsCategoryFromTheList(newsItemCategory);
        fillingOutTheFormCreatingNewsWithDateToday(date.get(Calendar.YEAR) + plusYear, date.get(Calendar.MONTH) + 1 + plusMonth, date.get(Calendar.DAY_OF_MONTH) + plusDay, title, description);
        TestUtils.waitView(saveBut).perform(click());
    }

    public void creatingTestNews1(Matcher<View> newsItemCategory, String title, String description, int plusYear, int plusMonth, int plusDay) {
        openCreatingNewsForm();
        selectANewsCategoryFromTheList1(newsItemCategory);
        fillingOutTheFormCreatingNewsWithDateToday(date.get(Calendar.YEAR) + plusYear, date.get(Calendar.MONTH) + 1 + plusMonth, date.get(Calendar.DAY_OF_MONTH) + plusDay, title, description);
        TestUtils.waitView(saveBut).perform(click());
    }

    public void isDialogWindowMessageTryAgainLatter() {
        TestUtils.waitView(withText("Something went wrong. Try again later.")).check(matches(isDisplayed()));
        TestUtils.waitView(okBut).check(matches(isDisplayed()));
    }

    public void openCreatingNewsForm(){
        TestUtils.waitView(addNewsImBut).perform(click());
    }

    public ViewInteraction getNewsItemTitle() {
        return TestUtils.waitView(newsItemTitleField);
    }

    public void saveNewsButtonClick() {
        TestUtils.waitView(saveBut).perform(click());
    }

    public void cancelButtonClick() {
        TestUtils.waitView(cancelBut).perform(click());
    }

    public ViewInteraction getMessageChangesWonTBeSaved() {
        return TestUtils.waitView(messageChangesWonTBeSaved);
    }

    public void okButtonClick() {
        TestUtils.waitView(okBut).perform(click());
    }

    public void cancelDeleteButtonClick() {
        TestUtils.waitView(cancelDeleteBut).perform(click());
    }

    public ViewInteraction getNewsRecyclerList() {
        return TestUtils.waitView(newsRecyclerList);
    }

    public void replaceNewsCategoryText(String category) {
        TestUtils.waitView(newsItemCategoryField).perform(replaceText(category));
    }

    public void switchNewsStatus() {
        TestUtils.waitView(switcherActive).perform(click());
    }

    public ViewInteraction getSwitcherNoteActive() {
        return TestUtils.waitView(switcherNotActive);
    }

    public ViewInteraction getNewsItemPublishDate () {
        return TestUtils.waitView(newsItemPublishDateField);
    }

    public ViewInteraction getNewsItemPublishTime() {
        return TestUtils.waitView(newsItemPublishTimeField);
    }

    public ViewInteraction getNewsItemCategory() {
        return TestUtils.waitView(newsItemCategoryField);
    }

    public ViewInteraction getNewsItemDescription() {
        return TestUtils.waitView(newsItemDescriptionField);
    }

    public ViewInteraction getMessageAboutDelete() {
        return TestUtils.waitView(messageAboutDelete);
    }


}
