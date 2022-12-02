package ru.iteco.fmhandroid.ui.steps;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.TestUtils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;

import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;

public class ClaimsPageSteps {
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();
    private static CreatingClaimsSteps creatingClaimsSteps = new CreatingClaimsSteps();

    public Matcher<View> claimsItemDescription = withId(R.id.description_text_view);
    public Matcher<View> titleClaimField = allOf(withId(R.id.title_edit_text), withParent(withParent(withId(R.id.title_text_input_layout))));
    public Matcher<View> executorClaimField = withId(R.id.executor_drop_menu_auto_complete_text_view);
    public Matcher<View> dateClaimField = withId(R.id.date_in_plan_text_input_edit_text);
    public Matcher<View> timeClaimField = allOf(withHint("Time"), withParent(withParent(withId(R.id.time_in_plan_text_input_layout))));
    public Matcher<View> descriptionClaimField = allOf(withHint("Description"), withParent(withParent(withId(R.id.description_text_input_layout))));
    public Matcher<View> compatImageView = allOf(TestUtils.childAtPosition(
            TestUtils.childAtPosition(
                    withId(R.id.claim_list_card),
                    0),
            11));
    //Окно настройки фильтрации
    public Matcher<View> claimsFiltersButton = withId(R.id.filters_material_button);
    public Matcher<View> claimFilterDialogTitle = withId(R.id.claim_filter_dialog_title);
    public Matcher<View> itemFilterOpen = withId(R.id.item_filter_open);
    public Matcher<View> itemFilterInProgress = withId(R.id.item_filter_in_progress);
    public Matcher<View> itemFilterExecuted = withId(R.id.item_filter_executed);
    public Matcher<View> itemFilterCancelled = withId(R.id.item_filter_cancelled);
    public Matcher<View> claimListFilterOkBut = withId(R.id.claim_list_filter_ok_material_button);
    public Matcher<View> claimFilterCancelBut = withId(R.id.claim_filter_cancel_material_button);
    public Matcher<View> addNewClaimBut = withId(R.id.add_new_claim_material_button);
    public Matcher<View> labelError = withText("Enter a valid time");

    public ViewInteraction executorIvanov = onView(withText("Иванов Данил Данилович")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction executorSmirnov = onView(withText("Смирнов Петр Петрович")).inRoot((RootMatchers.isPlatformPopup()));

    public Matcher<View> claimRecyclerList = withId(R.id.claim_list_recycler_view);
    public Matcher<View> statusLabelText = withId(R.id.status_label_text_view);
    public Matcher<View> messageFillEmptyFields = withText("Fill empty fields");
    public Matcher<View> addCommentBut = withId(R.id.add_comment_image_button);
    public Matcher<View> commentTextInputField = allOf(withHint("Comment"), withParent(withParent(withId(R.id.comment_text_input_layout))));
    public Matcher<View> commentDescriptionText = withId(R.id.comment_description_text_view);
    public Matcher<View> claimCommentsListRecyclerView = withId(R.id.claim_comments_list_recycler_view);
    public Matcher<View> editCommentImBut = withId(R.id.edit_comment_image_button);
    //Элементы карточки заявки
    public Matcher<View> titleTextView = withId(R.id.title_text_view);
    public Matcher<View> planeDateTextView = withId(R.id.plane_date_text_view);
    public Matcher<View> planeTimeTextView = withId(R.id.plan_time_text_view);
    public Matcher<View> statusIconImView = withId(R.id.status_icon_image_view);
    public Matcher<View> descriptionTextView = withId(R.id.description_text_view);

    public Matcher<View> editClaimBut = withId(R.id.edit_processing_image_button);
    public Matcher<View> closeImBut = withId(R.id.close_image_button);
    public Matcher<View> statusProcessingImBut = withId(R.id.status_processing_image_button);
    public Matcher<View> takeToWorkMenuItem = withText("take to work");
    public Matcher<View> toExecuteMenuItem = withText("To execute");
    public Matcher<View> throwOffMenuItem = withText("Throw off");
    public Matcher<View> cancelMenuItem = withText("Cancel");
    //Диалоговое окно изменения статуса заявки
    public Matcher<View> statusCommentTextInputField = allOf(withHint("Comment"), withId(R.id.editText));
    public Matcher<View> claimsListCard1 = TestUtils.withRecyclerView(R.id.claim_list_recycler_view)
            .atPositionOnView(1, R.id.claim_list_card);

    public int commentDescriptionTextView = R.id.comment_description_text_view;

    public ViewInteraction getItemClaimCompatImView(String title) {
        return TestUtils.waitView(allOf(compatImageView, hasSibling(withText(title))));
    }

    public void isStatusCommentDialog() {
        TestUtils.waitView(statusCommentTextInputField).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.okBut).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.cancelDeleteBut).check(matches(isDisplayed()));
    }

    public void isClaimsPage() {
        TestUtils.waitView(withText("Claims")).check(matches(isDisplayed()));
        TestUtils.waitView(withId(R.id.claim_list_recycler_view)).check(matches(isDisplayed()));
    }

    public void isClaimsForm() {
        TestUtils.waitView(withText("Creating")).check(matches(isDisplayed()));
        TestUtils.waitView(withText("Claims")).check(matches(isDisplayed()));
        TestUtils.waitView(titleClaimField).check(matches(isDisplayed()));
        TestUtils.waitView(executorClaimField).check(matches(isDisplayed()));
        TestUtils.waitView(dateClaimField).check(matches(isDisplayed()));
        TestUtils.waitView(timeClaimField).check(matches(isDisplayed()));
        TestUtils.waitView(descriptionClaimField).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.saveBut).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.cancelBut).check(matches(isDisplayed()));
    }

    public void isClaimsFilteringDialog() {
        TestUtils.waitView(claimFilterDialogTitle).check(matches(isDisplayed()));
        TestUtils.waitView(itemFilterOpen).check(matches(isDisplayed()));
        TestUtils.waitView(itemFilterInProgress).check(matches(isDisplayed()));
        TestUtils.waitView(itemFilterExecuted).check(matches(isDisplayed()));
        TestUtils.waitView(itemFilterCancelled).check(matches(isDisplayed()));
        TestUtils.waitView(claimListFilterOkBut).check(matches(isDisplayed()));
        TestUtils.waitView(claimFilterCancelBut).check(matches(isDisplayed()));
    }

    public ViewInteraction scrollToElementInRecyclerList(String description) {
        return TestUtils.waitView(claimRecyclerList).check(matches(isDisplayed()))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(description))));
    }

    public void isCommentForm() {
        TestUtils.waitView(commentTextInputField).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.saveBut).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.cancelBut).check(matches(isDisplayed()));
    }

    public ViewInteraction getItemClaimExecutorName(String title) {
        return TestUtils.waitView(AllOf.allOf(withId(R.id.executor_name_material_text_view), hasSibling(withText(title))));
    }

    public void isClaimCard(String title, String planeDate, String planeTime, String description) {
        TestUtils.waitView(titleTextView).check(matches(withText(title)));

        TestUtils.waitView(planeDateTextView).check(matches(withText(planeDate)));
        TestUtils.waitView(planeTimeTextView).check(matches(withText(planeTime)));
        TestUtils.waitView(statusIconImView).check(matches(isDisplayed()));
        TestUtils.waitView(descriptionTextView).check(matches(withText(description)));
    }

    public void openClaimCard(String title) {
        scrollToElementInRecyclerList(title);
        getItemClaimCompatImView(title).perform(click());
    }


    public ViewInteraction getClaimItemDescription() {
        return TestUtils.waitView(claimsItemDescription);
    }

    public void setStatusExecute() {
        TestUtils.waitView(statusProcessingImBut).perform(click());
        TestUtils.waitView(toExecuteMenuItem).perform(click());
    }

    public void replaceClaimStatusCommentText(String comment) {
        TestUtils.waitView(statusCommentTextInputField).perform(replaceText(comment));
    }

    public void setStatusCanceled() {
        TestUtils.waitView(statusProcessingImBut).perform(click());
        TestUtils.waitView(cancelMenuItem).perform(click());
    }

    public void closeImButtonClick() {
        TestUtils.waitView(closeImBut).perform(click());
    }

    public void openClaimsFilter() {
        TestUtils.waitView(claimsFiltersButton).perform(click());
    }

    public void claimFilterCancelButtonClick() {
        TestUtils.waitView(claimFilterCancelBut).perform(click());
    }

    public void openCreatingClaimsCard() {
        TestUtils.waitView(addNewClaimBut).perform(click());
    }

    public void openCreatingCommentForm() {
        TestUtils.waitView(addCommentBut).perform(click());
    }

    public void replaceCommentTextInputText(String comment) {
        TestUtils.waitView(commentTextInputField).perform(replaceText(comment));
    }

    public ViewInteraction getCommentDescriptionText() {
        return TestUtils.waitView(commentDescriptionText);
    }

    public ViewInteraction getClaimCommentsListRecyclerView() {
        return TestUtils.waitView(claimCommentsListRecyclerView);
    }

    public void editComment(String newComment) {
        TestUtils.waitView(editCommentImBut).perform(click());
        isCommentForm();
        replaceCommentTextInputText(newComment);
        controlPanelSteps.saveNewsButtonClick();
    }

    public ViewInteraction getLabelError() {
        return TestUtils.waitView(labelError);
    }

    public ViewInteraction getTitleClaim() {
        return TestUtils.waitView(titleClaimField);
    }

    public ViewInteraction getDescriptionClaimField() {
        return TestUtils.waitView(descriptionClaimField);
    }

    public void editClaim(int year, int month, int newDay, int newHour, int minutes, String newTitle, String newDescription) {
        editClaimButClick();
        creatingClaimsSteps.fillingOutTheFormCreatingClaimWithDateToday(year, month, newDay, newHour, minutes, newTitle, newDescription);
        controlPanelSteps.saveNewsButtonClick();
    }

    public void setStatusInProcess() {
        TestUtils.waitView(statusProcessingImBut).perform(click());
        TestUtils.waitView(takeToWorkMenuItem).perform(click());
    }

    public ViewInteraction getStatusLabel() {
        return TestUtils.waitView(statusLabelText);
    }

    public void setStatusOpen() {
        TestUtils.waitView(statusProcessingImBut).perform(click());
        TestUtils.waitView(throwOffMenuItem).perform(click());
    }

    public void editClaimButClick() {
        TestUtils.waitView(editClaimBut).perform(click());
    }

}
