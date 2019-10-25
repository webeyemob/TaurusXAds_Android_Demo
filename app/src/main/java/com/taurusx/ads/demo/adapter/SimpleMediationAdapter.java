package com.taurusx.ads.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.MediationActivity;
import com.taurusx.ads.demo.bean.Mediation;
import com.taurusx.ads.demo.constance.Constance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleMediationAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map.Entry<String, Mediation>> mListInfo;
    private ArrayList<String> mList = new ArrayList<>();

    public SimpleMediationAdapter(Context context, List<Map.Entry<String, Mediation>> listInfo) {
        mContext = context;
        mListInfo = listInfo;
        for (Map.Entry<String, Mediation> entry: mListInfo) {
            mList.add(entry.getKey());
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextViewHolder holder;
        if (convertView == null) {
            holder = new TextViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_textview, null);
            holder.mTextView = (TextView) convertView;
            convertView.setTag(holder);
        } else {
            holder = (TextViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(getItem(position));
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mediation mediation = mListInfo.get(position).getValue();
                Intent intent = new Intent();
                intent.putExtra(Constance.BUNDLE_NETWORK_NAME, mediation.getmName());
                intent.putExtra(Constance.BUNDLE_TYPE_BANNER, mediation.getBannerId());
                intent.putExtra(Constance.BUNDLE_TYPE_INTERSTITIAL, mediation.getInterstitialId());
                intent.putExtra(Constance.BUNDLE_TYPE_REWARDED, mediation.getRewardedId());
                intent.putExtra(Constance.BUNDLE_TYPE_NATIVE, mediation.getNativeId());
                intent.putExtra(Constance.BUNDLE_TYPE_FEEDLIST, mediation.getFeedListId());
                intent.putExtra(Constance.BUNDLE_TYPE_SPLASH, mediation.getSplashId());

                intent.setClass(mContext, MediationActivity.class);
                mContext.startActivity(intent);

            }
        });
        return convertView;
    }

    class TextViewHolder {
        public TextView mTextView;
    }
}
