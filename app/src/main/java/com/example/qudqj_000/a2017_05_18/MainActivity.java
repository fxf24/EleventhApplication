package com.example.qudqj_000.a2017_05_18;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    CustomWidget customWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customWidget = (CustomWidget)findViewById(R.id.custom_widget);
    }

    public void onClick(View v){
        customWidget.setOperationType((String)v.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"Bluring");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1){
            customWidget.setOperationType("Blur");
        }

        return super.onOptionsItemSelected(item);
    }
}
