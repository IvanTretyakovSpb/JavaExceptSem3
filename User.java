package seminar_3.homework;

import java.util.Calendar;
import java.util.Date;

// Класс для хранения данных о пользователях в соответствующих полях
public class User {
    private String surname;
    private String name;
    private String patronymic;
    private long phoneNumber;
    private char gender;
    private Date date;

    public User() {
        this.surname = "undefined";
        this.name = "undefined";
        this.patronymic = "undefined";
        this.phoneNumber = 123456789;
        this.gender = '-';
        this.date = new Date(1900, Calendar.JANUARY, 1);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
