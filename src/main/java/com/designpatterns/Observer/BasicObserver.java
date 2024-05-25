package com.designpatterns.Observer;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicObserver {
    public interface Subject {
        public void registerObserver(Observer observer);

        public void removeObserver(Observer observer);

        public void notifyObservers();
    }

    public interface Observer {
        public String getName();

        public void update(float temperature);
    }

    public static class CurrentConditionsDisplay implements Observer {
        @Override
        public String getName() {
            return "CurrentConditionsDisplay";
        }

        @Override
        public void update(float temperature) {
            log.info("updated temperature to [{}] in display: [{}]", temperature, getName());
        }
    }

    public static class ForecastDisplay implements Observer {
        @Override
        public String getName() {
            return "ForecastDisplay";
        }

        @Override
        public void update(float temperature) {
            log.info("updated temperature to [{}] in display: [{}]", temperature, getName());
        }
    }

    public static class StatisticsDisplay implements Observer {
        @Override
        public String getName() {
            return "StatisticsDisplay";
        }

        @Override
        public void update(float temperature) {
            log.info("updated temperature to [{}] in display: [{}]", temperature, getName());
        }
    }

    public static class WeatherMeasurements implements Subject {
        @Getter
        private float temperature = -1;
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
                observer.update(getTemperature());
            }
        }

        public void measurementsChanged() {
            log.info("Measurements Changed! new temperature = [{}]", getTemperature());
            notifyObservers();
        }
    }

    public static void main(String[] args) {
        WeatherMeasurements subject = new WeatherMeasurements();
        Observer ccd = new CurrentConditionsDisplay(), sd = new StatisticsDisplay(), fd = new ForecastDisplay();
        subject.registerObserver(ccd);
        subject.registerObserver(sd);
        sleep(500);
        subject.setTemperature(42);
        subject.removeObserver(sd);
        subject.registerObserver(fd);
        sleep(500);
        subject.setTemperature(30);
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