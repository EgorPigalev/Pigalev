package com.example.pigalev;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMaskFeeling extends BaseAdapter {

    private Context mContext;
    List<MaskFeeling> maskList;

    public AdapterMaskFeeling(Context mContext, List<MaskFeeling> maskList) {
        this.mContext = mContext;
        this.maskList = maskList;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext,R.layout.item_feeling,null);

        TextView title = v.findViewById(R.id.tvTitle);
        ImageView Image = v.findViewById(R.id.image);

        MaskFeeling maskFeeling = maskList.get(position);
        title.setText(maskFeeling.getTitle());

        return v;
    }
}
