package com.example.administrator.news.Cache;

import android.os.Environment;
import android.util.Log;
import android.util.MalformedJsonException;
import android.widget.Toast;

import com.example.administrator.news.MainActivity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/21.
 */

public class FileCache implements CacheInterface {
    protected File storeFile;
    //存储文件的路径
    public static final String External_Directory =Environment.getExternalStorageDirectory().getPath();
    public static final String Save_Directory=External_Directory+"/News";
    public static final String Bitmap_Save_File=Save_Directory+"/Bitmap";
    protected void makedirs(String url)
    {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(MainActivity.activityContext,"请确认Sd卡",Toast.LENGTH_LONG).show();
            return;
        }
          url = url.substring(External_Directory.length()+1,url.length());
        String []lujing = url.split("\\/");
        File file =null;
        String path =External_Directory;
        for(int i=0;i<lujing.length-1;i++)
        {
            path +=("/"+lujing[i]);
            file = new File(path);
            if(!file.exists())
            {
                boolean is =file.mkdirs();
            }
        }
    }
    protected void checkFileExists(String url)
    {
        //建立缓存文件夹
        makedirs(url);
        storeFile = new File(url);
        if(!storeFile.exists())
        {

            try {
              boolean is =  storeFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void add(String url, Object o) {
        checkFileExists(url);
    }

    @Override
    public void remove(Object o) {
        checkFileExists((String) o);
        storeFile.delete();
    }

    @Override
    public Object get(String url) {
        checkFileExists(url);
        return null;
    }
}
