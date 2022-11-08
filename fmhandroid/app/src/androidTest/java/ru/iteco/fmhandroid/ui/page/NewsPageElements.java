package ru.iteco.fmhandroid.ui.page;

import androidx.test.espresso.ViewInteraction;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.TestUtils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

public class NewsPageElements {
    public ViewInteraction newsPageHeader = onView(withText("News"));
    public ViewInteraction newsListSwipe = onView(withId(R.id.news_list_swipe_refresh));
    public ViewInteraction editNewsMaterialBut = onView(withId(R.id.edit_news_material_button));
    public ViewInteraction filterNewsMaterialBut = onView(withId(R.id.filter_news_material_button));
    public ViewInteraction newsItemMaterialCardView1 = onView(TestUtils.withRecyclerView(R.id.news_list_recycler_view)
            .atPositionOnView(1, R.id.news_item_material_card_view));
    public ViewInteraction newsItemDescriptionTextView1 = onView(TestUtils.withRecyclerView(R.id.news_list_recycler_view).atPositionOnView(1, R.id.news_item_description_text_view));

    public ViewInteraction controlPanelHeader = onView(withText("Control panel"));
    public ViewInteraction addNewsImBut = onView(withId(R.id.add_news_image_view));
    public ViewInteraction sortNewsMaterialBut = onView(withId(R.id.sort_news_material_button));
    public ViewInteraction newsItemCategoryField = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    public ViewInteraction newsItemTitleField = onView(withId(R.id.news_item_title_text_input_edit_text));
    public ViewInteraction newsItemPublishDateField = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
    public ViewInteraction newsItemPublishTimeField = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
    public ViewInteraction newsItemDescriptionField = onView(withId(R.id.news_item_description_text_input_edit_text));
    public ViewInteraction swithcherActive = onView(withId(R.id.switcher));
    public ViewInteraction saveBut = onView(withId(R.id.save_button));
    public ViewInteraction cancelBut = onView(withId(R.id.cancel_button));
    public ViewInteraction newsItemTitle = onView(withId(R.id.news_item_title_text_view));

    //Элементы на экране при пустом списке новостей
    public ViewInteraction emptyNewsListImage = onView(withId(R.id.empty_news_list_image_view));
    public ViewInteraction emptyNewsListText = onView(withText("There is nothing here yet…"));
    public ViewInteraction newsRetryButton = onView(allOf(withId(R.id.news_retry_material_button), withText("REFRESH")));


}
