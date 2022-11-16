package ru.iteco.fmhandroid.ui.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.List;

public class CustomViewAssertion {

    public static ViewAssertion itemViewMatches(int viewID,
                                                final Matcher<View> viewMatcher) {
        assertNotNull(viewMatcher);

        return (view, noViewException) -> {
            if (noViewException != null) {
                throw noViewException;
            }

            assertTrue(view instanceof RecyclerView);
            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();

            for (int i = 0; i < adapter.getItemCount(); i++) {
                int itemType = adapter.getItemViewType(i);
                RecyclerView.ViewHolder viewHolder = adapter.createViewHolder(recyclerView, itemType);
                adapter.bindViewHolder(viewHolder, i);

                View targetView = viewHolder.itemView.findViewById(viewID);

                if (viewMatcher.matches(targetView)) {
                    return; // Found match
                }
            }

            fail("No match found");
        };
    }


    public static BoundedMatcher<View, RecyclerView> notListed(String cardName, int targetViewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {

                description.appendText("Has not view id $targetViewId for item with name $cardName");
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                int itemCount = recyclerView.getAdapter().getItemCount();
                for (int i = 0; i < itemCount; i++) {
                    RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
                    if (holder != null) {
                        TextView cardNameView = (TextView) holder.itemView.findViewById(targetViewId);
                        if (cardNameView.getText().toString() == cardName) {

                            return false;

                        }

                    }
                    ;

                }

                return true;

            }
        };
    }

    public static boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!((List<?>) tasks).isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}