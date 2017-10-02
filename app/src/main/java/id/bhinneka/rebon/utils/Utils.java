package id.bhinneka.rebon.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.bhinneka.rebon.R;

/**
 * Created by bendet on 17/07/17.
 */

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static final Calendar TODAY;

    public static final int DAY_OF_TIME;

    static {
        TODAY = Calendar.getInstance();
        TODAY.add(Calendar.DAY_OF_WEEK, 0);

        DAY_OF_TIME = 7;
    }

    public static int getPositionForDay(Calendar day){
        if (day != null){
            return (int) ((day.getTimeInMillis()));
        }
        return 0;
    }

    public static Calendar getDayForPosition(int position) throws IllegalArgumentException{
        Log.e(TAG, "position : "+position);
        if (position < 0){
            throw new IllegalArgumentException("positon cannot be negative");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(TODAY.getTimeInMillis());
        cal.add(Calendar.DAY_OF_WEEK, position);
        return cal;
    }

    public static String getFormattedDate(Context context, long date){
        final String defaultPattern = "yyyy-MM-dd";
        String pattern ;
        SimpleDateFormat simpleDateFormat ;
        try {
            pattern = defaultPattern;
            simpleDateFormat = new SimpleDateFormat(pattern);
        }catch (IllegalArgumentException e){
            pattern = context.getString(R.string.date_format);
            simpleDateFormat = new SimpleDateFormat(pattern);
        }
        return simpleDateFormat.format(new Date(date));
    }

    public static String getFormattedDateIndo(Context context, long date){
        final String defaultPattern = "E, dd-MMM-yyyy";

        String pattern ;
        SimpleDateFormat simpleDateFormat = null;
        try {
            pattern = defaultPattern;
            simpleDateFormat = new SimpleDateFormat(pattern, new Locale("id", "ID"));
        }catch (IllegalArgumentException e){
            pattern = context.getString(R.string.date_format);
            simpleDateFormat = new SimpleDateFormat(pattern);
        }
        return ubahHari(simpleDateFormat.format(new Date(date)));
    }

    public static String getFormattedDateIndo(Context context, String date){
        final String defaultPattern = "E, dd-MMM-yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat ;
        long milisecond = 0;
        String pattern ;

        //convert date yyyy-MM-dd to long
        try {
            Date d = sdf.parse(date);
            milisecond = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            pattern = defaultPattern;
            simpleDateFormat = new SimpleDateFormat(pattern, new Locale("id", "ID"));
        }catch (IllegalArgumentException e){
            pattern = context.getString(R.string.date_format);
            simpleDateFormat = new SimpleDateFormat(pattern);
        }

        return ubahHari(simpleDateFormat.format(new Date(milisecond)));
    }

    public static long selisihJam(String exp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sekarang = new Date();
        long selisih = 0;
        try {
            Date expTime = sdf.parse(exp);
            selisih = expTime.getTime() - sekarang.getTime();
        } catch (ParseException e) {
            Log.e(TAG, "parse jam error : "+e.getMessage());
        }
        
        return selisih;
    }

    private static String ubahHari(String hari){
        String day = null;
        if (hari.contains("Sen"))
            day = hari.replace("Sen", "Senin");
        else if (hari.contains("Sel"))
            day = hari.replace("Sel", "Selasa");
        else if (hari.contains("Rab"))
            day = hari.replace("Rab", "Rabu");
        else if (hari.contains("Kam"))
            day = hari.replace("Kam", "Kamis");
        else if (hari.contains("Jum"))
            day = hari.replace("Jum", "Jumat");
        else if (hari.contains("Sab"))
            day = hari.replace("Sab", "Sabtu");
        else if (hari.contains("Min"))
            day = hari.replace("Min", "Minggu");

        return day;
    }

    public static String rupiah(Integer angka) {
        DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance(new Locale("id", "ID"));
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("");
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        return df.format(angka);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void systemBarLolipop(Activity act){
        if (getAPIVerison() >= 5.0) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private static float getAPIVerison() {

        Float f = null;
        try {
            StringBuilder strBuild = new StringBuilder();
            strBuild.append(android.os.Build.VERSION.RELEASE.substring(0, 2));
            f = new Float(strBuild.toString());
        } catch (NumberFormatException e) {
            Log.e("", "erro ao recuperar a versão da API" + e.getMessage());
        }

        return f.floatValue();
    }


}
