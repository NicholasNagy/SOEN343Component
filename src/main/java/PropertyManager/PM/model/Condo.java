package PropertyManager.PM.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Condo {
    private UUID id;
    private Property property;
    private boolean elevatorIncluded;
    private boolean storageIncluded;
    private boolean outdoorAreasIncluded;
    private boolean gymIncluded;
    private boolean conciergeIncluded;
    private boolean airConditioning;
    private boolean buildingInsurance;

    public Condo(@JsonProperty("property") Property property,
                     @JsonProperty("elevatorIncluded") boolean elevatorIncluded,
                     @JsonProperty("storageIncluded") boolean storageIncluded,
                     @JsonProperty("outdoorAreasIncluded") boolean outdoorAreasIncluded,
                     @JsonProperty("gymIncluded") boolean gymIncluded,
                     @JsonProperty("conciergeIncluded") boolean conciergeIncluded,
                     @JsonProperty("airConditioning") boolean airConditioning,
                     @JsonProperty("buildingInsurance") boolean buildingInsurance
    ){
        this.id = UUID.randomUUID();
        this.property = property;
        this.elevatorIncluded = elevatorIncluded;
        this.storageIncluded = storageIncluded;
        this.outdoorAreasIncluded = outdoorAreasIncluded;
        this.gymIncluded = gymIncluded;
        this.conciergeIncluded = conciergeIncluded;
        this.airConditioning = airConditioning;
        this.buildingInsurance = buildingInsurance;
    }

    public Condo(UUID id, Property property, boolean elevatorIncluded, boolean storageIncluded,
                 boolean outdoorAreasIncluded, boolean gymIncluded, boolean conciergeIncluded,
                 boolean airConditioning, boolean buildingInsurance)
    {
        this.id = id;
        this.property = property;
        this.elevatorIncluded = elevatorIncluded;
        this.storageIncluded = storageIncluded;
        this.outdoorAreasIncluded = outdoorAreasIncluded;
        this.gymIncluded = gymIncluded;
        this.conciergeIncluded = conciergeIncluded;
        this.airConditioning = airConditioning;
        this.buildingInsurance = buildingInsurance;
    }

    public boolean isOutdoorAreasIncluded() {
        return outdoorAreasIncluded;
    }

    public boolean isStorageIncluded() {
        return storageIncluded;
    }

    public boolean isElevatorIncluded() {
        return elevatorIncluded;
    }

    public UUID getId() {
        return id;
    }

    public boolean isGymIncluded() {
        return gymIncluded;
    }

    public boolean isConciergeIncluded() {
        return conciergeIncluded;
    }

    public boolean isAirConditioning() {
        return airConditioning;
    }

    public boolean isBuildingInsurance() {
        return buildingInsurance;
    }

    public Property getProperty() {
        return property;
    }
}
