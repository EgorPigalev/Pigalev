package com.example.pigalev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    ImageView image;
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvNameProfile);
        tvName.setText(Onboarding.nickName);

        image = findViewById(R.id.avatar);
        new AdapterMaskQuote.DownloadImageTask((ImageView) image)
                .execute(Onboarding.avatar);
    }

    public  void nextMenu(View view)
    {
        startActivity(new Intent(this, Menu.class));
    }

    public  void nextHome(View view)
    {
        startActivity(new Intent(this, Main.class));
    }

    public void nextListen(View view)
    {
        startActivity(new Intent(this, Listen.class));
    }

    public void nextLogin(View view)
    {
        SharedPreferences prefs = getSharedPreferences( // Сохранение данных
                "Date", Context.MODE_PRIVATE);
        prefs.edit().putString("Avatar", "").apply();
        prefs.edit().putString("NickName", "").apply();

        startActivity(new Intent(this, Login.class));
    }
}