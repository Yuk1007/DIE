package com.example.bmq;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity
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
    static final private int QUIZ_COUNT = 5;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            // {"都道府県名", "正解", "選択肢１", "選択肢２", "選択肢３"}
            {"TOの意味として正しいものはどれか？", "メインの宛先", "情報を共有したいかつ、メインの受信者にも知らせたい", "情報を共有したいが、メインの相手には隠したい", "誰にも送らない"},
            {"CCの意味として正しいものはどれか？", "情報を共有したいかつ、メインの受信者にも知らせたい", "メインの宛先", "情報を共有したいが、メインの相手には隠したい", "誰にも送らない"},
            {"BCCの意味として正しいものはどれか？", "情報を共有したいが、メインの相手には隠したい", "メインの宛先", "情報を共有したいかつ、メインの受信者にも知らせたい", "誰にも送らない"},
            {"TOには必ず宛先を入れたほうが良いか？", "必ず入れる", "余裕があれば入れる", "絶対入れない", "余裕があれば入れる"},
            {"メールの構成として左から順に正しいものはどれか？", "宛名、挨拶、本文、結びの言葉、署名", "宛名、署名、挨拶、本文、結びの言葉", "挨拶、宛名、署名、本文、結びの言葉", "挨拶、署名、宛名、本文、結びの言葉"},
            {"宛名として左から順に正しい書き方はどれか？", "会社名、部署名、役職名、名前、敬称", "名前、会社名、部署名、役職名、敬称", "会社名、名前、敬称、部署名、役職名", "名前、敬称、部署名、役職名、会社名"},
            {"宛名の書き方として正しいものは？", "○○部　山口部長", "山口部長様", "山口部長御中", "○○部　山口部長様"},
            {"団体宛で正しい宛名の書き方は？", "○○株式会社御中〇", "○○株式会社様", "○○株式会社殿", "○○株式会社へ"},
            {"複数名に送る場合に正しい宛名の書き方は？", "○○株式会社　××部　各位", "○○株式会社　××部　皆様", "○○株式会社　各位", "○○株式会社　皆様"},
            {"あいさつ文として正しいものはどれ？", "お世話になっております。\n" +
                    "○○株式会社△△部の○○です。", "例年より寒さが身にこたえております\n" +
                    "○○株式会社△△部の○○です。", "こんにちは\n" +
                    "○○株式会社△△部の○○です。", "よっす！\n" +
                    "○○株式会社△△部の○○だ。"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            ArrayList<String> tmpArray = new ArrayList<>();

            // クイズデータを追加
            tmpArray.add(quizData[i][0]);  // 都道府県名
            tmpArray.add(quizData[i][1]);  // 正解
            tmpArray.add(quizData[i][2]);  // 選択肢１
            tmpArray.add(quizData[i][3]);  // 選択肢２
            tmpArray.add(quizData[i][4]);  // 選択肢３

            // tmpArrayをquizArrayに追加する
            quizArray.add(tmpArray);


        }
        showNextQuiz();
    }

    public void showNextQuiz()
    {
        // クイズカウントラベルを更新
        countLabel.setText("Q" + quizCount);

        // ランダムな数字を取得
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        ArrayList<String> quiz = quizArray.get(randomNum);

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
        builder.setMessage("答え : " + rightAnswer);
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