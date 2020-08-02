package com.example.bmq;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ReviewActivity extends AppCompatActivity
{
    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;


    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 10;

    private int randomNum = 0;

    private OpenHelper helper;
    private SQLiteDatabase db;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    ArrayList<String> explanationArray = new ArrayList<>();

    Cursor cursor;

    ArrayList<String> quiz;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // ここから追加
        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        readData();

        for (int i = 0; i < cursor.getCount(); i++)
        {
            // 新しいArrayListを準備
            ArrayList<String> tmpquizarray = new ArrayList<>();


            // クイズデータを追加
            tmpquizarray.add(cursor.getString(0));  // 都道府県名
            tmpquizarray.add(cursor.getString(1));  // 正解
            tmpquizarray.add(cursor.getString(2));  // 選択肢１
            tmpquizarray.add(cursor.getString(3));  // 選択肢２
            tmpquizarray.add(cursor.getString(4));  // 選択肢３

            explanationArray.add(cursor.getString(5));//解説の保存（explanationArrayに解説が入ってる）

            // tmpquizarrayをquizArrayに追加する
            quizArray.add(tmpquizarray);
            cursor.moveToNext();

        }

        showNextQuiz();
    }

    private void readData()
    {
        if(helper == null){
            helper = new OpenHelper(getApplicationContext());
        }

        if(db == null){
            db = helper.getReadableDatabase();
        }

        cursor = db.query(
                "QUIZARRAY",
                new String[] { "quiz0", "quiz1","quiz2" ,"quiz3","quiz4","EXquiz"},
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
    }

    private void showNextQuiz(){

        // クイズカウントラベルを更新
        countLabel.setText("Q" + quizCount);

        // ランダムな数字を取得
        Random random = new Random();
        randomNum = random.nextInt(quizArray.size());

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        quiz = quizArray.get(randomNum);

        // 問題文（都道府県名）を表示
        questionLabel.setText(quiz.get(0));

        // 正解をrightAnswerにセット
        rightAnswer = quiz.get(1);

        // クイズ配列から問題文（都道府県名）を削除
        quiz.remove(0);

        // 正解と選択肢３つをシャッフル
        Collections.shuffle(quiz);

        // 回答ボタンに正解と選択肢３つを表示
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        // このクイズをquizArrayから削除
        quizArray.remove(randomNum);

    }

    public void checkAnswer(View view)
    {

        // どの回答ボタンが押されたか
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String explanation = explanationArray.get(randomNum);

        String alertTitle;
        if (btnText.equals(rightAnswer))
        {
            alertTitle = "正解!";
            rightAnswerCount++;
        }
        else
        {
            alertTitle = "不正解...";
        }


        // ダイアログを作成
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("答え : " + rightAnswer + "\n" + explanation);//解説の表示に成功
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if (quizCount == QUIZ_COUNT)
                {
                    // 結果画面へ移動
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    intent.putExtra("QUIZ_ALLCOUNT", QUIZ_COUNT);
                    startActivity(intent);
                }
                else
                {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}

