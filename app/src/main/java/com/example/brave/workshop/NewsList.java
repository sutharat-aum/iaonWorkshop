package com.example.brave.workshop;

import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class NewsList extends AppCompatActivity {
    private ListView lvNewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        create_lvNewList();
    }

    private void create_lvNewList() {
        lvNewList = (ListView) findViewById(R.id.lvNewList);
        lvNewList.setAdapter(new CustomAdapter(getApplicationContext()));

    }
}
