package com.example.administrator.news.Cache;

import android.graphics.Bitmap;

import com.example.administrator.news.MainActivity;
import com.example.administrator.news.Util.BitmapUtil;
import com.example.administrator.news.Util.DataUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/21.
 */

public class BitmapFileCache extends FileCache {
    @Override
    public void add(String url, Object o) {
      super.add(url,o);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(storeFile);
            fileOutputStream.write(BitmapUtil.bitmap2Byte((Bitmap) o));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(String url) {
        super.get(url);
        Bitmap bitmap = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(storeFile);
            bitmap = DataUtil.readBitmapFromInputStream(fileInputStream);
            return  bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
