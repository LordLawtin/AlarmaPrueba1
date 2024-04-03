package com.lawtin.alarmaprueba;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

public class AlarmReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isRunning = false;
        String action = intent.getAction();

        if (action != null && action.equals("com.example.ACTION_START_MUSIC")) {
            // Verificar si el servicio de música ya está en ejecución
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = manager.getRunningAppProcesses();
            if (runningProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                    if (processInfo.processName.equals(context.getPackageName())) {
                        isRunning = true;
                        break;
                    }
                }
            }

            // Iniciar el servicio de música si no está en ejecución
            if (!isRunning) {
                Intent musicIntent = new Intent(context, Music.class);
                context.startService(musicIntent);
                // Actualizar estado de alarma activa en MainActivity
                MainActivity.activeAlarm = "active";
            }
        } else if (action != null && action.equals("com.example.ACTION_STOP_MUSIC")) {
            // Detener el servicio de música
            Intent musicIntent = new Intent(context, Music.class);
            context.stopService(musicIntent);
            // Actualizar estado de alarma activa en MainActivity
            MainActivity.activeAlarm = " ";
        }
    }
}
