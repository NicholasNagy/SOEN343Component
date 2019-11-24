package PropertyManager.PM.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Apartment {

    private UUID id;
    private Property property;
    private boolean laundryIncluded;
    private boolean heatingIncluded;
    private boolean electricityIncluded;
    private boolean internetIncluded;
    private boolean furnished;
    private boolean airConditioning;
    private boolean smokersAccepted;

    public Apartment(@JsonProperty("property") Property property,
                     @JsonProperty("laundryIncluded") boolean laundryIncluded,
                     @JsonProperty("heatingIncluded") boolean heatingIncluded,
                     @JsonProperty("electricityIncluded") boolean electricityIncluded,
                     @JsonProperty("internetIncluded") boolean internetIncluded,
                     @JsonProperty("furnished") boolean furnished,
                     @JsonProperty("airConditioning") boolean airConditioning,
                     @JsonProperty("smokersAccepted") boolean smokersAccepted
                      ){
        this.id = UUID.randomUUID();
        this.property = property;
        this.laundryIncluded = laundryIncluded;
        this.heatingIncluded = heatingIncluded;
        this.electricityIncluded = electricityIncluded;
        this.internetIncluded = internetIncluded;
        this.furnished = furnished;
        this.airConditioning = airConditioning;
        this.smokersAccepted = smokersAccepted;
    }

    public Apartment(UUID id, Property property, boolean laundryIncluded, boolean heatingIncluded,
                     boolean electricityIncluded, boolean internetIncluded, boolean furnished,
                     boolean airConditioning, boolean smokersAccepted)
    {
        this.id = id;
        this.property = property;
        this.laundryIncluded = laundryIncluded;
        this.heatingIncluded = heatingIncluded;
        this.electricityIncluded = electricityIncluded;
        this.internetIncluded = internetIncluded;
        this.furnished = furnished;
        this.airConditioning = airConditioning;
        this.smokersAccepted = smokersAccepted;
    }

    public boolean isElectricityIncluded() {
        return electricityIncluded;
    }

    public boolean isHeatingIncluded() {
        return heatingIncluded;
    }

    public boolean isLaundryIncluded() {
        return laundryIncluded;
    }

    public UUID getId() {
        return id;
    }

    public boolean isInternetIncluded() {
        return internetIncluded;
    }

    public boolean isFurnished() {
        return furnished;
    }

    public boolean isAirConditioning() {
        return airConditioning;
    }

    public boolean isSmokersAccepted() {
        return smokersAccepted;
    }

    public Property getProperty() {
        return property;
    }
}
