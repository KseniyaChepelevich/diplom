package ru.iteco.fmhandroid.ui.page;

import android.view.View;
import androidx.test.espresso.DataInteraction;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsInstanceOf;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.TestUtils;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasEntry;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;

import java.util.Map;

public class MainPageElements {
    public ViewInteraction newsBlockHeader = onView(withText("News"));
    public ViewInteraction claimsBlockHeader = onView(withText("Claims"));
    public ViewInteraction newsBlock = onView(withId(R.id.container_list_news_include_on_fragment_main));
    public ViewInteraction claimsBlock = onView(withId(R.id.container_list_claim_include_on_fragment_main));
    public ViewInteraction authImBut = onView(withId(R.id.authorization_image_button));

    public ViewInteraction logOutBut = onView(withText("Log out"));


    public ViewInteraction allNewsBut = onView(withId(R.id.all_news_text_view));

    public ViewInteraction mainMenuImBut = onView(withId(R.id.main_menu_image_button));
    public ViewInteraction newsInTheMainMenu = onView(withText("News"));

    public ViewInteraction claimsInTheMainMenu = onView(withText("Claims"));

    public ViewInteraction allClaimsBut = onView(withId(R.id.all_claims_text_view));

    public ViewInteraction aboutInTheMainMenu = onView(withText("About"));

    public ViewInteraction ourMissionImBut = onView(withId(R.id.our_mission_image_button));

    public ViewInteraction newsItemMaterialCardList1 = onView(TestUtils.withRecyclerView(R.id.news_list_recycler_view)
            .atPositionOnView(1, R.id.news_item_material_card_view));

    public ViewInteraction newsItemDescription1 = onView(TestUtils.withRecyclerView(R.id.news_list_recycler_view).atPositionOnView(1, R.id.news_item_description_text_view));

    public ViewInteraction newsExpandMaterialBut = onView(allOf(withId(R.id.expand_material_button), withParent(withParent(withId(R.id.container_list_news_include_on_fragment_main)))));

    public ViewInteraction allNewsCardsBlockConstraintLayout = onView(withId(R.id.all_news_cards_block_constraint_layout));

    public ViewInteraction claimsExpandMaterialBut = onView(allOf(withId(R.id.expand_material_button), withParent(withParent(withId(R.id.container_list_claim_include_on_fragment_main)))));

    public ViewInteraction allClaimsCardsBlockConstraintLayout = onView(withId(R.id.all_claims_cards_block_constraint_layout));

    public ViewInteraction claimListRecyclerView =  onView(ViewMatchers.withId(R.id.claim_list_recycler_view));

    public ViewInteraction scrollView = onView(withClassName(endsWith("ScrollView")));

    public ViewInteraction addNewClaimBut = onView(withId(R.id.add_new_claim_material_button));
    public ViewInteraction claimsListCard4 = onView(TestUtils.withRecyclerView(R.id.claim_list_recycler_view)
            .atPositionOnView(4, R.id.claim_list_card));








}
