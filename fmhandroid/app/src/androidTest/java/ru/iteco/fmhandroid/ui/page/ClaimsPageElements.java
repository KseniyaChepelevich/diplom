package ru.iteco.fmhandroid.ui.page;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;

import ru.iteco.fmhandroid.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;

import android.widget.DatePicker;
import android.widget.TimePicker;

public class ClaimsPageElements {
    public ViewInteraction claimsPageHeader = onView(withText("Claims"));
    public ViewInteraction claimListRecyclerView = onView(withId(R.id.claim_list_recycler_view));
    public ViewInteraction claimsListSwipe = onView(withId(R.id.claim_list_swipe_refresh));
    public ViewInteraction claimsItemDescription = onView(withId(R.id.description_text_view));
    public ViewInteraction customAppBarTitleTextView = onView(withText("Creating"));
    public ViewInteraction customAppBarSubTitleTextView = onView(withText("Claims"));
    public ViewInteraction titleClaimField = onView(allOf(withHint("Title"), withParent(withParent(withId(R.id.title_text_input_layout)))));
    public ViewInteraction executorClaimField = onView(withId(R.id.executor_drop_menu_text_input_layout));
    public ViewInteraction dateClaimField = onView(allOf(withHint("Date"), withParent(withParent(withId(R.id.date_in_plan_text_input_layout)))));
    public ViewInteraction timeClaimField = onView(allOf(withHint("Time"), withParent(withParent(withId(R.id.time_in_plan_text_input_layout)))));
    public ViewInteraction descriptionClaimField = onView(allOf(withHint("Description"), withParent(withParent(withId(R.id.description_text_input_layout)))));
    public ViewInteraction saveClaimBut = onView(withId(R.id.save_button));
    public ViewInteraction cancelClaimBut = onView(withId(R.id.cancel_button));
    public ViewInteraction claimsFiltersButton = onView(withId(R.id.filters_material_button));
    //Элементы диалогового окна Фильтрация
    public ViewInteraction claimFilterDialogTitle = onView(withId(R.id.claim_filter_dialog_title));
    public ViewInteraction itemFilterOpen = onView(withId(R.id.item_filter_open));
    public ViewInteraction itemFilterInProgress = onView(withId(R.id.item_filter_in_progress));
    public ViewInteraction itemFilterExecuted = onView(withId(R.id.item_filter_executed));
    public ViewInteraction itemFilterCancelled = onView(withId(R.id.item_filter_cancelled));
    public ViewInteraction claimListFilterOkBut = onView(withId(R.id.claim_list_filter_ok_material_button));
    public ViewInteraction claimFilterCancelBut = onView(withId(R.id.claim_filter_cancel_material_button));

    public ViewInteraction addNewClaimBut = onView(withId(R.id.add_new_claim_material_button));

    public ViewInteraction datePicker = onView(isAssignableFrom(DatePicker.class));
    public ViewInteraction okBut = onView(withId(android.R.id.button1));
    public ViewInteraction cancelDeleteBut = onView(withId(android.R.id.button2));
    public ViewInteraction timePicker = onView(isAssignableFrom(TimePicker.class));
    public ViewInteraction timePickerToggleMode = onView(withContentDescription("Switch to text input mode for the time input."));
    public ViewInteraction labelError = onView(withText("Enter a valid time"));
    public ViewInteraction executorIvanov = onView(withText("Иванов Данил Данилович")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction executorSidorov = onView(withText("Сидоров Дмитрий Дмитриевич")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction executorPetrov = onView(withText("Петров Егор Егорович")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction executorSmirnov = onView(withText("Смирнов Петр Петрович")).inRoot((RootMatchers.isPlatformPopup()));

    public ViewInteraction newsRecyclerList = onView(withId(R.id.claim_list_recycler_view));

    public ViewInteraction statusLabelText = onView(withId(R.id.status_label_text_view));

    public ViewInteraction messageFillEmptyFields = onView(withText("Fill empty fields"));
    //public ViewInteraction okMesBut = onView(withId(android.R.id.button1));

    public ViewInteraction titleTextInputEndIcon = onView(allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.title_text_input_layout)))))));
    public ViewInteraction dateInPlaneTextInputEndIcon = onView(allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.date_in_plan_text_input_layout)))))));
    public ViewInteraction timeInPlaneTextInputEndIcon = onView(allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.time_in_plan_text_input_layout)))))));
    public ViewInteraction descriptionTextInputEndIcon = onView(allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.description_text_input_layout)))))));



}
