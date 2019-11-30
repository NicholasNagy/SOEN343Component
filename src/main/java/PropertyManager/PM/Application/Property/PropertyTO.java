package PropertyManager.PM.Application.Property;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class PropertyTO {

    private final UUID id;
    public boolean petsAllowed;
    private final int parkingSpaces;
    private final int bedrooms;
    private final int bathrooms;
    private final AddressTO address;

    public enum propertyType {APPARTMENT, CONDO, HOUSE};
    private propertyType type;
    private UUID propertyID;
    private float price;

    public PropertyTO(@JsonProperty("petsAllowed") boolean petsAllowed,
                      @JsonProperty("parkingSpaces") int parkingSpaces,
                      @JsonProperty("bedrooms") int bedrooms,
                      @JsonProperty("bathrooms") int bathrooms,
                      @JsonProperty("address") AddressTO address,
                      @JsonProperty("propertyType") propertyType propertyType,
                      @JsonProperty("price") float price) {
        this.id = UUID.randomUUID();
        this.petsAllowed = petsAllowed;
        this.parkingSpaces = parkingSpaces;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.address = address;

        type = propertyType;
        this.propertyID = UUID.randomUUID();
        this.price = price;

    }
    public PropertyTO(UUID id,
                      boolean petsAllowed,
                      int parkingSpaces,
                      int bedrooms,
                      int bathrooms,
                      AddressTO address,
                      propertyType propertyType,
                      UUID propertyID,
                      float price) {
        this.id = id;
        this.petsAllowed = petsAllowed;
        this.parkingSpaces = parkingSpaces;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.address = address;

        type = propertyType;
        this.propertyID = propertyID;
        this.price = price;
    }

    public boolean getPetsAllowed(){
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

    public AddressTO getAddress() {
        return address;
    }

    public UUID getId(){
        return id;
    }

    public propertyType getPropertyType(){
        return this.type;
    }

    public UUID getPropertyID(){
        return this.propertyID;
    }

    public void setPropertyID(UUID propertyID) { this.propertyID = propertyID; }

    public float getPrice(){
        return this.price;
    }

}
