package id.bhinneka.rebon.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import id.bhinneka.rebon.R;
import id.bhinneka.rebon.utils.Utils;

/**
 * Created by bendet on 05/07/17.
 */

public class SewaActivity extends AppCompatActivity {

    private Spinner spinnerJenisSewa, spinnerLamaSewa;
    private String[] jenisSewa = {"City Tours", "Sewa"};
    private String[] lamaSewa = {"3 Jam", "6 Jam", "8 Jam", "12 Jam"};
    private TextView tvTanggal, tvDariJam;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.fragment_user);

        spinnerJenisSewa = findViewById(R.id.spinner_jenis_sewa);
        spinnerLamaSewa = findViewById(R.id.spinner_lama_sewa);

        tvTanggal = findViewById(R.id.tv_tanggal);
        tvDariJam = findViewById(R.id.tv_dari_jam);

        ArrayAdapter<CharSequence> adapterJenisSewa = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_text, jenisSewa);
        ArrayAdapter<CharSequence> adapterLamaSewa = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_text, lamaSewa);

        adapterJenisSewa.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        adapterLamaSewa.setDropDownViewResource(R.layout.simple_spinner_dropdown);

        spinnerJenisSewa.setAdapter(adapterJenisSewa);
        spinnerLamaSewa.setAdapter(adapterLamaSewa);
        tvTanggal.setText(setDate());
        tvTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SewaActivity.this, TanggalActivity.class));
            }
        });
        tvDariJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int jam = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int menit = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mtimePicker = new TimePickerDialog(SewaActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        tvDariJam.setText(i +":"+ i1+" WIB");
                    }
                }, jam, menit, true);
                mtimePicker.setTitle("Dari Jam");
                mtimePicker.show();
            }
        });
    }

    private String setDate(){
        Calendar cal = Calendar.getInstance();
        String tanggal = Utils.getFormattedDateIndo(this,cal.getTimeInMillis());
        return tanggal;
    }
}
