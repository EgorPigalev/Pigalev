package com.example.pigalev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Photo extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imageView = findViewById(R.id.image);
        if(Profile.maskProfileImage.getImageProfile().exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(Profile.maskProfileImage.getImageProfile().getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
    }

    public void Close(View view)
    {
        startActivity(new Intent(this, Profile.class));
    }

    public void Delete(View view)
    {
        try
        {
            Profile.maskProfileImage.imageProfile.delete();
        }
        catch(Exception exception)
        {
            Toast.makeText(this, "При удаление картинки возникла ошибка!", Toast.LENGTH_LONG).show();
        }
        startActivity(new Intent(this, Profile.class));
    }
}