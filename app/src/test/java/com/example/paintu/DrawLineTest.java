package com.example.paintu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

public class DrawLineTest {

    Canvas mockCanvas;
    Paint mockPaint;
    MotionEvent mockMotion;

    @Before
    public void setUp() throws Exception {
        mockCanvas =  Mockito.mock(Canvas.class);
        mockPaint = Mockito.mock(Paint.class);
        mockMotion  = Mockito.mock(MotionEvent.class);
    }


    @Test
    public void testDrawLine(){
        DrawLine drawLineClass = new DrawLine(mockCanvas, mockPaint);

        boolean result = drawLineClass.drawLine(mockMotion);

        assertTrue(result);

    }

}
