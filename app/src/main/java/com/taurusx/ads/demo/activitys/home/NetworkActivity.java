package com.taurusx.ads.demo.activitys.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.adtype.AdTypeActivity;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;

import org.json.JSONObject;

import java.util.List;

public class NetworkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_network);
        getActionBar().setTitle("Network Test");

        GridView listView = findViewById(R.id.listView);

        List<JSONObject> mediationList = Utils.getMediationList(NetworkActivity.this, "network_ad.json");
        NetworkAdapter adapter = new NetworkAdapter(NetworkActivity.this, mediationList);
        listView.setAdapter(adapter);
    }

    private static class NetworkAdapter extends BaseAdapter {

        private Context mContext;

        private List<JSONObject> mMediationList;

        private NetworkAdapter(Context context, List<JSONObject> mediationList) {
            mContext = context;
            mMediationList = mediationList;
        }

        @Override
        public int getCount() {
            return mMediationList.size();
        }

        @Override
        public JSONObject getItem(int position) {
            return mMediationList.get(position);
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
                holder.mTextView = convertView.findViewById(R.id.textView_title);
                convertView.setTag(holder);
            } else {
                holder = (TextViewHolder) convertView.getTag();
            }

            holder.mTextView.setText(getItem(position).optString(Constant.JSON_KEY_ADNAME));
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdTypeActivity.start(mContext, getItem(position));
                }
            });
            return convertView;
        }

        private class TextViewHolder {
            private TextView mTextView;
        }
    }
}