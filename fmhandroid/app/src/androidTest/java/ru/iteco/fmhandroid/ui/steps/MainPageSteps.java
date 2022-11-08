package ru.iteco.fmhandroid.ui.steps;

import android.os.SystemClock;
import android.view.View;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.page.MainPageElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

public class MainPageSteps {
    MainPageElements mainPageElements = new MainPageElements();

    public Matcher<View> newsBlockHeader = withText("News");
    public Matcher<View> claimsBlockHeader = withText("Claims");
    public Matcher<View> authImBut = withId(R.id.authorization_image_button);

    public Matcher<View> logOutBut = withText("Log out");

    public Matcher<View> allNewsBut = withId(R.id.all_news_text_view);

    public Matcher<View> mainMenuImBut = withId(R.id.main_menu_image_button);
    public Matcher<View> newsInTheMainMenu = withText("News");

    public Matcher<View> claimsInTheMainMenu = withText("Claims");

    public Matcher<View> allClaimsBut = withId(R.id.all_claims_text_view);

    public Matcher<View> aboutInTheMainMenu = withText("About");

    public Matcher<View> ourMissionImBut = withId(R.id.our_mission_image_button);

    public Matcher<View> newsItemMaterialCardList1 = TestUtils.withRecyclerView(R.id.news_list_recycler_view)
            .atPositionOnView(1, R.id.news_item_material_card_view);

    public Matcher<View> newsItemDescription1 = TestUtils.withRecyclerView(R.id.news_list_recycler_view).atPositionOnView(1, R.id.news_item_description_text_view);

    public Matcher<View> newsExpandMaterialBut = allOf(withId(R.id.expand_material_button), withParent(withParent(withId(R.id.container_list_news_include_on_fragment_main))));

    public Matcher<View> allNewsCardsBlockConstraintLayout = withId(R.id.all_news_cards_block_constraint_layout);

    public Matcher<View> claimsExpandMaterialBut = allOf(withId(R.id.expand_material_button), withParent(withParent(withId(R.id.container_list_claim_include_on_fragment_main))));

    public Matcher<View> allClaimsCardsBlockConstraintLayout = withId(R.id.all_claims_cards_block_constraint_layout);

    public Matcher<View> claimListRecyclerView = ViewMatchers.withId(R.id.claim_list_recycler_view);

    public Matcher<View> scrollView = withClassName(endsWith("ScrollView"));

    public Matcher<View> addNewClaimBut = withId(R.id.add_new_claim_material_button);
    public Matcher<View> claimsListCard4 = TestUtils.withRecyclerView(R.id.claim_list_recycler_view)
            .atPositionOnView(4, R.id.claim_list_card);


    public void isMainPage() {
        TestUtils.waitView(newsBlockHeader).check(matches(isDisplayed()));
        TestUtils.waitView(withId(R.id.container_list_news_include_on_fragment_main)).check(matches(isDisplayed()));
        TestUtils.waitView(withId(R.id.container_list_claim_include_on_fragment_main)).check(matches(isDisplayed()));
        TestUtils.waitView(claimsBlockHeader).check(matches(isDisplayed()));
    }

    public void clickLogOutBut() {
        TestUtils.waitView(authImBut).check(matches(isDisplayed())).perform(click());
        TestUtils.waitView(logOutBut).check(matches(isDisplayed())).perform(click());
    }

    public void openNewsPageThroughTheMainMenu() {
        TestUtils.waitView(mainMenuImBut).perform(click());
        TestUtils.waitView(newsInTheMainMenu).perform(click());
    }

    public void openClaimsPageThroughTheMainMenu() {
        TestUtils.waitView(mainMenuImBut).perform(click());
        TestUtils.waitView(claimsInTheMainMenu).perform(click());
    }

    public void openAboutPageThroughTheMainMenu() {
        TestUtils.waitView(mainMenuImBut).perform(click());
        TestUtils.waitView(aboutInTheMainMenu).perform(click());
    }

    public void isNewsBlockCollapsed() {
        TestUtils.waitView(newsBlockHeader).check(matches(isDisplayed()));
        TestUtils.waitView(allNewsBut).check(matches(not(isDisplayed())));
        TestUtils.waitView(allNewsCardsBlockConstraintLayout).check(matches(not(isDisplayed())));
    }

    public void isClaimsBlockCollapsed() {
        TestUtils.waitView(claimsBlockHeader).check(matches(isDisplayed()));
        TestUtils.waitView(allClaimsBut).check(matches(not(isDisplayed())));
        TestUtils.waitView(allClaimsCardsBlockConstraintLayout).check(matches(not(isDisplayed())));
    }

}
