package com.example.myapplication.AdapterClasses;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.Admin.Fragments.ApprovedShops;
import com.example.myapplication.Admin.Fragments.RequestedShops;
import com.example.myapplication.Buyer.Fragments.AllProductFrag;
import com.example.myapplication.Buyer.Fragments.ApparelFrag;
import com.example.myapplication.Buyer.Fragments.JewelryFrag;

public class AdapterFragmentsAdmin extends FragmentStatePagerAdapter {
    private int tabCount;
    public AdapterFragmentsAdmin(@NonNull FragmentManager fm, int tabCount) {
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
                return new ApprovedShops();
            case 1:
                return new RequestedShops();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
