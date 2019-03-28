package com.example.paintu;

import static org.mockito.Mockito.*;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DrawPathTest {

    // canvas object
    Canvas mockedCanvas;
    Path mockedPath;
    Paint mockedPaint;
    MotionEvent mockedMotionEvent;

    // convert canvas into bitmap try

    @Before
    public void setUp() throws Exception {
        mockedCanvas = mock(Canvas.class);
        mockedPaint = mock(Paint.class);
        mockedPath = mock(Path.class);
        mockedMotionEvent = mock(MotionEvent.class);
    }


    @Test
    public void testGetColor()
    {
        // Init / Arange
        DrawPath testClass1 = new DrawPath(mockedCanvas, mockedPaint);

        // Act
        when(testClass1.getColor()).thenReturn(123);

        // Verify / Assert
        assertEquals(123, testClass1.getColor());
    }

    @Test
    public void testDraw()
    {
        DrawPath testClass2 = new DrawPath(mockedCanvas, mockedPaint);

        // event 001
        MotionEvent e001 = mock(MotionEvent.class);
        when(e001.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
        when(e001.getX()).thenReturn(10);
        when(e001.getY()).thenReturn(10);
        testClass2.draw(e001);

        // event 002
        MotionEvent e002 = mock(MotionEvent.class);
        when(e002.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(e002.getX()).thenReturn(11);
        when(e002.getY()).thenReturn(11);
        testClass2.draw(e002);

        // event 003
        MotionEvent e003 = mock(MotionEvent.class);
        when(e003.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(e003.getX()).thenReturn(12);
        when(e003.getY()).thenReturn(12);
        testClass2.draw(e003);

        // event 004
        MotionEvent e004 = mock(MotionEvent.class);
        when(e004.getAction()).thenReturn(MotionEvent.ACTION_UP);
        when(e004.getX()).thenReturn(13);
        when(e004.getY()).thenReturn(13);
        testClass2.draw(e004);


        //verify(testClass2).draw(mockedMotionEvent);
        verify(testClass2).draw(e001);
        verify(testClass2, never()).drawPath(mockedPath, mockedPaint);

        verify(testClass2).draw(e002);
        verify(testClass2, never()).drawPath(mockedPath, mockedPaint);

        verify(testClass2).draw(e003);
        verify(testClass2, never()).drawPath(mockedPath, mockedPaint);

        verify(testClass2).draw(e004);
        verify(mockedCanvas).drawPath(mockedPath, mockedPaint);
    }
}
