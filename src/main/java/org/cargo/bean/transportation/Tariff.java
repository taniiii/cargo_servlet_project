package org.cargo.bean.transportation;

import javax.persistence.*;
import java.util.Objects;

public class Tariff {

    private int id;
    private int price;
    @Enumerated(EnumType.STRING)
    private Address address;
    @Enumerated(EnumType.STRING)
    private Size size;
    @Enumerated(EnumType.STRING)
    private Weight weight;
    private int deliveryTermDays;

    public Tariff() {
    }

    public Tariff(int id, int price, Address address, Size size, Weight weight, int deliveryTermDays) {
        this.id = id;
        this.price = price;
        this.address = address;
        this.size = size;
        this.weight = weight;
        this.deliveryTermDays = deliveryTermDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public int getDeliveryTermDays() {
        return deliveryTermDays;
    }

    public void setDeliveryTermDays(int deliveryTermDays) {
        this.deliveryTermDays = deliveryTermDays;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", price=" + price +
                ", address=" + address +
                ", size=" + size +
                ", weight=" + weight +
                ", deliveryTermDays=" + deliveryTermDays +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return id == tariff.id && price == tariff.price
                && address == tariff.address && size == tariff.size
                && weight == tariff.weight && deliveryTermDays == tariff.deliveryTermDays;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, address, size, weight, deliveryTermDays);
    }
}
