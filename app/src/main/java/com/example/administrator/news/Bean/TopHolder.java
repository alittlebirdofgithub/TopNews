package com.example.administrator.news.Bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Binder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.administrator.news.Fragment.BaseFragment;
import com.example.administrator.news.MainActivity;
import com.example.administrator.news.R;
import com.example.administrator.news.Util.BitmapUtil;
import com.example.administrator.news.Util.DefaultCacheUtil;

/**
 * Created by Administrator on 2017-04-29.
 */

public class TopHolder extends RecyclerView.ViewHolder {
    private TableLayout mGridView;
    private String[]mUrl;
    private int cloumWidth;
    private TextView mTitle;

    public TopHolder(View itemView,String []url) {
        super(itemView);
        mGridView = (TableLayout) itemView.findViewById(R.id.top_holder_grid);
        mTitle = (TextView) itemView.findViewById(R.id.top_recycle_content);
        mTitle.setText("我勒个去，我勒个去,我勒个去，我勒个去我勒个去，我勒个去我勒个去，我勒个去");
        mUrl =url;
        setGridLayout(url);
    }

    private void setGridLayout(String []url) {
        int length = url.length;
        Context context = MainActivity.activityContext;
        TableRow.LayoutParams layoutParams = null;
        TableLayout.LayoutParams parentlayoutparams=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        ImageView imageView;
        TableRow tableRow;
        int row = 1;
        int col = 1;
        if (length == 4) {
            row = 2;
            col = 2;
            layoutParams = new TableRow.LayoutParams(MainActivity.displayWidth / 3, MainActivity.displayHeight / 3);
        } else if (length == 1) {
            row = 1;
            col = 1;
            layoutParams = new TableRow.LayoutParams(MainActivity.displayWidth, MainActivity.displayHeight);
        } else if (length == 2) {
            row = 1;
            col = 2;
            layoutParams = new TableRow.LayoutParams(MainActivity.displayWidth / 2, MainActivity.displayHeight / 2);
        } else {
            row = Math.round(length / 3.0f + 0.5f);
            if(length==7)
                MainActivity.log(row+"");
            col = 3;
            layoutParams = new TableRow.LayoutParams(MainActivity.displayWidth/3,MainActivity.displayHeight/3);
        }
        for (int i = 0; i < row; i++) {
            tableRow = new TableRow(context);
            for (int j = 0; j < col; j++) {
                int index = i * col + j;
                if (index == length)
                   break;
                imageView = new ImageView(context);
                imageView.setLayoutParams(layoutParams);

                bindImageView(imageView, index);
                tableRow.addView(imageView);
            }
            mGridView.addView(tableRow,parentlayoutparams);
        }

    }

    void bindImageView(ImageView imageView,int position)
    {
        //首先从内存中获取，再从本地获取
        Bitmap bitmap = (Bitmap) BaseFragment.defaultCacheUtil.dealGetCache(DefaultCacheUtil.BITMAP_MEMORY,mUrl[position]);
        if(bitmap==null) {
            bitmap = (Bitmap) BaseFragment.defaultCacheUtil.dealGetCache(DefaultCacheUtil.BITMAP_FILE, mUrl[position]);

            BaseFragment.defaultCacheUtil.dealAddCache(DefaultCacheUtil.BITMAP_MEMORY,mUrl[position],bitmap);
        }
        bitmap = BitmapUtil.getScaledBitmap(bitmap, cloumWidth, cloumWidth);
        imageView.setImageBitmap(bitmap);
    }
}
