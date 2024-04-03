package com.lawtin.alarmaprueba;
import java.io.Serializable;

public class Alarm implements Serializable {
    private int Id;
    private int hour;
    private int minute;
    private boolean status;
    private String name;




    public Alarm(int id,int hour, int minute, boolean status, String name) {
        Alarm alarm = new Alarm(id, hour, minute, status, name);

        this.Id=Id;
        this.hour = hour;
        this.minute = minute;
        this.status = status;
        this.name = name;
    }


    public int getId(){
        return Id;
    }
    public void setId(int Id){
        this.Id=Id;
    }

    // Agrega los métodos getters y setters según sea necesario

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        // Retorna una representación de cadena de la alarma (por ejemplo, hora y minuto)
        return hour + ":" + (minute < 10 ? "0" + minute : minute);
    }
}
