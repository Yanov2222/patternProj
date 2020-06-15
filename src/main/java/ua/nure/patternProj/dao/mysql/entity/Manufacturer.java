package ua.nure.patternProj.dao.mysql.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Manufacturer {
    private int id;
    private String name;

    public static  ManufacturerBuilder builder(){
        return new ManufacturerBuilder();
    }

    public static class ManufacturerBuilder{
        private Manufacturer manufacturer;

        private ManufacturerBuilder(){
            this.manufacturer = new Manufacturer();
        }

        public ManufacturerBuilder addId(int id){
            manufacturer.id =id;
            return this;
        }
        public ManufacturerBuilder addManufacturerName(String manufacturerName){
            manufacturer.name = manufacturerName;
            return this;
        }
        public Manufacturer build(){
            return manufacturer;
        }
    }
}
