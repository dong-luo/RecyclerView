package com.mhome.myapplication;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ScrollingActivity extends AppCompatActivity {
    private static final String TAG = "Tag";
    private int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        images = new int[]{R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4};
        ImageView iv = (ImageView) findViewById(R.id.iv_toolbar);
        Glide.with(this).load(getIntent().getIntExtra(TAG,0)).into(iv);
        CollapsingToolbarLayout tb= (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        tb.setTitle(getIntent().getStringExtra("subtitle"));

    }
}
