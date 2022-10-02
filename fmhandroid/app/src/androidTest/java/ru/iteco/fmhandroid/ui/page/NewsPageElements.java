package ru.iteco.fmhandroid.ui.page;

import androidx.test.espresso.ViewInteraction;
import ru.iteco.fmhandroid.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class NewsPageElements {
    public ViewInteraction newsPageHeader = onView(withText("News"));
    public ViewInteraction newsListSwipe = onView(withId(R.id.news_list_swipe_refresh));
    public ViewInteraction editNewsMaterialBut = onView(withId(R.id.edit_news_material_button));
    public ViewInteraction filterNewsMaterialBut = onView(withId(R.id.filter_news_material_button));
    public ViewInteraction filterNewsTitleTextView = onView(withId(R.id.filter_news_title_text_view));
    public ViewInteraction newsItemCategoryField = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
}
