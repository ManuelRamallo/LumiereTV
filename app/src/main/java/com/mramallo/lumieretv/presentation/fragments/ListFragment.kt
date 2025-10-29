package com.mramallo.lumieretv.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.mramallo.lumieretv.data.model.CastResponse
import com.mramallo.lumieretv.domain.model.DataModel
import com.mramallo.lumieretv.domain.model.Detail
import com.mramallo.lumieretv.presentation.presenters.CastItemPresenter
import com.mramallo.lumieretv.presentation.presenters.ItemPresenter

class ListFragment : RowsSupportFragment() {

    private var itemSelectedListener: ((Detail) -> Unit)? = null
    private var itemClickListener: ((Detail) -> Unit)? = null

    private val listRowPresenter =  object : ListRowPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM) {
        override fun isUsingDefaultListSelectEffect(): Boolean {
            return false
        }
    }.apply {
        shadowEnabled = false
    }

    private var rootAdapter: ArrayObjectAdapter = ArrayObjectAdapter(listRowPresenter)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = rootAdapter

        onItemViewSelectedListener = ItemViewSelectedListener()
        onItemViewClickedListener = ItemViewClickListener()
    }

    fun binData(dataList: DataModel) {
        dataList.result.forEachIndexed { index, result ->
            val arrayObjectAdapter = ArrayObjectAdapter(ItemPresenter())

            result.details.forEach {
                arrayObjectAdapter.add(it)
            }

            val headerItem = HeaderItem(result.title)
            val listRow = ListRow(headerItem, arrayObjectAdapter)
            rootAdapter.add(listRow)
        }
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

    fun setOnContentSelectedListener(listener: (Detail) -> Unit){
        this.itemSelectedListener = listener
    }

    fun setOnItemClickListener(listener: (Detail) -> Unit){
        this.itemClickListener = listener
    }

    inner class ItemViewSelectedListener: OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if(item is Detail){
                itemSelectedListener?.invoke(item)
            }
        }
    }

    inner class ItemViewClickListener: OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if(item is Detail){
                itemClickListener?.invoke(item)
            }
        }

    }

}