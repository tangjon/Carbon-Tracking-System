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
            String tip = res.getString(R.string.tip_bike1);
            bikeTipsNotRepeat[0] = 1;
            return tip;
        } else if (totalBike > 0.0 && totalBike <= 6.0 && bikeTipsNotRepeat[1] > 8) {
            String tip = res.getString(R.string.tip_bike2) + totalBike +
                    res.getString(R.string.tip_bike2_1);
            bikeTipsNotRepeat[1] = 1;
            return tip;
        } else if (totalBike >= 5.0 && totalBike <= 10.0 && bikeTipsNotRepeat[2] > 8) {
            String tip = res.getString(R.string.tip_bike3) + totalBike +
                    res.getString(R.string.tip_walk_good);
            bikeTipsNotRepeat[2] = 1;
            return tip;
        } else if (totalBike > 10.0 && totalBike < 30 && bikeTipsNotRepeat[3] > 8) {
            String tip = res.getString(R.string.tip_bike2) + totalBike +
                    res.getString(R.string.tip_walk4);
            bikeTipsNotRepeat[3] = 1;
            return tip;
        } else if (totalBike >= 30.0 && totalBike < 64.37 && bikeTipsNotRepeat[4] > 8) {
            String tip = res.getString(R.string.tip_walk2) + totalBike +
                    res.getString(R.string.tip_bike5);
            bikeTipsNotRepeat[4] = 1;
            return tip;
        } else if (totalBike >= 64.37 && totalBike <= 96.56 && bikeTipsNotRepeat[6] > 8) {
            String tip = res.getString(R.string.tip_bike2) + totalBike +
                    res.getString(R.string.tip_bike6);
            bikeTipsNotRepeat[6] = 1;
            return tip;
        } else if (totalBike >= 100 && totalBike < 300 && bikeTipsNotRepeat[7] > 8) {
            String tip = res.getString(R.string.tip_bike2) + totalBike +
                    res.getString(R.string.tip_bike7);
            bikeTipsNotRepeat[7] = 1;
            return tip;
        } else if (totalBike >= 200 && totalBike < 300 && bikeTipsNotRepeat[8] > 8) {
            String tip = res.getString(R.string.tip_bike8) +
                    res.getString(R.string.tip_bike2) + totalBike +
                    res.getString(R.string.tip_bike_good);
            bikeTipsNotRepeat[8] = 1;
            return tip;
        } else if (totalBike >= 300 && bikeTipsNotRepeat[9] > 8) {
            String tip = res.getString(R.string.tip_bike8) +
                    res.getString(R.string.tip_bike2) + totalBike +
                    res.getString(R.string.tip_bike_bad);
            bikeTipsNotRepeat[9] = 1;
            return tip;
        } else if (totalJourneyEmissions == 0 && totalBike != 0 && bikeTipsNotRepeat[10] > 8) {
            String tip = res.getString(R.string.tip_bike_only);
            bikeTipsNotRepeat[10] = 1;
            return tip;
        } else if (totalCarEmission == 0 && totalBike != 0 &&
                totalJourneyEmissions != 0 && bikeTipsNotRepeat[11] > 8) {
            String tip = res.getString(R.string.tip_bike9) + totalJourneyEmissions +
                    res.getString(R.string.tip_bike9_1);
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
            String tip = res.getString(R.string.tip_journey1);
            journeyEmissionsTipsNotRepeat[0] = 1;
            return tip;
        } else if (totalJourneyEmissions > 0 && totalJourneyEmissions < 5.48 &&
                journeyEmissionsTipsNotRepeat[1] > 8) {
            String tip = res.getString(R.string.tip_journey2) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey2_1);
            journeyEmissionsTipsNotRepeat[1] = 1;
            return tip;
        } else if (totalJourneyEmissions >= 5.48 && totalJourneyEmissions <= 13.69 &&
                journeyEmissionsTipsNotRepeat[2] > 8) {
            String tip = res.getString(R.string.tip_journey3) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey3_1);
            journeyEmissionsTipsNotRepeat[2] = 1;
            return tip;
        } else if (totalJourneyEmissions >= 13.69 && totalJourneyEmissions <= 36.9 &&
                journeyEmissionsTipsNotRepeat[3] > 8) {
            String tip = res.getString(R.string.tip_journey4) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey4_1);
            journeyEmissionsTipsNotRepeat[3] = 1;
            return tip;
        } else if (totalJourneyEmissions >= 36.9 &&
                journeyEmissionsTipsNotRepeat[4] > 8) {
            String tip = res.getString(R.string.tip_journey5) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey5_1);
            journeyEmissionsTipsNotRepeat[4] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[5] > 8) {
            String tip = res.getString(R.string.tip_journey5) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey5_2);
            journeyEmissionsTipsNotRepeat[5] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[6] > 8) {
            String tip = res.getString(R.string.tip_journey5) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey5_3);
            journeyEmissionsTipsNotRepeat[6] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[7] > 8) {
            String tip = res.getString(R.string.tip_journey5) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey5_4);
            journeyEmissionsTipsNotRepeat[7] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[8] > 8) {
            String tip = res.getString(R.string.tip_journey5) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey5_5);
            journeyEmissionsTipsNotRepeat[8] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[9] > 8) {
            String tip = res.getString(R.string.tip_journey5) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey5_6);
            journeyEmissionsTipsNotRepeat[9] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[10] > 8) {
            String tip = res.getString(R.string.tip_journey5) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey5_7);
            journeyEmissionsTipsNotRepeat[10] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[11] > 8) {
            String tip = res.getString(R.string.tip_journey5) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey5_8);
            journeyEmissionsTipsNotRepeat[11] = 1;
            return tip;
        } else if (journeyEmissionsTipsNotRepeat[12] > 8) {
            String tip = res.getString(R.string.tip_journey5) + totalJourneyEmissions +
                    res.getString(R.string.tip_journey5_9);
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
            String tip = res.getString(R.string.tip_electricity1);
            electricityTipsNotRepeat[2] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[3] > 8) {
            String tip = res.getString(R.string.tip_electricity2);
            electricityTipsNotRepeat[3] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[4] > 8) {
            String tip = res.getString(R.string.tip_electricity3);
            electricityTipsNotRepeat[4] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[5] > 8) {
            String tip = res.getString(R.string.tip_electricity4);
            electricityTipsNotRepeat[5] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[6] > 8) {
            String tip = res.getString(R.string.tip_electricity5);
            electricityTipsNotRepeat[6] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[7] > 8) {
            String tip = res.getString(R.string.tip_electricity6);
            electricityTipsNotRepeat[7] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[8] > 8) {
            String tip = res.getString(R.string.tip_electricity7);
            electricityTipsNotRepeat[8] = 1;
            return tip;
        } else if (electricityTipsNotRepeat[9] > 8) {
            String tip = res.getString(R.string.tip_electricity8);
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
            String tip = res.getString(R.string.tip_gas1);
            gasTipsNotRepeat[0] = 1;
            return tip;
        } else if (gasTipsNotRepeat[1] > 8) {
            String tip = res.getString(R.string.tip_gas2);
            gasTipsNotRepeat[1] = 1;
            return tip;
        } else if (gasTipsNotRepeat[2] > 8) {
            String tip = res.getString(R.string.tip_gas3);
            gasTipsNotRepeat[2] = 1;
            return tip;
        } else if (gasTipsNotRepeat[3] > 8) {
            String tip = res.getString(R.string.tip_gas4);
            gasTipsNotRepeat[3] = 1;
            return tip;
        } else if (gasTipsNotRepeat[4] > 8) {
            String tip = res.getString(R.string.tip_gas5);
            gasTipsNotRepeat[4] = 1;
            return tip;
        } else if (gasTipsNotRepeat[5] > 8) {
            String tip = res.getString(R.string.tip_gas6);
            gasTipsNotRepeat[5] = 1;
            return tip;
        } else if (gasTipsNotRepeat[6] > 8) {
            String tip = res.getString(R.string.tip_gas7);
            gasTipsNotRepeat[6] = 1;
            return tip;
        } else if (gasTipsNotRepeat[7] > 8) {
            String tip = res.getString(R.string.tip_gas8);
            gasTipsNotRepeat[7] = 1;
            return tip;
        }
        return "";
    }

}

