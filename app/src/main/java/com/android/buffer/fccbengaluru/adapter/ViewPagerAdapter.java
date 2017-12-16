package com.android.buffer.fccbengaluru.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.fragment.AboutFragment;
import com.android.buffer.fccbengaluru.fragment.EventFragment;
import com.android.buffer.fccbengaluru.fragment.HomeFragment;

import static com.android.buffer.fccbengaluru.util.Constants.TAB_ABOUT;
import static com.android.buffer.fccbengaluru.util.Constants.TAB_EVENTS;
import static com.android.buffer.fccbengaluru.util.Constants.TAB_HOME;

/**
 * Created by incred on 7/12/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] titles = {TAB_HOME, TAB_EVENTS, TAB_ABOUT};
    private int[] drawableId = {R.drawable.ic_home_white_24dp, R.drawable.ic_event_white_24dp, R.drawable.ic_person_white_24dp};
    private Context mContext;

    public ViewPagerAdapter(Context context, final FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /*
    set icons on tab
     */
    public void setIcons(final TabLayout tabLayout) {
        //set tab layout icons
        for (int count = 0; count < tabLayout.getTabCount(); count++) {
            tabLayout.getTabAt(count).setCustomView(getTabView(count));
        }
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/item_row_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_row_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.tvTabTitle);
        tv.setText(titles[position]);
        ImageView img = (ImageView) v.findViewById(R.id.ivTabIcon);
        img.setImageResource(drawableId[position]);
        return v;
    }

    @Override
    public Fragment getItem(final int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = HomeFragment.getInstance();
                break;
            case 1:
                fragment = EventFragment.getInstance();
                break;
            case 2:
                fragment = AboutFragment.getInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return titles[position];
    }
}
