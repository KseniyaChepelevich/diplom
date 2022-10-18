package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import android.widget.DatePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;

import ru.iteco.fmhandroid.R;

public class FilterNewsPageElements {
    public ViewInteraction filterNewsTitleTextView = onView(withId(R.id.filter_news_title_text_view));
    public ViewInteraction newsItemCategoryField = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    public ViewInteraction newsItemPublishDateStartField = onView(withId(R.id.news_item_publish_date_start_text_input_edit_text));
    public ViewInteraction newsItemPublishDateEndField = onView(withId(R.id.news_item_publish_date_end_text_input_edit_text));
    public ViewInteraction filterBut = onView(withId(R.id.filter_button));
    public ViewInteraction canselBut = onView(withId(R.id.cancel_button));
    public ViewInteraction filterNewsActiveCheckBox = onView(withId(R.id.filter_news_active_material_check_box));
    public ViewInteraction filterNewsInactiveCheckBox = onView(withId(R.id.filter_news_inactive_material_check_box));
    public ViewInteraction categoryAnnouncement = onView(withText("Объявление")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryBirthday = onView(withText("День рождения")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categorySalary = onView(withText("Зарплата")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryTradeUnion = onView(withText("Профсоюз")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryMassage = onView(withText("Массаж")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryGratitude = onView(withText("Благодарность")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryNeedHelp = onView(withText("Нужна помощь")).inRoot((RootMatchers.isPlatformPopup()));
    public ViewInteraction categoryHoliday = onView(withText("Праздник")).inRoot((RootMatchers.isPlatformPopup()));

    public ViewInteraction datePicker = onView(isAssignableFrom(DatePicker.class));
    public ViewInteraction okBut = onView(withId(android.R.id.button1));

}
