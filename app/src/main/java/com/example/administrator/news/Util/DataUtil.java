package com.example.administrator.news.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import com.example.administrator.news.Bean.NewsBean;
import com.example.administrator.news.Fragment.BaseFragment;
import com.example.administrator.news.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/4/15.
 */

public class DataUtil {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public static int getUnDifferienceViewID() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    /**
     * 从输入流中读取json转成对应对象。
     * @param in
     * @return
     */
    public static ArrayList<NewsBean> jsonToNewsBeanByJsonReader(InputStream in)
    {
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(in,"UTF-8"));
            ArrayList<NewsBean>newsBeen = new ArrayList<>();
            NewsBean newsBean = null;
            jsonReader.beginArray();
            String[]bitmaps=new String[3];
            while (jsonReader.hasNext())
            {
                int i=0;
                newsBean = new NewsBean();
                jsonReader.beginObject();
                while(jsonReader.hasNext()) {
                    switch (jsonReader.nextName()) {
                        case "author_name":
                            newsBean.author_name = jsonReader.nextString();
                            break;
                        case "category":
                            newsBean.category = jsonReader.nextString();
                            break;
                        case "date":
                            newsBean.date = jsonReader.nextString();
                            break;
                        case "thumbnail_pic_s":
                            i++;
                            bitmaps[0]=jsonReader.nextString();
                            break;
                        case "thumbnail_pic_s02":
                            i++;
                            bitmaps[1]=jsonReader.nextString();
                            break;
                        case "thumbnail_pic_s03":
                            i++;
                            bitmaps[2]=jsonReader.nextString();
                            break;
                        case "title":
                            newsBean.title=jsonReader.nextString();
                            break;
                        case "uniquekey":
                            newsBean.uniqueKey=jsonReader.nextString();
                            break;
                        case "url":
                            newsBean.url=jsonReader.nextString();
                            break;


                    }
                }

               if(i==1)
               {
                   newsBean.pics=new String[1];
                   newsBean.pics[0] = bitmaps[0];
               }
               else if(i==2)
               {
                   newsBean.pics=new String[2];
                   newsBean.pics[0] = bitmaps[0];
                   newsBean.pics[1]=bitmaps[1];
               }
               else if(i==3)
               {
                   newsBean.pics=new String[3];
                   newsBean.pics[0] = bitmaps[0];
                   newsBean.pics[1]=bitmaps[1];
                   newsBean.pics[2]=bitmaps[2];
               }
                newsBeen.add(newsBean);
                jsonReader.endObject();
            }
            jsonReader.endArray();
            jsonReader.close();
            Log.v("ssss",newsBeen.size()+"");
            return newsBeen;
        } catch (
                Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象以Json格式写入到输出流中
     * @param newsBeen
     * @param outputStream
     */
    public static  void newsBeanToJsonByJsonWriter(ArrayList<NewsBean> newsBeen,OutputStream outputStream)
    {
        try {
            JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            jsonWriter.setIndent(" ");
            jsonWriter.beginArray();

            for(NewsBean newsBean:newsBeen)
            {
                jsonWriter.beginObject();
                jsonWriter.name("author_name").value(newsBean.author_name);
                jsonWriter.name("category").value(newsBean.category);
                jsonWriter.name("date").value(newsBean.date);
                jsonWriter.name("thumbnail_pic_s").value(newsBean.pics[0]);
                if(newsBean.pics.length>=2)
                {
                    jsonWriter.name("thumbnail_pic_s02").value(newsBean.pics[1]);
                    if(newsBean.pics.length==3)
                    {
                        jsonWriter.name("thumbnail_pic_s03").value(newsBean.pics[2]);
                    }
                }
                jsonWriter.name("title").value(newsBean.title);
                jsonWriter.name("uniquekey").value(newsBean.uniqueKey);
                jsonWriter.name("url").value(newsBean.url);

                jsonWriter.endObject();
            }
            jsonWriter.endArray();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<NewsBean> jsonToNewsBean(String json)
    {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = (int) jsonObject.get("error_code");
            if(code!=0)//返回数据错误
            {
                return null;
            }

            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");

            NewsBean newsBean;
            JSONObject newsObject;
            ArrayList<NewsBean> newsBeen = new ArrayList<>();
            boolean imageUrl2;
            boolean imageUrl3;
            for(int i=0;i<jsonArray.length();i++) {
               newsBean  = new NewsBean();
                newsObject = jsonArray.getJSONObject(i);
                newsBean.author_name = newsObject.getString("author_name");
                newsBean.category = newsObject.getString("category");
                newsBean.date = newsObject.getString("date");

                imageUrl2= newsObject.has("thumbnail_pic_s02");
                imageUrl3= newsObject.has("thumbnail_pic_s03");
                if(imageUrl2&&imageUrl3)
                {
                    newsBean.pics=new String[3];
                    newsBean.pics[1]=newsObject.getString("thumbnail_pic_s02");
                    newsBean.pics[2]=newsObject.getString("thumbnail_pic_s03");

                }
                else if(imageUrl2)
                {
                    newsBean.pics = new String[2];
                    newsBean.pics[1]=newsObject.getString("thumbnail_pic_s02");
                }
                else
                {
                  newsBean.pics = new String[1];
                }
                newsBean.pics[0]=newsObject.getString("thumbnail_pic_s");
                newsBean.title = newsObject.getString("title");
                newsBean.uniqueKey = newsObject.getString("uniquekey");
                newsBean.url = newsObject.getString("url");
                newsBeen.add(newsBean);
            }
            return  newsBeen;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int dipToPix(int dip,Context context)
    {
        float density = context.getResources().getDisplayMetrics().density;
        int pix = (int) (density*dip+0.5f);
        return pix;
    }
    public static int pixToDip(int pix,Context context)
    {
        float density = context.getResources().getDisplayMetrics().density;
        int dip = (int) (pix/density-0.5f);
        return dip;
    }
    public static void closeStream(Closeable closeable)
    {
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String readJsonFromInputStream(InputStream inputStream)
    {
        byte[]bytes= new byte[1024];
        int length;
        String json="";
        try {
            while((length=inputStream.read(bytes))!=-1)
            {
                json += new String(bytes,0,length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataUtil.closeStream(inputStream);
        return json;
    }
       public static Bitmap readBitmapFromInputStream(InputStream inputStream)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[]bytes=new byte[1024];
        int length;
        try {
            while((length=inputStream.read(bytes))!=-1)
            {
                byteArrayOutputStream.write(bytes,0,length);
            }
            DataUtil.closeStream(inputStream);
            return BitmapUtil.byte2Bitmap(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    public static void writeBitmapToOutputStream(OutputStream outputStream)
    {

    }

    /**
     * 得到加密后的Bitmap文件名
     * @param uri
     * @return
     */
    public static String getMD5Name(String uri)
    {
        String cacheKey;
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(uri.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(uri.hashCode());
        }
        return  cacheKey;
    }
    private static String bytesToHexString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<digest.length;i++)
        {
            String hex = Integer.toHexString(0xFF&digest[i]);
            if(hex.length()==1)
            {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
