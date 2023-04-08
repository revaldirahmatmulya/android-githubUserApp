package com.revaldi.githubapp.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.revaldi.githubapp.R

class ViewPagerAdapter(private val mCtx: Context, fm:FragmentManager, data:Bundle) : FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    private var bundleFragment: Bundle
    init {
        bundleFragment = data
    }
    @StringRes
    private var TAB_TITLE = intArrayOf(R.string.tab_1,R.string.tab_2)
    override fun getCount(): Int {
       return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment:Fragment?=null
        when(position){
            0 -> fragment = FragmentFollowing()
            1 -> fragment = FragmentFollowers()
        }
        fragment?.arguments=this.bundleFragment
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mCtx.resources.getString(TAB_TITLE[position])
    }
}