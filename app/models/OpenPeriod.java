package models;

import java.time.LocalTime;

public class OpenPeriod{
        LocalTime begin;
        LocalTime end;

    public OpenPeriod(LocalTime begin, LocalTime end) {
        this.begin = begin;
        this.end = end;
    }

    public String toString() {
            return begin.toString() + " - " + end.toString();
        }

    }