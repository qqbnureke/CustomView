package com.nurda.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nurda.customview.views.EmojiView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EmojiView emojiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emojiView = findViewById(R.id.emoji_view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.happy_emoji: emojiView.setHappinessState(EmojiView.HAPPY); break;
            case R.id.sad_emoji: emojiView.setHappinessState(EmojiView.SAD); break;
            case R.id.shocked_emoji: emojiView.setHappinessState(EmojiView.SHOCKED); break;
            case R.id.neutral_emoji: emojiView.setHappinessState(EmojiView.NEUTRAL); break;
            case R.id.next_button:
                startActivity(new Intent(this, ViewGroupExample.class));
        }
    }
}
