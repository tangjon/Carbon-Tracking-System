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

    private int getCityDistance(String Mode) {
        Intent intent = getIntent();
        if(Mode!=null) {
            int City = intent.getIntExtra("Passing City Distance", 0);
            return City;
        }
        else
        {
            int City =intent.getIntExtra("Passing One City Distance",0);
            return City;
        }
    }

    private int getHighwayDistance(String Mode) {
        Intent intent = getIntent();
        if(Mode!=null) {
            int Highway = intent.getIntExtra("Passing Highway Distance", 0);
            return Highway;
        }
        else
        {
            int Highway =intent.getIntExtra("Passing One Highway Distance",0);
            return Highway;
        }
    }

    private String getJourneyNickname(String Mode) {
        Intent intent = getIntent();
        if(Mode!=null) {
            String nickname = intent.getStringExtra("Passing Journey nickname");
            return nickname;
        }
        else{
            String RouteNickname =intent.getStringExtra("Passing One Route nickname");
            return RouteNickname;
            }
    }

    private String getMode() {
        Intent intent = getIntent();
        String Mode =intent.getStringExtra("Passing Mode");
        return Mode;
    }

    private void setData() {
        String Mode=getMode();
        int TotalCity=getCityDistance(Mode);
        int TotalHighway=getHighwayDistance(Mode);
        String JouneyNickname=getJourneyNickname(Mode);

        //TODO pass car name to summary
        TextView carName = (TextView) findViewById(R.id.textCarName);
        //carName.setText(car.getNickname());
        carName.setText("Car name");

        if(Mode!=null) {
            setTextView(JouneyNickname, TotalCity, TotalHighway);

        }
        else{
            setTextView(JouneyNickname, TotalCity, TotalHighway);
        }
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

    //two inten
    //one is when user click one route to see the summary
    //one intent is after user enter lots of routes and want to see the summary
    public static Intent getJourneySummaryIntent(Context context, RouteCollection routes,String Mode) {
        Intent intent = new Intent(context, JourneySummaryActivity.class);
        intent.putExtra("Passing City Distance", routes.getTotleCityDistance());
        intent.putExtra("Passing Highway Distance", routes.getTotleHighWayDistance());
        intent.putExtra("Passing Journey nickname", routes.getJourneyName());
        intent.putExtra("Passing Mode", Mode);
        return intent;
    }
    public static Intent getOneRouteIntent(Context context, Route clicked_one,String Mode) {
        Intent intent = new Intent(context, JourneySummaryActivity.class);
        intent.putExtra("Passing One City Distance", clicked_one.getCityDistance());
        intent.putExtra("Passing One Highway Distance", clicked_one.getHighWayDistance());
        intent.putExtra("Passing One Route nickname", clicked_one.getName());
        return intent;
    }

}
