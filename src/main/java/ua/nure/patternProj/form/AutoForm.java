package ua.nure.patternProj.form;


import lombok.Data;

@Data
public class AutoForm {
    private int id;
    private int seats;
    private String model;
    private int price;
    private boolean hasBabySeat;
    private boolean hasConditioner;
    private boolean hasBar;
    private String manufacturer;
    private int manufacturerId;
}
