package com.example.pigalev;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Profile extends AppCompatActivity {

    ImageView image; // Иконка пользователя
    TextView tvName; // Имя пользлователя

    OutputStream outputStream;

    private AdapterMaskProfileImage pAdapter;
    private List<MaskProfileImage> list = new ArrayList<>();

    public static MaskProfileImage maskProfileImage; // Картинка, на которую нажал пользователь

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvNameProfile);
        tvName.setText(Onboarding.nickName);

        image = findViewById(R.id.avatar);
        new AdapterMaskQuote.DownloadImageTask((ImageView) image)
                .execute(Onboarding.avatar); // Заполнение иконки пользователя

        GridView gvImage = findViewById(R.id.lvImageProfile);
        pAdapter = new AdapterMaskProfileImage(Profile.this, list);
        gvImage.setAdapter(pAdapter);
        GetImageProfile();

        gvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MaskProfileImage mask = list.get(i);
                if(mask.getImageProfile() == null) // Если нажали на последний элемент, то это кнопка добавления фотографии
                {
                    addImage();
                }
                else
                {
                    maskProfileImage = list.get(i);
                    startActivity(new Intent(Profile.this, Photo.class));
                }
            }
        });
    }

    private void GetImageProfile() // Заполнение картинок из папки
    {
        File dir = new File(getApplicationInfo().dataDir + "/MyFiles/");
        dir.mkdirs();
        list.clear();
        pAdapter.notifyDataSetInvalidated();
        String path = getApplicationInfo().dataDir + "/MyFiles";
        File directory = new File(path);
        File[] files = directory.listFiles();
        int j = 0;
        for (int i = 0; i < files.length; i++)
        {
            Long last = files[i].lastModified();
            MaskProfileImage tempProduct = new MaskProfileImage(
                    j,
                    files[i].getAbsolutePath(),
                    files[i],
                    getFullTime(last)
            );
            list.add(tempProduct);
            pAdapter.notifyDataSetInvalidated();
        }
        MaskProfileImage tempProduct = new MaskProfileImage( // Последним элементом будет кнопка
                j,
                null,
                null,
                "null"
        );
        list.add(tempProduct);
        pAdapter.notifyDataSetInvalidated();
    }

    public static final String getFullTime(final long timeInMillis) // Преобразование из милисикунд в формат часы:минуты
    {
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeInMillis);
        c.setTimeZone(TimeZone.getDefault());
        return format.format(c.getTime());
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

    public void addImage() // Добавление фотографии
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        someActivityResultLauncher.launch(photoPickerIntent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Bitmap bitmap = null;
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage = result.getData().getData();
                        try
                        {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        File dir = new File(getApplicationInfo().dataDir + "/MyFiles/");
                        dir.mkdirs(); // Проверка наличия католога и его создание
                        File file = new File(dir, System.currentTimeMillis() + ".jpg"); // Создание файла
                        try {
                            outputStream = new FileOutputStream(file); // Отправка данных в файл на диске
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Toast.makeText(Profile.this, "Изображение успешно сохранено", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(Profile.this, "При сохранение изображения возникла ошибка!", Toast.LENGTH_LONG).show();
                        }
                        GetImageProfile();
                    }
                }
            });
}