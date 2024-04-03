package com.lawtin.alarmaprueba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.List;

public class CustomerAdapter extends BaseAdapter {
    private final Context context;
    private final List<Alarm> alarmList;
    private final LayoutInflater layoutInflater;

    public CustomerAdapter(Context context, List<Alarm> alarmList) {
        this.context = context;
        this.alarmList = alarmList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return alarmList.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_items, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.nameTextView);
            holder.timeTextView = convertView.findViewById(R.id.timeTextView);
            holder.toggleButton = convertView.findViewById(R.id.toggle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Alarm selectedAlarm = alarmList.get(position);
        holder.nameTextView.setText(selectedAlarm.getName());
        holder.timeTextView.setText(selectedAlarm.toString());
        holder.toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            selectedAlarm.setStatus(isChecked);
            DatabaseHelper db = new DatabaseHelper(context);
            db.updateAlarm(selectedAlarm);

            alarmList.clear();
            alarmList.addAll(db.getAllAlarms());
            notifyDataSetChanged();

            Intent serviceIntent = new Intent(context, AlarmReciver.class);
            serviceIntent.putExtra("position", position);
            serviceIntent.putExtra("extra", isChecked ? "on" : "off");
            serviceIntent.putExtra("active", isChecked ? selectedAlarm.toString() : "");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, position, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (isChecked) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, selectedAlarm.getHour());
                calendar.set(Calendar.MINUTE, selectedAlarm.getMinute());
                calendar.set(Calendar.SECOND, 0);
                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                    calendar.add(Calendar.DATE, 1);
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.cancel(pendingIntent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView timeTextView;
        ToggleButton toggleButton;
    }
}
