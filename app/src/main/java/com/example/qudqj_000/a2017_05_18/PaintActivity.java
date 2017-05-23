package com.example.qudqj_000.a2017_05_18;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.File;


public class PaintActivity extends AppCompatActivity {
    CheckBox checkBox;
    MyPainter myPainter;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        program();
        permissionCheck();
        makeDirectory();
        deleteFile();
    }

    void program() {
        checkBox = (CheckBox) findViewById(R.id.checkbox1);
        myPainter = (MyPainter) findViewById(R.id.myPainter);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myPainter.setCheckboxChecked(isChecked);
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.erase) {
            myPainter.clear();
        }
        if (v.getId() == R.id.open) {
            String path = getExternalPath() + "img/sample.png";

            if (myPainter.Open(path)) {
                Toast.makeText(this, "오픈", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "파일이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
        if (v.getId() == R.id.save) {
            String path = getExternalPath() + "img/sample.png";

            if (myPainter.Save(path)) {
                Toast.makeText(this, "저장완료", Toast.LENGTH_SHORT).show();
            }
        }
        if (v.getId() == R.id.rotate) {
            checkBox.setChecked(true);
            if (count == 0) {
                myPainter.setRotateImage(true);
                count++;
            } else {
                myPainter.setRotateImage(false);
                count = 0;
            }

        }
        if (v.getId() == R.id.move) {
            checkBox.setChecked(true);
            if (count == 0) {
                myPainter.setTranslate(true);
                count++;
            } else {
                myPainter.setTranslate(false);
                count = 0;
            }

        }
        if (v.getId() == R.id.scale) {
            checkBox.setChecked(true);
            if (count == 0) {
                myPainter.setBigImage(true);
                count++;
            } else {
                myPainter.setBigImage(false);
                count = 0;
            }

        }
        if (v.getId() == R.id.skew) {
            checkBox.setChecked(true);
            if (count == 0) {
                myPainter.setSkewImage(true);
                count++;
            } else {
                myPainter.setSkewImage(false);
                count = 0;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Blurring");
        menu.add(0, 2, 1, "Coloring");
        menu.add(0, 3, 2, "Pen Width Big");
        menu.add(1, 4, 3, "Pen Color RED");
        menu.add(1, 5, 4, "Pen Color BLUE");
        menu.add(1, 6, 5, "Pen Color Black");

        menu.setGroupCheckable(0, true, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            if (item.isChecked()) {
                item.setChecked(false);
                myPainter.setBlurring(false);
            } else {
                item.setChecked(true);
                myPainter.setBlurring(true);
            }
        } else if (item.getItemId() == 2) {
            if (item.isChecked()) {
                item.setChecked(false);
                myPainter.setColoring(false);
            } else {
                item.setChecked(true);
                myPainter.setColoring(true);
            }
        } else if (item.getItemId() == 3) {
            if (item.isChecked()) {
                myPainter.setPenWidth(3);
                item.setChecked(false);
            } else {
                myPainter.setPenWidth(5);
                item.setChecked(true);
            }
        } else if (item.getItemId() == 4) {
            myPainter.setPenColorRed();
        } else if (item.getItemId() == 5) {
            myPainter.setPenColorBlue();
        } else if (item.getItemId() == 6) {
            myPainter.setPenColorBlack();
        }

        return super.onOptionsItemSelected(item);
    }

    void makeDirectory() {
        String path = getExternalPath();

        File file = new File(path + "img");
        file.mkdir();
    }

    void permissionCheck() {
        int permissioninfo = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissioninfo == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(getApplicationContext(),"SDcard 쓰기권한있음",Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "권한설명", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    public String getExternalPath() {
        String sdPath = "";
        String ext = Environment.getExternalStorageState();
        if (ext.equals(Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"; //sdPath = "/mnt/sdcard/";
        } else
            sdPath = getFilesDir() + "";
        return sdPath;
    }

    void deleteFile() {
        String path = getExternalPath() + "img/sample.png";

        File file = new File(path);
        file.delete();
    }
}
