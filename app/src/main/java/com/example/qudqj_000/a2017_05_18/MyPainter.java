package com.example.qudqj_000.a2017_05_18;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by qudqj_000 on 2017-05-18.
 */

public class MyPainter extends View {
    Bitmap mBitmap;
    Canvas mCanvas;
    Paint mPaint = new Paint();
    boolean isCheckboxChecked;
    int oldX = -1, oldY = -1;

    public MyPainter(Context context) {
        super(context);
        mPaint.setColor(Color.BLACK);
    }

    public MyPainter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.WHITE);
    }

    private void drawStamp(int x, int y){
        Bitmap img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mCanvas.drawBitmap(img, x, y, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mBitmap!=null)
            canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int)event.getX();
        int Y = (int)event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            oldX = X;
            oldY = Y;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
           if(oldX !=-1) {
               mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
               invalidate();
               oldX = X;
               oldY = Y;
           }
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(oldX !=-1) {
                mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
                invalidate();
            }
            if(isCheckboxChecked) {
                drawStamp(X, Y);
            }
            oldX = -1;
            oldY = -1;
        }

        return true;
    }

    public boolean Save(String file_name){
        try {
            FileOutputStream out = new FileOutputStream(file_name, false);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return false;
    }

    public boolean Open(String file_name){
        try {
            FileInputStream in = new FileInputStream(file_name);
            mCanvas.drawBitmap(BitmapFactory.decodeStream(in),0,0,mPaint);
            in.close();
            invalidate();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setCheckboxChecked(boolean isCheckboxChecked){
        this.isCheckboxChecked = isCheckboxChecked;
    }
    public void clear(){
        try {
            mBitmap.eraseColor(Color.WHITE);
        }
        catch(IllegalStateException e){
            Toast.makeText(getContext(), "바꿀수 없음", Toast.LENGTH_SHORT).show();
        }
        invalidate();
    }
}
