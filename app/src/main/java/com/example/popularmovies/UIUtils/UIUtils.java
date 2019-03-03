package com.example.popularmovies.UIUtils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class UIUtils {

    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(RecyclerView recyclerView) {

        RecyclerView.Adapter recyclerViewAdapter = recyclerView.getAdapter();
        if (recyclerViewAdapter != null) {

            int numberOfItems = recyclerViewAdapter.getItemCount();

            // Get total height of all items.
            int totalItemsHeight = 200*numberOfItems;

            //for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            //    View item= recyclerView.getLayoutManager().findViewByPosition(itemPos);
            //    if (item!=null) {
            //        item.measure(0, 0);
            //        totalItemsHeight += item.getMeasuredHeight();
            //    }
            //}

            // Get total height of all item dividers.
            int totalDividersHeight = recyclerView.getHeight()*
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            recyclerView.setLayoutParams(params);
            recyclerView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
}
