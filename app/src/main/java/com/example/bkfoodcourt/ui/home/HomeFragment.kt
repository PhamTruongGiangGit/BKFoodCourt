package com.example.bkfoodcourt.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.asksira.loopingviewpager.LoopingViewPager
import com.example.bkfoodcourt.Model.MyBestDealsApdapter
import com.example.bkfoodcourt.Model.MyPopularCategoriesAdapter
import com.example.bkfoodcourt.Model.PopularCategoryModel
import com.example.bkfoodcourt.R
import com.example.bkfoodcourt.ui.gallery.GalleryViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    var recyclerView:RecyclerView?=null
    var viewPager:LoopingViewPager?=null
    var unbinder : Unbinder?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        unbinder = ButterKnife.bind(this, root)
        initView(root)

        homeViewModel.popularList.observe(viewLifecycleOwner, Observer {
            val listData = it
            val adapter = MyPopularCategoriesAdapter(requireContext(), listData)
            recyclerView!!.adapter = adapter
        })

        homeViewModel.bestDealList.observe(viewLifecycleOwner, Observer {
            val adapter = MyBestDealsApdapter(requireContext(), it, false)
            viewPager!!.adapter = adapter
        })
        return root
    }

    private fun initView(root:View) {
        viewPager = root.findViewById(R.id.viewpager) as LoopingViewPager
        recyclerView = root.findViewById(R.id.recycler_popular) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager= LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    /*
    override fun onResume() {
        super.onResume()
        viewPager!!.resumeAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        viewPager!!.pauseAutoScroll()
    }

     */
}