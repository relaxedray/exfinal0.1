package com.example.exfinal01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

public class AssistGroups extends AppCompatActivity {
    ListView grouplist;
    SharedPreferences sharedPreferences = getSharedPreferences("sharedprefs", MODE_PRIVATE);
    int usuid = sharedPreferences.getInt("usuid", 0);
    int rol = sharedPreferences.getInt("rol", 2);
    int profid = sharedPreferences.getInt("profid",0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assist_groups);
        grouplist = findViewById(R.id.gruposlist);
        if()



    }




}