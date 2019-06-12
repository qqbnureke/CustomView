package com.nurda.customview;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nurda.customview.model.Airport;
import com.nurda.customview.model.CardInfo;
import com.nurda.customview.model.Flight;
import com.nurda.customview.views.CardView;
import com.nurda.customview.views.EmojiView;
import com.nurda.customview.views.FlightView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Flight FLIGHT = new Flight(
            "AA 1",
            "Sep 18, 2017",
            "07:57",
            "11:19",
            "Duration: 6h 22m",
            new Airport("JFK", "New York, US", "John F. Kennedy International Airport"),
            new Airport("LAX", "Los Angeles, US", "Los Angeles International Airport")
    );

    private static final CardInfo cardInfo = new CardInfo(
            "travel",
            "New York City",
            "Day reappeared. The tempest still raged with Day reappeared. " +
                    "The tempest still raged with Day reappeared. The tempest still raged " +
                    "with Day reappeared. The tempest still raged with ",
            R.drawable.imagee,
            "Button 1", "Button 2");

    EmojiView emojiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // final CardView customFlightView = findViewById(R.id.flight_view);
//        customFlightView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Flying", Toast.LENGTH_SHORT).show();
//            }
//        });
      //  customFlightView.bindFlight(cardInfo);

        emojiView = findViewById(R.id.emoji_view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.happy_emoji: emojiView.setHappinessState(EmojiView.HAPPY); break;
            case R.id.sad_emoji: emojiView.setHappinessState(EmojiView.SAD); break;
            case R.id.shocked_emoji: emojiView.setHappinessState(EmojiView.SHOCKED); break;
            case R.id.neutral_emoji: emojiView.setHappinessState(EmojiView.NEUTRAL); break;
        }
    }
}
