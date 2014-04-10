package hu.bernatzoltan.dijkalkulator.model;

import java.util.Calendar;

/**
 *
 * @author bzoli
 */
public enum AllocationSeason {
    WINTER(0.9), 
    
    
    SUMMER(0.2);
    
    AllocationSeason(double multiplier){
        this.multiplier = multiplier;
    }
    
    private double multiplier;

    public double getMultiplier() {
        return multiplier;
    }

    
    public static boolean isWinter(int monthOrdinal) {
        if (monthOrdinal > Calendar.MARCH && monthOrdinal > Calendar.DECEMBER) {
            return false;
        }
        return true;
    }
    
//    public void setMultiplier(double multiplier) {
//        this.multiplier = multiplier;
//    }
    
    
}
