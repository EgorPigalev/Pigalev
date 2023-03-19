package com.example.pigalev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class Photo extends AppCompatActivity {

    SubsamplingScaleImageView imageView; // Изображение
    View view; // Элемент, который группирует все

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imageView = findViewById(R.id.image);
        if (Profile.maskProfileImage.getImageProfile().exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(Profile.maskProfileImage.getImageProfile().getAbsolutePath());
            imageView.setImage(ImageSource.bitmap(myBitmap));
        }

        imageView.setOnTouchListener(new OnSwipeTouchListener(Photo.this) { // Добавляем обработку смахивания
            public void onSwipeRight() {
                Profile.maskProfileImage = null;
                startActivity(new Intent(Photo.this, Profile.class));
            }
            public void onSwipeLeft() {
                try {
                    Profile.maskProfileImage.imageProfile.delete();
                    Profile.maskProfileImage = null;
                    Toast.makeText(Photo.this, "Фотография успешно удалена", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Photo.this, Profile.class));
                }
                catch (Exception e)
                {
                    Toast.makeText(Photo.this, "При удаление фотографии возникла ошибка!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view = findViewById(R.id.view);
        view.setOnTouchListener(new OnSwipeTouchListener(Photo.this) { // Добавляем обработку смахивания
            public void onSwipeRight() {
                Profile.maskProfileImage = null;
                startActivity(new Intent(Photo.this, Profile.class));
            }
            public void onSwipeLeft() {
                try {
                    Profile.maskProfileImage.imageProfile.delete();
                    Profile.maskProfileImage = null;
                    Toast.makeText(Photo.this, "Фотография успешно удалена", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Photo.this, Profile.class));
                }
                catch (Exception e)
                {
                    Toast.makeText(Photo.this, "При удаление фотографии возникла ошибка!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Close(View view)
    {
        Profile.maskProfileImage = null;
        startActivity(new Intent(this, Profile.class));
    }

    public void Delete(View view)
    {
        try {
            Profile.maskProfileImage.imageProfile.delete();
            Profile.maskProfileImage = null;
            Toast.makeText(Photo.this, "Фотография успешно удалена", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Photo.this, Profile.class));
        }
        catch (Exception e)
        {
            Toast.makeText(Photo.this, "При удаление фотографии возникла ошибка!", Toast.LENGTH_SHORT).show();
        }
    }
}
