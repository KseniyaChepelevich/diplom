package ru.iteco.fmhandroid.ui.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import ru.iteco.fmhandroid.R;

public class CustomRecyclerViewActions {

    public static class RecyclerViewMatcher {
        private final int recyclerViewId;

        public RecyclerViewMatcher(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return atPositionOnView(position, -1);
        }

        public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

            return new TypeSafeMatcher<View>() {
                Resources resources = null;
                View childView;

                public void describeTo(Description description) {
                    String idDescription = Integer.toString(recyclerViewId);
                    if (this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(recyclerViewId);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = String.format("%s (resource name not found)",
                                    new Object[]{Integer.valueOf
                                            (recyclerViewId)});
                        }
                    }

                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {

                    this.resources = view.getResources();

                    if (childView == null) {
                        RecyclerView recyclerView =
                                (RecyclerView) view.getRootView().findViewById(recyclerViewId);
                        if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                        } else {
                            return false;
                        }
                    }

                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }

                }
            };
        }

        public static BoundedMatcher<View, RecyclerView> matchChildViewisNotExist(int targetViewId, Matcher<View> itemMatcher ){
            return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
                @Override
                public void describeTo(Description description) {
                    description.appendText("Does not have view id $targetViewId with matches $itemMatcher");
                }

                @Override
                protected boolean matchesSafely(RecyclerView recyclerView) {
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();

                    for (int i = 0; i < adapter.getItemCount(); i++){
                        int itemType = adapter.getItemViewType(i);
                        RecyclerView.ViewHolder viewHolder = adapter.createViewHolder(recyclerView, itemType);
                        adapter.bindViewHolder(viewHolder, i);

                        View targetView = viewHolder.itemView.findViewById(targetViewId);

                        if (itemMatcher.matches(targetView)) {
                            return false; // Found match
                            }
                        }
                    return true;
                    }

                };
            };
        }



    /*public static class CustomViewMatcher {
        public static Matcher<View> recyclerViewSizeMatcher(final int matcherSize) {
            return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

                String result;

                @Override
                public void describeTo(Description description) {
                    description.appendText("Item count: " + matcherSize);
                    description.appendText("\nGot: ");
                    description.appendText("result");
                }

                @Override
                protected boolean matchesSafely(RecyclerView recyclerView) {
                    int items = recyclerView.getAdapter().getItemCount();
                    result = "List size: " + items;
                    return matcherSize == items;

                }
            };

        }
    }*/

    public static int getItemCount(RecyclerView recyclerView) {
        if (recyclerView != null)
            return recyclerView.getAdapter().getItemCount();
        return 0;

    }


}
