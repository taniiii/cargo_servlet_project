package org.cargo.bean.transportation;

public enum Size {
    SMALL("40*40*40 and under"), MIDDLE("70*70*70 and under"), BIG("100*100*100 and under");

    private String sizeInCentimeters;

    Size(String sizeInCentimeters){
        this.sizeInCentimeters = sizeInCentimeters;
    }
    public String getSize(){
        return sizeInCentimeters;
    }
}
