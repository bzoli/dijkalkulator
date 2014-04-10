package hu.bernatzoltan.dijkalkulator.model;

/**
 *
 * @author bzoli
 */
public enum CapacityType {
    M0(1d), M10(0.5);
    
    CapacityType(double multiplier){
        this.multiplier = multiplier;
    }
    
    private double multiplier;

    public double getMultiplier() {
        return multiplier;
    }

//    public void setMultiplier(double multiplier) {
//        this.multiplier = multiplier;
//    }
    
    
}
