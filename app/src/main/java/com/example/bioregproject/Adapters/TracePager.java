package com.example.bioregproject.Adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.bioregproject.R;
import com.example.bioregproject.ui.Traceability.ImageFlow.ImageFlow;
import com.example.bioregproject.ui.Traceability.ManualTraceablility.ManualForm;

public class TracePager extends FragmentStatePagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab1,R.string.tab2,R.string.tab3};
    private final Context mContext;

    public TracePager(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return ImageFlow.newInstance();
            case 1: return ManualForm.newInstance();
            default: return ImageFlow.newInstance();
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}

