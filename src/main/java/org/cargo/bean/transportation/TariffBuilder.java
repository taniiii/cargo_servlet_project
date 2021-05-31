package org.cargo.bean.transportation;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class TariffBuilder {

    int id;
    int price;
    @Enumerated(EnumType.STRING)
    Address address;
    @Enumerated(EnumType.STRING)
    Size size;
    @Enumerated(EnumType.STRING)
    Weight weight;
    int deliveryTermDays;

    public TariffBuilder setId(int id) {
        this.id = id;
        return  this;
    }

    public TariffBuilder setPrice(int price) {
        this.price = price;
        return  this;
    }

    public TariffBuilder setAddress(Address address) {
        this.address = address;
        return  this;
    }

    public TariffBuilder setSize(Size size) {
        this.size = size;
        return  this;
    }

    public TariffBuilder setWeight(Weight weight) {
        this.weight = weight;
        return  this;
    }

    public TariffBuilder setDeliveryTermDays(int deliveryTermDays) {
        this.deliveryTermDays = deliveryTermDays;
        return  this;
    }

    public Tariff build(){
        return new Tariff(id, price, address, size, weight, deliveryTermDays);
    }
}
