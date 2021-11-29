package com.bolognatrailteam.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.preference.PreferenceFragmentCompat;
import com.bolognatrailteam.R;
import com.google.android.material.transition.MaterialFadeThrough;

import java.util.prefs.PreferenceChangeEvent;

public class MenuFragment extends PreferenceFragmentCompat {
    MaterialFadeThrough materialFadeThrough=new MaterialFadeThrough();
    public MenuFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materialFadeThrough.setDuration(500);
        setEnterTransition(materialFadeThrough);
    }
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey){
        addPreferencesFromResource(R.xml.preferences);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=super.onCreateView(inflater,container,savedInstanceState);
        assert view != null;
        view.setBackground(ResourcesCompat.getDrawable(getResources(),R.color.materialBackground,null));
        return view;
    }
}