package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.DataHelper;

public class ControlPanelElements {
    public ViewInteraction contrloPanelHeader = onView(withText("Control panel"));
    public ViewInteraction addNewsImBut = onView(withId(R.id.add_news_image_view));
    public ViewInteraction sortNewsBut = onView(withId(R.id.sort_news_material_button));
    public ViewInteraction filterNewsBut = onView(withId(R.id.filter_news_material_button));
    public ViewInteraction newsItemCategoryField = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    public ViewInteraction newsItemTitleField = onView(withId(R.id.news_item_title_text_input_edit_text));
    public ViewInteraction newsItemPublishDateField = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
    public ViewInteraction newsItemPublishTimeField = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
    public ViewInteraction newsItemDescriptionField = onView(withId(R.id.news_item_description_text_input_edit_text));
    public ViewInteraction switcherActive = onView(withId(R.id.switcher));
    public ViewInteraction switcherNotActive = onView(withText("Not active"));
    public ViewInteraction saveBut = onView(withId(R.id.save_button));
    public ViewInteraction cancelBut = onView(withId(R.id.cancel_button));
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
    public ViewInteraction cancelDeleteBut = onView(withId(android.R.id.button2));
    public ViewInteraction timePicker = onView(isAssignableFrom(TimePicker.class));
    public ViewInteraction timePickerToggleMode = onView(withContentDescription("Switch to text input mode for the time input."));
    public ViewInteraction timePickerTopLabel = onView(withText("Type in time"));
    //public ViewInteraction inputHourTimePicker = onView(withId(android.R.id.input_hour));
    public ViewInteraction deleteNewsItemBut = onView(withId(R.id.delete_news_item_image_view));
    public ViewInteraction messageAboutDelete = onView(withText("Are you sure you want to permanently delete the document? These changes cannot be reversed in the future."));
    public ViewInteraction newsRecyclerList = onView(withId(R.id.news_list_recycler_view));
    public ViewInteraction categoryTextInputStartIcon = onView(allOf(withId(R.id.text_input_start_icon), withParent(withParent(withParent(withId(R.id.news_item_category_text_input_layout))))));
    public ViewInteraction titleTextInputEndIcon = onView(allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_title_text_input_layout)))))));
    public ViewInteraction createDateTextInputEndIcon = onView(allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_create_date_text_input_layout)))))));
    public ViewInteraction createTimeInputEndIcon = onView(allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_publish_time_text_input_layout)))))));
    public ViewInteraction descriptionTextInputEndIcon = onView(allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_description_text_input_layout)))))));
    public ViewInteraction messageChangesWonTBeSaved = onView(withText("The changes won't be saved, do you really want to log out?"));




    public ViewInteraction wrongСategoryToast(String text) {
        return onView(withText(text)).inRoot(new DataHelper.ToastMatcher());
    }




}
