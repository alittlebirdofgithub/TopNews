package com.example.administrator.news.Fragment;

import android.content.Context;

import com.example.administrator.news.Fragment.FirstPage.CaiJinFragmentFirstPage;
import com.example.administrator.news.Fragment.FirstPage.GuoJiFragmentFirstPage;
import com.example.administrator.news.Fragment.FirstPage.GuoNeiFragmentFirstPage;
import com.example.administrator.news.Fragment.FirstPage.JunShiFragmentFirstPage;
import com.example.administrator.news.Fragment.FirstPage.KeJiFragmentFirstPage;
import com.example.administrator.news.Fragment.FirstPage.SheHuiFragmentFirstPage;
import com.example.administrator.news.Fragment.FirstPage.ShiShanFragmentFirstPage;
import com.example.administrator.news.Fragment.FirstPage.TiYuFragmentFirstPage;
import com.example.administrator.news.Fragment.FirstPage.TouTiaoFragmentFirstPage;
import com.example.administrator.news.Fragment.FirstPage.YuLeFragmentFirstPage;
import com.example.administrator.news.Fragment.Top.TopFragment;
import com.example.administrator.news.Fragment.Video.TuiJianFragment;
import com.example.administrator.news.MainActivity;

/**
 * Created by Administrator on 2017/4/15.
 */

public class SubFragmentFactory {

       public static final int ToutTiao=0;
      public static final int  SheHui=1;
    public static final int  GuoNei=2;
    public static final int  GuoJi=3;
    public static final int  YuLe=4;
    public static final int  TiYu=5;
    public static final int  JunShi=6;
    public static final int  KeJi=7;
    public static final int  CaiJin=8;
    public static final int  ShiShan=9;
    public static final int TuiJian=10;
    public static final int YinYue=11;
    public static final int GaoXiao=12;
    public static final int XiaoPing=13;
    public static final int ShengHuo=14;
    public static final int YinShi=15;
    public static final int DaiMeng=16;
    public static final int YouXi=17;
    public static final int YuanChuang=18;
    public static final int Top=19;
    public static final String[] firstpageSubFragmentTitleDes ={"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};
    public static final String[] videoSubFragmentTitleDes ={"推荐","音乐","搞笑","小品","生活","影视","呆萌","游戏","原创"};
    public static String getTitleAccordType(int type)
    {
        switch (type)
        {
            case 0:
                return "头条";
            case 1:
                return "社会";
            case 2:
                return "国内";
            case 3:
                return "国际";
            case 4:
                return "娱乐";
            case 5:
                return  "体育";
            case 6:
                return "军事";
            case 7:
                return "科技";
            case 8:
                return "财经";
            case 9:
                return "时尚";
            case 10:
                return "推荐";
            case 11:
                return  "音乐";
            case 12:
                return "搞笑";
            case 13:
                return "小品";
            case 14:
                return "生活";
            case 15:
                return "影视";
            case 16:
                return "呆萌";
            case 17:
                return "游戏";
            case 18:
                return "原创";
        }
        return "";
    }
    public static int getTypeAccordTitle(String title)
    {
        switch (title)
        {
            case "头条":
                return ToutTiao;
            case "社会":
                return SheHui;
            case "国内":
                return GuoNei;
            case "国际":
                return GuoJi;
            case "娱乐":
                return YuLe;
            case "体育":
                return TiYu;
            case "军事":
                return JunShi;
            case "科技":
                return KeJi;
            case "时尚":
                return ShiShan;
            case "推荐":
                return TuiJian;
            case "音乐":
                return YinYue;
            case "搞笑":
                return GaoXiao;
            case "小品":
                return XiaoPing;
            case "生活":
                return  ShengHuo;
            case "影视":
                return YinShi;
            case "呆萌":
                return DaiMeng;
            case "游戏":
                return YouXi;
            case "原创":
                return YuanChuang;
        }
        return -1;
    }
    public static SubBaseFragment createSubBaseFragment(int type, Context context)
    {
        SubBaseFragment subBaseFragment =null;
      if(type==ToutTiao)
      {
          subBaseFragment = new TouTiaoFragmentFirstPage(context);

      }
      else if(type==SheHui)
      {
         subBaseFragment = new SheHuiFragmentFirstPage(context);
      }
      else if(type==GuoNei)
      {
          subBaseFragment = new GuoNeiFragmentFirstPage(context);
      }
      else if(type==GuoJi)
      {
         subBaseFragment = new GuoJiFragmentFirstPage(context);
      }else if(type==YuLe)
      {
        subBaseFragment =  new YuLeFragmentFirstPage(context);
      }else if(type==TiYu)
      {
       subBaseFragment = new TiYuFragmentFirstPage(context);
      }else if(type==JunShi)
      {
       subBaseFragment = new JunShiFragmentFirstPage(context);
      }
      else if(type==KeJi)
      {
        subBaseFragment = new KeJiFragmentFirstPage(context);
      }
      else if(type==CaiJin)
      {
         subBaseFragment = new CaiJinFragmentFirstPage(context);
      }
      else if(type==ShiShan)
      {
         subBaseFragment = new ShiShanFragmentFirstPage(context);
      }
      else if(type==TuiJian)
      {
          subBaseFragment = new TuiJianFragment(context);
      }

      else//视频其他界面也是Tuijian内容
      {
          subBaseFragment = new TuiJianFragment(context);
      }
      return subBaseFragment;
    }

}
