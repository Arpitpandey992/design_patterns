package com.designpatterns.Decorator;

import lombok.Getter;

public class Starbuzz {
    public static class Beverage {
        String description;
        double cost;

        public Beverage(String description, double cost) {
            this.description = description;
            this.cost = cost;
        }

        public double getCost() {
            return cost;
        }

        public String getDescription() {
            return description;
        }

        public String getName() {
            return this.getClass().getSimpleName();
        }

        public void prettyPrint() {
            String separator = "-----------------------------";
            System.out.println(separator +
                    "\nName: " + this.getName() +
                    "\nCost: " + this.getCost() +
                    "\nDescription: " + this.getDescription() +
                    "\n" + separator + "\n");
        }
    }

    public static class Extra extends Beverage {
        @Getter
        private Beverage beverage;

        public Extra(String description, double cost, Beverage beverage) {
            super(description, cost);
            this.beverage = beverage;
        }

        @Override
        public double getCost() {
            return this.cost + beverage.getCost(); // better to do this iteratively ;)
        }

        @Override
        public String getName() {
            Beverage base = beverage;
            if (base instanceof Extra) {
                return base.getName() + ", " + super.getName();
            } else {
                return base.getName() + " With Condiments: " + super.getName();
            }
        }
    }

    public static class HouseBlend extends Beverage {
        public HouseBlend() {
            super("Blended stuff made in-house!", 1.4);
        }
    }

    public static class DarkRoast extends Beverage {
        public DarkRoast() {
            super("Chocolate hell!", 3.0);
        }
    }

    public static class Espresso extends Beverage {
        public Espresso() {
            super("Caffeine hell!", 0.2);
        }
    }

    public static class Decaf extends Beverage {
        public Decaf() {
            super("Expensive stuff for the nobles!", 5.3);
        }
    }

    public static class Milk extends Extra {
        public Milk(Beverage beverage) {
            super("White goodness!", 0.1, beverage);
        }
    }

    public static class Mocha extends Extra {
        public Mocha(Beverage beverage) {
            super("Fancy Name for Chocolate", 0.5, beverage);
        }
    }

    public static class Soy extends Extra {
        public Soy(Beverage beverage) {
            super("Who eats soy in their coffee?!", 0.001, beverage);
        }
    }

    public static class Whip extends Extra {
        public Whip(Beverage beverage) {
            super("Stuff of nightmare! Allucard would be proud!", 0.96, beverage);
        }
    }

    public static void main(String[] args) {
        // simple darkRoast coffee
        Beverage darkRoast = new DarkRoast();
        darkRoast.prettyPrint();
        // adding some mocha and soy
        Beverage newBeverage = new Soy(new Mocha(new Espresso()));
        newBeverage.prettyPrint();

        // maybe add one more topping, you glutton
        Beverage fatBeverage = new Whip(new Milk(newBeverage));
        fatBeverage.prettyPrint();
    }
}
