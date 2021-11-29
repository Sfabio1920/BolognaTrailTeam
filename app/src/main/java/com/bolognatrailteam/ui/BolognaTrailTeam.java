package com.bolognatrailteam.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bolognatrailteam.R;
import com.bolognatrailteam.base.BaseActivity;
import me.ibrahimsn.lib.SmoothBottomBar;

public class BolognaTrailTeam extends BaseActivity{
    private SmoothBottomBar bottomNavigation;
    private FragmentManager fragmentTransaction= getSupportFragmentManager();
    private HomeFragment homeFragment=new HomeFragment();
    private PagamentiFragment pagamentiFragment =new PagamentiFragment();
    private SitoWebFragment sitoWebFragment=new SitoWebFragment();
    private MenuFragment menuFragment=new MenuFragment();
    private Fragment active = homeFragment;
    private int activeIndex=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        fragments();
    }
    public void addFragments(){
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container,pagamentiFragment).hide(pagamentiFragment).commit();
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container,sitoWebFragment).hide(sitoWebFragment).commit();
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container,menuFragment).hide(menuFragment).commit();
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container,homeFragment).hide(homeFragment).commit();
    }
    public void openFragment(Fragment fragment){
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).hide(active).show(fragment).commit();
        active=fragment;
    }
    private void fragments(){
        bottomNavigation.setOnItemSelectedListener(i->{
            switch(i){
                case 0:
                    openFragment(homeFragment);
                    return true;
                case 1:
                    openFragment(pagamentiFragment);
                    return true;
                case 2:
                    openFragment(sitoWebFragment);
                    return true;
                case 3:
                    openFragment(menuFragment);
                    return true;
            }
            return false;
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, @Nullable KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && sitoWebFragment.isVisible() && sitoWebFragment.onKeyDown()) {
            return true;
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK && pagamentiFragment.isVisible() && pagamentiFragment.onKeyDown()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onStart(){
        super.onStart();
        addFragments();
        switch(bottomNavigation.getItemActiveIndex()){
            case 0:
                openFragment(homeFragment);
                break;
            case 1:
                openFragment(pagamentiFragment);
                break;
            case 2:
                openFragment(sitoWebFragment);
                break;
            case 3:
                openFragment(menuFragment);
                break;
        }
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        switch(savedInstanceState.getInt("ACTIVE_INDEX",0)){
            case 0:
                openFragment(homeFragment);
                break;
            case 1:
                openFragment(pagamentiFragment);
                break;
            case 2:
                openFragment(sitoWebFragment);
                break;
            case 3:
                openFragment(menuFragment);
                break;
        }
        bottomNavigation.setItemActiveIndex(savedInstanceState.getInt("ACTIVE_INDEX",0));
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("ACTIVE_INDEX",bottomNavigation.getItemActiveIndex());
    }
    @Override
    protected void onStop(){
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).remove(homeFragment).remove(pagamentiFragment).remove(sitoWebFragment).remove(menuFragment).commit();
        activeIndex=bottomNavigation.getItemActiveIndex();
        super.onStop();
        boolean condizione=false;
        if(condizione){
            AppUtils.deleteCache(getApplicationContext());
        }
    }
}