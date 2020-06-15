package ua.nure.patternProj.dao.mysql.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Auto {
    private int id;
    private int seats;
    private String model;
    private int price;
    private int hasBabySeat;
    private int hasConditioner;
    private int hasBar;
    private int manufacturerId;
    private String uuid;


    public static AutoBuilder builder(){
        return new AutoBuilder();
    }

    public static class AutoBuilder {
        private Auto auto;

        private AutoBuilder(){
            this.auto = new Auto();
        }

        public AutoBuilder addId(int id) {
            auto.id = id;
            return this;
        }
        public AutoBuilder addSeats(int seats){
            auto.seats = seats;
            return this;
        }
        public AutoBuilder addModel(String model) {
            auto.model = model;
            return this;
        }
        public AutoBuilder addPrice(int price){
            auto.price=price;
            return this;
        }
        public AutoBuilder addBabySeat(int i){
            auto.hasBabySeat = i;
            return this;
        }
        public AutoBuilder addConditioner(int i){
            auto.hasConditioner = i;
            return this;
        }
        public AutoBuilder addBar(int i){
            auto.hasBar =i;
            return this;
        }
        public AutoBuilder addManufacturerId(int manufacturerId){
            auto.manufacturerId = manufacturerId;
            return this;
        }
        public Auto build(){
            return auto;
        }
    }

    public Memento save(){
        return new Memento(this);
    }

    public void restore(Memento memento){
        this.id = memento.getAuto().getId();
        this.price = memento.getAuto().getPrice();
        this.model = memento.getAuto().getModel();
        this.seats = memento.getAuto().getSeats();
        this.hasBabySeat = memento.getAuto().getHasBabySeat();
        this.hasBar = memento.getAuto().getHasBar();
        this.hasConditioner = memento.getAuto().getHasConditioner();
        this.manufacturerId = memento.getAuto().getManufacturerId();
    }

    public class Memento{
        private Date date;
        private Auto auto;

        private Memento(Auto obj){
            this.auto =obj;
            this.date = new Date();
        }

        public Auto getAuto(){
            return auto;
        }

    }

}
