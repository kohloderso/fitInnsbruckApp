package models;

import javax.persistence.Entity;
import java.time.LocalTime;

public class OpenPeriod{
        public LocalTime begin;
        public LocalTime end;


   public OpenPeriod() {
        begin = LocalTime.now();
        end = LocalTime.now();
    }

    public OpenPeriod(LocalTime begin, LocalTime end) {
        this.begin = begin;
        this.end = end;
    }

    public String toString() {
            return begin.toString() + " - " + end.toString();
        }

    }