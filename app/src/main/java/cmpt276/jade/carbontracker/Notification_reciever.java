package cmpt276.jade.carbontracker;
/*this is the reciever for notification
it could show the smart text to user about their current use
like if there is no elec bill or gas bill for more than one month
it will ask user to add new bills
otherwise, it will show how many journeies the user entered
and ask user to add more.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cmpt276.jade.carbontracker.model.Bill;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Utilities;

import static android.content.Context.NOTIFICATION_SERVICE;


public class Notification_reciever extends BroadcastReceiver{

    public static JourneyCollection listOfJourneys = Emission.getInstance().getJourneyCollection();
    public Utilities utilities = Emission.getInstance().getUtilities();
    public List<Bill> billsElec;
    public List<Bill> billsGas;

    private Resources res = Resources.getSystem();

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentForAddingJourney= new Intent(context, JourneyListActivity.class);
        intentForAddingJourney.putExtra("mode",1);
        intentForAddingJourney.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Intent intentForAddingBills= new Intent(context, Utilities_Activities.class);
        intentForAddingBills.putExtra("mode",2);
        intentForAddingBills.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent pendingIntentForJourney= PendingIntent.getActivity(context,100,intentForAddingJourney,
                PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentForBills= PendingIntent.getActivity(context,200,intentForAddingBills,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        int todayYear=calendar.get(Calendar.YEAR);
        int todayMonth=calendar.get(Calendar.MONTH)+1;
        int todayDay=calendar.get(Calendar.DAY_OF_MONTH);
        int haveEnteredJourney=0;
        for (Journey journey : listOfJourneys.getJourneyList()){
            String date=journey.getDate();
            String[] addedJourneyData = date.trim().split("/", 3);
            int addedJourneyMonth = 0;
            int addedJourneyDay = 0;
            int addedJourneyYear = 0;
            addedJourneyDay = Integer.parseInt(addedJourneyData[0]);
            addedJourneyMonth = Integer.parseInt(addedJourneyData[1]);
            addedJourneyYear = Integer.parseInt(addedJourneyData[2]);
            if(addedJourneyDay==todayDay && todayMonth==addedJourneyMonth && todayYear==addedJourneyYear ) {
                haveEnteredJourney++;
            }
        }

        billsElec = utilities.getListBillElec();
        billsGas = utilities.getListBillGas();
        Date now = new Date(System.currentTimeMillis());
        long todayInTimeMillis=now.getTime();

        boolean there_Is_ElecBill_In_One_And_Half_Mouth=false;
        boolean there_Is_GasBill_In_One_And_Half_Mouth=false;

        if(billsElec.size()>0) {
            for (Bill bill : billsElec) {
                long newestElecDateInTimeMillis;
                Date newestDate = bill.getEndDate();
                newestElecDateInTimeMillis = newestDate.getTime();

                //45 days
                long oneHalfMonthInTimeMillis = Long.parseLong("3884000000");
                long different = todayInTimeMillis - newestElecDateInTimeMillis;
                Log.i("test ele time different", "different " + different);
                if (different < oneHalfMonthInTimeMillis) {
                    there_Is_ElecBill_In_One_And_Half_Mouth = true;
                }
            }
        }
        else {
            //for first time if there are no bills in list
            there_Is_ElecBill_In_One_And_Half_Mouth=false;
        }

        if(billsGas.size()>0) {
            for (Bill gas : billsGas) {
                long newestGasDateInTimeMillis;
                Date newestDate = gas.getEndDate();
                newestGasDateInTimeMillis = newestDate.getTime();

                long oneHalfMonthInTimeMillis = Long.parseLong("3884000000");
                long different = todayInTimeMillis - newestGasDateInTimeMillis;
                Log.i("test Gas time different", "different " + different);
                if (different < oneHalfMonthInTimeMillis) {
                    there_Is_GasBill_In_One_And_Half_Mouth = true;
                }
            }
        }
        else {
            there_Is_GasBill_In_One_And_Half_Mouth=false;
        }

        if(!there_Is_ElecBill_In_One_And_Half_Mouth) {
            String text = res.getString(R.string.notification_new_bill);

            NotificationCompat.BigTextStyle style=new NotificationCompat.BigTextStyle();
            style.setBigContentTitle(res.getString(R.string.app_name));
            style.bigText(text);

            NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
            builder.setContentIntent(pendingIntentForBills);
            builder.setSmallIcon(R.drawable.car);
            builder.setContentTitle(res.getString(R.string.app_name));
            builder.setContentText(text);
            builder.setAutoCancel(true);
            builder.setStyle(style);

            Notification notification=builder.build();
            NotificationManager notificationManager=
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(200,notification);
        }
        else if(!there_Is_GasBill_In_One_And_Half_Mouth) {
            String text = res.getString(R.string.notification_new_bill);

            NotificationCompat.BigTextStyle style=new NotificationCompat.BigTextStyle();
            style.setBigContentTitle(res.getString(R.string.app_name));
            style.bigText(text);

            NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
            builder.setContentIntent(pendingIntentForBills);
            builder.setSmallIcon(R.drawable.car);
            builder.setContentTitle(res.getString(R.string.app_name));
            builder.setContentText(text);
            builder.setAutoCancel(true);
            builder.setStyle(style);

            Notification notification=builder.build();
            NotificationManager notificationManager=
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(200,notification);
        }
        else {
            String text= res.getString(R.string.notification_new_journey1) +
                    haveEnteredJourney +
                    res.getString(R.string.notification_new_journey2);

            NotificationCompat.BigTextStyle style=new NotificationCompat.BigTextStyle();
            style.setBigContentTitle(res.getString(R.string.app_name));
            style.bigText(text);

            NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
            builder.setContentIntent(pendingIntentForJourney);
            builder.setSmallIcon(R.drawable.car);
            builder.setContentTitle(res.getString(R.string.app_name));
            builder.setContentText(text);
            builder.setAutoCancel(true);
            builder.setStyle(style);

            Notification notification=builder.build();
            NotificationManager notificationManager=
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(100,notification);
        }
    }
}
