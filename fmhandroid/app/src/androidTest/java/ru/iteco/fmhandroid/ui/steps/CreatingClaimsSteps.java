package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.AllOf.allOf;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matchers;

import java.util.Calendar;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;

public class CreatingClaimsSteps {
    Calendar date = Calendar.getInstance();
    ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    public void replaceTextClaimExecutor(String nameExecutor) {
        TestUtils.waitView(claimsPageSteps.executorClaimField).check(matches(isDisplayed())).perform(click(), replaceText(nameExecutor));
        Espresso.closeSoftKeyboard();

    }

    public void selectAClaimExecutorFromTheList(ViewInteraction nameExecutor) {
        TestUtils.waitView(claimsPageSteps.executorClaimField).perform(click());
        Espresso.closeSoftKeyboard();
        DataHelper.EspressoBaseTest.clickButton(nameExecutor);
    }

    public void setDateToDatePicker(int year, int month, int day) {
        TestUtils.waitView(claimsPageSteps.dateClaimField).perform(click());
        TestUtils.waitView(controlPanelSteps.datePicker).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.datePicker).perform(setDate(year, month, day));
    }

    public void setTimeToTimeField(int hour, int minute) {
        TestUtils.waitView(claimsPageSteps.timeClaimField).perform(click());
        controlPanelSteps.setTimeToTimePicker(hour, minute);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
    }

    public void fillingOutTheFormCreatingClaimWithDateToday(int year, int month, int day, int hour, int minute, String title, String description) {
        TestUtils.waitView(claimsPageSteps.titleClaimField).perform(replaceText(title));
        setDateToDatePicker(year, month, day);
        TestUtils.waitView(controlPanelSteps.okBut).perform(click());
        setTimeToTimeField(hour, minute);
        TestUtils.waitView(claimsPageSteps.descriptionClaimField).perform(replaceText(description));
    }

    public void creatingAClaim(int year, int month, int day, int hour, int minutes, String title, String description) {
        TestUtils.waitView(claimsPageSteps.addNewClaimBut).perform(click());
        selectAClaimExecutorFromTheList(claimsPageSteps.executorIvanov);
        fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, title, description);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
    }

    public void creatingAClaimWithStatusOpen(int year, int month, int day, int hour, int minutes, String title, String description) {
        TestUtils.waitView(claimsPageSteps.addNewClaimBut).perform(click());
        fillingOutTheFormCreatingClaimWithDateToday(year, month, day, hour, minutes, title, description);
        TestUtils.waitView(controlPanelSteps.saveBut).perform(click());
        SystemClock.sleep(3000);
    }

    public void isFillEmptyFieldsMessage() {
        TestUtils.waitView(claimsPageSteps.messageFillEmptyFields).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.okBut).check(matches(isDisplayed()));
    }

    public void isWrongEmptyFormClaim() {
        TestUtils.waitView(allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.title_text_input_layout))))))).check(matches(isDisplayed()));
        TestUtils.waitView(Matchers.allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.date_in_plan_text_input_layout))))))).check(matches(isDisplayed()));
        TestUtils.waitView(Matchers.allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.time_in_plan_text_input_layout))))))).check(matches(isDisplayed()));
        TestUtils.waitView(Matchers.allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.description_text_input_layout))))))).check(matches(isDisplayed()));
    }

}
