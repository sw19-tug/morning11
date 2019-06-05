package com.example.paintu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


public class DeepFryFilterTest {
    Canvas mockCanvas;
    Bitmap mockBitmap;
    DeepFryFilter deepFryFilter;
    Paint mockPaint;

    @Before
    public void setUp() throws Exception{
        mockCanvas =  Mockito.mock(Canvas.class);
        mockPaint = Mockito.mock(Paint.class);
        mockBitmap = Mockito.mock(Bitmap.class);
        deepFryFilter = new DeepFryFilter(mockCanvas, mockBitmap);
    }

    @Test
    public void testBlackAndWhiteFilter() {
        deepFryFilter.applyFilter();
        verify(mockCanvas).drawBitmap(eq(mockBitmap), eq(0f), eq(0f), any(Paint.class));
    }
}
