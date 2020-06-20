package com.example.bkfoodcourt.Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bkfoodcourt.R
import com.google.android.gms.common.internal.service.Common
import de.hdodenhof.circleimageview.CircleImageView

class MyCategoriesAdapter(
        internal var context: Context,
        internal var cateGoriesList: List<CategoryModel>
    ) : RecyclerView.Adapter<MyCategoriesAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var category_name: TextView? = null

        var category_image: ImageView? = null

        init {
            category_name = itemView.findViewById(R.id.category_name) as TextView
            category_image = itemView.findViewById(R.id.category_image) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoriesAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return if(cateGoriesList.size == 1)
            com.example.bkfoodcourt.Common.Common.DEFAULT_COLUMN_COUNT
        else {
            if(cateGoriesList.size % 2 == 0)
                com.example.bkfoodcourt.Common.Common.DEFAULT_COLUMN_COUNT
            else
                if(position > 1 && position == cateGoriesList.size - 1)
                    com.example.bkfoodcourt.Common.Common.FULL_WIDTH_CONLUMN
                else
                    com.example.bkfoodcourt.Common.Common.DEFAULT_COLUMN_COUNT
        }
    }

    override fun getItemCount(): Int {
        return cateGoriesList.size
    }

    override fun onBindViewHolder(holder: MyCategoriesAdapter.MyViewHolder, position: Int) {
        Glide.with(context).load(cateGoriesList.get(position).image).into(holder.category_image!!)
        holder.category_name!!.setText(cateGoriesList.get(position).name)
    }
}