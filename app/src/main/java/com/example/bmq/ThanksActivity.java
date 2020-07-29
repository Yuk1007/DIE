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

public class ThanksActivity extends AppCompatActivity
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
    static final private int QUIZ_COUNT = 3;

    private int randomNum = 0;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    ArrayList<String> explanationArray = new ArrayList<>();

    String quizData[][] = {
            // {"都道府県名", "正解", "選択肢１", "選択肢２", "選択肢３"}
            {"さて、この度は○○を頂戴しまして、誠にありがとうございます。有難く拝受させていただきます。", "結構な品", "(品名)", "ありがたいもの", "つまらないもの"},
            {"(社外)本日はお忙しいところ、打ち合わせのお時間を割いていただきましたこと、○○○○。", "心よりお礼申し上げます", "本当にありがとうございます", "感謝してます", "ありがとう"},
            {"○○○○、皆様お忙しい中、多大なるご協力をいただき誠にありがとうございました。", "このたびのA(件名)につきまして", "この度は", "今回は", "Aについて"},
            {"先ほどは△△システムについてご説明いただき、ありがとうございます。 さっそく、詳しい資料もお送りいただき、○○○○。", "重ねてお礼申し上げます", "お礼申し上げます", "感謝します", "ありがとうございました"},
            {"午前中の出来事に関するものに対するメールを送る時期として適切なものは? ", " その日の夕方まで", "その日の夜", "次の日", "いつでもいい"},
    };

    //解説データ、クイズデータと配列番号は対応してる
    String explanationData[] = {
            "贈り物やお土産を頂いた際は、「結構な品」を使いましょう。「結構な」とは目上の人に素晴らしい、立派なという表現をする言葉です。", "社外の方に、深い感謝の気持ちを表す際は、「心より御礼申し上げます。」を使います。", "何か自分にやってもらったときは、この表現を使いましょう。何をしてもらったかを忘れずに書くとなお良いです。", "お礼を繰り返し書く場合、「重ねて」を使いましょう", "午前中の出来事に関するお礼のメールはその日の夕方までには送るのが良いです。夜に起こったことならば、次の日が良いでしょう。"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

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