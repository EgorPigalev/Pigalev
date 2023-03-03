package com.example.pigalev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        startActivity(new Intent(this, Login.class));
    }
}