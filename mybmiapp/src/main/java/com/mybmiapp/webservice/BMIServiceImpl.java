package com.mybmiapp.webservice;

import com.mybmiapp.services.BMICalculator;

import javax.jws.WebService;

@WebService(endpointInterface = "com.mybmiapp.webservice.BMIService")
public class BMIServiceImpl implements BMIService {
    @Override
    public double calculateBMI(double height, double weight) {
        return BMICalculator.calculateBMI(height, weight);
    }

    @Override
    public String determineBMICategory(double bmi) {
        return BMICalculator.determineBMICategory(bmi);
    }
}
