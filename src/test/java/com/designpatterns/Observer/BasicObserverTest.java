package com.designpatterns.Observer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.designpatterns.Observer.BasicObserver.CurrentConditionsDisplay;
import com.designpatterns.Observer.BasicObserver.ForecastDisplay;
import com.designpatterns.Observer.BasicObserver.StatisticsDisplay;
import com.designpatterns.Observer.BasicObserver.Subject;
import com.designpatterns.Observer.BasicObserver.WeatherMeasurements;

public class BasicObserverTest {
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
