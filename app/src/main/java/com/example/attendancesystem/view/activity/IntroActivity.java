package com.example.attendancesystem.view.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.adapter.IntroAdapter;
import com.example.attendancesystem.model.IntroContent;
import com.example.attendancesystem.storage.SaveUser;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager intro_viewpager;
    private TabLayout intro_tab_indecator;
    private Button intro_start_btn;
    private TextView intro_next_textview;
    int position;
    private IntroAdapter introAdapter;
    Animation into_start_btn_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        intro_viewpager = findViewById(R.id.intro_viewpager_id);
        intro_tab_indecator = findViewById(R.id.into_tab_indecator_id);
        intro_next_textview = findViewById(R.id.intro_next_textview_id);
        intro_start_btn = findViewById(R.id.intro_start_btn_id);
        into_start_btn_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        final List<IntroContent> intro_content_list = new ArrayList<>();
        String intro_details = getResources().getString(R.string.intro_details);
        String intro_contacts = getResources().getString(R.string.intro_contacts);
        String intro_blood_donation = getResources().getString(R.string.intro_blood_donation);
        String intro_find_location = getResources().getString(R.string.intro_find_location);

        intro_content_list.add(new IntroContent(intro_contacts, intro_details, R.drawable.resume));
        intro_content_list.add(new IntroContent(intro_blood_donation, intro_details, R.drawable.course_allo));
        intro_content_list.add((new IntroContent(intro_find_location, intro_details, R.drawable.attendance_icon)));

        introAdapter = new IntroAdapter(this, intro_content_list);
        intro_viewpager.setAdapter(introAdapter);
        intro_tab_indecator.setupWithViewPager(intro_viewpager);

        intro_next_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = intro_viewpager.getCurrentItem();
                if (position < intro_content_list.size()) {
                    position++;
                    intro_viewpager.setCurrentItem(position);
                }
                if (position == intro_content_list.size() - 1) {
                    intro_load_last_Screen();
                }
            }
        });
        intro_tab_indecator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == intro_content_list.size() - 1) {
                    intro_load_last_Screen();
                    ;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        intro_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveUser().introSave(getApplicationContext(),true);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
    }


    private void intro_load_last_Screen() {
        intro_next_textview.setVisibility(View.INVISIBLE);
        intro_start_btn.setVisibility(View.VISIBLE);
        intro_tab_indecator.setVisibility(View.INVISIBLE);
        intro_start_btn.setAnimation(into_start_btn_anim);
    }
}
