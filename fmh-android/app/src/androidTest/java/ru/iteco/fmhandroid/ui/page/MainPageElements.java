package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class MainPageElements {
    public ViewInteraction newsBlockHeader = onView(withText("News"));
    public ViewInteraction claimsBlockHeader = onView(withText("Claims"));
    public ViewInteraction newsBlock = onView(withId(R.id.container_list_news_include_on_fragment_main));
    public ViewInteraction claimsBlock = onView(withId(R.id.container_list_claim_include_on_fragment_main));
    public ViewInteraction authImBut = onView(withId(R.id.authorization_image_button));
    public ViewInteraction logOutBut = onView(withId(R.id.title));

}
