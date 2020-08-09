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

public class ReportActivity extends AppCompatActivity
{
    private TextView countLabel;
    private TextView questionLabel;
    private TextView eternalquestionLabel;
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

            {"Aについて、取引先へのお知らせメールでは","現状・変更内容を簡潔に曖昧な表現は避けて知らせる。","現状・変更内容を詳しく曖昧な表現は避けて知らせる。","現状・変更内容を簡潔に断定的な表現は避けて知らせる。","現状・変更内容を詳しく断定的な表現は避けて知らせる。"},
        {"Aについて、⑴に記入すべき内容は","変更前と変更後の日程","変更後の日程","変更が決まった変更前の日程","変更点"},
        {"Bについて、これは上司に指示を求めるメール例文であるが、この際重要となる点は以下のうちどれ"," 情報の共有を図ること","曖昧な表現で断定しないこと","結論のみを簡潔に示すこと","自分の考えも加えること"},
        {"Bについて、⑴に入る内容として適切な書き方はどれ"," 箇条書き方式で２つ書く","１文章で書くこと","2点をそれぞれ文章でかくこと","部長の問い合わせ文をコピペする"}
        ,
    };

    //解説データ、クイズデータと配列番号は対応してる
    private String[] explanationData =
        {"現状・変更内容を簡潔にまとめて知らせるようにします。曖昧な表現はできるだけ避けて、明確な情報を伝えるように心掛けます。",
                " 【変更前】00:00〜00:00（20xx年00月00日まで）/n" +
                "【変更後】00:00〜00:00（20xx年00月00日から） のように相手に伝わりやすいよう変更前と後の日程を明確に示す。",
                " 社内向けの報告メールでは、現状・対応策をまとめて知らせる必要がある。途中経過や結果報告を忘れずにすることで上司との情報の共有を図ります。不安を助長する曖昧な表現はできるだけ避け、明確な報告をするように心掛けましょう",
                " ・試作品の完成予定日\n" +
                        "・現段階での概算見積り\n" +
                        "のように\n簡潔にわかりやすく明確に伝える必要がある"};

        private String externalQuizData = "メールA\n"+"件名：営業時間変更のお知らせ\n" +
                "○○株式会社\n" +
                "営業部　○○様\n" +
                "\n" +
                "平素よりご愛顧頂き誠にありがとうございます。\n" +
                "株式会社○○・営業部の佐藤です。\n" +
                "\n" +
                "さて、この度当社では、\n" +
                "社内業務の効率化および\n" +
                "働き方改革の一環として、\n" +
                "誠に勝手ながら営業時間を下記の通り\n" +
                "変更させていただくこととなりました。\n" +
                "\n" +
                "○○様にはご不便をおかけすることとなり\n" +
                "大変恐縮ではございますが、\n" +
                "これまで以上にサービス向上に誠心誠意努めて参りますので、\n" +
                "何卒ご理解ご協力を賜わりますようお願い申し上げます。\n" +
                "\n" +
                "(1)\n" +
                "※営業日に変更はございません。\n" +
                "※00時以降のご依頼分につきましては、翌営業日の作業扱いとなります。\n"+"\n"+"\n"+
                "メールB\n"+
                "件名：○○株式会社○○部長から問い合わせ\n" +
                "\n" +
                "○○課長\n" +
                "お疲れさまです。佐藤です。\n" +
                "\n" +
                "課長が外出中に、○○部長からお電話があり、\n" +
                "至急、以下の2点について\n" +
                "確認をお願いしたいとのことです。\n" +
                "\n" +
                "（１）\n" +
                "\n" +
                "完成予定日につきましては、\n" +
                "製造工場に電話で確認し、先方にお伝えいたしました。\n" +
                "見積りにつきましては、2日程お時間いただき\n" +
                "詳細を詰め、作成次第お渡しする運びとなりました。\n" +
                "\n" +
                "以上、取り急ぎ\n" +
                "ご確認よろしくお願いいたします。\n"
            ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // ここから追加
        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        eternalquestionLabel = findViewById(R.id.eternalquestionLabel);

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
