package com.example.bmq;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GuideActivity extends AppCompatActivity
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

    private ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    private ArrayList<String> explanationArray = new ArrayList<>();


    private String[][] quizData = {
            // {"都道府県名", "正解", "選択肢１", "選択肢２", "選択肢３"}

            {"(A)において、接待の参加率を高めるため簡単に明記しておくとよいものは","開催経緯","参加確定者の名前","会場の候補","簡潔にするためになにも書かないほうが良い"},
        {"(A)において、⑴に入る最も適切な文を選びなさい"," もしもご都合が合わない場合は、 ご遠慮なくお知らせください。","楽しみにしております。","ご都合調整よろしくお願いいたします。","返信お待ちしております。"},
        {"(A)において、次のうち、⑴に記入する必要のない項目はどれ","参加予定人数：〇〇人"," 会場：○○「○○○○」","場所：○○○○○○○○○○○○","会費：0,000円"},
        {"(A)において、⑵に入る適切な文章があるそれは次のうちどれ","尚、出欠は○月○日までに、このメールの返信にてお知らせ下さい。","尚、会場へのアクセスは各自でよろしくお願いいたします。","出席よろしくお願いします。","各自、返信よろしくお願いします。"}
,{"(B)において、(1)にあてはまる言葉","弊社","当社","わが社","御社"},
        {"(B)において、(2)にあてはまる言葉","ご検討の上","確認の上","参照の上","招致の上"}

        };

    //解説データ、クイズデータと配列番号は対応してる
    private String[] explanationData =
            {"開催経緯を付記することで、参加者側にもわかりやすく参加率を高める効果があります。","日程や場所を決める際は希望を伺い、相手の都合を最優先に考慮します。","参加予定人数を記入する必要はありません。","日程の調整を円滑に進めるため、返信締め切りの日を決めておくとよいです。","一般的に社内向けに使う場合「わが社」対外的に自社のことを指す場合「弊社」客観性の高いシーンで自社のことをいう場合「当社」。「御社」は相手の会社を指すため不適切。"," 「～の上」というのは、「…したのち、…した結果、…に基づいて」を意味する。ここでは「よく調べて吟味する」を意味するご検討の上が適切"};

    private String externalQuizData = "メールA\n"+"\n"+"件名：ご会食日程のお伺い\n" +
            "\n" +
            "○○株式会社\n" +
            "販売促進部　本部長　○○様\n" +
            "\n" +
            "平素より、大変お世話になっております。\n" +
            "○○株式会社の営業部・佐藤です。\n" +
            "\n" +
            "弊社、営業部長○○より申し付かりまして、\n" +
            "会食のお誘いのご連絡をさせていただきました。\n" +
            "\n" +
            "恐れ入りますが、来週以降でご都合の良い日程は\n" +
            "ございますでしょうか。\n" +
            "いくつか候補日を頂戴できれば幸いです。\n" +
            "ご予定は当方まで返信くださいますようお願いいたします。\n" +
            "\n" +
            "(1)\n" +
            "\n" +
            "もしもご都合が合わない場合は、\n" +
            "お忙しいところ恐縮ですが、\n" +
            "日程調整の程、何卒よろしくお願い申し上げます。\n"+"\n"+"\n"+"メールB\n"+
            "件名：忘年会開催のご案内\n" +
            "\n" +
            "社員各位\n" +
            "\n" +
            "皆様おつかれさまです。\n" +
            "営業部の佐藤です。\n" +
            "\n" +
            "さて、今年も残りわずかとなりました。\n" +
            "本年も1年の労をねぎらう\n" +
            "恒例の社内忘年会を下記のとおり開催致します。\n" +
            "\n" +
            "当日は、ビンゴゲームはじめ\n" +
            "様々なゲームや催しを予定しておりますので\n" +
            "皆様奮ってご参加ください。\n" +
            "\n" +
            "・日時：○月○日(○)00：00～00：00\n" +
            "・場所：○○○○○○○○○○○○\n" +
            "・会場：○○「○○○○」\n" +
            "・会費：0,000円\n" +
            "\n" +
            "(1)\n" +
            "\n" +
            "会費は当日集金します。よろしくお願いします。\n" +
            "また、ご不明な点がございましたら､\n" +
            "営業部・佐藤までご遠慮なくご連絡ください。\n" +
            "\n" +
            "以上、年末でお忙しいとは思いますが、\n" +
            "皆様のご参加をお待ちしております。\n" +
            "(2)\n"
            ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        // ここから追加
        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        TextView eternalquestionLabel = findViewById(R.id.eternalquestionLabel);

        eternalquestionLabel.setText(externalQuizData);

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
