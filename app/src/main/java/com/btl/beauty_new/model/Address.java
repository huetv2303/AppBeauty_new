package com.btl.beauty_new.model;

public class Address {
    private int idAddress;
    private int userID;
    private String NameRecipient;
    private String phone;
    private String building;
    private String gate;
    private String type_address;
    private String note;


    public Address(int idAddress, int userID, String nameRecipient, String phone, String building, String gate, String type_address, String note) {
        this.idAddress = idAddress;
        this.userID = userID;
        NameRecipient = nameRecipient;
        this.phone = phone;
        this.building = building;
        this.gate = gate;
        this.type_address = type_address;
        this.note = note;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getNameRecipient() {
        return NameRecipient;
    }

    public void setNameRecipient(String nameRecipient) {
        NameRecipient = nameRecipient;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getType_address() {
        return type_address;
    }

    public void setType_address(String type_address) {
        this.type_address = type_address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return
                ", NameRecipient='" + NameRecipient + "\n" +
                ", phone='" + phone + "\n" +
                ", building='" + building + "\n" +
                ", gate='" + gate + "\n" +
                ", type_address='" + type_address + '\'' +
                ", note='" + note + "\n" ;
    }
}
