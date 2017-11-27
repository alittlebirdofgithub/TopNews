package com.example.administrator.news.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;

import com.example.administrator.news.MainActivity;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/4/17.
 */

public class BitmapUtil {

    public static Bitmap byte2Bitmap(ByteArrayOutputStream byteArrayOutputStream)
    {
        byte[]bytes = byteArrayOutputStream.toByteArray();
        DataUtil.closeStream(byteArrayOutputStream);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
    public static byte[] bitmap2Byte(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes= byteArrayOutputStream.toByteArray();

        DataUtil.closeStream(byteArrayOutputStream);
        return bytes;
    }
   public static Bitmap getScaledBitmap(Bitmap bitmap,int desWidth,int desHeight)
   {
       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
       byte[]bytes = byteArrayOutputStream.toByteArray();
       final BitmapFactory.Options options = new BitmapFactory.Options();
       options.inJustDecodeBounds=true;
       BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
       options.inSampleSize = calculateInSampleSize(options,desWidth,desHeight);
       options.inJustDecodeBounds=false;
       return BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
   }
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if(reqWidth==0||reqHeight==0)
            return 1;
        final int height =options.outHeight;
        final  int width = options.outWidth;

        int inSamplesize=1;
        //思想:缩小后的高度或宽度只要有一个小于要求值就可以了。
        if(height>reqHeight||width>reqWidth)
        {
            final int halfHeight =height/2;
            final int halfWidth = width/2;
            while((halfHeight/inSamplesize)>=reqHeight&&(halfWidth/inSamplesize)>=reqWidth)
            {
                inSamplesize*=2;
            }
        }

        return inSamplesize;

    }
}
