package net.onesandzeros.zero.photocast.presentation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.onesandzeros.zero.photocast.presentation.fragment.PhotoGridFragment;
public class PagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGES = 3;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
        case 0:
            return PhotoGridFragment.newInstance(PhotoGridFragment.getItemBundle("photo"));
        case 1:
            return PhotoGridFragment.newInstance(PhotoGridFragment.getItemBundle("album"));
        case 2:
            return PhotoGridFragment.newInstance(PhotoGridFragment.getItemBundle("video"));

        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return "Photos";
        case 1:
            return "Albums";
        case 2:
            return "Videos";

        }
        return null;
    }

}
