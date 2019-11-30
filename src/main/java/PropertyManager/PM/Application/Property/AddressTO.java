package PropertyManager.PM.Application.Property;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AddressTO {

    private UUID id;
    private int address;
    private String street;
    private String city;
    private String province;
    private String country;
    private String postalCode;

    public AddressTO(@JsonProperty("address") int address,
                     @JsonProperty("street") String street,
                     @JsonProperty("city") String city,
                     @JsonProperty("province") String province,
                     @JsonProperty("country") String country,
                     @JsonProperty("postalcode") String postalCode) {
        this.id = UUID.randomUUID();
        this.address = address;
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
    }

    public AddressTO(UUID id, int address,
                     String street,
                     String city,
                     String province,
                     String country,
                     String postalCode) {
        this.id = id;
        this.address = address;
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
    }

    public UUID getId(){
        return id;
    }

    public int getAddress(){
        return  address;
    }

    public String getStreet(){
        return street;
    }

    public String getCity(){
        return  city;
    }

    public String getProvince(){
        return  province;
    }

    public String getCountry(){
        return country;
    }

    public String getPostalCode(){
        return postalCode;
    }
}
