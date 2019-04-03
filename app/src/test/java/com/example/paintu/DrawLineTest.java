package com.example.paintu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void testActionDown(){
        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
        when(mockMotion.getX()).thenReturn(10f);
        when(mockMotion.getY()).thenReturn(34f);
        DrawLine drawLine = new DrawLine (mockCanvas, mockPaint);
        DrawLine.Line line = drawLine.draw(mockMotion);
        assertEquals(line.getEndX(), -1);
        assertEquals(line.getEndY(), -1);
    }

    @Test
    public void testActionMove(){
        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(mockMotion.getX()).thenReturn(16f);
        when(mockMotion.getY()).thenReturn(24f);
        DrawLine drawLine = new DrawLine (mockCanvas, mockPaint);
        DrawLine.Line line = drawLine.draw(mockMotion);
        assertEquals(line.getEndX(), 16);
        assertEquals(line.getEndY(), 34);
    }

    @Test
    public void testActionUp(){
        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_UP);
        when(mockMotion.getX()).thenReturn(16f);
        when(mockMotion.getY()).thenReturn(24f);
        verify(mockCanvas).drawLine(10, 34, 16, 34, mockPaint);
    }
}
