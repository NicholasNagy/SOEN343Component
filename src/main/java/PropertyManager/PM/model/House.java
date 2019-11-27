package PropertyManager.PM.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class House {

    private UUID id;
    private Property property;
    private boolean transitFriendly;
    private boolean privateBackyardIncluded;
    private boolean poolIncluded;
    private boolean basementIncluded;
    private boolean pedestrianFriendly;
    private int yearBuilt;


    public House(@JsonProperty("property") Property property,
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

    public House(UUID id, Property property, boolean transitFriendly, boolean privateBackyardIncluded,
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



    public Property getProperty() {
        return property;
    }
}
