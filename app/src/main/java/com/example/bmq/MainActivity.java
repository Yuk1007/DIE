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

    private int randomNum = 0;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    ArrayList<String> explanationArray = new ArrayList<>();

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

    //解説データ、クイズデータと配列番号は対応してる
    String explanationData[] = {
            "TOは必ずメールの要件を伝えたい、メインの宛先です。担当者が複数いる場合は、TOに複数人のメールアドレスを書きます。「CC」を使いたくなりますが、「CC」は「メインの宛先ではありませんが、参考に送ります」という意味があるので、必ず読んでほしい場合は「To」を使いましょう。",
            "「CC」は全員のメールアドレスが送信先に表示されるので、送信者は各関係者を知っていても受信者がお互いを知らない場合もあります。情報漏えいにあたらないよう細心の注意を払いましょう。",
            "「BCC」は、例えば複数の顧客へ同じ内容のメールを一斉に送りたい、取引先とのメールのやり取りをこっそり上司にも送りたいときなど、参考先を隠したい場合に使います。",
            "メインの宛先である「To」にメールアドレスを入れずに、「CC」や「BCC」だけ入力してメールを送信してしまうと、受信者側の迷惑メールフォルダに入ってしまう可能性があります。",
            "・宛名\n" +
                    "・挨拶、名乗り\n" +
                    "・本文\n" +
                    "・結びの言葉\n" +
                    "・署名",
            "・会社名\n" +
                    "・部署名\n" +
                    "・役職名\n" +
                    "・名前\n" +
                    "・敬称",
            "「山口部長様」のように、役職と敬称を重ねて書いている人がいますが、これは間違った書き方です。\n" +
                    "「部長」「課長」などの役職名は「様」と同様に敬称ですから、正しい書き方は、以下のとおりです。\n" +
                    "・○○部　山口部長\n" +
                    "・○○部　部長　山口様",
            "団体向けの宛名では「○○株式会社御中」または「○○株式会社　△△部御中」と書きます。\n",
            "送信先が部署全体や、複数の取引先等の場合は「各位」を使います。\n" +
                    "○○株式会社　××部　各位\n" +
                    "お取引先　各位\n" +
                    "名前を書く場合は、必ず役職順に書きましょう。\n" +
                    "並び方は、横でも縦でも構いません。\n" +
                    "○○株式会社　\n" +
                    "吉田様、川口様、林様",
            "手紙と違って、時候の挨拶はいらないです。",
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