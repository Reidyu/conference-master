package com.example.franklin.conference.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.franklin.conference.fragment.AttendFragment;
import com.example.franklin.conference.fragment.EstablishFragment;
import com.example.franklin.conference.fragment.InformationFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return AttendFragment.newInstance();
            case 1:
                return EstablishFragment.newInstance();
            case 2:
                return InformationFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "参加会议";
            case 1:
                return "我要发布";
            case 2:
                return "我的消息";
        }
        return null;
    }
}