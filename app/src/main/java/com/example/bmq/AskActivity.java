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

public class AskActivity extends AppCompatActivity
{
    private TextView countLabel;
    private TextView questionLabel;
    private TextView eternalquestionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;
    private Button homeBtn;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 3;

    private int randomNum = 0;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    ArrayList<String> explanationArray = new ArrayList<>();


    String quizData[][] = {
            // {"都道府県名", "正解", "選択肢１", "選択肢２", "選択肢３"}

            {"①で正しいのは", "株式会社●●●●●●●●様"," ●●●●様 株式会社●●●●", "●●●●(会社名)●●●●様","●●●●様●●●●(会社名)"},
        {"②にあてはまる文章は","お世話になっております。","お世話様です。","ご苦労様です。","お世話になります。"},
        {"③に入る適切な文は","お聞かせください。","お聞かせ下さい。","教えてください。","教えて下さい。"},
        {"④に入る最も適切な文は","いずれの時間帯も、ご都合が悪い場合は遠慮なくお知らせください。","お手数ですが、●月●日（●）までにお知らせください。","ご都合お知らせください。","時間帯の調整よろしくお願いいたします。"},
        {"⑴に入るより適切な表現は次のうちどれ。","お願いできますでしょうか。","お願いします。","してください。","頼みます。"},
        {"⑵伝える要件で必要なものは","日程、場所、金額、支払い方法、要望(その他)","日程、場所、金額、要望(その他)","日程、場所、金額","日程、場所、金額、時間"}
,
        };

    //解説データ、クイズデータと配列番号は対応してる
    String explanationData[] =
            {"相手が起業に属している場合、会社名と部署名を書いてから、名前を書く。その際敬称の「様」を忘れないようにしましょう。相手の会社名の「株式会社」や「(株)」「(有)」などは略してはいけません。",
                    "2、3、4、依頼のメールでは「～していただけないでしょうか。」など問いかけ型のフレーズを利用することで、相手の依頼されている抵抗感を軽減させてくれます。","2、3、4、依頼のメールでは「～していただけないでしょうか。」など問いかけ型のフレーズを利用することで、相手の依頼されている抵抗感を軽減させてくれます。",
        "2、3、4、依頼のメールでは「～していただけないでしょうか。」など問いかけ型のフレーズを利用することで、相手の依頼されている抵抗感を軽減させてくれます。",
        "2、3、4、依頼のメールでは「～していただけないでしょうか。」など問いかけ型のフレーズを利用することで、相手の依頼されている抵抗感を軽減させてくれます。",
        "2、3、4、依頼のメールでは「～していただけないでしょうか。」など問いかけ型のフレーズを利用することで、相手の依頼されている抵抗感を軽減させてくれます。",
        " ■日程：8月26日（月）/n" +
        "■場所：「梅田駅」徒歩7分以内/n" +
        "■金額：12,000円以内/n" +
        "■支払い：当日現金/n" +
        "■その他：食事なし、禁煙、部屋は極力広めで /n" +
        "宿泊手配する際に必要な条件を全て列挙する。相手に手間をかけさせないように業務を円滑に進めることを心掛け、依頼内容や希望をできるだけ具体的に示す。"};

    String externalQuizData = "①\n" +
            "②\n" +
            "\n" +
            "\n" +
            "平素よりご愛顧頂き誠にありがとうございます。\n" +
            "株式会社●●●●の山田太郎です。\n" +
            "\n" +
            "先日ご依頼いただきました●●の打ち合わせ日程についてご相談です。\n" +
            "\n" +
            "下記に候補日から、●●様のご都合のいい日を③\n" +
            "\n" +
            "＜候補日時＞\n" +
            "------------------------------------------\n" +
            "省略\n" +
            "------------------------------------------\n" +
            "④\n" +
            "\n" +
            "調整しますので、候補日を複数いただけると幸いです。\n" +
            "\n" +
            "よろしくお願いいたします。\n"
            ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        // ここから追加
        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);
        homeBtn = findViewById(R.id.homeBtn);

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

        homeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplication(), StartActivity.class);
                startActivity(intent);
            }
        });
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
