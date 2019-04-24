package com.ict.delivirko.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.ict.delivirko.R;

public class SelectServiceActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutRest, layoutPilot, layoutClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);

        layoutClient = findViewById(R.id.layoutClient);
        layoutRest = findViewById(R.id.layoutRest);
        layoutPilot = findViewById(R.id.layoutPilot);

        layoutClient.setOnClickListener(this);
        layoutRest.setOnClickListener(this);
        layoutPilot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutClient:

                break;

            case R.id.layoutRest:
                startActivity(new Intent(this, LoginRestActivity.class));
                finish();
                break;

            case R.id.layoutPilot:
                Intent intent = new Intent(this, LoginPilotActivity.class);
                intent.putExtra("LAT", getIntent().getDoubleExtra("LAT",0 ));
                intent.putExtra("LNG", getIntent().getDoubleExtra("LNG",0 ));
                startActivity(intent);

                finish();
                break;
        }
    }


}
