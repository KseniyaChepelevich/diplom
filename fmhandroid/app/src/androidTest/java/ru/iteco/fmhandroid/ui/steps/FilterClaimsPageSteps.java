package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.CustomRecyclerViewActions;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.data.ViewActions;

public class FilterClaimsPageSteps {
    ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();

    public void filterClaims(boolean openStatus, boolean inProgressStatus, boolean executedStatus, boolean cancelledStatus) {
        TestUtils.waitView(claimsPageSteps.claimsFiltersButton).perform(click());
        TestUtils.waitView(claimsPageSteps.itemFilterOpen).perform(ViewActions.setChecked(openStatus));
        TestUtils.waitView(claimsPageSteps.itemFilterInProgress).perform(ViewActions.setChecked(inProgressStatus));
        TestUtils.waitView(claimsPageSteps.itemFilterExecuted).perform(ViewActions.setChecked(executedStatus));
        TestUtils.waitView(claimsPageSteps.itemFilterCancelled).perform(ViewActions.setChecked(cancelledStatus));
        TestUtils.waitView(claimsPageSteps.claimListFilterOkBut).perform(click());
    }

    public void checkClaimIsExist(String title) {
        TestUtils.waitView(claimsPageSteps.claimRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsExist(R.id.description_material_text_view, withText(title))));
    }

    public void checkClaimIsNotExist(String title) {
        TestUtils.waitView(claimsPageSteps.claimRecyclerList).check(matches(CustomRecyclerViewActions.RecyclerViewMatcher.matchChildViewIsNotExist(R.id.description_material_text_view, withText(title))));
    }
}
