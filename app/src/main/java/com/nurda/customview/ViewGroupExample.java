package com.nurda.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nurda.customview.model.CardInfo;
import com.nurda.customview.views.CardView;

public class ViewGroupExample extends AppCompatActivity {

    private static final CardInfo cardInfo1 = new CardInfo(
            "25 June",
            "BA (Hons) and FdA",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                    " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
            R.drawable.events_1);

    private static final CardInfo cardInfo2 = new CardInfo(
            "20 July",
            "Dominator Festival",
            "A deafening sandstorm approaches with deadly speed. Seven extreme " +
                    "formations will compete for a tremendous trophy. Screaming engines, " +
                    "smoking exhausts and dreaded drivers will invade our savage sands. ",
            R.drawable.events_2);

    private static final CardInfo cardInfo3 = new CardInfo(
            "5 October",
            "Music Festival",
            "Every year on the first weekend of October, Cape Town is kicking off the " +
                    "summer with one of the biggest and hottest outdoor festivals.",
            R.drawable.events_3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_example);


        ((CardView)findViewById(R.id.card_view_first)).bindCard(cardInfo1);
        ((CardView)findViewById(R.id.card_view_second)).bindCard(cardInfo2);
        ((CardView)findViewById(R.id.card_view_third)).bindCard(cardInfo3);
    }
}
