package com.mybmiapp.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface BMIService {
    @WebMethod
    double calculateBMI(double weight, double height);
}
