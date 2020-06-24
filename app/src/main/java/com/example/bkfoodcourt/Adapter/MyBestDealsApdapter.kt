package com.example.bkfoodcourt.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.example.bkfoodcourt.Model.BestDealModel
import com.example.bkfoodcourt.R

class MyBestDealsApdapter(context: Context, itemList:List<BestDealModel>, isInfinite:Boolean) : LoopingPagerAdapter<BestDealModel>(context, itemList, isInfinite)  {
    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        val imageView = convertView!!.findViewById<ImageView>(R.id.img_best_deal)

        val textView = convertView!!.findViewById<TextView>(R.id.txt_best_deal)
        Glide.with(context).load(itemList?.get(listPosition)?.image).into(imageView)
        textView.text = itemList?.get(listPosition)?.name
    }

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.layout_best_deals_item, container!!, false)
    }

}