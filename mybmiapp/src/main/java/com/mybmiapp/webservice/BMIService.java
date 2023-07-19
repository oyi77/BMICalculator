package com.mybmiapp.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface BMIService {
    @WebMethod
    double calculateBMI(@WebParam(name = "height") double height, @WebParam(name = "weight") double weight);

    @WebMethod
    String determineBMICategory(@WebParam(name = "bmi") double bmi);
}
