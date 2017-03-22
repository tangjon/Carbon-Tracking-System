package cmpt276.jade.carbontracker.model;

// Tip class
// has 32 tips for journey(car bus skytrain walk bike)
// getJourneyTip();
// has 16 tips for utility(electricity,gas)
// tipsForGasEmissions();
// tipsForElectricityEmissions();
// won't repeat in 7 times peroid
public class Tip {

    double totalCityEmissions;
    double totalHighwayEmissions;
    double totalCarEmissions=totalCityEmissions+totalHighwayEmissions;

    double totalWalk;
    double totalBike;
    double totalBusEmission;
    double totalSkyTrainEmission;

    double totalJourneyEmissions=totalCarEmissions+ totalBusEmission + totalSkyTrainEmission;
    double totalElectricityEmissions;
    double totalGasEmissions;

    int walkTipsNotRepeat[]= {8,8,8,8,8, 8,8,8,8,8};                        //8
    int bikeTipsNotRepeat[]= {8,8,8,8,8, 8,8,8,8,8, 8,8};                   //11
    int journeyEmissionsTipsNotRepeat[]= {8,8,8,8,8, 8,8,8,8,8, 8,8,8,8};  //13
    int electricityTipsNotRepeat[]=  {8,8,8,8,8, 8,8,8,8,8};                //8
    int gasTipsNotRepeat[]=  {8,8,8,8,8, 8,8,8,8,8};                        //8


    // total 32 tips for journey(include, walk,bike,bus,skytrain)
    public String getJourneyTip() {
        String tips= tipsForWalk();
        if(tips.length()>2){
            // if there are available tips for walk
            return tips;
        }
        else {
            // if there are no available tips for walk, get bike tips
            tips= tipsForBike();
            if(tips.length()>2){
                return tips;}
            else {
                // if there are no available tips for walk,and bike, get journey tips
                tips= tipsForJourneyTotalEmissions();
                if(tips.length()>2){
                    return tips;
                }}}
        //there are no tips available for that three array
        return "";
    }

    //there are 8 tips for walking
    // Will have 1~3 tips for one period
    public String tipsForWalk() {
        for (int x = 0; x < walkTipsNotRepeat.length; x++) {
            walkTipsNotRepeat[x] ++;
        }
        if(totalWalk ==0.0 && walkTipsNotRepeat[0]>8) {
            String tip= "You haven't walk today,"
                    +"Walking have lots of benefits to your health. "
                    +"Have a walk after dinner tomorrow.";
            walkTipsNotRepeat[0]=1;
            return tip;
        }
        else if(totalWalk <2.8 && walkTipsNotRepeat[1]>8)
        {
            String tip="The minimum standard for for young person is 4000 steps per day "
                    + "which is around 2.8km. "
                    +"You have walked " + totalWalk
                    +" km today.You can walk more by tomorrow.";
            walkTipsNotRepeat[1]=1;
            return tip;
        }
        else if(totalWalk >=2.8 &&totalWalk <4.9&& walkTipsNotRepeat[2]>8)
        {
            String tip="The minimum standard for young person is 4000 steps per day "
                    + "which is around 2.8km. "
                    +"You have walked " + totalWalk
                    +" km today.Well done!";
            walkTipsNotRepeat[2]=1;
            return tip;
        }
        else if(totalWalk >=2.8&& totalWalk <4.9 && walkTipsNotRepeat[3]>8)
        {
            String tip="The normal standard for for young person is 7000 steps per day"
                    + "which is around 4.9 km. "
                    +"You have walked " + totalWalk
                    +" km today.You can walk more by tomorrow.";
            walkTipsNotRepeat[3]=1;
            return tip;
        }
        else if(totalWalk >=4.9 && totalWalk <7.0 && walkTipsNotRepeat[4]>8)
        {
            String tip="The normal standard for for young person is 7000 steps per day"
                    + "which is around 4.9 km. "
                    +"You have walked " + totalWalk
                    +" km today. Well done!";
            walkTipsNotRepeat[4]=1;
            return tip;
        }
        else if(totalWalk >=4.9 && totalWalk <7.0 && walkTipsNotRepeat[5]>8)
        {
            String tip="The healthy lifestyle for young person is 10000 steps per day"
                    + "which is around 7.0 km. "
                    +"You have walked " + totalWalk
                    +" km today.You can walk more by tomorrow.";
            walkTipsNotRepeat[5]=1;
            return tip;
        }
        else if(totalWalk >=7.0 && walkTipsNotRepeat[6]>8)
        {
            String tip="The healthy lifestyle for young person is 10000 steps per day"
                    + "which is around 7.0 km. "
                    +"You have walked " + totalWalk
                    +" km today.Well done!";
            walkTipsNotRepeat[6]=1;
            return tip;
        }
        else if(totalJourneyEmissions ==0 && totalWalk !=0 && walkTipsNotRepeat[7] >= 7)
        {
            String tip="Emm, I see, you went outside on foot and didn't drove or took any trans. " +
                    "The total emissions is 0. We can together make this a better world, well done.";
            walkTipsNotRepeat[7] = 1;
            return tip;
        }
        //out of tips
        return "";
    }




    //there are 11 tips for biking
    //Will have 1~2 tips for one period
    public String tipsForBike() {
        for (int x = 0; x < bikeTipsNotRepeat.length; x++) {
            bikeTipsNotRepeat[x] ++;
        }
        if (totalBike == 0.0 && bikeTipsNotRepeat[0] >8) {
            String tip = "You haven't rode bike today,"
                    + "Riding bike have lots of benefits to your health, "
                    + "Have some ride by tomorrow.";
            bikeTipsNotRepeat[0] = 1;
            return tip;
        }
        else if (totalBike > 0.0 && totalBike <= 6.0 && bikeTipsNotRepeat[1] >8) {
            String tip = "You rode " + totalBike
                    + " km today. Ride bike around 5 km everyday,"
                    + "could help boost estrogen and androgen levels.";
            bikeTipsNotRepeat[1] = 1;
            return tip;
        }
        else if (totalBike >= 5.0 && totalBike <= 10.0 && bikeTipsNotRepeat[2] >8) {
            String tip = "The average daily distance recommended for most bicycle tourists per day"
                    + "is 5 km tO 10 km."
                    + "You rode " + totalBike
                    + " km today.Well done!";
            bikeTipsNotRepeat[2] = 1;
            return tip;
        }
        else if (totalBike > 10.0 && totalBike < 30 && bikeTipsNotRepeat[3] >8) {
            String tip = "You rode " + totalBike
                    + " km today." +
                    "It's not only good to your health, but also saved around 17kg carbon emission," +
                    "which is world average carbon emission per person.";
            bikeTipsNotRepeat[3] = 1;
            return tip;
        }
        else if (totalBike >= 30.0 && totalBike < 64.37 && bikeTipsNotRepeat[4] >8) {
            String tip = "You rode " + totalBike
                    + " km today." +
                    "It's not only good to your health, but also saved around 34 kg carbon emission," +
                    "which is Canada average carbon emission.";
            bikeTipsNotRepeat[4] = 1;
            return tip;
        }
        else if (totalBike >= 64.37 && totalBike <= 96.56 && bikeTipsNotRepeat[6] >8) {
            String tip = "The average daily distance for training recommended for most bicycle tourists per day"
                    + "is 40 to 60 mile. Which is 64.37 km TO 96.56 km."
                    + "You rode " + totalBike
                    + " km today,and also saved around 66 kg carbon emission.";
            bikeTipsNotRepeat[6] = 1;
            return tip;
        }
        else if (totalBike >= 100 && totalBike <300 && bikeTipsNotRepeat[7] >8) {
            String tip = "For strong physical training purpose,the recommended for most bicycle tourists per day"
                    + "is over 100 km if you are beginner"
                    + "You rode " + totalBike
                    + " km today. You are going to be the veteran!";
            bikeTipsNotRepeat[7] = 1;
            return tip;
        }
        else if (totalBike >= 200 && totalBike <300 && bikeTipsNotRepeat[8] >8) {
            String tip = "For strong physical training purpose,the recommended for most bicycle tourists per day"
                    + "is around 200 km if you are veteran"
                    + "You rode " + totalBike
                    + " km today.";
            bikeTipsNotRepeat[8] = 1;
            return tip;
        }
        else if (totalBike >= 300 && bikeTipsNotRepeat[9] >8) {
            String tip = "For strong physical training purpose,the recommended for most bicycle tourists per day"
                    + " is around 200 km if you are veteran. "
                    + "You rode " + totalBike
                    + " km today.You have rode too much, you may hurt yourself.";
            bikeTipsNotRepeat[9] = 1;
            return tip;
        }
        else if(totalJourneyEmissions ==0 && totalBike !=0 && bikeTipsNotRepeat[10] >8)
        {
            String tip="Emm, I see, you went outside by riding bike and didn't drove or took any trans. " +
                    "The total emissions is 0. We can together make this a better world, well done.";
            bikeTipsNotRepeat[10] = 1;
            return tip;
        }
        else if(totalCityEmissions==0 && totalHighwayEmissions==0 &&
                totalBike !=0 && totalJourneyEmissions !=0 && bikeTipsNotRepeat[11] >8) {
            String tip="Emm, I see, you went outside by riding bike and public transportation. " +
                    "The total emissions by transportation is: " +totalJourneyEmissions+
                    ". You tried best to make this a better world, well done.";
            bikeTipsNotRepeat[11] = 1;
            return tip;
        }
        //out of tips
        return "";
    }

    //The maximum amount of CO2 a person should produce per year in order to halt climate change
    //2.0 t
    //so 2.0/365=0.005479t=5.48kg
    //place            2013 year         per day        in kg
    //world            4.996            0.01368          13.69kg
    //Canada           13.5             0.0369          36.9kg

    public String tipsForJourneyTotalEmissions() {
        for (int x = 0; x < journeyEmissionsTipsNotRepeat.length; x++) {
            journeyEmissionsTipsNotRepeat[x] ++;
        }
        if(totalJourneyEmissions ==0 && totalBike ==0 && totalWalk ==0 &&
                journeyEmissionsTipsNotRepeat[0] >8)
        {
            String tip="You didn't went outside today, not good to your health, "
                    +" call yours friends, have some fun outside tomorrow.";
            journeyEmissionsTipsNotRepeat[0] = 1;
            return tip;
        }
        else if(totalJourneyEmissions >0 && totalJourneyEmissions <5.48 &&
                journeyEmissionsTipsNotRepeat[1] >8)
        {
            String tip="The maximum amount of CO2 a person should produce per year in "
                    + "order to halt climate change is 5.48 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    +", which is below that. We can together make this a better world, well done.";
            journeyEmissionsTipsNotRepeat[1] = 1;
            return tip;
        }
        else if(totalJourneyEmissions >=5.48 && totalJourneyEmissions <=13.69 &&
                journeyEmissionsTipsNotRepeat[2] >8)
        {
            String tip="The maximum amount of CO2 a person should produce per year in "
                    + "order to halt climate change is 5.48 kg.The world average is 13.69 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    +". Although you exceeded the expection, " +
                    "but you still below the World average, well done!";
            journeyEmissionsTipsNotRepeat[2] = 1;
            return tip;
        }
        else if(totalJourneyEmissions >=13.69 && totalJourneyEmissions <=36.9 &&
                journeyEmissionsTipsNotRepeat[3] >8)
        {
            String tip="The world average Emissionsis 13.69 kg."
                    +"and Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    +". Although you exceeded the world average, " +
                    "but you still below the Canada average, well done!";
            journeyEmissionsTipsNotRepeat[3] = 1;
            return tip;
        }
        else if(totalJourneyEmissions >=36.9 &&
                journeyEmissionsTipsNotRepeat[4] >8)
        {
            String tip= "Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    +". You have exceeded the Canada average."
                    + "Tip: Avoid idling for long periods of time. " +
                    "Idling is considered a significant contributor to pollution, " +
                    "so consider turning your vehicle off when waiting in one spot for " +
                    "longer than a few minutes.";
            journeyEmissionsTipsNotRepeat[4] = 1;
            return tip;
        }
        else if(journeyEmissionsTipsNotRepeat[5] >8)
        {
            String tip= "Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    + "Tip: Drive at a steady speed. Rapid acceleration increases " +
                    "fuel consumption and pollutant emissions.";
            journeyEmissionsTipsNotRepeat[5] = 1;
            return tip;
        }
        else if(journeyEmissionsTipsNotRepeat[6] >8)
        {
            String tip= "Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    + " Tip: Properly maintain your vehicle. Clogged air filters, " +
                    "dirty carburetors and worn out engine parts make your car work harder " +
                    "and increase air pollution.";
            journeyEmissionsTipsNotRepeat[6] = 1;
            return tip;
        }
        else if(journeyEmissionsTipsNotRepeat[7] >8)
        {
            String tip= "Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    + " Tip: Keep your tires properly inflated. Properly inflated tires " +
                    "will lower your gas consumption and provide a smoother drive " +
                    "which will reduce the need for frequent acceleration.";
            journeyEmissionsTipsNotRepeat[7] = 1;
            return tip;
        }
        else if(journeyEmissionsTipsNotRepeat[8] >8)
        {
            String tip= "Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    + " Tip: Opt for a fuel-efficient vehicle. Choosing a hybrid or " +
                    "electric vehicle will reduce the amount of pollution you emit as your drive.";
            journeyEmissionsTipsNotRepeat[8] = 1;
            return tip;
        }
        else if(journeyEmissionsTipsNotRepeat[9] >8)
        {
            String tip= "Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    + " Tip: Reduce car air conditioner usage. Reducing your usage can improve " +
                    "car performance and gas mileage by as much as 20%. " +
                    "Instead, roll down your windows when you can and enjoy the breeze.";
            journeyEmissionsTipsNotRepeat[9] = 1;
            return tip;
        }
        else if(journeyEmissionsTipsNotRepeat[10] >8)
        {
            String tip= "Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    + " Tip: Change Air Filter Regularly.";
            journeyEmissionsTipsNotRepeat[10] = 1;
            return tip;
        }
        else if(journeyEmissionsTipsNotRepeat[11] >8)
        {
            String tip= "Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    + " Tip: Change Engine Oil as Required";
            journeyEmissionsTipsNotRepeat[11] = 1;
            return tip;
        }
        else if(journeyEmissionsTipsNotRepeat[12] >8)
        {
            String tip= "Canada average Emissionsis 36.9 kg."
                    +"Your TotalEmissions is :"+ totalJourneyEmissions
                    + " Tip: By keeping the tires on your vehicle properly inflated, " +
                    "you will enjoy not only a smoother ride, but you will get much better fuel efficiency as well. ";
            journeyEmissionsTipsNotRepeat[12] = 1;
            return tip;
        }
        return "";
    }



    public String tipsForElectricityEmissions() {
        for (int x = 0; x < electricityTipsNotRepeat.length; x++) {
            electricityTipsNotRepeat[x] ++;
        }
        if(electricityTipsNotRepeat[2] >8)
        {
            String tip="You generate: "
                    +totalElectricityEmissions +"kg  of CO2 today from Electricity use. " +
                    "Tips: " +
                    "Turn off lights after leaving room, " +
                    "may reduce the emission.";
            electricityTipsNotRepeat[2] = 1;
            return tip;
        }
        else if(electricityTipsNotRepeat[3] >8)
        {
            String tip="You generate: "
                    +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                    "Tips: " +
                    "Switch to compact fluorescent (CFL) or LED light bulbs, " +
                    "may reduce the emission.";
            electricityTipsNotRepeat[3] = 1;
            return tip;
        }
        else if(electricityTipsNotRepeat[4] >8)
        {
            String tip="You generate: "
                    +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                    "Tips: " +
                    "Go for a walk instead of watching TV or booting up your computer, " +
                    "may reduce the emission.";
            electricityTipsNotRepeat[4] = 1;
            return tip;
        }
        else if(electricityTipsNotRepeat[5] >8)
        {
            String tip="You generate: "
                    +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                    "Tips: " +
                    "Check walls, doors and windows for drafts and seal them up in winter to avoid " +
                    "heat loss. " ;
            electricityTipsNotRepeat[5] = 1;
            return tip;
        }
        else if(electricityTipsNotRepeat[6] >8)
        {
            String tip="You generate: "
                    +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                    "Tips: " +
                    "Set your clothes washer to the warm or cold water setting, not hot " +
                    "may reduce the emission.";
            electricityTipsNotRepeat[6] = 1;
            return tip;
        }
        else if(electricityTipsNotRepeat[7] >8)
        {
            String tip="You generate: "
                    +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                    "Tips: " +
                    "Turn down your water heater thermostat, " +
                    "may reduce the emission.";
            electricityTipsNotRepeat[7] = 1;
            return tip;
        }
        else if(electricityTipsNotRepeat[8] >8)
        {
            String tip="You generate: "
                    +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                    "Tips: " +
                    "Plant shade trees and paint your house a light color if you live in a warm climate, " +
                    "or a dark color if you live in a cold climate.";
            electricityTipsNotRepeat[8] = 1;
            return tip;
        }
        else if(electricityTipsNotRepeat[9] >8)
        {
            String tip="You generate: "
                    +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                    "Tips: " +
                    "Use less hot water by installing low-flow shower heads.";
            electricityTipsNotRepeat[9] = 1;
            return tip;
        }
        return "";
    }




    public String tipsForGasEmissions() {
        for (int x = 0; x < gasTipsNotRepeat.length; x++) {
            gasTipsNotRepeat[x] ++;
        }
        if(gasTipsNotRepeat[0] > 8)
        {
            String tip="You generate: "
                    +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                    "Tips: " +
                    "Install a programmable thermostat to automatically reduce heating " +
                    "and cooling in your home when you don't need as much. ";
            gasTipsNotRepeat[0] = 1;
            return tip;
        }
        else if( gasTipsNotRepeat[1] > 8)
        {
            String tip="You generate: "
                    +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                    "Tips: " +
                    "Ovens should reach their desired temperature within 15 minutes" +
                    "to save energy, avoid pre-heating ovens for more than 15 minutes.";
            gasTipsNotRepeat[1] = 1;
            return tip;
        }
        else if(gasTipsNotRepeat[2] > 8)
        {
            String tip="You generate: "
                    +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                    "Tips: " +
                    "Only use hood fans while cooking; they draw in heated air " +
                    "and exhaust it to the outside.";
            gasTipsNotRepeat[2] = 1;
            return tip;
        }
        else if(gasTipsNotRepeat[3] > 8)
        {
            String tip="You generate: "
                    +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                    "Tips: " +
                    "For gas fryers and gas griddles, use infrared (IR) burners that operate " +
                    "with less than 10 percent excess air, reducing combustion " +
                    "energy loss up the flue.";
            gasTipsNotRepeat[3] = 1;
            return tip;
        }
        else if(gasTipsNotRepeat[4] > 8)
        {
            String tip="You generate: "
                    +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                    "Tips: " +
                    "Implement an effective steam trap maintenance program. " +
                    "This promotes efficient " +
                    "operation of end-use heat transfer equipment and reduces " +
                    "live steam in the condensate system.";
            gasTipsNotRepeat[4] = 1;
            return tip;
        }
        else if(gasTipsNotRepeat[5] > 8)
        {
            String tip="You generate: "
                    +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                    "Tips: " +
                    "Watch that water heater. It’s also not likely to be noticeable " +
                    "if you turn down the thermostat on your water heater to, " +
                    "say, 120 degrees from about 140 degrees.";
            gasTipsNotRepeat[5] = 1;
            return tip;
        }
        else if(gasTipsNotRepeat[6] > 8)
        {
            String tip="You generate: "
                    +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                    "Tips: " +
                    "Reduce or eliminate openings in furnace walls " +
                    "and doors to minimize heat losses and to prevent air " +
                    "from leaking into furnaces and ovens.";
            gasTipsNotRepeat[6] = 1;
            return tip;
        }
        else if(gasTipsNotRepeat[7] > 8)
        {
            String tip="You generate: "
                    +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                    "Tips: " +
                    "If you have a fireplace, close the damper or install " +
                    "glass doors to prevent warm air escaping up the chimney.";
            gasTipsNotRepeat[7] = 1;
            return tip;
        }
        return "";
    }


    /*
    function that call tips

    Tip tip = new Tip();
    private void setupNextTipBtn() {
        Button btn = (Button) findViewById(R.id.);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) findViewById(R.id.);
                String s = tip.getJourneyTip();
                tv.setText(s);
            }
        });
    }
     */



}

