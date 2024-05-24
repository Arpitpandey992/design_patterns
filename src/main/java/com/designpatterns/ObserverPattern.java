package com.designpatterns;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ObserverPattern {
    interface Subject {
        public void registerObserver(Observer observer);

        public void removeObserver(Observer observer);

        public void notifyObserver();
    }

    interface Observer {
        public void update(float temperature);
    }

    class WeatherMeasurements implements Subject {
        @Getter
        @Setter
        float temperature;

        List<Observer> observers;

        @Override
        public void registerObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void removeObserver(Observer observer) {

        }

        @Override
        public void notifyObserver() {
            for (Observer observer : observers) {
                observer.update(1);
            }
        }

        public void measurementsChanged() {

        }

    }
}