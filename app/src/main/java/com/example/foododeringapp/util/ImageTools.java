package com.example.foododeringapp.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageTools {
    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     * @param activity
     * @return
     * @throws IOException
     */
    public static Bitmap getBitmapFromUri(Uri uri, Activity activity) throws IOException {
        InputStream inputStream = activity.getContentResolver().openInputStream(uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inDither = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeStream(inputStream, null, options);

        inputStream.close();

        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;
        if (originalWidth == -1 || originalHeight == -1) {
            return null;
        }

        float height = 800f;
        float width = 480f;
        int be = 1; //be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > width) {
            be = (int) (originalWidth / width);
        } else if (originalWidth < originalHeight && originalHeight > height) {
            be = (int) (originalHeight / height);
        }

        if (be <= 0) {
            be = 1;
        }
        BitmapFactory.Options bitmapOptinos = new BitmapFactory.Options();
        bitmapOptinos.inSampleSize = be;
        bitmapOptinos.inDither = true;
        bitmapOptinos.inPreferredConfig = Bitmap.Config.ARGB_8888;
        inputStream = activity.getContentResolver().openInputStream(uri);

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, bitmapOptinos);
        inputStream.close();

//        return bitmap;
        return compressImage(bitmap);
    }

    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     * @param activity
     * @return
     * @throws IOException
     */
//    public static String getBitmapFromImageView(Uri uri, Activity activity) throws IOException {
//        InputStream inputStream = activity.getContentResolver().openInputStream(uri);
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        options.inDither = true;
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        BitmapFactory.decodeStream(inputStream, null, options);
//
//        inputStream.close();
//
//
//        BitmapFactory.Options bitmapOptinos = new BitmapFactory.Options();
//        bitmapOptinos.inSampleSize = be;
//        bitmapOptinos.inDither = true;
//        bitmapOptinos.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        inputStream = activity.getContentResolver().openInputStream(uri);
//
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, bitmapOptinos);
//        inputStream.close();
//
//        return encodedString;
//    }

    /**
     * 质量压缩方法
     *
     * @param bitmap
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        int options = 100;
        while (byteArrayOutputStream.toByteArray().length / 1024 > 100) {
            byteArrayOutputStream.reset();
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差 ，第三个参数：保存压缩后的数据的流
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
            options -= 10;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Bitmap bitmapImage = BitmapFactory.decodeStream(byteArrayInputStream, null, null);

        String encodedString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        Log.i("encodingString",encodedString);
        return bitmapImage;
    }

    public static String ImageString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        int options = 100;
        while (byteArrayOutputStream.toByteArray().length / 1024 > 100) {
            byteArrayOutputStream.reset();
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差 ，第三个参数：保存压缩后的数据的流
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
            options -= 10;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Bitmap bitmapImage = BitmapFactory.decodeStream(byteArrayInputStream, null, null);
        String encodedString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        Log.i("encodingString",encodedString);

        return encodedString;
    }
}
