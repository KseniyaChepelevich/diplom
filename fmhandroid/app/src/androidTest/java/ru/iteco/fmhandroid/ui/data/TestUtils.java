package ru.iteco.fmhandroid.ui.data;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Checks.checkNotNull;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

import static org.hamcrest.EasyMock2Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.AdapterView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;

public class TestUtils {
    public static <VH extends RecyclerView.ViewHolder> ViewAction actionOnItemViewAtPosition(int position,
                                                                                             @IdRes
                                                                                                     int viewId,
                                                                                             ViewAction viewAction) {
        return new ActionOnItemViewAtPositionViewAction(position, viewId, viewAction);
    }

    private static final class ActionOnItemViewAtPositionViewAction<VH extends RecyclerView
            .ViewHolder>
            implements

            ViewAction {
        private final int position;
        private final ViewAction viewAction;
        private final int viewId;

        private ActionOnItemViewAtPositionViewAction(int position,
                                                     @IdRes int viewId,
                                                     ViewAction viewAction) {
            this.position = position;
            this.viewAction = viewAction;
            this.viewId = viewId;
        }

        public Matcher<View> getConstraints() {
            return Matchers.allOf(new Matcher[]{
                    ViewMatchers.isAssignableFrom(RecyclerView.class), ViewMatchers.isDisplayed()
            });
        }

        public String getDescription() {
            return "actionOnItemAtPosition performing ViewAction: "
                    + this.viewAction.getDescription()
                    + " on item at position: "
                    + this.position;
        }

        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            (new ScrollToPositionViewAction(this.position)).perform(uiController, view);
            uiController.loopMainThreadUntilIdle();

            View targetView = recyclerView.getChildAt(this.position).findViewById(this.viewId);

            if (targetView == null) {
                throw (new PerformException.Builder()).withActionDescription(this.toString())
                        .withViewDescription(

                                HumanReadables.describe(view))
                        .withCause(new IllegalStateException(
                                "No view with id "
                                        + this.viewId
                                        + " found at position: "
                                        + this.position))
                        .build();
            } else {
                this.viewAction.perform(uiController, targetView);
            }
        }
    }

    private static final class ScrollToPositionViewAction implements ViewAction {
        private final int position;

        private ScrollToPositionViewAction(int position) {
            this.position = position;
        }

        public Matcher<View> getConstraints() {
            return Matchers.allOf(new Matcher[]{
                    ViewMatchers.isAssignableFrom(RecyclerView.class), ViewMatchers.isDisplayed()
            });
        }

        public String getDescription() {
            return "scroll RecyclerView to position: " + this.position;
        }

        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.scrollToPosition(this.position);
        }
    }


    public static CustomRecyclerViewActions.RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {

        return new CustomRecyclerViewActions.RecyclerViewMatcher(recyclerViewId);
    }

    public static ViewInteraction waitView(Matcher<View> matcher) {
        onView(isRoot()).perform(ViewActions
                .waitElement(matcher, 20000));
        return onView((matcher));

    }

    public static ViewInteraction waitPopupView(Matcher<View> matcher) {
        onView(isRoot()).inRoot((RootMatchers.isPlatformPopup())).perform(ViewActions
                .waitElement(matcher, 10000));
        return onView((matcher));

    }




    public static ViewInteraction onViewWithTimeout(Matcher<View> matcher) {
        int retries = 10;
        int retryDelayMs = 500;
        ViewAssertion retryAssertion= matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE));
        for (int i = 0; i < retries; i++)
         {

            try {
                ViewInteraction viewInteraction = Espresso.onView(matcher);
                viewInteraction.check(retryAssertion);
                return viewInteraction;
            } catch (NoMatchingViewException e) {
                if (i >= retries) {
                    throw (e);
                } else {
                    try {
                        Thread.sleep(retryDelayMs);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        throw new AssertionError("View matcher is broken for $matcher");
    }

    public static String getDateToString(int date) {
        if (date < 10) {
            return "0" + date;
        }
        return Integer.toString(date);
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static Matcher<View> withAdaptedData(final Matcher<Object> dataMatcher) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("with class name: ");
                dataMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof AdapterView)) {
                    return false;
                }

                @SuppressWarnings("rawtypes")
                Adapter adapter = ((AdapterView) view).getAdapter();
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (dataMatcher.matches(adapter.getItem(i))) {
                        return true;
                    }
                }

                return false;
            }
        };
    }

    public static Matcher<Object> withItemContent(String expectedText) {
        checkNotNull(expectedText);
        return withItemContent(equalTo(expectedText));
    }

    /*return new BoundedMatcher<Object, Map>(Map.class) {
        @Override
        public boolean matchesSafely(Map map) {
            return hasEntry(equalTo("STR"), itemTextMatcher).matches(map);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("with item content: ");
            itemTextMatcher.describeTo(description);
        }
    };*/

}
