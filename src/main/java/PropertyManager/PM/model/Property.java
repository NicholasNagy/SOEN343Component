package PropertyManager.PM.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class Property {

    private final UUID id;
    private enum pets {SMALL, YES, NO};
    //private final boolean heatingIncluded;
    private final pets petsAllowed; //Are pets allowed? Small is for small animals only
    private final int parkingSpaces;
    //private final boolean laundry;
    private final int bedrooms;
    private final int bathrooms;
    private final String address; //Address maybe should be another object?

    public Property(@JsonProperty("petsAllowed") pets petsAllowed,
                    @JsonProperty("parkingSpaces") int parkingSpaces,
                    @JsonProperty("laundry") boolean laundry,
                    @JsonProperty("bedrooms") int bedrooms,
                    @JsonProperty("bathrooms") int bathrooms,
                    @JsonProperty("address") String address) {
        this.id = UUID.randomUUID();
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

    public String getAddress() {
        return address;
    }

    public UUID getId(){
        return id;
    }

}
