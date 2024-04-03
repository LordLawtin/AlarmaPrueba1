package com.lawtin.alarmaprueba;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        timePicker = findViewById(R.id.timePick);
        editText = findViewById(R.id.nameAlarm);
        Button buttonSave = findViewById(R.id.saveButtom);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int hour = timePicker.getCurrentHour();
                    int minute = timePicker.getCurrentMinute();
                    String name = editText.getText().toString();

                    // Crear un objeto de tipo Alarm con los valores proporcionados
                    Alarm alarm = new Alarm(0, hour, minute, true, name); // Suponiendo que el ID debe ser 0 por ahora

                    // Llamar al método addAlarm() de DatabaseHelper con el objeto Alarm
                    DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                    dbHelper.addAlarm(alarm);

                    boolean needRefresh = true;

                    onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AddActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void addAlarmToDatabase(int hour, int minute, String name) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Alarm alarm = new Alarm(0, hour, minute, true, name); // Suponiendo que el ID debe ser 0 por ahora
        dbHelper.addAlarm(alarm);
    }
}








