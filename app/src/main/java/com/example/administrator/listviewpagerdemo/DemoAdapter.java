package com.example.administrator.listviewpagerdemo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 项目名： ListviewPagerDemo2
 * 创建者： xxxxx
 * 创建时间：  2017/2/21 15:21
 * 包名：com.example.administrator.listviewpagerdemo
 * 文件名： ${name}
 * 描述：  TODO
 */

public class DemoAdapter extends PagerAdapter{
    private List<View> viewList;

    public DemoAdapter(List<View> list) {
        // TODO Auto-generated constructor stub
        this.viewList = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.viewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(this.viewList.get(position));
        return this.viewList.get(position);
    }
}
