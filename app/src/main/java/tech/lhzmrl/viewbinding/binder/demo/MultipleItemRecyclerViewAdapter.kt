package tech.lhzmrl.viewbinding.binder.demo

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tech.lhzmrl.bind.viewbinding.demo.databinding.RvItemDarkBinding
import tech.lhzmrl.bind.viewbinding.demo.databinding.RvItemLightBinding
import tech.lhzmrl.viewbinding.binder.annotation.ViewBinding

import tech.lhzmrl.viewbinding.binder.demo.dummy.DummyContent.DummyItem

class MultipleItemRecyclerViewAdapter(
    private val values: List<DummyItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE_LIGHT = 1
        const val ITEM_TYPE_DARK = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if(position % 2 == 0) ITEM_TYPE_LIGHT else ITEM_TYPE_DARK
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_LIGHT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.rv_item_light, parent, false)
                LightViewHolder(view)
            }
            ITEM_TYPE_DARK -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.rv_item_dark, parent, false)
                DarkViewHolder(view)
            }
            else -> {
                // should not happen
                throw RuntimeException("You haven't support this kind of view type($viewType) now!! ")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val viewType = getItemViewType(position)) {
            ITEM_TYPE_LIGHT -> {
                onBindLightViewHolder(holder as LightViewHolder, position)
            }
            ITEM_TYPE_DARK -> {
                onBindDarkViewHolder(holder as DarkViewHolder, position)
            }
            else -> {
                // should not happen
                throw RuntimeException("You haven't support this kind of view type($viewType) now!! ")
            }
        }
    }

    private fun onBindLightViewHolder(holder: LightViewHolder, position: Int) {
        val item = values[position]
        holder.binding.itemNumber.text = item.id
        holder.binding.content.text = item.content

    }

    private fun onBindDarkViewHolder(holder: DarkViewHolder, position: Int) {
        val item = values[position]
        holder.binding.itemNumber.text = item.id
        holder.binding.content.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class LightViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @ViewBinding
        lateinit var binding: RvItemLightBinding

        init {
            binding = RvItemLightBinding.bind(view)
        }

        override fun toString(): String {
            return super.toString() + " '" + binding.content.text + "'"
        }
    }

    inner class DarkViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @ViewBinding
        lateinit var binding: RvItemDarkBinding

        init {
            binding = RvItemDarkBinding.bind(view)
        }

        override fun toString(): String {
            return super.toString() + " '" + binding.content.text + "'"
        }
    }

}