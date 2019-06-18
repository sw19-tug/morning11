package com.example.paintu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DrawCircleTest {
    Canvas mockCanvas;
    Paint mockPaint;
    MotionEvent mockMotion;
    DrawCircle drawCircle;

    @Before
    public void setUp() throws Exception{
        mockCanvas =  Mockito.mock(Canvas.class);
        mockPaint = Mockito.mock(Paint.class);
        mockMotion  = Mockito.mock(MotionEvent.class);
        drawCircle = new DrawCircle(mockCanvas, mockPaint);
    }

    @Test
    public void testDrawCircle(){
        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
        when(mockMotion.getX()).thenReturn(4f);
        when(mockMotion.getY()).thenReturn(4f);

        DrawCircle.Circle circle = drawCircle.draw(mockMotion);
        assertNull(circle);


        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(mockMotion.getX()).thenReturn(8f);
        when(mockMotion.getY()).thenReturn(8f);
        circle = drawCircle.draw(mockMotion);

        float radius = calculateRadius(4f, 4f, 8f, 8f);
        Assert.assertEquals(circle.getCenterX(), 6f, 0.00000001f);
        Assert.assertEquals(circle.getCenterY(), 6f, 0.00000001f);
        Assert.assertEquals(circle.getRadius(), radius, 0.00000001f);


        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_UP);
        when(mockMotion.getX()).thenReturn(8f);
        when(mockMotion.getY()).thenReturn(8f);
        circle = drawCircle.draw(mockMotion);
        assertNull(circle);
        verify(mockCanvas).drawCircle(6f, 6f, radius, mockPaint);

    }
    private float calculateRadius(float x1, float y1, float x2, float y2)
    {
       float calculateRadius = (float) Math.sqrt(Math.pow((x2 - x1), 2) - Math.pow((y2 - y1), 2));

       return calculateRadius;
    }

}
