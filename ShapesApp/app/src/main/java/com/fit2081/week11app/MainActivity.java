package com.fit2081.week11app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    CustomView view;
    View constraint_layout;
    int shape2Draw = Shape.CIRCLE;
//    int shape2Draw = Shape.RECTANGLE;

    GestureDetector gestureDetector;
    ScaleGestureDetector scaleGestureDetector;

    int finger_id_1;
    int finger_id_2;
    int x1;
    int y1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.myView);
        constraint_layout = findViewById(R.id.constraint_id);
//        view.addShape(new Circle(10,20,100));
//        view.addShape(new Rectangle(100,250,100,100));
//        view.clearShapes();
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        scaleGestureDetector = new ScaleGestureDetector(this, new MyScaleGestureDetector());

        constraint_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                scaleGestureDetector.onTouchEvent(event);

                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    x1 = (int) event.getX();
                    y1 = (int) event.getY();
                }

//                if (event.getPointerCount()==1) {
//                    finger_id_1 = event.getPointerId(0);
//                } else if (event.getPointerCount()==2) {
//                    finger_id_1 = event.getPointerId(0);
//                    finger_id_2 = event.getPointerId(1);
//
//                      event.getX(event.findPointerIndex(finger_id_1));
//                }

                return true;
            }
        });
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public void onLongPress(MotionEvent e) {
            view.clearShapes();
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            switch(shape2Draw) {
                case Shape.CIRCLE:
                    shape2Draw = Shape.RECTANGLE;
                    Toast.makeText(getApplicationContext(), "Rectangle", Toast.LENGTH_SHORT).show();
                    return true;
                case Shape.RECTANGLE:
                    shape2Draw = Shape.CIRCLE;
                    Toast.makeText(getApplicationContext(), "Circle", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            addShape(e);
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            addShape(e2);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        private boolean addShape(MotionEvent e) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            switch(shape2Draw) {
                case Shape.CIRCLE:
                    view.addShape(new Circle(x, y,100));
                    return true;
                case Shape.RECTANGLE:
                    view.addShape(new Rectangle(x, y, x+100, y+100));
                    return true;
            }
            return false;
        }
    }

    class MyScaleGestureDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            int scale = (int) (detector.getScaleFactor() * 100);
            if(shape2Draw == Shape.CIRCLE) {
                view.addShape(new Circle(x1, y1, scale));
            } else {
                view.addShape(new Rectangle(x1, y1, x1 + scale, y1 + scale));
            }
        }
    }
}
