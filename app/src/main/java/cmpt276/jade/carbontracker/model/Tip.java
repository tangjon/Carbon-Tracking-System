package cmpt276.jade.carbontracker.model;

/**
 * Created by user on 2017/3/21.
 */

public class Tip {


        public static  Emission oneDayRoutes;
        //TODO need add some functions
        //double totalCityEmissions=oneDayRoutes.getTotleCityEmissions();
        //double totalHighwayEmissions=OneDayRoutes.getTotleHighWayEmissions();
        //double totalWalk= OneDayRoutes.getTotleWalkDistance();
        //double totalBike= OneDayRoutes.getTotleBikeDistance();
        //double totalBusEmission=  OneDayRoutes.getTotleBusEmissions();
        //double totalSkyTrainEmission=OneDayRoutes.getTotleSkyTrainEmissions();
        //double totalElectricityEmissions=OneDayRoutes.getTotleElectricEmissions();
        //double totalGasEmissions=OneDayRoutes.getTotleGasEmissions();


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

        int walkTipsNotRepeat[]= {7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7};              // 15 tips
        int bikeTipsNotRepeat[]= {7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7};              //  12 tips
        int journeyEmissionsTipsNotRepeat[]=  {7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7}; //7 tips
        int electricityTipsNotRepeat[]=  {7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7};      //10 tips
        int gasTipsNotRepeat[]=  {7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7};                  //10 tips
        // total 34 tips for journey(include, walk,bike,bus,skytrain)
        // total 10 tips for utilities(electricity  and gas)
        // TODO we can show one tips for joreney and one tips for utilities




        private String getJourneyTip() {
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

        private String getUtilityTip() {
            String tips= tipsForElectricityEmissions();
            if(tips.length()>2){
                // if there are available tips for election
                return tips;
            }
            else {
                // if there are no available tips for election, get gas tips
                tips = tipsForGasEmissions();
                if (tips.length() > 2) {
                    return tips;
                }
            }
            //there are no tips available for that three array
            return "";
        }





        //there are 15 tips for walking
        private String tipsForWalk() {
            for (int x = 0; x < 20; x++) {
                walkTipsNotRepeat[x] ++;
            }
            //for young person
            if(totalWalk ==0.0 && walkTipsNotRepeat[0]>=7) {
                String tip= "You haven't walk today,"
                        +"Walking have lots of benefits to your health, "
                        +"Try to walk tomorrow.";
                walkTipsNotRepeat[0]=1;
                return tip;
            }
            else if(totalWalk <2.8 && walkTipsNotRepeat[1]>=7)
            {
                String tip="The minimum standard for for young person is 4000 steps per day "
                        + "which is around 2.8km. "
                        +"You have walked " + totalWalk
                        +" km today.You haven't met the minimum standard for young person.";
                walkTipsNotRepeat[1]=1;
                return tip;
            }
            else if(totalWalk >=2.8 && walkTipsNotRepeat[2]>=7)
            {
                String tip="The minimum standard for young person is 4000 steps per day "
                        + "which is around 2.8km. "
                        +"You have walked " + totalWalk
                        +" km today.You have met the minimum standard for young person";
                walkTipsNotRepeat[2]=1;
                return tip;
            }
            else if(totalWalk <4.9 && walkTipsNotRepeat[3]>=7)
            {
                String tip="The normal standard for for young person is 7000 steps per day"
                        + "which is around 4.9 km. "
                        +"You have walked " + totalWalk
                        +" km today.You haven't met the normal standard for young person.";
                walkTipsNotRepeat[3]=1;
                return tip;
            }
            else if(totalWalk >=4.9 && walkTipsNotRepeat[4]>=7)
            {
                String tip="The normal standard for for young person is 7000 steps per day"
                        + "which is around 4.9 km. "
                        +"You have walked " + totalWalk
                        +" km today.You have met the normal standard for young person.";
                walkTipsNotRepeat[4]=1;
                return tip;
            }
            else if(totalWalk <7.0 && walkTipsNotRepeat[5]>=7)
            {
                String tip="The healthy lifestyle for young person is 10000 steps per day"
                        + "which is around 7.0 km. "
                        +"You have walked " + totalWalk
                        +" km today.You haven't met the healthy standard for young person.";
                walkTipsNotRepeat[5]=1;
                return tip;
            }
            else if(totalWalk >=7.0 && walkTipsNotRepeat[6]>=7)
            {
                String tip="The healthy lifestyle for young person is 10000 steps per day"
                        + "which is around 7.0 km. "
                        +"You have walked " + totalWalk
                        +" km today.You have met the healthy standard for young person.";
                walkTipsNotRepeat[6]=1;
                return tip;
            }



            //for old person
            else if(totalWalk <3.5 && walkTipsNotRepeat[7]>=7)
            {
                String tip="The healthy lifestyle for elder person is 5000 steps per day"
                        + "which is around 3.5 km. "
                        +"You have walked " + totalWalk
                        +" km today.You haven't met the healthy standard for elder person.";
                walkTipsNotRepeat[7]=1;
                return tip;
            }
            else if(totalWalk >=3.5 && walkTipsNotRepeat[8]>=7)
            {
                String tip="The healthy lifestyle for elder person is 5000 steps per day"
                        + "which is around 3.5 km. "
                        +"You have walked " + totalWalk
                        +" km today.You have met the healthy standard for elder person.";
                walkTipsNotRepeat[8]=1;
                return tip;
            }
            else if(totalWalk <7.0  && walkTipsNotRepeat[9]>=7)
            {
                String tip="The limition of healthy lifestyle for elder person is 10000 steps per day"
                        + "which is around 7.0 km. "
                        +"You have walked " + totalWalk
                        +" km today.You haven't reached the limition of healthy standard for elder person.";
                walkTipsNotRepeat[9]=1;
                return tip;
            }
            else if(totalWalk >=7.0 && walkTipsNotRepeat[10]>=7)
            {
                String tip="The limition of healthy lifestyle for elder person is 10000 steps per day"
                        + "which is around 7.0 km. "
                        +"You have walked " + totalWalk
                        +" km today.You reached the limition of healthy standard for elder person.";
                walkTipsNotRepeat[10]=1;
                return tip;
            }
            else if(totalWalk >=10.5 && walkTipsNotRepeat[11]>=7)
            {
                String tip="The unhealthy lifestyle for elder person is walking more than 10000 steps per day"
                        + "which is more than 7.0 km. You may get gonarthromeningitis in the future if you"
                        + "keep walking more than 10.5 km. "
                        +"You have walked " + totalWalk
                        +" km today. You could try to reduce the distance next day.";
                walkTipsNotRepeat[11]=1;
                return tip;
            }
            else if(totalWalk >=14.0  && walkTipsNotRepeat[12]>=7)
            {
                String tip="The unhealthy lifestyle for elder person is walking more than 10000 steps per day"
                        + "which is more than 7.0 km. You may get effusionofkneejoint if you keep "
                        + "walking more than 14.0 km. "
                        +"You have walked " + totalWalk
                        +" km today. You could try to reduce the distance next day.";
                walkTipsNotRepeat[12]=1;
                return tip;
            }
            else if(totalWalk >=21.0 && walkTipsNotRepeat[13]>=7)
            {
                String tip="The unhealthy lifestyle for elder person is walking more than 10000 steps per day"
                        + "which is more than 7.0 km. You may hurt your femur when walking more than 21.0 km. "
                        +"You have walked " + totalWalk
                        +" km today.You could try to reduce the distance next day.";
                walkTipsNotRepeat[13]=1;
                return tip;
            }
            else if(totalJourneyEmissions ==0 && totalWalk !=0 && walkTipsNotRepeat[14] >= 7)
            {
                String tip="Emm, I see, you went outside on foot and didn't drove or took any trans. " +
                        "The total emissions is 0. We can together make this a better world, well done.";
                walkTipsNotRepeat[14] = 1;
                return tip;
            }

            //out of tips
            return "";
        }




        //there are 12 tips for biking
        private String tipsForBike() {
            for (int x = 0; x < 20; x++) {
                bikeTipsNotRepeat[x] ++;
            }
            if (totalBike == 0.0 && bikeTipsNotRepeat[0] >= 7) {
                String tip = "You haven't rode bike today,"
                        + "Riding bike have lots of benefits to your health, "
                        + "Try to ride bike tomorrow.";
                bikeTipsNotRepeat[0] = 1;
                return tip;
            }
            else if (totalBike > 0.0 && totalBike <= 6.0 && bikeTipsNotRepeat[1] >= 7) {
                String tip = "You rode " + totalBike
                        + " km today. Ride bike around 5 km everyday,"
                        + "could help boost estrogen and androgen levels.";
                bikeTipsNotRepeat[1] = 1;
                return tip;
            }
            else if (totalBike >= 5.0 && totalBike <= 10.0 && bikeTipsNotRepeat[2] >= 7) {
                String tip = "The average daily distance recommended for most bicycle tourists per day"
                        + "is 5 km tO 10 km."
                        + "You rode " + totalBike
                        + " km today.You have met the healthy standard for young person";
                bikeTipsNotRepeat[2] = 1;
                return tip;
            }
            else if (totalBike > 10.0 && totalBike < 30 && bikeTipsNotRepeat[3] >= 7) {
                String tip = "You rode " + totalBike
                        + " km today." +
                        "It's not only good to your health, but also saved around 17kg carbon emission," +
                        "which is world average carbon emission.";
                bikeTipsNotRepeat[3] = 1;
                return tip;
            }
            else if (totalBike >= 30.0 && totalBike < 64.37 && bikeTipsNotRepeat[4] >= 7) {
                String tip = "You rode " + totalBike
                        + " km today." +
                        "It's not only good to your health, but also saved around 34 kg carbon emission," +
                        "which is Canada average carbon emission.";
                bikeTipsNotRepeat[4] = 1;
                return tip;
            }
            else if (totalBike > 10.0 && totalBike < 64.37 && bikeTipsNotRepeat[5] >= 7) {
                String tip = "The average daily distance for training recommended for most bicycle tourists per day"
                        + "is 40 to 60 mile. Which is 64.37 km TO 96.56 km."
                        + "You rode " + totalBike
                        + " km today.You You have met the healthy standard," +
                        "but haven't met the recommendation for training.";
                bikeTipsNotRepeat[5] = 1;
                return tip;
            }
            else if (totalBike >= 64.37 && totalBike <= 96.56 && bikeTipsNotRepeat[6] >= 7) {
                String tip = "The average daily distance for training recommended for most bicycle tourists per day"
                        + "is 40 to 60 mile. Which is 64.37 km TO 96.56 km."
                        + "You rode " + totalBike
                        + " km today.You have met the healthy standard for young person";
                bikeTipsNotRepeat[6] = 1;
                return tip;
            }
            else if (totalBike >= 100 && bikeTipsNotRepeat[7] >= 7) {
                String tip = "For strong physical training purpose,the recommended for most bicycle tourists per day"
                        + "is over 100 km if you are beginner"
                        + "You rode " + totalBike
                        + " km today. You are going to be the veteran!";
                bikeTipsNotRepeat[7] = 1;
                return tip;
            }
            else if (totalBike >= 200 && bikeTipsNotRepeat[8] >= 7) {
                String tip = "For strong physical training purpose,the recommended for most bicycle tourists per day"
                        + "is over 200 km if you are veteran"
                        + "You rode " + totalBike
                        + " km today.";
                bikeTipsNotRepeat[8] = 1;
                return tip;
            } else if (totalBike >= 300 && bikeTipsNotRepeat[9] >= 7) {
                String tip = "For strong physical training purpose,the recommended for most bicycle tourists per day"
                        + "is over 200 km if you are veteran"
                        + "You rode " + totalBike
                        + " km today.You have rode too much, you may hurt yourself.";
                bikeTipsNotRepeat[9] = 1;
                return tip;
            }
            else if(totalJourneyEmissions ==0 && totalBike !=0 && bikeTipsNotRepeat[10] >= 7)
            {
                String tip="Emm, I see, you went outside by riding bike and didn't drove or took any trans. " +
                        "The total emissions is 0. We can together make this a better world, well done.";
                bikeTipsNotRepeat[10] = 1;
                return tip;
            }
            else if(totalCityEmissions==0 && totalHighwayEmissions==0 &&
                    totalBike !=0 && totalJourneyEmissions !=0 && bikeTipsNotRepeat[11] >= 7) {
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

        // 7 tips of JourneyTotalEmissions
        private String tipsForJourneyTotalEmissions() {
            for (int x = 0; x < 20; x++) {
                journeyEmissionsTipsNotRepeat[x] ++;
            }
            if(totalJourneyEmissions ==0 && totalBike ==0 && totalWalk ==0 && journeyEmissionsTipsNotRepeat[0] >= 7)
            {
                String tip="You didn't went outside today, not good to your health, "
                        +" call yours friends, have some fun outside tomorrow.";
                journeyEmissionsTipsNotRepeat[0] = 1;
                return tip;
            }
            else if(totalJourneyEmissions >0 && totalJourneyEmissions <5.48 && journeyEmissionsTipsNotRepeat[1] >= 7)
            {
                String tip="The maximum amount of CO2 a person should produce per year in "
                        + "order to halt climate change is 5.48 kg."
                        +"Your TotalEmissions is :"+ totalJourneyEmissions
                        +", which is below that. We can together make this a better world, well done.";
                journeyEmissionsTipsNotRepeat[1] = 1;
                return tip;
            }
            else if(totalJourneyEmissions >=5.48 && totalJourneyEmissions <=13.69 && journeyEmissionsTipsNotRepeat[2] >= 7)
            {
                String tip="The maximum amount of CO2 a person should produce per year in "
                        + "order to halt climate change is 5.48 kg.The world average is 13.69 kg."
                        +"Your TotalEmissions is :"+ totalJourneyEmissions
                        +". Although you exceeded the expection, but you still below the World average.";
                journeyEmissionsTipsNotRepeat[2] = 1;
                return tip;
            }
            else if(totalJourneyEmissions >=13.69 && totalJourneyEmissions <=36.9 && journeyEmissionsTipsNotRepeat[3] >= 7)
            {
                String tip="The world average Emissionsis 13.69 kg."
                        +"and Canada average Emissionsis 36.9 kg."
                        +"Your TotalEmissions is :"+ totalJourneyEmissions
                        +". Although you exceeded the world average, but you still below the Canada average.";
                journeyEmissionsTipsNotRepeat[3] = 1;
                return tip;
            }
            else if(totalJourneyEmissions >=36.9 && totalJourneyEmissions >74 && journeyEmissionsTipsNotRepeat[4] >= 7)
            {
                String tip= "Canada average Emissionsis 36.9 kg."
                        +"Your TotalEmissions is :"+ totalJourneyEmissions
                        +". You exceeded two times Canada average."
                        + " Try change half of your journey by taking some bike or transportation, " +
                        "leave the younger generations a healthy earth";
                journeyEmissionsTipsNotRepeat[4] = 1;
                return tip;
            }
            else if(totalJourneyEmissions >=74 && totalJourneyEmissions <150 && journeyEmissionsTipsNotRepeat[5] >= 7)
            {
                String tip="Canada average Emissionsis 36.9 kg."
                        +"Your TotalEmissions is :"+ totalJourneyEmissions
                        +". You exceeded three times Canada average."
                        + " Try change most of your journey by taking some bike or transportation, " +
                        "leave the younger generations a healthy earth";
                journeyEmissionsTipsNotRepeat[5] = 1;
                return tip;
            }
            else if(totalJourneyEmissions >=150 && journeyEmissionsTipsNotRepeat[6] >= 7)
            {
                String tip="Emm, seem like you are traveling to another city."
                        + "Being careful driving, have a good trip." +
                        "The total journey emission: "+totalJourneyEmissions;
                journeyEmissionsTipsNotRepeat[6] = 1;
                return tip;
            }
            return "";
        }



        //year 2010
        //http://shrinkthatfootprint.com/average-household-electricity-consumption
        //TODO I think 0.018kg/day and 0.1161 kg/day is not so correct,may double check with team
        //world average per person  731 kwh/year= 2 kwh/day= 0.000002 Gwh/day  *9000 kg/GWh= 0.018 kg/day
        //canada average per person 4741 kwh/year=12.9 kwh/day=0.0000129 Gwh/day *9000 CO2/GWh=0.1161 kg/day
        // 10 tips of ElectricityEmissions
        private String tipsForElectricityEmissions() {
            for (int x = 0; x < 20; x++) {
                electricityTipsNotRepeat[x] ++;
            }
            double worldAve=0.018;
            double canadaAve=0.1161;
            if(totalElectricityEmissions <worldAve && electricityTipsNotRepeat[0] >= 7)
            {
                String tip="You generate: "
                        +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                        "which is lower than average level of world. Well Done!";
                electricityTipsNotRepeat[0] = 1;
                return tip;
            }
            else if(totalElectricityEmissions >=worldAve && totalElectricityEmissions <canadaAve
                    && electricityTipsNotRepeat[1] >= 7)
            {
                String tip="You generate: "
                        +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                        "which is greater than average level of world, but lower than" +
                        "average level of Canada. Well Done";
                electricityTipsNotRepeat[1] = 1;
                return tip;
            }
            else if(totalElectricityEmissions >=canadaAve && electricityTipsNotRepeat[2] >= 7)
            {
                String tip="You generate: "
                        +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                        "which is greater than average level of Canada. " +
                        "Turn off lights after leaving room, " +
                        "may reduce the emission.";
                electricityTipsNotRepeat[2] = 1;
                return tip;
            }
            else if(totalElectricityEmissions >=canadaAve && electricityTipsNotRepeat[3] >= 7)
            {
                String tip="You generate: "
                        +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                        "which is greater than average level of Canada. " +
                        "Switch to compact fluorescent (CFL) or LED light bulbs, " +
                        "may reduce the emission.";
                electricityTipsNotRepeat[3] = 1;
                return tip;
            }
            else if(totalElectricityEmissions >=canadaAve && electricityTipsNotRepeat[4] >= 7)
            {
                String tip="You generate: "
                        +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                        "which is greater than average level of Canada. " +
                        "Go for a walk instead of watching TV or booting up your computer, " +
                        "may reduce the emission.";
                electricityTipsNotRepeat[4] = 1;
                return tip;
            }
            else if(totalElectricityEmissions >=canadaAve && electricityTipsNotRepeat[5] >= 7)
            {
                String tip="You generate: "
                        +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                        "which is greater than average level of Canada. " +
                        "Check walls, doors and windows for drafts and seal them up in winter to avoid " +
                        "heat loss. " ;
                electricityTipsNotRepeat[5] = 1;
                return tip;
            }
            else if(totalElectricityEmissions >=canadaAve && electricityTipsNotRepeat[6] >= 7)
            {
                String tip="You generate: "
                        +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                        "which is greater than average level of Canada. " +
                        "Set your clothes washer to the warm or cold water setting, not hot " +
                        "may reduce the emission.";
                electricityTipsNotRepeat[6] = 1;
                return tip;
            }
            else if(totalElectricityEmissions >=canadaAve && electricityTipsNotRepeat[7] >= 7)
            {
                String tip="You generate: "
                        +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                        "which is greater than average level of Canada. " +
                        "Turn down your water heater thermostat, " +
                        "may reduce the emission.";
                electricityTipsNotRepeat[7] = 1;
                return tip;
            }
            else if(totalElectricityEmissions >=canadaAve && electricityTipsNotRepeat[8] >= 7)
            {
                String tip="You generate: "
                        +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                        "which is greater than average level of Canada. " +
                        "Plant shade trees and paint your house a light color if you live in a warm climate, " +
                        "or a dark color if you live in a cold climate.";
                electricityTipsNotRepeat[8] = 1;
                return tip;
            }
            else if(totalElectricityEmissions >=canadaAve && electricityTipsNotRepeat[9] >= 7)
            {
                String tip="You generate: "
                        +totalElectricityEmissions +"kg  of CO2 today from Electricity use, " +
                        "which is greater than average level of Canada. " +
                        "Use less hot water by installing low-flow shower heads.";
                electricityTipsNotRepeat[9] = 1;
                return tip;
            }

            return "";
        }





        //TODO still finding the average of world and canada,
        // 10 tips of GasEmissions
        private String tipsForGasEmissions() {
            for (int x = 0; x < 20; x++) {
                electricityTipsNotRepeat[x] ++;
            }
            double worldAve=0;
            double canadaAve=0;
            if(totalGasEmissions <worldAve && gasTipsNotRepeat[0] >= 7)
            {
                String tip="You generate: "
                        +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                        "which is lower than average level of world. Well done!";
                electricityTipsNotRepeat[0] = 1;
                return tip;
            }
            else if(totalGasEmissions >=worldAve && totalGasEmissions <canadaAve
                    && gasTipsNotRepeat[1] >= 7)
            {
                String tip="You generate: "
                        +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                        "which is greater than average level of world, but lower than" +
                        "average level of Canada. Well Done!";
                gasTipsNotRepeat[1] = 1;
                return tip;
            }
            else if(totalGasEmissions >=canadaAve && gasTipsNotRepeat[2] >= 7)
            {
                String tip="You generate: "
                        +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                        "which is greater than average level of Canada, Tips: " +
                        "Install a programmable thermostat to automatically reduce heating " +
                        "and cooling in your home when you don't need as much. ";
                gasTipsNotRepeat[2] = 1;
                return tip;
            }
            else if(totalGasEmissions >=canadaAve && gasTipsNotRepeat[3] >= 7)
            {
                String tip="You generate: "
                        +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                        "which is greater than average level of Canada, Tips: " +
                        "Ovens should reach their desired temperature within 15 minutes" +
                        "to save energy, avoid pre-heating ovens for more than 15 minutes.";
                gasTipsNotRepeat[3] = 1;
                return tip;
            }
            else if(totalGasEmissions >=canadaAve && gasTipsNotRepeat[4] >= 7)
            {
                String tip="You generate: "
                        +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                        "which is greater than average level of Canada, Tips: " +
                        "Only use hood fans while cooking; they draw in heated air " +
                        "and exhaust it to the outside.";
                gasTipsNotRepeat[4] = 1;
                return tip;
            }
            else if(totalGasEmissions >=canadaAve && gasTipsNotRepeat[5] >= 7)
            {
                String tip="You generate: "
                        +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                        "which is greater than average level of Canada, Tips: " +
                        "For gas fryers and gas griddles, use infrared (IR) burners that operate " +
                        "with less than 10 percent excess air, reducing combustion " +
                        "energy loss up the flue.";
                gasTipsNotRepeat[5] = 1;
                return tip;
            }
            else if(totalGasEmissions >=canadaAve && gasTipsNotRepeat[6] >= 7)
            {
                String tip="You generate: "
                        +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                        "which is greater than average level of Canada, Tips: " +
                        "Implement an effective steam trap maintenance program. " +
                        "This promotes efficient " +
                        "operation of end-use heat transfer equipment and reduces " +
                        "live steam in the condensate system.";
                gasTipsNotRepeat[6] = 1;
                return tip;
            }
            else if(totalGasEmissions >=canadaAve && gasTipsNotRepeat[7] >= 7)
            {
                String tip="You generate: "
                        +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                        "which is greater than average level of Canada, Tips: " +
                        "Watch that water heater. It’s also not likely to be noticeable " +
                        "if you turn down the thermostat on your water heater to, " +
                        "say, 120 degrees from about 140 degrees.";
                gasTipsNotRepeat[7] = 1;
                return tip;
            }
            else if(totalGasEmissions >=canadaAve && gasTipsNotRepeat[8] >= 7)
            {
                String tip="You generate: "
                        +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                        "which is greater than average level of Canada, Tips: " +
                        "Reduce or eliminate openings in furnace walls " +
                        "and doors to minimize heat losses and to prevent air " +
                        "from leaking into furnaces and ovens.";
                gasTipsNotRepeat[8] = 1;
                return tip;
            }
            else if(totalGasEmissions >=canadaAve && gasTipsNotRepeat[9] >= 7)
            {
                String tip="You generate: "
                        +totalGasEmissions +"kg  of CO2 today from nutural gas use, " +
                        "which is greater than average level of Canada, Tips: " +
                        "If you have a fireplace, close the damper or install " +
                        "glass doors to prevent warm air escaping up the chimney.";
                gasTipsNotRepeat[9] = 1;
                return tip;
            }
            return "";
        }



}
