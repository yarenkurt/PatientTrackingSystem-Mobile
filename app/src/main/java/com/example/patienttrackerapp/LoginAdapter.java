package com.example.patienttrackerapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LoginAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> stringList=new ArrayList<>();

    public LoginAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position
        );
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    public void addFragment(Fragment fragment,String title){
        fragmentList.add(fragment);
        stringList.add(title);
    }


/*private  Context context;
int totalTabs;

public LoginAdapter(FragmentManager fm,Context context,int totalTabs){
    super(fm);
    this.context=context;
    this.totalTabs=totalTabs;
}

    @NonNull
    @Override
    public Fragment getItem(int position) {
     switch (position){
         case 0:
             LoginTabFragment loginTabFragment=new LoginTabFragment();
             return loginTabFragment;
         case 1:
             SignUpTabFragment signUpTabFragment=new SignUpTabFragment();
             return  signUpTabFragment;
         default:
             return null;
     }
    }

    @Override
    public int getCount() {

    return totalTabs;
    }*/
}
