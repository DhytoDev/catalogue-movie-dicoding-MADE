package com.dhytodev.cataloguemovie.ui.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.ui.main.nav_menu.nowplaying.NowPlayingFragment;
import com.dhytodev.cataloguemovie.ui.main.nav_menu.upcoming.UpcomingFragment;


/**
 * Created by TRIPOD STUDIO on 10/14/2015.
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context ;

    String[] title ;

    public TabFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context ;
        title = new String[] {context.getString(R.string.now_playing), context.getString(R.string.upcoming)};
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
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
