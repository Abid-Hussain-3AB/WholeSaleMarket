package com.example.myapplication.AdapterClasses;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.Fragments.AllProductFrag;
import com.example.myapplication.Fragments.ApparelFrag;
import com.example.myapplication.Fragments.JewelryFrag;

public class AdapterFragments extends FragmentStatePagerAdapter {
    private int tabCount;
    public AdapterFragments(@NonNull FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                return new AllProductFrag();
            case 1:
                return new ApparelFrag();
            case 2:
                return new JewelryFrag();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
