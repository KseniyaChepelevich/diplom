package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.AllOf.allOf;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.core.IsInstanceOf;

import ru.iteco.fmhandroid.R;

public class AuthPageElements {
    public ViewInteraction screenName =
            onView(allOf(withText("Authorization"), withParent(withParent(withId(R.id.nav_host_fragment)))));
    public ViewInteraction loginField =
            onView(allOf(withHint("Login"), withParent(withParent(withId(R.id.login_text_input_layout)))));
    public ViewInteraction passField =
            onView(allOf(withHint("Password"), withParent(withParent(withId(R.id.password_text_input_layout)))));
    public ViewInteraction signBtn =
            onView(allOf(withId(R.id.enter_button), withText("SIGN IN"), withContentDescription("Save"), withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))));
}
