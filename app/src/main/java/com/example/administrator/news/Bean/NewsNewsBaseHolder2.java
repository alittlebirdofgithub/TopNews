package com.example.administrator.news.Bean;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.news.R;

/**
 * Created by Administrator on 2017-04-25.
 */

public class NewsNewsBaseHolder2 extends NewsBaseHolder {

    private ImageView imageView1;
    public NewsNewsBaseHolder2(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.recycle_replace_tv_title);
        describle = (TextView) itemView.findViewById(R.id.recycle_replace_tv_des);
        imageView1 = (ImageView) itemView.findViewById(R.id.recyle_replace_image1);
    }




   public void bindViewOneImage(String url)
   {
       bindImageThreeCache(imageView1,url);
   }

}
