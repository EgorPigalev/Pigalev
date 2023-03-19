package com.example.pigalev;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterMaskProfileImage extends BaseAdapter
{
    private Context mContext;
    List<MaskProfileImage> maskList;

    public AdapterMaskProfileImage(Context mContext, List<MaskProfileImage> maskList) {
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

        MaskProfileImage maskProfileImage  = maskList.get(position);
        View v = null;
        if(maskProfileImage.getImageProfile() == null) // Если картинка не указана, то это последний элемент
        {
            v = View.inflate(mContext,R.layout.item_profile_image_add,null); // Выводится форма с кнопкой
        }
        else
        {
            v = View.inflate(mContext,R.layout.item_profile_image,null); // Вывод стандартной формы

            ImageView Image = v.findViewById(R.id.image);
            TextView dateCreat = v.findViewById(R.id.dateCreat);

            if(maskProfileImage.getImageProfile().exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(maskProfileImage.getImageProfile().getAbsolutePath());
                Image.setImageBitmap(myBitmap);
            }
            dateCreat.setText(maskProfileImage.getData());
        }
        return v;
    }
}
