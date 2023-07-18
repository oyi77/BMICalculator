package com.mybmiapp.services;

public class BMICalculator {

    public static double calculateBMI(double height, double weight) {
        if (height <= 0 || weight <= 0) {
            throw new IllegalArgumentException("Height and weight must be greater than zero.");
        }
        return weight / (height * height);
    }

    public static String determineBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            return "Normal";
        } else if (bmi >= 25 && bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public static String determineWeightStatus(String bmiCategory) {
        if ("Underweight".equals(bmiCategory)) {
            return "You are underweight. Please consider gaining weight.";
        } else if ("Normal".equals(bmiCategory)) {
            return "Your weight is normal. Keep up the good work!";
        } else if ("Overweight".equals(bmiCategory)) {
            return "You are overweight. Please consider losing weight for a healthier lifestyle.";
        } else {
            return "You are obese. Please take necessary actions to improve your health.";
        }
    }

    public static String calculateIdealWeightRange(double height) {
        double lowerBound = 18.5 * height * height;
        double upperBound = 24.9 * height * height;
        return String.format("%.2f - %.2f", lowerBound, upperBound);
    }
}
