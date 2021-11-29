package com.bolognatrailteam.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bolognatrailteam.R;
import com.google.android.material.transition.MaterialFadeThrough;
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager;
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter;
import com.michalsvec.singlerowcalendar.selection.CalendarDetailsLookup;

import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment{
    private MaterialFadeThrough materialFadeThrough=new MaterialFadeThrough();
    private Calendar cal=Calendar.getInstance();
    private int currentMonth=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        materialFadeThrough.setDuration(500);
        setEnterTransition(materialFadeThrough);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        cal.setTime(new Date());
        currentMonth=cal.get(Calendar.MONTH);
        CalendarViewManager myCalManager=new CalendarViewManager(){
            @Override
            public int setCalendarViewResourceId(int i, Date date, boolean b){
                Calendar cal=Calendar.getInstance();
                cal.setTime(date);
                if(b){
                    return R.layout.selected_calendar_item;
                }
                else{
                    return R.layout.unselected_calendar_item;
                }
            }

            @Override
            public void bindDataToCalendarView(SingleRowCalendarAdapter.CalendarViewHolder calendarViewHolder, Date date, int i, boolean b){

            }
        };
    }
}