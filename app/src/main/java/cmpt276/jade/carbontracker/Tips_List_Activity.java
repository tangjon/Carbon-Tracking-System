package cmpt276.jade.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.model.RouteCollection;

public class Tips_List_Activity extends AppCompatActivity {

    public static RouteCollection OneDayRoutes = new RouteCollection();
    double TotalEmissions;
    double TotalCity=OneDayRoutes.getTotleCityDistance();
    double TotalHighWay=OneDayRoutes.getTotleHighWayDistance();
    double TotalWalk= 1.0;
    double TotalBike=1.0;
    double TotalBus=1.0;
    double TotalSkyTrain=1.0;

    private List<String> List_Tips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tips_list);

        TipsForWalk();//14
        TipsForBike();//8
        TipsForTotalEmissions();//6
    }

    //there are 14 tips for walking
    private void TipsForWalk() {
        //for young person
        if(TotalWalk==0.0)
        {
            String tip= "You haven't walk today,"
                    +"Walking have lots of benefit for your health, "
                    +"Try to walk tomorrow.";
            List_Tips.add(tip);
        }
        else if(TotalWalk<2.8)
        {
            String tip="The minimum standard for for young person is 4000 steps per day"
                    + "which is around 2.8km. "
                    +"You have walked " +TotalWalk
                    +" km today.You haven't met the minimum standard for young person.";
            List_Tips.add(tip);
        }
        else if(TotalWalk>=2.8)
        {
            String tip="The minimum standard for young person is 4000 steps per day"
                    + "which is around 2.8km. "
                    +"You have walked " +TotalWalk
                    +" km today.You have met the minimum standard for young person";
            List_Tips.add(tip);
        }
        else if(TotalWalk<4.9)
        {
            String tip="The normal standard for for young person is 7000 steps per day"
                    + "which is around 4.9 km. "
                    +"You have walked " +TotalWalk
                    +" km today.You haven't met the normal standard for young person.";
            List_Tips.add(tip);
        }
        else if(TotalWalk>=4.9)
        {
            String tip="The normal standard for for young person is 7000 steps per day"
                    + "which is around 4.9 km. "
                    +"You have walked " +TotalWalk
                    +" km today.You have met the normal standard for young person.";
            List_Tips.add(tip);
        }
        else if(TotalWalk<7.0)
        {
            String tip="The healthy lifestyle for young person is 10000 steps per day"
                    + "which is around 7.0 km. "
                    +"You have walked " +TotalWalk
                    +" km today.You haven't met the healthy standard for young person.";
            List_Tips.add(tip);
        }
        else if(TotalWalk>=7.0)
        {
            String tip="The healthy lifestyle for young person is 10000 steps per day"
                    + "which is around 7.0 km. "
                    +"You have walked " +TotalWalk
                    +" km today.You have met the healthy standard for young person.";
            List_Tips.add(tip);
        }



        //for old person
        else if(TotalWalk<3.5)
        {
            String tip="The healthy lifestyle for elder person is 5000 steps per day"
                    + "which is around 3.5 km. "
                    +"You have walked " +TotalWalk
                    +" km today.You haven't met the healthy standard for elder person.";
            List_Tips.add(tip);
        }
        else if(TotalWalk>=3.5)
        {
            String tip="The healthy lifestyle for elder person is 5000 steps per day"
                    + "which is around 3.5 km. "
                    +"You have walked " +TotalWalk
                    +" km today.You have met the healthy standard for elder person.";
            List_Tips.add(tip);
        }
        else if(TotalWalk<7.0)
        {
            String tip="The limition of healthy lifestyle for elder person is 10000 steps per day"
                    + "which is around 7.0 km. "
                    +"You have walked " +TotalWalk
                    +" km today.You haven't reached the limition of healthy standard for elder person.";
            List_Tips.add(tip);
        }
        else if(TotalWalk>=7.0)
        {
            String tip="The limition of healthy lifestyle for elder person is 10000 steps per day"
                    + "which is around 7.0 km. "
                    +"You have walked " +TotalWalk
                    +" km today.You reached the limition of healthy standard for elder person.";
            List_Tips.add(tip);
        }
        else if(TotalWalk>=10.5)
        {
            String tip="The unhealthy lifestyle for elder person is walking more than 10000 steps per day"
                    + "which is more than 7.0 km. "
                    +"You have walked " +TotalWalk
                    +" km today.You may get gonarthromeningitis in the future.";
            List_Tips.add(tip);
        }
        else if(TotalWalk>=14.0)
        {
            String tip="The unhealthy lifestyle for elder person is walking more than 10000 steps per day"
                    + "which is more than 7.0 km. "
                    +"You have walked " +TotalWalk
                    +" km today.You may get effusionofkneejoint in the future.";
            List_Tips.add(tip);
        }
        else if(TotalWalk>=21.0)
        {
            String tip="The unhealthy lifestyle for elder person is walking more than 10000 steps per day"
                    + "which is more than 7.0 km. "
                    +"You have walked " +TotalWalk
                    +" km today.You may hurt your femur in the future.";
            List_Tips.add(tip);
        }

    }




    //there are 8 tips for walking
    private void TipsForBike() {
        if(TotalBike==0.0)
        {
            String tip= "You haven't rode bike today,"
                    +"Riding bike have lots of benefit for your health, "
                    +"Try to ride bike tomorrow.";
            List_Tips.add(tip);
        }
        else if(TotalBike>=4.0 && TotalBike<=5.0)
        {
            String tip= "You rode " +TotalBike
                    +" km today. Ride bike around 5 km everyday,"
                    +"could help boost estrogen and androgen levels.";
            List_Tips.add(tip);
        }
        else if(TotalBike>=5.0 && TotalBike<=10.0)
        {
            String tip="The average daily distance recommended for most bicycle tourists per day"
                    + "is 5 km TO 10 km."
                    +"You rode " +TotalBike
                    +" km today.You have met the healthy standard for young person";
            List_Tips.add(tip);
        }
        else if(TotalBike>10.0 && TotalBike<64.37 )
        {
            String tip="The average daily distance for training recommended for most bicycle tourists per day"
                    + "is 40 to 60 mile. Which is 64.37 km TO 96.56 km."
                    +"You rode " +TotalBike
                    +" km today.You You have met the healthy standard," +
                    "but haven't met the recommendation for training.";
            List_Tips.add(tip);
        }
        else if(TotalBike>=64.37 && TotalBike<=96.56)
        {
            String tip="The average daily distance for training recommended for most bicycle tourists per day"
                    + "is 40 to 60 mile. Which is 64.37 km TO 96.56 km."
                    +"You rode " +TotalBike
                    +" km today.You have met the healthy standard for young person";
            List_Tips.add(tip);
        }
        else if(TotalBike>=100)
        {
            String tip="For strong physical training purpose,the recommended for most bicycle tourists per day"
                    + "is over 100 km if you are beginner"
                    +"You rode " +TotalBike
                    +" km today.It is good for your health";
            List_Tips.add(tip);
        }
        else if(TotalBike>=200) {
            String tip = "For strong physical training purpose,the recommended for most bicycle tourists per day"
                    + "is over 200 km if you are veteran"
                    + "You rode " + TotalBike
                    + " km today.It is good for your health";
            List_Tips.add(tip);
        }
        else if(TotalBike>=300)
        {
            String tip="For strong physical training purpose,the recommended for most bicycle tourists per day"
                    + "is over 200 km if you are veteran"
                    +"You rode " +TotalBike
                    +" km today.You have rode too much, you may hurt yourself.";
            List_Tips.add(tip);
        }
    }


    //The maximum amount of CO2 a person should produce per year in order to halt climate change
    //2.0 t
    //so 2.0/365=0.005479t=5.48kg

    //place            2013 year         per day        in kg
    //world            4.996            0.01368          13.69kg
    //Canada           13.5             0.0369          36.9kg
    //US               16.4             0.0449          44.9kg
    //Hign Income      11.0             0.03013         30.13kg
    //Low & Middle     3.5              0.00958         9.58kg
    //Low              0.3              0.00082         0.82kg
    //Lower Middle     1.4              0.00383         4.83kg
    //Middle           3.9              0.01068         10.68kg
    //Upper Middle     6.6              0.018           18kg
    private void TipsForTotalEmissions() {
        if(TotalEmissions==0 && TotalBike==0 && TotalWalk==0)
        {
            String tip="You didn't went outside today, not good for your health, "
                    +" call yours friends, have some fun outside tomorrow.";
            List_Tips.add(tip);
        }
        else if(TotalEmissions==0 && TotalBike!=0 || TotalWalk!=0)
        {
            String tip="You didn't drove or took any trans, The TotalEmissions is 0,"
                    +" well done.";
            List_Tips.add(tip);
        }
        else if(TotalEmissions>0 && TotalEmissions<5.48)
        {
            //TODO I copy this from Internet, may rewrite
            String tip="The maximum amount of CO2 a person should produce per year in "
                   + "order to halt climate change is 5.48 kg."
                    +"Your TotalEmissions is :"+TotalEmissions
                    +". Well done, you are helping our world becoming a great place.";
            List_Tips.add(tip);
        }
        else if(TotalEmissions>=5.48 && TotalEmissions<=13.69)
        {
            //TODO I copy this from Internet, may rewrite
            String tip="The maximum amount of CO2 a person should produce per year in "
                    + "order to halt climate change is 5.48 kg.The world average is 13.69 kg."
                    +"Your TotalEmissions is :"+TotalEmissions
                    +". Although you exceeded the expection, but you still below the world average.";
            List_Tips.add(tip);
        }
        else if(TotalEmissions>=13.69 && TotalEmissions<=36.9)
        {
            String tip="The world average Emissionsis 13.69 kg."
                    +"and Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+TotalEmissions
                    +". Although you exceeded the world average, but you still below the Canada average.";
            List_Tips.add(tip);
        }
        else if(TotalEmissions>=36.9)
        {
            String tip="The world average Emissionsis 13.69 kg."
                    +"and Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+TotalEmissions
                    +". You exceeded both the world average, and Canada average.";
            List_Tips.add(tip);
        }
    }


}
