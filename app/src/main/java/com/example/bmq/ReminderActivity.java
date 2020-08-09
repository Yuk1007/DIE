package com.example.bmq;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ReminderActivity extends AppCompatActivity
{
    private SQLiteDatabase db;
    private OpenHelper helper;
    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 3;

    private int randomNum = 0;

    private ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    private ArrayList<String> explanationArray = new ArrayList<>();
    private ArrayList<String> quiz;

    private String[][] quizData = {
            // {"都道府県名", "正解", "選択肢１", "選択肢２", "選択肢３"}
            {"○○○○、ご提案書をメールでお送りいたしましたが、ご覧になりましたでしょうか。", "△月×日に", "以前", "この前", "昔々"},

            {"△月△日に下記のお問い合わせをいたしましたが、○○○○。", "ご確認いただいておりますでしょうか", "早く返答してください", "確認してますか", "まだですか"}
            ,
            {"△月△日に商品のご納品をお願いしておりましたが、○○○○。", "まだ到着を確認できていない状況です", "まだですか", "早く発送してください", "まだ来てないです"}
            ,
            {"事務処理の関係上、恐れ入りますが*月*日（*曜日）までに本社総務部に到着するようご送付頂きたく存じます。○○○○。お手数おかけしますが、ご対応の程、何卒宜しくお願い申し上げます。", "なお、本メールと行き違いでお送り頂いた場合は、何卒ご容赦くださいませ "
                    , "早急にお願いいたします", "もし送れなさそうなら連絡ください", "早めにお願いします"}
            ,

    };

    //解説データ、クイズデータと配列番号は対応してる
    private String[] explanationData =
            {"いつの提案書なのかをしっかり書いておきましょう。", "2.相手に早く送ってほしい時でも、丁寧な言葉遣いで催促しましょう", "納品物の催促はこちらの状況を伝えましょう。相手がすでに発送している場合もあります。", "もし行き違いが起こった場合、相手に不快感を与えてしまうこともあるので、この文を加えて、トラブルが起こらないようにしましょう。"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        // ここから追加
        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        // quizDataからクイズ出題用のquizArrayを作成する
        for (int i = 0; i < quizData.length; i++)
        {
            // 新しいArrayListを準備
            ArrayList<String> tmpquizarray = new ArrayList<>();


            // クイズデータを追加
            tmpquizarray.add(quizData[i][0]);  // 都道府県名
            tmpquizarray.add(quizData[i][1]);  // 正解
            tmpquizarray.add(quizData[i][2]);  // 選択肢１
            tmpquizarray.add(quizData[i][3]);  // 選択肢２
            tmpquizarray.add(quizData[i][4]);  // 選択肢３

            explanationArray.add(explanationData[i]);//解説の保存（explanationArrayに解説が入ってる）

            // tmpquizarrayをquizArrayに追加する
            quizArray.add(tmpquizarray);

        }
        showNextQuiz();
    }


    public void showNextQuiz()
    {
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
            if (helper == null)
            {
                helper = new OpenHelper(getApplicationContext());
            }

            if (db == null)
            {
                db = helper.getWritableDatabase();
            }

            quiz = quizArray.get(randomNum);
//          quiz(0)が問題、quiz(1)正解,quiz(2)選択肢,quiz(3)選択肢,quiz(4)選択肢
            insertData(db, quiz.get(0), quiz.get(1), quiz.get(2), quiz.get(3), quiz.get(4), explanation);
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

    private void insertData(SQLiteDatabase db, String quiz0, String quiz1, String quiz2, String quiz3,String quiz4,String EXquiz){

        ContentValues values = new ContentValues();
        values.put("quiz0", quiz0);
        values.put("quiz1", quiz1);
        values.put("quiz2", quiz2);
        values.put("quiz3", quiz3);
        values.put("quiz4", quiz4);
        values.put("explanation", EXquiz);
        db.insert("quizarray", null, values);
    }
}
