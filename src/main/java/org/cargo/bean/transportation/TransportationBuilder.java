package org.cargo.bean.transportation;

import org.cargo.bean.user.User;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

public class TransportationBuilder {
     int id;
     String comment;
     User customer;
     Tariff tariff;
     @Enumerated(EnumType.STRING)
     OrderStatus orderStatus;
     LocalDate creationDate;
     LocalDate deliveryDate;

    public TransportationBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TransportationBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public TransportationBuilder setCustomer(User customer) {
        this.customer = customer;
        return this;
    }

    public TransportationBuilder setTariff(Tariff tariff) {
        this.tariff = tariff;
        return this;
    }

    public TransportationBuilder setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public TransportationBuilder setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public TransportationBuilder setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }
    public Transportation build(){
        return new Transportation(id, comment, customer, tariff, orderStatus, creationDate, deliveryDate);
    }
}
