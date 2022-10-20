package ru.iteco.fmhandroid.ui.steps;

import ru.iteco.fmhandroid.ui.page.ClaimsPageElements;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

public class ClaimsPageSteps {
    ClaimsPageElements claimsPageElements = new ClaimsPageElements();

    public void isClaimsPage() {
        claimsPageElements.claimsPageHeader.check(matches(isDisplayed()));
        claimsPageElements.claimListRecyclerView.check(matches(isDisplayed()));
        //claimsPageElements.claimsListSwipe.check(matches(isDisplayed()));


    }

    public void isClaimsForm() {
        claimsPageElements.customAppBarTitleTextView.check(matches(isDisplayed()));
        claimsPageElements.customAppBarSubTitleTextView.check(matches(isDisplayed()));
        claimsPageElements.titleClaimField.check(matches(isDisplayed()));
        claimsPageElements.executorClaimField.check(matches(isDisplayed()));
        claimsPageElements.dateClaimField.check(matches(isDisplayed()));
        claimsPageElements.timeClaimField.check(matches(isDisplayed()));
        claimsPageElements.descriptionClaimField.check(matches(isDisplayed()));
        claimsPageElements.saveClaimBut.check(matches(isDisplayed()));
        claimsPageElements.cancelClaimBut.check(matches(isDisplayed()));

    }

    public void isClaimsCard() {
        claimsPageElements.claimsItemDescription.check(matches(isDisplayed()));

    }

    public void isClaimsFilteringDialog() {
        claimsPageElements.claimFilterDialogTitle.check(matches(isDisplayed()));
        claimsPageElements.itemFilterOpen.check(matches(isDisplayed()));
        claimsPageElements.itemFilterInProgress.check(matches(isDisplayed()));
        claimsPageElements.itemFilterExecuted.check(matches(isDisplayed()));
        claimsPageElements.itemFilterCancelled.check(matches(isDisplayed()));
        claimsPageElements.claimListFilterOkBut.check(matches(isDisplayed()));
        claimsPageElements.claimFilterCancelBut.check(matches(isDisplayed()));
    }
}
