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
    static DrawLine drawLine;


    @Before
    public void setUp() throws Exception {
        mockCanvas =  Mockito.mock(Canvas.class);
        mockPaint = Mockito.mock(Paint.class);
        mockMotion  = Mockito.mock(MotionEvent.class);
        drawLine = new DrawLine (mockCanvas, mockPaint);
    }


    @Test
    public void testActionDown(){
        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
        when(mockMotion.getX()).thenReturn(10f);
        when(mockMotion.getY()).thenReturn(34f);
        DrawLine.Line line = drawLine.draw(mockMotion);
        assertEquals(line.getEndX(), -1, 0.00000001f);
        assertEquals(line.getEndY(), -1, 0.00000001f);
    }

    @Test
    public void testActionMove(){
        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(mockMotion.getX()).thenReturn(16f);
        when(mockMotion.getY()).thenReturn(24f);
        DrawLine.Line line = drawLine.draw(mockMotion);
        assertEquals(line.getEndX(), 16,0.00000001f);
        assertEquals(line.getEndY(), 24,0.00000001f);
    }

    @Test
    public void testActionUp(){
        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
        when(mockMotion.getX()).thenReturn(10f);
        when(mockMotion.getY()).thenReturn(34f);
        DrawLine.Line line = drawLine.draw(mockMotion);
        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(mockMotion.getX()).thenReturn(16f);
        when(mockMotion.getY()).thenReturn(24f);
        line = drawLine.draw(mockMotion);
        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_UP);
        when(mockMotion.getX()).thenReturn(16f);
        when(mockMotion.getY()).thenReturn(24f);
        line = drawLine.draw(mockMotion);
        verify(mockCanvas).drawLine(10, 34, 16, 24, mockPaint);
    }
}
