package com.taurusx.ads.demo.adapter;

/**
 * Created by creator on 18-6-9.
 */

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;
public class PageAdapter extends PagerAdapter {
    private List<View>viewList;

    //实现构造方法
    public PageAdapter(List<View> viewList){
        this.viewList=viewList;
    }


    @Override
    public int getCount() {
        return viewList.size();  //返回当前页卡数量
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {   //View是否来自对象
        return view==object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //实例化一个页卡
        container.addView(viewList.get(position));  //position代表当前的位置（所定位的View）
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {  //销毁页卡
        container.removeView(viewList.get(position));
    }
}


