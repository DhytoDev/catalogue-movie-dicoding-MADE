package com.dhytodev.cataloguemovie.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dhytodev.cataloguemovie.ui.main.nav_menu.nowplaying.NowPlayingFragment;
import com.dhytodev.cataloguemovie.ui.main.nav_menu.upcoming.UpcomingFragment;


/**
 * Created by TRIPOD STUDIO on 10/14/2015.
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    String[] title = new String[] { "Now Playing", "Upcoming" };



    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null ;
        switch (position) {
            case 0:
                fragment = new NowPlayingFragment();
                break;
            case 1:
                fragment = new UpcomingFragment();
                break;

            default:
                fragment = null;
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return title[position];
    }

    @Override
    public int getCount() {

        return title.length;
    }
}
