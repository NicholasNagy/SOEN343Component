package PropertyManager.PM.Application.House;

import PropertyManager.PM.Application.Property.PropertyTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class HouseTO {

    private UUID id;
    private PropertyTO property;
    private boolean transitFriendly;
    private boolean privateBackyardIncluded;
    private boolean poolIncluded;
    private boolean basementIncluded;
    private boolean pedestrianFriendly;
    private int yearBuilt;


    public HouseTO(@JsonProperty("property") PropertyTO property,
                   @JsonProperty("transitFriendly") boolean transitFriendly,
                   @JsonProperty("privateBackyardIncluded") boolean privateBackyardIncluded,
                   @JsonProperty("poolIncluded") boolean poolIncluded,
                   @JsonProperty("basementIncluded") boolean basementIncluded,
                   @JsonProperty("pedestrianFriendly") boolean pedestrianFriendly,
                   @JsonProperty("yearBuilt") int yearBuilt

    ){
        this.id = UUID.randomUUID();
        this.property = property;
        this.transitFriendly = transitFriendly;
        this.privateBackyardIncluded = privateBackyardIncluded;
        this.poolIncluded = poolIncluded;
        this.basementIncluded = basementIncluded;
        this.pedestrianFriendly = pedestrianFriendly;
        this.yearBuilt = yearBuilt;

    }

    public HouseTO(UUID id, PropertyTO property, boolean transitFriendly, boolean privateBackyardIncluded,
                   boolean poolIncluded, boolean basementIncluded, boolean pedestrianFriendly,
                   int yearBuilt)
    {
        this.id = id;
        this.property = property;
        this.transitFriendly = transitFriendly;
        this.privateBackyardIncluded = privateBackyardIncluded;
        this.poolIncluded = poolIncluded;
        this.basementIncluded = basementIncluded;
        this.pedestrianFriendly = pedestrianFriendly;
        this.yearBuilt = yearBuilt;

    }

    public boolean isPoolIncluded() {
        return poolIncluded;
    }

    public boolean isPrivateBackyardIncluded() {
        return privateBackyardIncluded;
    }

    public boolean isTransitFriendly() {
        return transitFriendly;
    }

    public UUID getId() {
        return id;
    }

    public boolean isBasementIncluded() {
        return basementIncluded;
    }

    public boolean isPedestrianFriendly() {
        return pedestrianFriendly;
    }

    public int getYearBuilt() {
        return yearBuilt;
    }



    public PropertyTO getProperty() {
        return property;
    }
}
