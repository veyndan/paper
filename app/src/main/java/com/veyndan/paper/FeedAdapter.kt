package com.veyndan.paper

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bumptech.glide.Glide
import org.jetbrains.anko.AnkoContext
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class FeedAdapter(private val items: List<Item>) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.ViewHolder {
        return FeedAdapter.ViewHolder(parent, ItemUi())
    }

    override fun onBindViewHolder(holder: FeedAdapter.ViewHolder, position: Int) {
        val item = items[position]

        Glide.with(holder.itemView.context)
                .load(item.profile)
                .into(holder.component.profile)

        holder.component.name.text = item.username

        holder.component.date.text = PrettyTime().format(Date(item.date * 1000L))

        // Sets height of ImageView while image is waiting to be loaded into it.
        holder.component.image.aspectRatio(item.width, item.height, Aspect.WIDTH)

        Glide.with(holder.itemView.context)
                .load(item.image)
                .into(holder.component.image)

        holder.component.text.text = item.text
    }

    override fun getItemCount() = items.size

    class ViewHolder(parent: ViewGroup, val component: ItemUi)
        : RecyclerView.ViewHolder(component.createView(AnkoContext.create(parent.context, parent)))
}