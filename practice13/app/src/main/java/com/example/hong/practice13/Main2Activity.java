package com.example.hong.practice13;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    ImageButton imageButton;
    TextView textView;
    EditText editText;
    ImageButton button;
    myTaks task;
    int click = 0;
    int count = 0;//지나간 초
    int second;//사용자가 입력한 숫자
    int i = 0;//picture,name
    int picture[] = {R.drawable.flower1, R.drawable.flower2, R.drawable.flower3, R.drawable.flower4, R.drawable.flower5};
    String name[] = {"진달래", "장미", "개나리", "제비꽃", "코스모스"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        button = (ImageButton) findViewById(R.id.button);

    }

    class myTaks extends AsyncTask<Integer, Integer, Void> {

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            imageButton.setImageResource(R.drawable.play);
            textView.setText("");


        }

        @Override
        protected Void doInBackground(Integer... params) {
            while (isCancelled() == false) {
                count++;
                try {
                    Thread.sleep(1000);
                    if (count % params[0] == 0) {
                        if (i >= 4) {
                            i = -1;
                        }
                        publishProgress(count, ++i);
                    } else {
                        publishProgress(count, i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {//ui
            super.onProgressUpdate(values);
            textView.setText("시작 부터 " + values[0] + "초");
            imageButton.setImageResource(picture[values[1]]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            textView.setText(name[i] + " 선택(" + count + "초)");
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button) {//초기화
            task.cancel(true);
            imageButton.setImageResource(R.drawable.play);
            textView.setText("");
            editText.setText("");
            click = 0;
            count = 0;
            i = 0;

        } else if (v.getId() == R.id.imageButton) {

            if (click == 0) {
                task = new myTaks();
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    second = Integer.parseInt(editText.getText().toString());
                    click = 1;
                    count = 0;
                    i = 0;
                    task.execute(second);
                }
            } else {
                task.cancel(true);
                click = 0;
            }
        }
    }
}
