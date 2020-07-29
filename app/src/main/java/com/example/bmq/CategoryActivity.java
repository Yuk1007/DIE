package com.example.bmq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class CategoryActivity extends AppCompatActivity
{

    private Button categoryBtn1;
    private Button categoryBtn2;
    private Button categoryBtn3;
    private Button categoryBtn4;
    private Button categoryBtn5;
    private Button categoryBtn6;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);

        /*Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });*/

        Button categoryBtn1 = findViewById(R.id.category_Btn1);
        categoryBtn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button categoryBtn2 = findViewById(R.id.category_Btn2);
        categoryBtn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplication(), ThanksActivity.class);
                startActivity(intent);
            }
        });

        Button categoryBtn3 = findViewById(R.id.category_Btn3);
        categoryBtn3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplication(), ApologizeActivity.class);
                startActivity(intent);
            }
        });

        Button categoryBtn4 = findViewById(R.id.category_Btn4);
        categoryBtn4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplication(), ReminderActivity.class);
                startActivity(intent);
            }
        });

        Button categoryBtn5 = findViewById(R.id.category_Btn5);
        categoryBtn5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplication(), AskActivity.class);
                startActivity(intent);
            }
        });

        Button categoryBtn6 = findViewById(R.id.category_Btn6);
        categoryBtn6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplication(), ReportActivity.class);
                startActivity(intent);
            }
        });

        Button categoryBtn7 = findViewById(R.id.category_Btn7);
        categoryBtn6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplication(), GuideActivity.class);
                startActivity(intent);
            }
        });

    }
}
