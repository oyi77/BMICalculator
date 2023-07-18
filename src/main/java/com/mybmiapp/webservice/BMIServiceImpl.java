package com.mybmiapp.webservice;

import javax.jws.WebService;

@WebService(endpointInterface = "com.mybmiapp.webservice.BMIService")
public class BMIServiceImpl implements BMIService {
    @Override
    public double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }
}
