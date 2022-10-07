package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;

import java.util.Calendar;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.ControlPanelElements;
import ru.iteco.fmhandroid.ui.page.NewsPageElements;


public class ControlPanelSteps {
    Calendar date = Calendar.getInstance();

    ControlPanelElements controlPanelElements = new ControlPanelElements();
    NewsPageElements newsPageElements = new NewsPageElements();


    public void isControlPanel() {
        controlPanelElements.contrloPanelHeader.check(matches(isDisplayed()));
        controlPanelElements.addNewsImBut.check(matches(isDisplayed()));
    }

    public void openCreatingNewsForm() {
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.addNewsImBut);
        SystemClock.sleep(3000);
    }

    public void isCreatingNewsForm() {
        controlPanelElements.newsItemCategoryField.check(matches(isDisplayed()));
        controlPanelElements.newsItemTitleField.check(matches(isDisplayed()));
        controlPanelElements.newsItemPublishDateField.check(matches(isDisplayed()));
        controlPanelElements.newsItemPublishTimeField.check(matches(isDisplayed()));
        controlPanelElements.newsItemDescriptionField.check(matches(isDisplayed()));
        controlPanelElements.saveBut.check(matches(isDisplayed()));
        controlPanelElements.cancelBut.check(matches(isDisplayed()));
        controlPanelElements.switcherActive.check(matches(isDisplayed()));
    }

    public void isCardTestNews(String title) {
        controlPanelElements.newsItemCategoryField.check(matches(withText("Объявление")));
        controlPanelElements.newsItemTitleField.check(matches(withText(title)));
        controlPanelElements.newsItemDescriptionField.check(matches(withText(title)));
        controlPanelElements.saveBut.check(matches(isDisplayed()));
        controlPanelElements.cancelBut.check(matches(isDisplayed()));
        controlPanelElements.switcherActive.check(matches(isDisplayed()));

    }

    public void selectANewsCategoryFromTheList(ViewInteraction nameCategory) {
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.newsItemCategoryField);
        Espresso.closeSoftKeyboard();
        DataHelper.EspressoBaseTest.clickButton(nameCategory);
    }
    public void replaceTextNewsCategory(String nameCategory) {
        controlPanelElements.newsItemCategoryField.perform(replaceText(nameCategory));
    }

    public void setDateToDatePicker(int plusYear, int plusMonth, int plusDay) {
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.newsItemPublishDateField);
        controlPanelElements.datePicker.check(matches(isDisplayed()));
        controlPanelElements.datePicker.perform(setDate(date.get(Calendar.YEAR)+plusYear, date.get(Calendar.MONTH)+1+plusMonth, date.get(Calendar.DAY_OF_MONTH)+plusDay));
        //DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
    }

    public void setTimeToTimePicker(int plusHour, int plusMinute) {
        controlPanelElements.timePicker.check(matches(isDisplayed()));
        controlPanelElements.timePicker.perform(setTime(date.get(Calendar.HOUR_OF_DAY)+plusHour, date.get(Calendar.MINUTE)+plusMinute));


    }

    public void setTimeToTimeField(int plusHour, int plusMinute) {
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.newsItemPublishTimeField);
        //controlPanelElements.timePicker.check(matches(isDisplayed()));
        //controlPanelElements.timePicker.perform(setTime(date.get(Calendar.HOUR_OF_DAY)+plusHour, date.get(Calendar.MINUTE)+plusMinute));
        //DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
        setTimeToTimePicker(plusHour, plusMinute);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
    }


    public void fillingOutTheFormCreatingNewsWithDateToday(int plusYear, int plusMonth, int plusDay, int plusHour, int plusMinute, String title, String description) {
        SystemClock.sleep(3000);
        controlPanelElements.newsItemTitleField.perform(replaceText(title));
        setDateToDatePicker(plusYear, plusMonth, plusDay);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
        //DataHelper.EspressoBaseTest.clickButton(controlPanelElements.newsItemPublishTimeField);
        //controlPanelElements.timePicker.check(matches(isDisplayed()));
        //controlPanelElements.timePicker.perform(setTime(date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE)));
        //DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
        setTimeToTimeField(plusHour, plusMinute);
        controlPanelElements.newsItemDescriptionField.perform(replaceText(description));

    }

    public ViewInteraction getItemNewsDeleteElement(String title) {
        return onView(allOf(withId(R.id.delete_news_item_image_view), withParent(withParent(allOf(withId(R.id.news_item_material_card_view),withChild(withChild(withText(title))))))));
    }

    public ViewInteraction getItemNewsEditElement(String title) {
        return onView(allOf(withId(R.id.edit_news_item_image_view), withParent(withParent(allOf(withId(R.id.news_item_material_card_view), withChild(withChild(withText(title))))))));
    }

    public void deleteItemNews (String description) {
        getItemNewsDeleteElement(description).perform(click());
        controlPanelElements.okBut.perform(click());
    }


    public void checkToast(String text, boolean visible) {
        if (visible) {
            controlPanelElements.wrongСategoryToast(text).check(matches(isDisplayed()));
        } else {
            controlPanelElements.wrongСategoryToast(text).check(matches(not(isDisplayed())));
        }
    }

    public void isWrongEmptyFormNews() {
        controlPanelElements.categoryTextInputStartIcon.check(matches(isDisplayed()));
        controlPanelElements.titleTextInputEndIcon.check(matches(isDisplayed()));
        controlPanelElements.createDateTextInputEndIcon.check(matches(isDisplayed()));
        controlPanelElements.createTimeInputEndIcon.check(matches(isDisplayed()));
        controlPanelElements.descriptionTextInputEndIcon.check(matches(isDisplayed()));
    }

    public void creatingTestNews(String title, String description) {
        openCreatingNewsForm();
        selectANewsCategoryFromTheList(controlPanelElements.categoryAnnouncement);
        fillingOutTheFormCreatingNewsWithDateToday(0, 0, 0, 0, 0, title, description);
        DataHelper.EspressoBaseTest.clickButton(controlPanelElements.saveBut);
        SystemClock.sleep(3000);
    }

    /*public boolean NoNewsCheck(String title) {
        if (controlPanelElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(title))
                )) == null) {
            return true;
        }
        return false;
    }*/


}
