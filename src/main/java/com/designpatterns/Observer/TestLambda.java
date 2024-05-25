package com.designpatterns.Observer;

public class TestLambda {
    public interface singleInterface {
        public void print(int x);
    }

    static void doPrint(singleInterface s) {
        s.print(4);
    }

    public static void main(String[] args) {
        doPrint(new singleInterface() {
            @Override
            public void print(int x) {
                System.out.println("printing using anonymous class with arg = " + x);
            }
        });

        doPrint(x -> System.out.println("printing using lambda with arg = " + x));
    }
}
