package com.example.bmq;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.example.bmq.R.*;

public class MainActivity extends AppCompatActivity
{

    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;
    private Button homeBtn;


    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 10;

    private int randomNum = 0;

    private OpenHelper helper;
    private SQLiteDatabase db;

    private ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    private ArrayList<String> explanationArray = new ArrayList<>();

    private ArrayList<String> quiz;

    private String quizkeep;

    private String[][] quizData = {
            // {"都道府県名", "正解", "選択肢１", "選択肢２", "選択肢３"}
            {"TOの意味として正しいものはどれか？", "メインの宛先", "情報を共有したいかつ、メインの受信者にも知らせたい", "情報を共有したいが、メインの相手には隠したい", "誰にも送らない"},
            {"CCの意味として正しいものはどれか？", "情報を共有したいかつ、メインの受信者にも知らせたい", "メインの宛先", "情報を共有したいが、メインの相手には隠したい", "誰にも送らない"},
            {"BCCの意味として正しいものはどれか？", "情報を共有したいが、メインの相手には隠したい", "メインの宛先", "情報を共有したいかつ、メインの受信者にも知らせたい", "誰にも送らない"},
            {"TOには必ず宛先を入れたほうが良いか？", "必ず入れる", "余裕があれば入れる", "絶対入れない", "余裕があれば入れる"},
            {"メールの構成として左から順に正しいものはどれか？", "宛名、挨拶、本文、結びの言葉、署名", "宛名、署名、挨拶、本文、結びの言葉", "挨拶、宛名、署名、本文、結びの言葉", "挨拶、署名、宛名、本文、結びの言葉"},
            {"宛名として左から順に正しい書き方はどれか？", "会社名、部署名、役職名、名前、敬称", "名前、会社名、部署名、役職名、敬称", "会社名、名前、敬称、部署名、役職名", "名前、敬称、部署名、役職名、会社名"},
            {"宛名の書き方として正しいものは？", "○○部　山口部長", "山口部長様", "山口部長御中", "○○部　山口部長様"},
            {"団体宛で正しい宛名の書き方は？", "○○株式会社御中", "○○株式会社様", "○○株式会社殿", "○○株式会社へ"},
            {"複数名に送る場合に正しい宛名の書き方は？", "○○株式会社　××部　各位", "○○株式会社　××部　皆様", "○○株式会社　各位", "○○株式会社　皆様"},
            {"あいさつ文として正しいものはどれ？", "お世話になっております。\n" +
                    "○○株式会社△△部の○○です。", "例年より寒さが身にこたえております\n" +
                    "○○株式会社△△部の○○です。", "こんにちは\n" +
                    "○○株式会社△△部の○○です。", "よっす！\n" +
                    "○○株式会社△△部の○○だ。"},
//            １１
            {"結びの言葉として正しいものはどれか？", "何卒よろしくお願いいたします", "読んでくれてありがとうございました", "お疲れさまでした", "これで終わりです"},
            {"署名の順番として正しいものはどれか？", "会社名、名前、住所、電話番号、メールアドレス、ウェブサイト名", "名前、住所、電話番号、メールアドレス、ウェブサイト、会社名", "名前、住所、電話番号、メールアドレス、ウェブサイト、会社名", "名前、住所、電話番号、メールアドレス、会社名、ウェブサイト名"},
            {"添付ファイルを送る際に気を付けるもとして正しくないものは？", "「ファイルを送ること」をメールの相手にあらかじめ伝える", "添付ファイルの容量や形式に注意する", "ウィルスに感染していないかチェックする", "添付ファイルを送る旨を文面に記載する"},
            {"メール送信前に気を付けることとして、正しくないものはどれ？", "メールのコピーをとっておく", "添付ファイルを送るなら、そのことが記載してあるか確認する", "宛先を再度確認する", "誤字脱字をチェックする"},
            {"件名を書くときに注意しないといけないことは？", "一目でわかるようにわかりやすく書く", "誰から来たかわかるように名前を入れる", "注目してもらうために絵文字を入れる", "件名ですべてが読み取れるように長く書く"},
            {"宛名の書き方として正しいものは？", "XXX株式会社\n" +
                    "営業本部\n" +
                    "部長　XXX様", "XXX株式会社御中\n" +
                    "営業本部\n" +
                    "部長　XXX様", "XXX株式会社\n" +
                    "営業本部\n" +
                    "部長　XXX様御中", "XXX株式会社御中\n" +
                    "営業本部\n" +
                    "部長　XXX様御中"},
            {"ビジネスメールを用いる際の注意点として正しくないものはどれか？", "急用にこそメールを使う", "受け取ったメールは、必ず1営業日以内に返信する", "メールでは「御社」ではなく「貴社」を使う", "「ご苦労様です」「了解しました」は使わない"},
            {"メールの返信時に気を付けることは？", "Re：は適度に書き直して返信する", "Re：は絶対に書き直してはならない", "Re：は絶対に使わず、一回一回返信のメールを行う", "Re：書き直す必要がないので、気にしない"},
            {"文章の構成として正しいものはどれ？", "結論→理由→事例→まとめ", "理由→事例→まとめ→結論", "理由→事例→まとめ→結論", "理由→結論→事例→まとめ"},
            {"文章の構成として正しいものはどれか？", "６Ｗ３Ｈ", "５Ｗ１Ｈ", "６Ｗ２Ｈ", "５Ｗ３Ｈ"},
//            ２１
            {"ファイルの送信をするときに注意することで正しく無いものは？", "ファイルの種類を確認しなくてよい", "ファイルのデータ容量を確認する", "ファイルを添付していることを文面に記載する", "ウィルスに感染していないか確認する"},
            {"ビジネスメールの表現として正しいものはどれか？", "（上司に対して）大変勉強になりました", "お体にご自愛ください", "（目上の人に対して）ご苦労様です", "（目上の人に対して）～殿"},
            {"メールでの表現として正しいものはどれか？", "承知しました", "～部長様", "御社", "～会社御中"},
            {"ビジネスメールのポイントとして正しくないものは？", "ＨＴＭＬ形式を利用する", "フォント環境の違いに気を付ける", "行間を適度にとる", "読点を多めにする"},
            {"ビジネスメールのポイントとして正しくないものは？", "横幅に気を付ける必要はない", "専門用語は避ける", "記号や罫線を使う", "機種依存文字を使わない"},
            {"件名の例でよいものはどれか？", "ビジネスメール研修（４／６）感想のお知らせ", "ご依頼について", "出社時間について", "経理処理について"},
            {"ＣＣで届いたメールはどのように返信するのが正しいか？", "情報を共有すべき場合は「全員に返信」、それ以外は「送信者のみ」に返信", "どんな時でも「全員に返信」に返信", "どんな時でも「送信者のみ」に返信", "ＢＣＣを用いて返信"},
            {"あなたが講義を開いて、終了後全員にお礼のメールを送るとき、どのように返信するのが正しいか？名詞を交換していないと仮定する。", "ＢＣＣを用いて返信する", "ＣＣを用いて返信", "ＴＯを用いて一人ずつ返信", "半分はＴＯ、半分はＣＣで返信する"},
            {"転送するときの注意点は？", "転送するときは前置きを付ける", "わかりやすいように編集する", "迷っても確認せず転送する", "転送内容は手元にコピーをとっておく"},
            {"催促メールを送るときの注意点は？", "期日を決めて、丁寧に書くようにする", "できるだけ急いでることがわかるように「まだですか？」と送る", "怒っていることがわかるように「いつになるの？」と送る", "とにかく怒っていることを伝える"},
//            ３１
            {"メール送信時の注意点で正しくないものは？", "時間がないので急いで送信する", "誤字脱字をチェックする", "ＣＣとＢＣＣにミスがないかチェックする", "敬語のミスがないか確認する"},
            {"件名を目立たせる方法は？", "【】を使う", "！を多用する", "顔文字を使う", "絵文字を使う"},
            {"メールの本文を書くときに気を付けるもとして正しくないものはどれか？", "１文のなかに用件ををたくさん詰める", "１メールにつき１用件を意識する", "本文の要点を押さえる", "文章を分割して読みやすくする"},
            {"メールの送信時間は何時が良いか？", "先方の就業時間内", "就業時間外", "平日の９時から１７時", "土日以外ならいつでも"},
            {"ビジネスメールを送る際のマナーとして正しくないものは？", "開封通知を付ける", "長いメールを送る場合は冒頭にメールが長いことを書く", "ボリューム感は先方にそろえる", "添付ファイルは両者了承のもとで送る"},
            {"ビジネスメールで好感度を上げる方法で間違っているものは？", "すみません、を多用する", "マイナス面を先に述べる", "気遣いの一文を入れる", "ありがとう、を意識して使う"},

    };

    //解説データ、クイズデータと配列番号は対応してる
    private String[] explanationData = {
            "「TO」はメインの宛先です。送りたい人が複数いる場合は、TOに複数人のメールアドレスを書きます。このとき「CC」を使わないようにしましょう。参考程度に読んでほしい、と思われます。",
            "「CC」に書かれている人には、参考程度に見ておいてください、という意味があります。",
            "「BCC」は、「TO」で送った相手にばれないように、メールを送るときに使います。",
            "メインの宛先である「To」にメールアドレスを入れないと、相手の迷惑メールフォルダに入ってしまう可能性があります。",
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
                    "「部長」「課長」などの役職名は「様」と同様に敬称なので、正しい書き方は、以下のとおりです。\n" +
                    "・○○部　山口部長\n" +
                    "・○○部　部長　山口様",
            "団体向けの宛名では「○○株式会社御中」または「○○株式会社　△△部御中」と書きます。\n",
            "送信先が部署全体や、複数の取引先等の場合は「各位」を使います。\n" +
                    "○○株式会社　××部　各位\n" +
                    "お取引先　各位\n" ,
            "手紙と違って、時候の挨拶はいらないです。",
//            １１
            "ビジネスメールでは、本文の後に「結びの言葉」を入れます。\n" +
                    "例、「何卒よろしくお願いいたします」「お手数をおかけしますが、どうぞよろしくお願いいたします」",
            "・会社名、部署名\n" +
            "・名前\n" +
            "・住所\n" +
            "・電話番号、FAX番号\n" +
            "・メールアドレス\n" +
            "・Webサイト名、URL\n" +
            "一般的に、会社では署名のテンプレートが用意されていることが多いので、それを書き換えましょう。",
            "添付ファイルを送るときは、容量や形式、ウィルス感染に気を付け、ファイルを送る旨を文面に記述しましょう。",
            "宛先の確認を再度行い、誤字脱字をチェックするようにしましょう",
            "件名はわかりやすく書くことが重要なので、一目でわかるように書きましょう。",
            "「会社名、部署名、役職、氏名」の順に記載します\n" +
            "組織や団体に送信する場合、「御中」を使います。「御中」に敬意が含まれているため「○○様 御中」は間違った使い方となります",
            "急用の時はメールではなく電話を使うようにしましょう",
            "何度も相手とやり取りをしているとRe:Re:Re:と重ねって失礼なので、適度に書き直すようにしましょう。",
            "これはPREP法という話法で、結論→理由→事例→まとめの順で文書などを作成します",
            "who:誰が\n" +
            "whom:誰に\n" +
            "when:日時\n" +
            "where:場所\n" +
            "what:何を\n" +
            "why:理由\n" +
            "how to:手段\n" +
            "how many:量\n" +
            "how much:金額\n" +
            "これらを書きましょう",
//            21
            "自分が使っているソフトが相手のパソコンに閲覧できるソフトが入っているとは限らないので、相手に確認をとる必要があります",
            "お体にご自愛ください→どうぞご自愛ください\n" +
            "ご苦労様です→お疲れ様です\n" +
            "～殿→～様",
            "部長と様を重ねてはいけません。御社は話し言葉です。御中はメールで使いません",
            "ビジネスメールではテキスト形式で送るほうが良いです",
            "文字幅が長くても読みづらいので、20～30字くらいにしましょう",
            "開封しなくてもメールに何が書いているかわかるような件名を付けるようにしましょう",
            "メールを受け取った人が迷惑を受けないように考えて返信しましょう",
            "この場合ＣＣを用いて返信してしまうと、情報漏洩になってしまうので気を付けましょう",
            "転送するときは、編集・加工は厳禁です。前置きは付けておくようにしましょう。",
            "相手に思いやりの気持ちをもってメールしましょう",
//           31
            "時間がなくても必ずチェックするようにしましょう",
            "学生のやり取りではないので【】使いましょう",
            "１文の中には用件を詰め込みすぎなようにしましょう",
            "相手に負担をかけないためにも、就業時間内に送りましょう。遅くなる場合は、夜分遅くに申し訳ありません、と付けましょう",
            "開封通知はつけなくても大丈夫です",
            "何かしてもらったとき、謝られるよりも感謝されたほうが嬉しいですよね"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        // ここから追加
        countLabel = findViewById(id.countLabel);
        questionLabel = findViewById(id.questionLabel);
        answerBtn1 = findViewById(id.answerBtn1);
        answerBtn2 = findViewById(id.answerBtn2);
        answerBtn3 = findViewById(id.answerBtn3);
        answerBtn4 = findViewById(id.answerBtn4);
        homeBtn = findViewById(id.homeBtn);

        // quizDataからクイズ出題用のquizArrayを作成する
        for (int i = 0; i < quizData.length; i++)
        {
            // 新しいArrayListを準備
            ArrayList<String> tmpquizarray = new ArrayList<>();


            // クイズデータを追加
            tmpquizarray.add(quizData[i][0]);  // 問題
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
        quiz = quizArray.get(randomNum);

        // 問題文を表示
        questionLabel.setText(quiz.get(0));

        // 正解をrightAnswerにセット
        rightAnswer = quiz.get(1);

        // 削除する前に保存しとく
        quizkeep = quiz.get(0);

        // クイズ配列から問題文（都道府県名）を削除
        quiz.remove(0);

        // 正解と選択肢３つをシャッフル
        Collections.shuffle(quiz);

        // 回答ボタンに正解と選択肢３つを表示
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

//        debug--;
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
            // このクイズをquizArrayから削除
            quizArray.remove(randomNum);
        }
        else
        {
            alertTitle = "不正解...";

            if(helper == null){
                helper = new OpenHelper(getApplicationContext());
            }

            if(db == null){
                db = helper.getWritableDatabase();
            }

            quiz = quizArray.get(randomNum);

            // quiz(0)が問題、quiz(1)正解,quiz(2)選択肢,quiz(3)選択肢,quiz(4)選択肢
            insertData(db, quizkeep,quiz.get(0),quiz.get(1),quiz.get(2),quiz.get(3),explanation);

            // このクイズをquizArrayから削除
            quizArray.remove(randomNum);
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