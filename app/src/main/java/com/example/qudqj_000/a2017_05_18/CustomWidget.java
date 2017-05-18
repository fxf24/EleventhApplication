package com.example.qudqj_000.a2017_05_18;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by qudqj_000 on 2017-05-18.
 */

public class CustomWidget extends View {
    String operationType="";
    float x1= -1, x2=-1, y1=-1, y2=-1;

    public CustomWidget(Context context) {
        super(context);
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public CustomWidget(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Bitmap bigimg = Bitmap.createScaledBitmap(img, img.getWidth()*2, img.getHeight()*2, false);

        int cenX = (this.getWidth() -bigimg.getWidth())/2;
        int cenY = (this.getHeight() -bigimg.getHeight())/2;

        if(operationType.equals("rotate"))
            canvas.rotate(45, getWidth()/2, getHeight()/2);

        float[] array={
                2,0,0,0,-25f,
                0,2,0,0,-25f,
                0,0,2,0,-25f,
                0,0,0,2,0
        };

        ColorMatrix colorMatrix = new ColorMatrix(array);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(filter);
        canvas.drawBitmap(bigimg, cenX, cenY, paint);
    }

    public void setOperationType(String operationType){
        this.operationType = operationType;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            x1 = event.getX();
            y1 = event.getY();
        }
        else if(event.getAction()==MotionEvent.ACTION_MOVE){

        }
        else if(event.getAction()==MotionEvent.ACTION_UP){
            x2 = event.getX();
            y2 = event.getY();
            invalidate();
        }

        return true;
    }
}
