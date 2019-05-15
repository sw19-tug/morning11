package com.example.paintu;
import  android.view.MotionEvent;
import android.graphics.Paint;
import android.graphics.Canvas;



public class DrawCircle {
    private Paint paint;
    private Canvas canvas;
    private Circle circle;
    private float start_x;
    private float start_y;
    private float end_x;
    private float end_y;


    public DrawCircle(Canvas canvas, Paint paint) {
        this.canvas = canvas;
        this.paint = paint;
        this.circle = new Circle();
    }

    public Circle draw(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            start_x = x;
            start_y = y;
            return null;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            end_x = x;
            end_y = y;
            circle.center_x = (end_x + start_x)/2;
            circle.center_y = (end_y + start_y)/2;
            circle.radius = calculateRadius(circle.center_x, circle.center_y, start_x, start_y);
            return circle;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            canvas.drawCircle(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), paint);
            return null;
        }

        return null;

    }

    public class Circle{
        private float center_x, center_y, radius;

        public Circle( float center_x, float center_y, float radius){
            this.center_x = center_x;
            this.center_y = center_y;
            this.radius = radius;
        }

        public Circle(){}

        public float getCenterX() {
            return center_x;
        }

        public float getCenterY() {
            return center_y;
        }

        public float getRadius() {
            return radius;
        }


        public void setStarting_x(float starting_x) {
            this.center_x = starting_x;
        }

        public void setStarting_y(float starting_y) {
            this.center_y = starting_y;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }
    }


    private float calculateRadius(float x1, float y1, float x2, float y2) {
        float calculateRadius = (float) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));

        return calculateRadius;
    }
}
