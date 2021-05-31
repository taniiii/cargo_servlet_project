package org.cargo.bean.transportation;

public enum Weight {
    LIGHT("under 2kg"), MEDIUM("under 8kg"), HEAVY("under 16kg");

    private String weightInKilograms;

    Weight(String weightInKilograms){
        this.weightInKilograms = weightInKilograms;
    }

    public String getWeight(){
        return weightInKilograms;
    }
}
