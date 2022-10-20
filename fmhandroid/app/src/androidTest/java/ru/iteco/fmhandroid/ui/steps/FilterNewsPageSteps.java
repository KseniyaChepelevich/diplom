package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import static org.hamcrest.Matchers.not;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;

import java.util.Calendar;

import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.FilterNewsPageElements;
import ru.iteco.fmhandroid.ui.page.NewsPageElements;

public class FilterNewsPageSteps {
    FilterNewsPageElements filterNewsPageElements = new FilterNewsPageElements();
    NewsPageElements newsPageElements = new NewsPageElements();
    Calendar date = Calendar.getInstance();
    //date.setTime(measurement.getDateTime());


    public void isFilterNewsForm() {
        filterNewsPageElements.filterNewsTitleTextView.check(matches(isDisplayed()));
        filterNewsPageElements.newsItemCategoryField.check(matches(isDisplayed()));
        filterNewsPageElements.newsItemPublishDateStartField.check(matches(isDisplayed()));
        filterNewsPageElements.newsItemPublishDateEndField.check(matches(isDisplayed()));
        filterNewsPageElements.filterBut.check(matches(isDisplayed()));
        filterNewsPageElements.cancelBut.check(matches(isDisplayed()));
    }

    public void isFilterNewsFormControlPanel() {
        filterNewsPageElements.filterNewsTitleTextView.check(matches(isDisplayed()));
        filterNewsPageElements.newsItemCategoryField.check(matches(isDisplayed()));
        filterNewsPageElements.newsItemPublishDateStartField.check(matches(isDisplayed()));
        filterNewsPageElements.newsItemPublishDateEndField.check(matches(isDisplayed()));
        filterNewsPageElements.filterNewsActiveCheckBox.check(matches(isDisplayed()));
        filterNewsPageElements.filterNewsInactiveCheckBox.check(matches(isDisplayed()));
        filterNewsPageElements.filterBut.check(matches(isDisplayed()));
        filterNewsPageElements.cancelBut.check(matches(isDisplayed()));
    }

    public void fillingOutTheFilterNewsForm(ViewInteraction nameCategory, int plusYearStart, int plusMonthStart, int plusDayStart, int plusYearEnd, int plusMonthEnd, int plusDayEnd) {
        selectANewsCategoryFromTheList(nameCategory);

        setDateToDatePicker(filterNewsPageElements.newsItemPublishDateStartField, plusYearStart, plusMonthStart, plusDayStart);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.okBut);

        setDateToDatePicker(filterNewsPageElements.newsItemPublishDateEndField, plusYearEnd, plusMonthEnd, plusDayEnd);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.okBut);

        //DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.filterBut);
    }

    public void selectANewsCategoryFromTheList(ViewInteraction nameCategory) {
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.newsItemCategoryField);
        Espresso.closeSoftKeyboard();
        DataHelper.EspressoBaseTest.clickButton(nameCategory);
    }

    public void setDateToDatePicker(ViewInteraction nameDtePicker, int plusYear, int plusMonth, int plusDay) {
        DataHelper.EspressoBaseTest.clickButton(nameDtePicker);
        filterNewsPageElements.datePicker.check(matches(isDisplayed()));
        filterNewsPageElements.datePicker.perform(setDate(date.get(Calendar.YEAR)+plusYear, date.get(Calendar.MONTH)+1+plusMonth, date.get(Calendar.DAY_OF_MONTH)+plusDay));
        //DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
    }

    public void replaceTextNewsCategory(String nameCategory) {
        filterNewsPageElements.newsItemCategoryField.perform(replaceText(nameCategory));
    }

    public void checkToast(String text, boolean visible) {
        if (visible) {
            filterNewsPageElements.wrongСategoryToast(text).check(matches(isDisplayed()));
        } else {
            filterNewsPageElements.wrongСategoryToast(text).check(matches(not(isDisplayed())));
        }
    }

    public void isEmptyNewsList() {
        newsPageElements.emptyNewsListImage.check(matches(isDisplayed()));
        newsPageElements.emptyNewsListText.check(matches(isDisplayed()));
        newsPageElements.newsRetryButton.check(matches(isDisplayed()));

    }

    public void isMessageWrongPeriod() {
        filterNewsPageElements.messageTextWrongPeriod.check(matches(isDisplayed()));
        filterNewsPageElements.okBut.check(matches(isDisplayed()));
    }
}
