package com.example.pigalev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText etEmail, etPassword; // Почта и пароль

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        SharedPreferences prefs = this.getSharedPreferences(
                "Date", Context.MODE_PRIVATE); // Получение данных о пользователе
        if(prefs != null)
        {
            etEmail.setText(prefs.getString("Email", "")); // Заполнение почты ранее входившего пользователя
            etPassword.requestFocus();
        }
    }

    public void nextMain(View v)
    {
        if(etEmail.getText().toString().equals("") || etPassword.getText().toString().equals("")) // Проверка заполненности полей
        {
            Toast.makeText(Login.this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Pattern p = Pattern.compile("@", Pattern.CASE_INSENSITIVE); // Регулярное выражение для проверки наличия символа @ в почте
            Matcher m = p.matcher(etEmail.getText().toString());
            boolean b = m.find();
            if(b)
            {
                callLogin();
            }
            else
            {
                Toast.makeText(Login.this, "Поле для Email обязательно должно содержать символ '@'", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callLogin() // Процесс проверки пользователя
    {
        String email = String.valueOf(etEmail.getText());
        String password = String.valueOf(etPassword.getText());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        ModelSendUser modelSendUser = new ModelSendUser(email, password);
        Call<MaskUser> call = retrofitAPI.createUser(modelSendUser);
        call.enqueue(new Callback<MaskUser>() {
            @Override
            public void onResponse(Call<MaskUser> call, Response<MaskUser> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Login.this, "Пользователь с такой почтой и паролем не найден", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body() != null)
                {
                    if(response.body().getToken() != null)
                    {
                        SharedPreferences prefs = getSharedPreferences( // Сохранение данных
                                "Date", Context.MODE_PRIVATE);
                        prefs.edit().putString("Email", "" + email).apply();
                        prefs.edit().putString("Avatar", "" + response.body().getAvatar()).apply();
                        prefs.edit().putString("NickName", "" + response.body().getNickName()).apply();

                        Onboarding.avatar = response.body().getAvatar();
                        Onboarding.nickName = response.body().getNickName();

                        Intent intent = new Intent(Login.this, Main.class);
                        Bundle b = new Bundle();
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<MaskUser> call, Throwable t) {
                Toast.makeText(Login.this, "При авторизации возникла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void nextRegister(View v)
    {
        startActivity(new Intent(this, Register.class));
    }
}