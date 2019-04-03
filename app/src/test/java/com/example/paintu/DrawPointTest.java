package com.example.paintu;
import android.app.slice.SliceItem;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class DrawPointTest {
    Canvas MockCanvas;
    Paint MockPaint;
    MotionEvent MockMotion;
    MotionEvent mockEvent = mock(MotionEvent.class);

    @Before
    public void setUp() throws Exception{
        MockCanvas =  Mockito.mock(Canvas.class);
        MockPaint = Mockito.mock(Paint.class);
        MockMotion  = Mockito.mock(MotionEvent.class);
        when(mockEvent.getAction()).thenReturn(MotionEvent.ACTION_UP);
        when(mockEvent.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
    }


    @Test
    public void testActionDown(){
        when(mockEvent.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
                when(mockEvent.getX()).thenReturn(10f);
                when(mockEvent.getY()).thenReturn((20f));


        DrawPoint testClass1 = new DrawPoint(MockCanvas, MockPaint);

        boolean result = testClass1.drawPoint(mockEvent);

        verify(MockCanvas).drawPoint(10, 20, MockPaint);

    }

    @Test
    public void TestActionUp(){
        when(mockEvent.getAction()).thenReturn(MotionEvent.ACTION_UP);
        when(mockEvent.getX()).thenReturn(10f);
        when(mockEvent.getY()).thenReturn((20f));

        DrawPoint testClass1 = new DrawPoint(MockCanvas, MockPaint);

        boolean result = testClass1.drawPoint(mockEvent);

        assertFalse(result);

    }
}