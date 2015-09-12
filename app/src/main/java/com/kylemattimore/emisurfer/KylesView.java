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
    public KylesView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    private Paint paint = new Paint();
    private Path path = new Path();

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0.0f, 0.0f, 100.0f, 100.0f, paint);
        canvas.drawPath(path, paint);
        try {
            Thread.sleep(1);
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
                    path.addCircle(eventX, eventY, 1.0f, Path.Direction.CW);
                    break;
                case MotionEvent.ACTION_UP:
                    // nothing to do
                    break;
                default:
                    return false;
            }
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }
}
