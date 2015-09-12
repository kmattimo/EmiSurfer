package com.kylemattimore.emisurfer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cours on 9/12/2015.
 */
public class KylesView extends View {
    private Paint strokePaint = new Paint();
    private Paint bgPaint = new Paint();
    private Path path = new Path();

    Double multiplier = 1.0;
    boolean dotToggle = false;
    boolean flashToggle = false;
    boolean secondColor = false;

    public KylesView(Context context, AttributeSet attrs) {
        super(context, attrs);

        strokePaint.setAntiAlias(true);
        strokePaint.setStrokeWidth(6f);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeJoin(Paint.Join.ROUND);

        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.CYAN);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(flashToggle) {
            if (bgPaint.getColor() == Color.CYAN) {
                bgPaint.setColor(Color.YELLOW);
            } else {
                bgPaint.setColor(Color.CYAN);
            }
        }
        canvas.drawRect(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), bgPaint);
        canvas.drawPath(path, strokePaint);
        try {
            Thread.sleep( (long) ( multiplier.doubleValue() /2 ) );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            float eventX = event.getX(i);
            float eventY = event.getY(i);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    if(dotToggle) {
                        path.addCircle(eventX, eventY, 1.0f, Path.Direction.CW);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    // nothing to do
                    path.reset();
                    break;
                default:
                    return false;
            }
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }

    public void setMultiplier(float val) {
        multiplier = (double) val;
    }

    public void setDotToggle(boolean checked) {
        dotToggle = checked;
    }
    public void setFlashToggle(boolean checked) {
        flashToggle = checked;
    }
    public void flash(int barValue) {
        if(secondColor) {
            secondColor = false;
            bgPaint.setColor(Color.BLACK);
        }
        else {
            secondColor = true;
            bgPaint.setColor(Color.GRAY);
        }

        invalidate();

    }

    public double getMultiplier() {
        return multiplier;
    }
}
