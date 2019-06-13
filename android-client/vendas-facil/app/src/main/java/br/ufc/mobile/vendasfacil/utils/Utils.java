package br.ufc.mobile.vendasfacil.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utils {

    /**
     * @param bitmap the Bitmap to be scaled
     * @param threshold the maxium dimension (either width or height) of the scaled bitmap
     * @param isNecessaryToKeepOrig is it necessary to keep the original bitmap? If not recycle the original bitmap to prevent memory leak.
     * */
    public static Bitmap getScaledDownBitmap(Bitmap bitmap, int threshold, boolean isNecessaryToKeepOrig){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = width;
        int newHeight = height;

        if(width > height && width > threshold){
            newWidth = threshold;
            newHeight = (int)(height * (float)newWidth/width);
        }

        if(width > height && width <= threshold){
            //the bitmap is already smaller than our required dimension, no need to resize it
            return bitmap;
        }

        if(width < height && height > threshold){
            newHeight = threshold;
            newWidth = (int)(width * (float)newHeight/height);
        }

        if(width < height && height <= threshold){
            //the bitmap is already smaller than our required dimension, no need to resize it
            return bitmap;
        }

        if(width == height && width > threshold){
            newWidth = threshold;
            newHeight = newWidth;
        }

        if(width == height && width <= threshold){
            //the bitmap is already smaller than our required dimension, no need to resize it
            return bitmap;
        }

        return getResizedBitmap(bitmap, newWidth, newHeight, isNecessaryToKeepOrig);
    }

    private static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight, boolean isNecessaryToKeepOrig) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        if(!isNecessaryToKeepOrig){
            bm.recycle();
        }
        return resizedBitmap;
    }

    public static List<MarkerOptions> sortListbyDistance(List<MarkerOptions> markers, final Location location){
        Collections.sort(markers, new Comparator<MarkerOptions>() {
            @Override
            public int compare(MarkerOptions marker2, MarkerOptions marker1) {
                if(getDistanceBetweenPoints(marker1.getPosition().latitude, marker1.getPosition().longitude,
                        location.getLatitude(), location.getLongitude()) >
                        getDistanceBetweenPoints(marker2.getPosition().latitude, marker2.getPosition().longitude,
                                location.getLatitude(), location.getLongitude())){
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return markers;
    }

    public static float getDistanceBetweenPoints(double firstLatitude, double firstLongitude, double secondLatitude, double secondLongitude) {
        float[] results = new float[1];
        Location.distanceBetween(firstLatitude, firstLongitude, secondLatitude, secondLongitude, results);
        return results[0];
    }

}
