package cmpt276.jade.carbontracker.model;

import android.content.res.Resources;

import cmpt276.jade.carbontracker.R;

// Tip class
// has 32 tips for journey(car bus skytrain walk bike)
// getJourneyTip();
// has 16 tips for utility(electricity,gas)
// tipsForGasEmissions();
// tipsForElectricityEmissions();
// won't repeat in 7 times peroid
public class Tip {

    double totalBusEmission;
    double totalSkyTrainEmission;
    double totalCarEmission;

    double totalWalk;
    double totalBike;

    double totalElectricityEmissions;
    double totalGasEmissions;

    static int walkTipsNotRepeat[] = {8, 8, 8, 8, 8, 8, 8, 8, 8, 8};                        //8
    static int bikeTipsNotRepeat[] = {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};                   //11
    static int journeyEmissionsTipsNotRepeat[] = {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};  //13
    static int electricityTipsNotRepeat[] = {8, 8, 8, 8, 8, 8, 8, 8, 8, 8};                //8
    static int gasTipsNotRepeat[] = {8, 8, 8, 8, 8, 8, 8, 8, 8, 8};                        //8

    private Resources res = Resources.getSystem();

    public void setTotalCarEmissions(double totalCarEmission) {
        this.totalCarEmission = totalCarEmission;
    }

    public void setTotalBusEmission(double totalBusEmission) {
        this.totalBusEmission = totalBusEmission;
    }

    public void setTotalSkyTrainEmission(double totalSkyTrainEmission) {
        this.totalSkyTrainEmission = totalSkyTrainEmission;
    }

    public void setTotalWalk(double totalWalk) {
        this.totalWalk = totalWalk;
    }

    public void setTotalBike(double totalBike) {
        this.totalBike = totalBike;
    }

    public void setTotalElectricityEmissions(double totalElectricityEmissions) {
        this.totalElectricityEmissions = totalElectricityEmissions;
    }

    public void setTotalGasEmissions(double totalGasEmissions) {
        this.totalGasEmissions = totalGasEmissions;
    }

    public double getTotalJourneyEmissions() {
        return totalCarEmission + totalBusEmission + totalSkyTrainEmission;
    }

    // total 32 tips for journey(include, walk,bike,bus,skytrain)
    public String getJourneyTip() {
        String tips = tipsForWalk();
        if (tips.length() > 2) {
            // if there are available tips for walk
            return tips;
        } else {
            // if there are no available tips for walk, get bike tips
            tips = tipsForBike();
            if (tips.length() > 2) {
                return tips;
            } else {
                // if there are no available tips for walk,and bike, get journey tips
                tips = tipsForJourneyTotalEmissions();
                if (tips.length() > 2) {
                    return tips;
                }
            }
        }
        //there are no tips available for that three array
        return "";
    }

    //there are 8 tips for walking
    // Will have 1~3 tips for one period
    public String tipsForWalk() {
        for (int x = 0; x < walkTipsNotRepeat.length; x++) {
            walkTipsNotRepeat[x]++;
        }
        double totalJourneyEmissions = getTotalJourneyEmissions();
        if (totalWalk == 0.0 && walkTipsNotRepeat[0] > 8) {
            String tip = res.getString(R.string.tip_walk1);
            walkTipsNotRepeat[0] = 1;
            return tip;
        } else if (totalWalk < 2.8 && walkTipsNotRepeat[1] > 8) {
            String tip = res.getString(R.string.tip_walk2) + totalWalk +
                    res.getString(R.string.tip_walk_more);
            walkTipsNotRepeat[1] = 1;
            return tip;
        } else if (totalWalk >= 2.8 && totalWalk < 4.9 && walkTipsNotRepeat[2] > 8) {
            String tip = res.getString(R.string.tip_walk2) + totalWalk +
                    res.getString(R.string.tip_walk_good);
            walkTipsNotRepeat[2] = 1;
            return tip;
        } else if (totalWalk >= 2.8 && totalWalk < 4.9 && walkTipsNotRepeat[3] > 8) {
            String tip = res.getString(R.string.tip_walk3) + totalWalk +
                    res.getString(R.string.tip_walk_more);
            walkTipsNotRepeat[3] = 1;
            return tip;
        } else if (totalWalk >= 4.9 && totalWalk < 7.0 && walkTipsNotRepeat[4] > 8) {
            String tip = res.getString(R.string.tip_walk3) + totalWalk +
                    res.getString(R.string.tip_walk_good);
            walkTipsNotRepeat[4] = 1;
            return tip;
        } else if (totalWalk >= 4.9 && totalWalk < 7.0 && walkTipsNotRepeat[5] > 8) {
            String tip = res.getString(R.string.tip_walk4) + totalWalk +
                    res.getString(R.string.tip_walk_more);
            walkTipsNotRepeat[5] = 1;
            return tip;
        } else if (totalWalk >= 7.0 && walkTipsNotRepeat[6] > 8) {
            String tip = res.getString(R.string.tip_walk4) + totalWalk +
                    res.getString(R.string.tip_walk_good);
            walkTipsNotRepeat[6] = 1;
            return tip;
        } else if (totalJourneyEmissions == 0 && totalWalk != 0 && walkTipsNotRepeat[7] >= 7) {
            String tip = res.getString(R.string.tip_walk_only);
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
            bikeTipsNotRepeat[x]++;
        }
        double totalJourneyEmissions = getTotalJourneyEmissions();
        if (totalBike == 0.0 && bikeTipsNotRepeat[0] > 8) {
            String tip = "You haven't rode your bike today,"
                    + "Riding a bike has lots of benefits for your health, "
                    + "Try riding it later today.";
            bikeTipsNotRepeat[0] = 1;
            return tip;
        } else if (totalBike > 0.0 && totalBike <= 6.0 && bikeTipsNotRepeat[1] > 8) {
            String tip = "You rode " + totalBike
                    + " km today. Ride your bike around 5 km everyday,"
                    + "it can help boost estrogen and androgen levels.";
            bikeTipsNotRepeat[1] = 1;
            return tip;
        } else if (totalBike >= 5.0 && totalBike <= 10.0 && bikeTipsNotRepeat[2] > 8) {
            String tip = "The average daily distance recommended for most cyclists"
                    + "is 5 km tO 10 km."
                    + "You rode " + totalBike
                    + " km today. Well done!";
            bikeTipsNotRepeat[2] = 1;
            return tip;
        } else if (totalBike > 10.0 && totalBike < 30 && bikeTipsNotRepeat[3] > 8) {
            String tip = "You rode " + totalBike
                    + " km today." +
                    "It's not only good for your health, but you also saved around 17kg in carbon emissions," +
                    "which is the world average carbon emission per person.";
            bikeTipsNotRepeat[3] = 1;
            return tip;
        } else if (totalBike >= 30.0 && totalBike < 64.37 && bikeTipsNotRepeat[4] > 8) {
            String tip = "You rode " + totalBike
                    + " km today." +
                    "It's not only good for your health, but also saved around 34 kg in carbon emissions," +
                    "which is a Canadian's average carbon emission.";
            bikeTipsNotRepeat[4] = 1;
            return tip;
        } else if (totalBike >= 64.37 && totalBike <= 96.56 && bikeTipsNotRepeat[6] > 8) {
            String tip = "The average daily distance for training cyclists "
                    + "is 40 to 60 miles. Which is 64.37 km to 96.56 km."
                    + "You rode " + totalBike
                    + " km today,and also saved around 66 kg in carbon emissions.";
            bikeTipsNotRepeat[6] = 1;
            return tip;
        } else if (totalBike >= 100 && totalBike < 300 && bikeTipsNotRepeat[7] > 8) {
            String tip = "For professional training purposes,the recommended daily distance for most cyclists "
                    + "is over 100 km if you are beginner"
                    + "You rode " + totalBike
                    + " km today. You are going to be a pro!";
            bikeTipsNotRepeat[7] = 1;
            return tip;
        } else if (totalBike >= 200 && totalBike < 300 && bikeTipsNotRepeat[8] > 8) {
            String tip = "For rofessional training purposes, the recommended daily distance for most cyclists "
                    + "is around 200 km if you are veteran"
                    + "You rode " + totalBike
                    + " km today. Wow!";
            bikeTipsNotRepeat[8] = 1;
            return tip;
        } else if (totalBike >= 300 && bikeTipsNotRepeat[9] > 8) {
            String tip = "For rofessional training purposes, the recommended daily distance for most cyclists "
                    + "is around 200 km if you are veteran "
                    + "You rode " + totalBike
                    + " km today.You have rode too much, you may hurt yourself.";
            bikeTipsNotRepeat[9] = 1;
            return tip;
        } else if (totalJourneyEmissions == 0 && totalBike != 0 && bikeTipsNotRepeat[10] > 8) {
            String tip = "Emm, I see, you went outside by riding bike and didn't drive or took any transit. " +
                    "The total emissions is 0. We can together to make this a better world, well done.";
            bikeTipsNotRepeat[10] = 1;
            return tip;
        } else if (totalCarEmission == 0 && totalBike != 0 &&
                totalJourneyEmissions != 0 && bikeTipsNotRepeat[11] > 8) {
            String tip = "Emm, I see, you went outside by riding your bike and taking public transportation. " +
                    "The total emissions from transit is: " + totalJourneyEmissions +
                    ". You tried your best to make this a better world, well done.";
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
            journeyEmissionsTipsNotRepeat[x]++;
        }
        double totalJourneyEmissions = getTotalJourneyEmissions();
        if (totalJourneyEmissions == 0 && totalBike == 0 && totalWalk == 0 &&
                journeyEmissionsTipsNotRepeat[0] > 8) {
            String tip = "You didn't go outside today, that is not good for your health, "
                    + " call yours friends, have some fun outside tomorrow.";
            journeyEmissionsTipsNotRepeat[0] = 1;
            return tip;
        } else if (totalJourneyEmissions > 0 && totalJourneyEmissions < 5.48 &&
                journeyEmissionsTipsNotRepeat[1] > 8) {
            String tip = "The maximum amount of CO2 a person should produce per year in "
                    + "order to halt climate change is 5.48 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + ", which is below that. We can all help make this a better world, well done.";
            journeyEmissionsTipsNotRepeat[1] = 1;
            return tip;
        } else if (totalJourneyEmissions >= 5.48 && totalJourneyEmissions <= 13.69 &&
                journeyEmissionsTipsNotRepeat[2] > 8) {
            String tip = "The maximum amount of CO2 a person should produce per year in "
                    + "order to halt climate change is 5.48 kg.The world average is 13.69 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + ". Although you exceeded the expection, " +
                    "but you still below the World average, well done!";
            journeyEmissionsTipsNotRepeat[2] = 1;
            return tip;
        } else if (totalJourneyEmissions >= 13.69 && totalJourneyEmissions <= 36.9 &&
                journeyEmissionsTipsNotRepeat[3] > 8) {
            String tip = "The world average Emissionsis 13.69 kg."
                    + "and Canada average Emissionsis 36.9 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + ". Although you exceeded the world average, " +
                    "but you still below the Canada average, well done!";
            journeyEmissionsTipsNotRepeat[3] = 1;
            return tip;
        } else if (totalJourneyEmissions >= 36.9 &&
                journeyEmissionsTipsNotRepeat[4] > 8) {
            String tip = "Canada average Emissionsis 36.9 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + ". You have exceeded the Canada average."
                    + "Tip: Avoid idling for long periods of time. " +
                    "Idling is considered a significant contributor to pollution, " +
                    "so consider turning your vehicle off when waiting in one spot for " +
                    "longer than a few minutes.";
            journeyEmissionsTipsNotRepeat[4] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[5] > 8) {
            String tip = "Canada average Emissionsis 36.9 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + "Tip: Drive at a steady speed. Rapid acceleration increases " +
                    "fuel consumption and pollutant emissions.";
            journeyEmissionsTipsNotRepeat[5] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[6] > 8) {
            String tip = "Canada average Emissionsis 36.9 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + " Tip: Properly maintain your vehicle. Clogged air filters, " +
                    "dirty carburetors and worn out engine parts make your car work harder " +
                    "and increase air pollution.";
            journeyEmissionsTipsNotRepeat[6] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[7] > 8) {
            String tip = "Canada average Emissionsis 36.9 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + " Tip: Keep your tires properly inflated. Properly inflated tires " +
                    "will lower your gas consumption and provide a smoother drive " +
                    "which will reduce the need for frequent acceleration.";
            journeyEmissionsTipsNotRepeat[7] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[8] > 8) {
            String tip = "Canada average Emissionsis 36.9 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + " Tip: Opt for a fuel-efficient vehicle. Choosing a hybrid or " +
                    "electric vehicle will reduce the amount of pollution you emit as your drive.";
            journeyEmissionsTipsNotRepeat[8] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[9] > 8) {
            String tip = "Canada average Emissionsis 36.9 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + " Tip: Reduce car air conditioner usage. Reducing your usage can improve " +
                    "car performance and gas mileage by as much as 20%. " +
                    "Instead, roll down your windows when you can and enjoy the breeze.";
            journeyEmissionsTipsNotRepeat[9] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[10] > 8) {
            String tip = "Canada average Emissionsis 36.9 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + " Tip: Change Air Filter Regularly.";
            journeyEmissionsTipsNotRepeat[10] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[11] > 8) {
            String tip = "Canada average Emissionsis 36.9 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + " Tip: Change Engine Oil as Required";
            journeyEmissionsTipsNotRepeat[11] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[12] > 8) {
            String tip = "Canada average Emissionsis 36.9 kg."
                    + "Your TotalEmissions is :" + totalJourneyEmissions
                    + " Tip: By keeping the tires on your vehicle properly inflated, " +
                    "you will enjoy not only a smoother ride, but you will get much better fuel efficiency as well. ";
            journeyEmissionsTipsNotRepeat[12] = 1;
            return tip;
        }
        return "";
    }


    public String tipsForElectricityEmissions() {
        for (int x = 0; x < electricityTipsNotRepeat.length; x++) {
            electricityTipsNotRepeat[x]++;
        }
        if (electricityTipsNotRepeat[2] > 8) {
            String tip =
                    "Tips: " +
                            "Turn off lights after leaving room, " +
                            "may reduce the emission.";
            electricityTipsNotRepeat[2] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[3] > 8) {
            String tip =
                    "Tips: " +
                            "Switch to compact fluorescent (CFL) or LED light bulbs, " +
                            "may reduce the emission.";
            electricityTipsNotRepeat[3] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[4] > 8) {
            String tip =
                    "Tips: " +
                            "Go for a walk instead of watching TV or booting up your computer, " +
                            "may reduce the emission.";
            electricityTipsNotRepeat[4] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[5] > 8) {
            String tip =
                    "Tips: " +
                            "Check walls, doors and windows for drafts and seal them up in winter to avoid " +
                            "heat loss. ";
            electricityTipsNotRepeat[5] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[6] > 8) {
            String tip =
                    "Tips: " +
                            "Set your clothes washer to the warm or cold water setting, not hot " +
                            "may reduce the emission.";
            electricityTipsNotRepeat[6] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[7] > 8) {
            String tip =
                    "Tips: " +
                            "Turn down your water heater thermostat, " +
                            "may reduce the emission.";
            electricityTipsNotRepeat[7] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[8] > 8) {
            String tip =
                    "Tips: " +
                            "Plant shade trees and paint your house a light color if you live in a warm climate, " +
                            "or a dark color if you live in a cold climate.";
            electricityTipsNotRepeat[8] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[9] > 8) {
            String tip =
                    "Tips: " +
                            "Use less hot water by installing low-flow shower heads.";
            electricityTipsNotRepeat[9] = 1;
            return tip;
        }
        return "";
    }


    public String tipsForGasEmissions() {
        for (int x = 0; x < gasTipsNotRepeat.length; x++) {
            gasTipsNotRepeat[x]++;
        }
        if (gasTipsNotRepeat[0] > 8) {
            String tip =
                    "Tips: " +
                            "Install a programmable thermostat to automatically reduce heating " +
                            "and cooling in your home when you don't need as much. ";
            gasTipsNotRepeat[0] = 1;
            return tip;
        } else if (gasTipsNotRepeat[1] > 8) {
            String tip =
                    "Tips: " +
                            "Ovens should reach their desired temperature within 15 minutes" +
                            "to save energy, avoid pre-heating ovens for more than 15 minutes.";
            gasTipsNotRepeat[1] = 1;
            return tip;
        } else if (gasTipsNotRepeat[2] > 8) {
            String tip =
                    "Tips: " +
                            "Only use hood fans while cooking; they draw in heated air " +
                            "and exhaust it to the outside.";
            gasTipsNotRepeat[2] = 1;
            return tip;
        } else if (gasTipsNotRepeat[3] > 8) {
            String tip =
                    "Tips: " +
                            "For gas fryers and gas griddles, use infrared (IR) burners that operate " +
                            "with less than 10 percent excess air, reducing combustion " +
                            "energy loss up the flue.";
            gasTipsNotRepeat[3] = 1;
            return tip;
        } else if (gasTipsNotRepeat[4] > 8) {
            String tip =
                    "Tips: " +
                            "Implement an effective steam trap maintenance program. " +
                            "This promotes efficient " +
                            "operation of end-use heat transfer equipment and reduces " +
                            "live steam in the condensate system.";
            gasTipsNotRepeat[4] = 1;
            return tip;
        } else if (gasTipsNotRepeat[5] > 8) {
            String tip =
                    "Tips: " +
                            "Watch that water heater. It’s also not likely to be noticeable " +
                            "if you turn down the thermostat on your water heater to, " +
                            "say, 120 degrees from about 140 degrees.";
            gasTipsNotRepeat[5] = 1;
            return tip;
        } else if (gasTipsNotRepeat[6] > 8) {
            String tip =
                    "Tips: " +
                            "Reduce or eliminate openings in furnace walls " +
                            "and doors to minimize heat losses and to prevent air " +
                            "from leaking into furnaces and ovens.";
            gasTipsNotRepeat[6] = 1;
            return tip;
        } else if (gasTipsNotRepeat[7] > 8) {
            String tip =
                    "Tips: " +
                            "If you have a fireplace, close the damper or install " +
                            "glass doors to prevent warm air escaping up the chimney.";
            gasTipsNotRepeat[7] = 1;
            return tip;
        }
        return "";
    }

}

