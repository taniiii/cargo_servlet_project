package org.cargo.bean.transportation;

import org.cargo.bean.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

public class Transportation {

    private int id;
    private String comment;
    private User customer;
    private Tariff tariff;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDate creationDate;
    private LocalDate deliveryDate;

    public Transportation() {
        this.creationDate = LocalDate.now();
    }

    public Transportation(int id, String comment, User customer, Tariff tariff, OrderStatus status, LocalDate creationDate, LocalDate deliveryDate) {
        this.id = id;
        this.comment = comment;
        this.customer = customer;
        this.tariff = tariff;
        this.orderStatus = status;
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getCustomer() {
        return customer;
    }

    public String getCustomerName(){
        return customer.getUsername();
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "Transportation{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", customer=" + customer +
                ", tariff=" + tariff +
                ", order status=" + orderStatus +
                ", creationDate=" + creationDate +
                ", deliveryDate=" + deliveryDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transportation that = (Transportation) o;
        return id == that.id && Objects.equals(comment, that.comment)
                && Objects.equals(customer, that.customer)
                && Objects.equals(tariff, that.tariff)
                && orderStatus == that.orderStatus
                && Objects.equals(creationDate, that.creationDate)
                && Objects.equals(deliveryDate, that.deliveryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, customer, tariff, orderStatus, creationDate, deliveryDate);
    }
}
