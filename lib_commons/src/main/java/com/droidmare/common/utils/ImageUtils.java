package com.droidmare.common.utils;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;

import java.io.ByteArrayOutputStream;

//utils for managing bitmap images
//@author Eduardo on 27/05/2019.

public class ImageUtils {

    //Method that scales a bitmap ensuring that its size is not to big to being sent within an Intent:
    private static Bitmap scaleBitmap (Bitmap bitmapImage) {

        Bitmap scaledBitmap = bitmapImage;

        boolean mustBeScaled = false;

        //The maximum width and height shall be 300 pixels:
        int maxScaledSize = 300;

        int scaledWidth = maxScaledSize;
        int scaledHeight = maxScaledSize;

        int bitmapWidth = bitmapImage.getWidth();
        int bitmapHeight = bitmapImage.getHeight();

        //The operation is performed in a fashion that ensures that the biggest parameter (usually the width) is, at maximum, 300 pixels, scaling the other parameter accordingly:
        if (bitmapWidth > scaledWidth) {
            mustBeScaled = true;
            scaledHeight = scaledWidth * bitmapHeight / bitmapWidth;
            if (scaledHeight > maxScaledSize) {
                scaledHeight = maxScaledSize;
                scaledWidth = scaledHeight * bitmapWidth / bitmapHeight;
            }
        }

        else if (bitmapHeight > scaledHeight) {
            mustBeScaled = true;
            scaledWidth = scaledHeight * bitmapWidth / bitmapHeight;
            if (scaledWidth > maxScaledSize) {
                scaledWidth = maxScaledSize;
                scaledHeight = scaledWidth * bitmapHeight / bitmapWidth;
            }
        }

        //If the image must be scaled, the createScaledBitmap method from the Bitmap class is used:
        if (mustBeScaled) scaledBitmap = Bitmap.createScaledBitmap(bitmapImage, scaledWidth, scaledHeight, false);

        return scaledBitmap;
    }

    //Method that transforms a Bitmap into a String:
    public static String encodeBitmapImage(Bitmap bitmap) {

        if (bitmap == null) return null;

        bitmap = scaleBitmap(bitmap);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //The image is compressed to avoid an excessive length of the string, thus preventing transmission and storage issues:
        bitmap.compress(Bitmap.CompressFormat.WEBP, 80, byteArrayOutputStream);

        byte[] bitmapByteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(bitmapByteArray, Base64.DEFAULT);
    }

    //Method that transforms a String into a Bitmap:
    public static Bitmap decodeBitmapString(String encodedString) {

        byte[] encodedBitmapByteArray = Base64.decode(encodedString, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(encodedBitmapByteArray, 0, encodedBitmapByteArray.length);
    }

    //Method that scales the given pixel value based on the device density:
    public static int scalePixelValue(Context context, int pixValue) {

        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case 160:
                return (int) (pixValue * 1.5); //mdpi
            case 320:
                return (int) (pixValue * 0.75); //xhdpi
            case 480:
                return (int) (pixValue * 0.5); //xxhdpi
            case 640:
                return (int) (pixValue * 0.375); //xxxhdpi
        }

        //hdpi or any other density:
        return pixValue;
    }

    //Method that obtains a Drawable from an image stored within the Assets directory:
    public static Drawable getImageFromAssets(Context context, String name) {
        try {
            return Drawable.createFromStream(context.getAssets().open(name), null);
        } catch (Exception e) {
            return null;
        }
    }
}