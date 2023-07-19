package com.mybmiapp.dao;

import com.mybmiapp.models.Calculation;

import java.util.List;

public interface CalculationDao {
    boolean addCalculation(Calculation calculation);
    List<Calculation> getCalculationsByUserId(int userId);
    // Add other calculation-related methods as needed
}
