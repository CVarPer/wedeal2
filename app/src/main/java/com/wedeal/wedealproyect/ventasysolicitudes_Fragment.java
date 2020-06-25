package com.wedeal.wedealproyect;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;


public class ventasysolicitudes_Fragment extends Fragment {
    TabItem ventas, solicitudes;
    ViewPager viewPager;
    TabLayout tablayout2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ventasysolicitudes, container, false);

        tablayout2 = view.findViewById(R.id.tab_layout2);
        //tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager = view.findViewById(R.id.vista_pags2);
        //tablayout.setupWithViewPager(viewPager);

        ventas = view.findViewById(R.id.tab_ventas);
        solicitudes = view.findViewById(R.id.tab_solicitudes);
        tablayout2.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#ffffff"));

        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), tablayout2.getTabCount())); //setAdapter(PagerAdapter)
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout2));

        tablayout2.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //pagerAdapter = new PagerController(getChildFragmentManager(),tablayout.getTabCount());
        //viewPager.setAdapter();
        //viewPager


        return view;

    }
    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new venta_historial_Fragment();
                case 1:
                    return new solicitudes_Fragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

}
