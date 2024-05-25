package com.designpatterns.Observer;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PushPullObserver {
    public interface Subject {
        public void registerObserver(Observer observer);

        public void removeObserver(Observer observer);

        public void notifyObservers();
    }

    public static abstract class Observer {
        @Getter
        private WeatherMeasurements weatherMeasurements;

        public String getName() {
            return this.getClass().getName();
        }

        Observer(WeatherMeasurements wm) {
            this.weatherMeasurements = wm;
        }

        public abstract void ChangeNotification();
    }

    public static class CurrentConditionsDisplay extends Observer {

        CurrentConditionsDisplay(WeatherMeasurements wm) {
            super(wm);
        }

        @Override
        public void ChangeNotification() {
            log.info("updated temperature to [{}], humidity to [{}] in display: [{}]",
                    getWeatherMeasurements().getTemperature(), getWeatherMeasurements().getHumidity(),
                    getName());
        }
    }

    public static class ForecastDisplay extends Observer {
        ForecastDisplay(WeatherMeasurements wm) {
            super(wm);
        }

        @Override
        public void ChangeNotification() {
            log.info("updated temperature to [{}], humidity to [{}] in display: [{}]",
                    getWeatherMeasurements().getTemperature(), getWeatherMeasurements().getHumidity(),
                    getName());
        }
    }

    public static class StatisticsDisplay extends Observer {
        StatisticsDisplay(WeatherMeasurements wm) {
            super(wm);
        }

        @Override
        public void ChangeNotification() {
            log.info("updated temperature to [{}], humidity to [{}] in display: [{}]",
                    getWeatherMeasurements().getTemperature(), getWeatherMeasurements().getHumidity(),
                    getName());
        }
    }

    public static class WeatherMeasurements implements Subject {
        @Getter
        private float temperature = -1;
        @Getter
        private long humidity = -1;

        private Set<Observer> displays = new HashSet<Observer>();

        WeatherMeasurements() {
            log.info("initialized WeatherMeasurements with temperature: [{}] and no displays", temperature);
        }

        WeatherMeasurements(float temperature) {
            this.temperature = temperature;
        }

        WeatherMeasurements(Set<Observer> displays) {
            this.displays = displays;
        }

        public void setTemperature(float newTemperature) {
            if (newTemperature != this.temperature) {
                this.temperature = newTemperature;
                this.measurementsChanged();
            }
        }

        public void setHumidity(long newHumidity) {
            if (newHumidity != this.humidity) {
                this.humidity = newHumidity;
                this.measurementsChanged();
            }
        }

        @Override
        public void registerObserver(Observer display) {
            if (display == null) {
                throw new NullPointerException("display cannot be null");
            }
            if (displays.contains(display)) {
                log.info("Display: [{}] is already registered", display.getName());
                return;
            }
            displays.add(display);
            log.info("added observer: [{}] to weather measurement broadcast", display.getName());
        }

        @Override
        public void removeObserver(Observer display) {
            if (display == null) {
                throw new NullPointerException("display cannot be null");
            }
            if (displays.contains(display)) {
                displays.remove(display);
                log.info("removed display: [{}] from weather measurement broadcast", display.getName());
            } else {
                throw new NoSuchElementException(
                        String.format("Display %s is not currently displaying weather measurements",
                                display.getName()));
            }
        }

        @Override
        public void notifyObservers() {
            for (Observer observer : displays) {
                observer.ChangeNotification();
            }
        }

        public void measurementsChanged() {
            log.info("Measurements Changed!");
            notifyObservers();
        }
    }

    public static void main(String[] args) {
        WeatherMeasurements subject = new WeatherMeasurements();
        Observer ccd = new CurrentConditionsDisplay(subject), sd = new StatisticsDisplay(subject),
                fd = new ForecastDisplay(subject);
        subject.registerObserver(ccd);
        subject.registerObserver(sd);
        sleep(500);
        subject.setTemperature(42);
        subject.setHumidity(3);
        subject.removeObserver(sd);
        subject.registerObserver(fd);
        sleep(500);
        subject.setTemperature(30);
        subject.setHumidity(6666);
        sleep(500);
        subject.setTemperature(16);

    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }
}