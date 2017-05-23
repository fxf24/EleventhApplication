package com.example.qudqj_000.a2017_05_18;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
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
    boolean isCheckboxChecked, rotateImage, moveImage, biggerImage, skewImage;
    int oldX = -1, oldY = -1;
    Matrix matrix = new Matrix();

    public MyPainter(Context context) {
        super(context);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
    }

    public MyPainter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
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
        if(rotateImage){
            img = setRotate(img);
            matrix.postRotate(-30);
        }
        else if(moveImage){
            x +=10;
            y +=10;
        }
        else if(biggerImage){
            img = setScale(img);
            matrix.postScale(.667f,.667f);
        }
        else if(skewImage){
            img = setSkew(img);
            matrix.postSkew(-0.2f, 0);
        }

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
        clear();
        try {
            FileInputStream in = new FileInputStream(file_name);
            Bitmap img = BitmapFactory.decodeStream(in);
            matrix.postScale(.5f,.5f);
            img = Bitmap.createBitmap(img,0,0,img.getWidth(),img.getHeight(),matrix,true);
            mCanvas.drawBitmap(img,getWidth()/4,getHeight()/4,mPaint);
            matrix.postScale(2,2);
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
            Toast.makeText(getContext(), "지울수 없음", Toast.LENGTH_SHORT).show();
        }
        invalidate();
    }

    public void setPenWidth(int size){
        if(size==5)
            mPaint.setStrokeWidth(5);
        else{
            mPaint.setStrokeWidth(3);
        }
    }

    public void setBlurring(boolean tf){
        if(tf) {
            BlurMaskFilter blur = new BlurMaskFilter(15, BlurMaskFilter.Blur.INNER);
            mPaint.setMaskFilter(blur);
        }
        else{
            mPaint.setMaskFilter(null);
        }
    }

    public void setColoring(boolean tf){
        if(tf) {
            float[] array = {
                    2, 0, 0, 0, -25f,
                    0, 2, 0, 0, -25f,
                    0, 0, 2, 0, -25f,
                    0, 0, 0, 2, 0
            };

            ColorMatrix colorMatrix = new ColorMatrix(array);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
            mPaint.setColorFilter(filter);
        }
        else{
            mPaint.setColorFilter(null);
        }
    }

    public void setPenColorRed(){
        mPaint.setColor(Color.RED);
    }

    public  void setPenColorBlue(){
        mPaint.setColor(Color.BLUE);
    }

    public Bitmap setRotate(Bitmap bitmap){
        matrix.postRotate(30);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    public void setRotateImage(boolean rotateImage){
        this.rotateImage = rotateImage;
    }

    public void setTranslate(boolean move){
        this.moveImage = move;
    }

    public void setBigImage(boolean scale){
        this.biggerImage = scale;
    }
    public Bitmap setScale(Bitmap bitmap){
        matrix.postScale(1.5f, 1.5f);
        return Bitmap.createBitmap(bitmap, 0,0,bitmap.getWidth(), bitmap.getHeight(),matrix,true);
    }

    public void setSkewImage(boolean skewImage){
        this.skewImage = skewImage;
    }
    public Bitmap setSkew(Bitmap bitmap){
        matrix.postSkew(.2f, 0);
        return Bitmap.createBitmap(bitmap, 0,0,bitmap.getWidth(), bitmap.getHeight(),matrix,true);
    }
}
