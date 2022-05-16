package com.example.myapplication.AdapterClasses;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.Admin.Fragments.ApprovedShops;
import com.example.myapplication.Admin.Fragments.RequestedShops;
import com.example.myapplication.Buyer.Fragments.AllProductFrag;
import com.example.myapplication.Buyer.Fragments.ApparelFrag;
import com.example.myapplication.Buyer.Fragments.ConstructionFrag;
import com.example.myapplication.Buyer.Fragments.CosmeticsFrag;
import com.example.myapplication.Buyer.Fragments.ElectronicsFrag;
import com.example.myapplication.Buyer.Fragments.FertilizerFrag;
import com.example.myapplication.Buyer.Fragments.GroceriesFrag;
import com.example.myapplication.Buyer.Fragments.HomeAppliancesFrag;
import com.example.myapplication.Buyer.Fragments.JewelryFrag;
import com.example.myapplication.Buyer.Fragments.VehiclePartsFrag;

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
                return new ElectronicsFrag();
            case 2:
                return new GroceriesFrag();
            case 3:
                return new HomeAppliancesFrag();
            case 4:
                return new CosmeticsFrag();
            case 5:
                return new JewelryFrag();
            case 6:
                return new ApparelFrag();
            case 7:
                return new VehiclePartsFrag();
            case 8:
                return new ConstructionFrag();
            case 9:
                return new FertilizerFrag();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
