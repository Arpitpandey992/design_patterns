package com.designpatterns;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.designpatterns.ObserverPattern.CurrentConditionsDisplay;
import com.designpatterns.ObserverPattern.ForecastDisplay;
import com.designpatterns.ObserverPattern.StatisticsDisplay;
import com.designpatterns.ObserverPattern.Subject;
import com.designpatterns.ObserverPattern.WeatherMeasurements;

public class ObserverPatternTest {
    Subject subject;

    @BeforeEach
    public void setUp() {
        subject = new WeatherMeasurements();
    }

    @Test
    public void testNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            subject.registerObserver(null);
        });
    }

    @Test
    public void testAddingObservers() {
        subject.registerObserver(new CurrentConditionsDisplay());
        subject.registerObserver(new StatisticsDisplay());
        subject.registerObserver(new ForecastDisplay());
    }
}
