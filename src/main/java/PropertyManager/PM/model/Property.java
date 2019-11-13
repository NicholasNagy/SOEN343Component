package PropertyManager.PM.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class Property {

    private final UUID id;
    public enum pets {SMALL, YES, NO};
    //private final boolean heatingIncluded;
    private final pets petsAllowed; //Are pets allowed? Small is for small animals only
    private final int parkingSpaces;
    //private final boolean laundry;
    private final int bedrooms;
    private final int bathrooms;
    private final Address address; //Address maybe should be another object?

    public Property(@JsonProperty("petsAllowed") pets petsAllowed,
                    @JsonProperty("parkingSpaces") int parkingSpaces,
                    @JsonProperty("bedrooms") int bedrooms,
                    @JsonProperty("bathrooms") int bathrooms,
                    @JsonProperty("address") Address address) {
        this.id = UUID.randomUUID();
        this.petsAllowed = petsAllowed;
        this.parkingSpaces = parkingSpaces;

        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.address = address;
    }
    public Property(UUID id,
                    pets petsAllowed,
                    int parkingSpaces,
                    int bedrooms,
                    int bathrooms,
                    Address address) {
        this.id = id;
        this.petsAllowed = petsAllowed;
        this.parkingSpaces = parkingSpaces;

        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.address = address;
    }

    public pets getPetsAllowed(){
        return petsAllowed;
    }

    public int getParkingSpaces(){
        return parkingSpaces;
    }

    public int getBedrooms(){
        return bedrooms;
    }

    public int getBathrooms(){
        return bathrooms;
    }

    public Address getAddress() {
        return address;
    }

    public UUID getId(){
        return id;
    }

}
