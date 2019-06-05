package com.example.paintu;
import android.content.Context;
import android.content.SharedPreferences;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

public class ColorBlindModeTest {
    Context context;

    @Before
    public void before() throws Exception {
        this.sharedPrefs = Mockito.mock(SharedPreferences.class);
        this.context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
    }

    @Test
    public void testGetValidToken() throws Exception {
        Mockito.when(sharedPrefs.getString(anyString(), anyString())).thenReturn("test123");
        assertEquals("test123", Auth.getValidToken(context));
    }
}
