package ru.iteco.fmhandroid.ui.page;

import androidx.test.espresso.ViewInteraction;
import ru.iteco.fmhandroid.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class AboutPageElements {
    public ViewInteraction aboutPageTitle = onView(withText("Version:"));
    public ViewInteraction aboutPagePrivacyPolicy = onView(withText("Privacy Policy:"));
    public ViewInteraction aboutPageTermsOfUse = onView(withText("Terms of use:"));

    public ViewInteraction aboutCompanyInfo = onView(withId(R.id.about_company_info_label_text_view));

    public ViewInteraction aboutPrivacyPolicyValueTextView = onView(withId(R.id.about_privacy_policy_value_text_view));

    public ViewInteraction aboutTermsOfUseValueTextView = onView(withId(R.id.about_terms_of_use_value_text_view));
    public ViewInteraction aboutBackImageBut = onView(withId(R.id.about_back_image_button));
}
