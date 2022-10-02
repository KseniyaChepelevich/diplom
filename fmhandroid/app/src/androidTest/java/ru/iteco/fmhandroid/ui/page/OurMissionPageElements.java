package ru.iteco.fmhandroid.ui.page;

import androidx.test.espresso.ViewInteraction;
import ru.iteco.fmhandroid.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class OurMissionPageElements {
    public ViewInteraction ourMissionPageTitle = onView(withId(R.id.our_mission_title_text_view));

    public ViewInteraction ourMissionItemList = onView(withId(R.id.our_mission_item_list_recycler_view));

}
