package ru.iteco.fmhandroid.ui.page;

import androidx.test.espresso.ViewInteraction;
import ru.iteco.fmhandroid.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class ClaimsPageElements {
    public ViewInteraction claimsPageHeader = onView(withText("Claims"));
    public ViewInteraction claimListRecyclerView = onView(withId(R.id.claim_list_recycler_view));
    public ViewInteraction claimsListSwipe = onView(withId(R.id.claim_list_swipe_refresh));
    public ViewInteraction claimsItemDescription = onView(withId(R.id.description_text_view));
    public ViewInteraction customAppBarTitleTextView = onView(withText("Creating"));
    public ViewInteraction customAppBarSubTitleTextView = onView(withText("Claims"));
    public ViewInteraction titleClaimField = onView(allOf(withHint("Title"), withParent(withParent(withId(R.id.title_text_input_layout)))));
    public ViewInteraction executorClaimField = onView(allOf(withHint("Executor"), withParent(withParent(withId(R.id.executor_drop_menu_text_input_layout)))));
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


}
