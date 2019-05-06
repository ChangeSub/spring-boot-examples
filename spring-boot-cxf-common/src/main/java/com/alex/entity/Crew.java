package com.alex.entity;

public class Crew {
    //  name,id_card_no idCard,MOBILE_PHONE phone
    private String name;
    private String idCard;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
