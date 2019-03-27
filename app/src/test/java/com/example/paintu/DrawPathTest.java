package com.example.paintu;

import static org.mockito.Mockito.*;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DrawPathTest {

    // canvas object
    Canvas mockedCanvas;
    Path mockedPath;
    Paint mockedPaint;

    // convert canvas into bitmap try

    @Before
    public void setUp() throws Exception {
        mockedCanvas = mock(Canvas.class);
        mockedPaint = mock(Paint.class);
        mockedPath = mock(Path.class);
    }


    @Test
    public void testSetColor()
    {
        // Init / Arange
        DrawPath testClass1 = new DrawPath(mockedCanvas, mockedPaint);

        // Act
        testClass1.setColor(0xFFFFFFFF);

        // Verify / Assert
        assertEquals(0xFFFFFFFF, testClass1.getColor());
    }

}
