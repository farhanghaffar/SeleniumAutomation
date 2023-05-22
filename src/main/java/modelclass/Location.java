package modelclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    private String locationName;
    private String yelpName;
    private String tripadvisiorName;
    private String city;
    private String postalCode;
    private String country;

    @JsonProperty("locationName")
    public String getLocationName() { return locationName; }
    @JsonProperty("locationName")
    public void setLocationName(String value) { this.locationName = value; }

    @JsonProperty("yelpName")
    public String getYelpName() { return yelpName; }
    @JsonProperty("yelpName")
    public void setYelpName(String value) { this.yelpName = value; }

    @JsonProperty("tripadvisiorName")
    public String getTripadvisiorName() { return tripadvisiorName; }
    @JsonProperty("tripadvisiorName")
    public void setTripadvisiorName(String value) { this.tripadvisiorName = value; }

    @JsonProperty("city")
    public String getCity() { return city; }
    @JsonProperty("city")
    public void setCity(String value) { this.city = value; }

    @JsonProperty("postalCode")
    public String getPostalCode() { return postalCode; }
    @JsonProperty("postalCode")
    public void setPostalCode(String value) { this.postalCode = value; }

    @JsonProperty("country")
    public String getCountry() { return country; }
    @JsonProperty("country")
    public void setCountry(String value) { this.country = value; }
}

