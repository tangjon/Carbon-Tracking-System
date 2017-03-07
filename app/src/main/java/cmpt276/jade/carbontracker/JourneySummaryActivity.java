package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.model.RouteCollection;

public class JourneySummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_summary);

        setData();
        setupBackBtn();
    }

    private int getCityDistance() {
        Intent intent = getIntent();
            int City = intent.getIntExtra("Passing City Distance", 0);
            return City;
    }

    private int getHighwayDistance() {
        Intent intent = getIntent();
            int Highway = intent.getIntExtra("Passing Highway Distance", 0);
            return Highway;
    }

    private String getJourneyNickname() {
        Intent intent = getIntent();
            String nickname = intent.getStringExtra("Passing Journey nickname");
            return nickname;
    }


    private void setData() {
        //String Mode=getMode();
        int TotalCity=getCityDistance();
        int TotalHighway=getHighwayDistance();
        String JouneyNickname=getJourneyNickname();

        //TODO pass car name to summary
        TextView carName = (TextView) findViewById(R.id.textCarName);
        //carName.setText(car.getNickname());
        carName.setText("Car name");

        setTextView(JouneyNickname, TotalCity, TotalHighway);

    }

    private void setupBackBtn() {
        Button btn = (Button) findViewById(R.id.btnJourneySummaryBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    private void setTextView(String JouneyNickname,int TotalCity,int TotalHighway) {
        TextView routeName = (TextView) findViewById(R.id.textRouteName);
        routeName.setText(JouneyNickname);
        TextView cityDrive = (TextView) findViewById(R.id.textCitydrv);
        cityDrive.setText("" + TotalCity);
        TextView hwayDrive = (TextView) findViewById(R.id.textHwaydrv);
        hwayDrive.setText("" + TotalHighway);
        TextView totalDrive = (TextView) findViewById(R.id.textTotaldrv);
        totalDrive.setText("" + (TotalCity + TotalHighway));
    }

   public static Intent getJourneySummaryIntent(Context context, RouteCollection routes) {
        Intent intent = new Intent(context, JourneySummaryActivity.class);
        intent.putExtra("Passing City Distance", routes.getTotleCityDistance());
        intent.putExtra("Passing Highway Distance", routes.getTotleHighWayDistance());
        intent.putExtra("Passing Journey nickname", routes.getJourneyName());
        return intent;
    }



}
