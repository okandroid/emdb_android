package com.epigra.emdb.utils.behavior


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RecyclerViewScrollListener(private val scrollCallback: ScrollCallback) : RecyclerView.OnScrollListener() {

    var firstVisibleItem: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    /**
     * The minimum amount of items to have below current scroll position before loading more.
     */
    var visibleThreshold = 6

    /**
     * The total number of items in the data set after the last load.
     */
    private var previousTotal = 0

    /**
     * True if still waiting for the last set of data to load.
     */
    private var loading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = recyclerView.layoutManager!!.itemCount

        firstVisibleItem = when {
            recyclerView.layoutManager is StaggeredGridLayoutManager -> (recyclerView.layoutManager as StaggeredGridLayoutManager)
                .findFirstCompletelyVisibleItemPositions(null)[0]
            else -> (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstVisibleItemPosition()
        }

        if (loading && totalItemCount > previousTotal) {
            loading = false
            previousTotal = totalItemCount
        }

        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached invoke the callback.
            scrollCallback.onScrollCompleted(firstVisibleItem, false)
            loading = true
        }
    }

    fun reset() {
        previousTotal = 0
        totalItemCount = 0
        firstVisibleItem = 0
    }

    interface ScrollCallback {
        fun onScrollCompleted(firstVisibleItem: Int, isLoadingMoreData: Boolean)
    }
}