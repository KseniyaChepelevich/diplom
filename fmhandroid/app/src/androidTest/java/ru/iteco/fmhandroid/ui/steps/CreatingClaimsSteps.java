package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.AllOf.allOf;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;

import java.util.Calendar;

import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.ClaimsPageElements;

public class CreatingClaimsSteps {
    Calendar date = Calendar.getInstance();
    ClaimsPageElements claimsPageElements = new ClaimsPageElements();

    public void replaceTextClaimExecutor(String nameExecutor) {
        claimsPageElements.executorClaimField.perform(click(), replaceText(nameExecutor));

    }

    public void selectAClaimExecutorFromTheList(ViewInteraction nameExecutor) {
        DataHelper.EspressoBaseTest.clickButton( claimsPageElements.executorClaimField);
        Espresso.closeSoftKeyboard();
        DataHelper.EspressoBaseTest.clickButton(nameExecutor);
    }

    public void setDateToDatePicker(int plusYear, int plusMonth, int plusDay) {
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.dateClaimField);
        claimsPageElements.datePicker.check(matches(isDisplayed()));
        claimsPageElements.datePicker.perform(setDate(date.get(Calendar.YEAR)+plusYear, date.get(Calendar.MONTH)+1+plusMonth, date.get(Calendar.DAY_OF_MONTH)+plusDay));
        //DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
    }

    public void setTimeToTimePicker(int plusHour, int plusMinute) {
        claimsPageElements.timePicker.check(matches(isDisplayed()));
        claimsPageElements.timePicker.perform(setTime(date.get(Calendar.HOUR_OF_DAY)+plusHour, date.get(Calendar.MINUTE)+plusMinute));
    }

    public void setTimeToTimeField(int plusHour, int plusMinute) {
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.timeClaimField);
        //controlPanelElements.timePicker.check(matches(isDisplayed()));
        //controlPanelElements.timePicker.perform(setTime(date.get(Calendar.HOUR_OF_DAY)+plusHour, date.get(Calendar.MINUTE)+plusMinute));
        //DataHelper.EspressoBaseTest.clickButton(controlPanelElements.okBut);
        setTimeToTimePicker(plusHour, plusMinute);
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.okBut);
    }

    public void fillingOutTheFormCreatingClaimWithDateToday(int plusYear, int plusMonth, int plusDay, int plusHour, int plusMinute, String title, String description) {
        SystemClock.sleep(3000);
        claimsPageElements.titleClaimField.perform(replaceText(title));
        setDateToDatePicker(plusYear, plusMonth, plusDay);
        DataHelper.EspressoBaseTest.clickButton(claimsPageElements.okBut);
        setTimeToTimeField(plusHour, plusMinute);
        claimsPageElements.descriptionClaimField.perform(replaceText(description));
    }

    public void cancellationOfTheClaim (String description) {
        claimsPageElements.newsRecyclerList
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(description))))).perform(click());

    }

    public void isFillEmptyFieldsMessage() {
        claimsPageElements.messageFillEmptyFields.check(matches(isDisplayed()));
        claimsPageElements.okBut.check(matches(isDisplayed()));
    }

    public void isWrongEmptyFormClaim() {
        claimsPageElements.titleTextInputEndIcon.check(matches(isDisplayed()));
        claimsPageElements.dateInPlaneTextInputEndIcon.check(matches(isDisplayed()));
        claimsPageElements.timeInPlaneTextInputEndIcon.check(matches(isDisplayed()));
        claimsPageElements.descriptionTextInputEndIcon.check(matches(isDisplayed()));
    }

}
