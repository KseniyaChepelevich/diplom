package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.os.SystemClock;

import java.util.Calendar;

import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.FilterNewsPageElements;

public class FilterNewsPageSteps {
    FilterNewsPageElements filterNewsPageElements = new FilterNewsPageElements();
    Calendar date = Calendar.getInstance();
    //date.setTime(measurement.getDateTime());


    public void isFilterNewsForm() {
        filterNewsPageElements.filterNewsTitleTextView.check(matches(isDisplayed()));
        filterNewsPageElements.newsItemCategoryField.check(matches(isDisplayed()));
        filterNewsPageElements.newsItemPublishDateStartField.check(matches(isDisplayed()));
        filterNewsPageElements.newsItemPublishDateEndField.check(matches(isDisplayed()));
        filterNewsPageElements.filterBut.check(matches(isDisplayed()));
        filterNewsPageElements.canselBut.check(matches(isDisplayed()));
    }

    public void fillingOutTheFilterNewsForm() {
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.newsItemCategoryField);
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.categoryAnnouncement);
        SystemClock.sleep(3000);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.newsItemPublishDateStartField);
        filterNewsPageElements.datePicker.check(matches(isDisplayed()));
        filterNewsPageElements.datePicker.perform(setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)));
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.okBut);
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.newsItemPublishDateEndField);
        filterNewsPageElements.datePicker.check(matches(isDisplayed()));
        filterNewsPageElements.datePicker.perform(setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH)+1, date.get(Calendar.DAY_OF_MONTH)));
        DataHelper.EspressoBaseTest.clickButton(filterNewsPageElements.okBut);


    }
}
