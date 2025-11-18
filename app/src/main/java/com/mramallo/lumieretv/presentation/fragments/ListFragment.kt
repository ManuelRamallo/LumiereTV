package com.mramallo.lumieretv.presentation.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.OnItemViewSelectedListener
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter
import com.mramallo.lumieretv.data.model.Cast
import com.mramallo.lumieretv.data.model.Result
import com.mramallo.lumieretv.presentation.presenters.CastItemPresenter
import com.mramallo.lumieretv.presentation.presenters.ItemPresenter

class ListFragment : RowsSupportFragment() {

    private var itemSelectedListener: ((Result) -> Unit)? = null
    private var itemClickListener: ((Result) -> Unit)? = null
    private var navigateLeftListener: (() -> Unit)? = null
    private var currentRowViewHolder: ListRowPresenter.ViewHolder? = null

    private val listRowPresenter = object : ListRowPresenter(FocusHighlight.ZOOM_FACTOR_SMALL) {
        override fun isUsingDefaultListSelectEffect(): Boolean = false
    }.apply {
        shadowEnabled = false
    }

    private var rootAdapter: ArrayObjectAdapter = ArrayObjectAdapter(listRowPresenter)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = rootAdapter
        onItemViewSelectedListener = ItemViewSelectedListener()
        onItemViewClickedListener = ItemViewClickListener()

        verticalGridView?.apply {
            windowAlignment = 1
            val context = view.context
            val resources = context.resources
            val density = resources.displayMetrics.density
            val topMarginDp = 35
            val offsetPx = (topMarginDp * density).toInt()
            windowAlignmentOffset = offsetPx
            windowAlignmentOffsetPercent = 33f

            setOnKeyInterceptListener { event ->
                if (
                    navigateLeftListener != null &&
                    event?.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_DPAD_LEFT &&
                    isAtRowStart()
                ) {
                    navigateLeftListener?.invoke()
                    return@setOnKeyInterceptListener true
                }
                false
            }
        }

    }

    fun bindData(results: List<Result>?, title: String) {
        val arrayObjectAdapter = ArrayObjectAdapter(ItemPresenter())

        results?.forEach {
            arrayObjectAdapter.add(it)
        }

        val headerItem = HeaderItem(title)
        val listRow = ListRow(headerItem, arrayObjectAdapter)
        rootAdapter.add(listRow)
    }

    fun bindCastData(list: List<Cast>) {
        val arrayObjectAdapter = ArrayObjectAdapter(CastItemPresenter())

        list.forEach { content ->
            arrayObjectAdapter.add(content)
        }

        val headerItem = HeaderItem("Cast & Crew")
        val listRow = ListRow(headerItem, arrayObjectAdapter)
        rootAdapter.add(listRow)
    }

    fun setOnContentSelectedListener(listener: (Result) -> Unit) {
        this.itemSelectedListener = listener
    }

    fun setOnItemClickListener(listener: (Result) -> Unit) {
        this.itemClickListener = listener
    }

    fun setOnNavigateLeftRequest(listener: () -> Unit) {
        this.navigateLeftListener = listener
    }

    inner class ItemViewSelectedListener: OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is Result) {
                itemSelectedListener?.invoke(item)
            }
            currentRowViewHolder = rowViewHolder as? ListRowPresenter.ViewHolder
        }
    }

    inner class ItemViewClickListener: OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is Result) {
                itemClickListener?.invoke(item)
            }
        }

    }

    private fun isAtRowStart(): Boolean {
        val rowHolder = currentRowViewHolder ?: return false
        val gridView = rowHolder.gridView ?: return false
        return gridView.selectedPosition == 0
    }

    fun requestFocus(): View {
        val view = view
        view?.requestFocus()
        return view!!
    }

}