package com.example.privatevpn

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyvpAdapter(fa:FragmentManager): FragmentStatePagerAdapter(fa,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var fragmentList : ArrayList<Fragment> =ArrayList()
    var fragmenttitle : ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }

    fun addFragmnet(fragment: Fragment,title:String){
        fragmentList.add(fragment)
        fragmenttitle.add(title)

    }


}



//    override fun createFragment(position: Int): Fragment {
//        return when(position){
//            0 -> AllFragment()
//            1 -> StreamingFragment()
//            2 -> GamingFragment()
//            else -> AllFragment()
//        }
//    }
