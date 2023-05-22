package modelclass;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Review implements Serializable{
    private String companyName;
    private long rating;
    private Location location;
    private String status;
    private Object challengeStatus;
    private String fullName;
    private String merchantFullname;
    private String challengeText;
    private String flagIssue;
    private String gmbEmail;
    private UUID id;
    private String site;
    private String feedback;
    private String datediff;

    @JsonProperty("companyName")
    public String getCompanyName() { return companyName; }
    @JsonProperty("companyName")
    public void setCompanyName(String value) { this.companyName = value; }

    @JsonProperty("rating")
    public long getRating() { return rating; }
    @JsonProperty("rating")
    public void setRating(long value) { this.rating = value; }

    @JsonProperty("location")
    public Location getLocation() { return location; }
    @JsonProperty("location")
    public void setLocation(Location value) { this.location = value; }

    @JsonProperty("status")
    public String getStatus() { return status; }
    @JsonProperty("status")
    public void setStatus(String value) { this.status = value; }

    @JsonProperty("challengeStatus")
    public Object getChallengeStatus() { return challengeStatus; }
    @JsonProperty("challengeStatus")
    public void setChallengeStatus(Object value) { this.challengeStatus = value; }

    @JsonProperty("fullName")
    public String getFullName() { return fullName; }
    @JsonProperty("fullName")
    public void setFullName(String value) { this.fullName = value; }

    @JsonProperty("merchantFullname")
    public String getMerchantFullname() { return merchantFullname; }
    @JsonProperty("merchantFullname")
    public void setMerchantFullname(String value) { this.merchantFullname = value; }

    @JsonProperty("challengeText")
    public String getChallengeText() { return challengeText; }
    @JsonProperty("challengeText")
    public void setChallengeText(String value) { this.challengeText = value; }

    @JsonProperty("flag_issue")
    public String getFlagIssue() { return flagIssue; }
    @JsonProperty("flag_issue")
    public void setFlagIssue(String value) { this.flagIssue = value; }

    @JsonProperty("gmbEmail")
    public String getGmbEmail() { return gmbEmail; }
    @JsonProperty("gmbEmail")
    public void setGmbEmail(String value) { this.gmbEmail = value; }

    @JsonProperty("id")
    public UUID getID() { return id; }
    @JsonProperty("id")
    public void setID(UUID value) { this.id = value; }

    @JsonProperty("site")
    public String getSite() { return site; }
    @JsonProperty("site")
    public void setSite(String value) { this.site = value; }

    @JsonProperty("feedback")
    public String getFeedback() { return feedback; }
    @JsonProperty("feedback")
    public void setFeedback(String value) { this.feedback = value; }

    @JsonProperty("datediff")
    public String getDatediff() { return datediff; }
    @JsonProperty("datediff")
    public void setDatediff(String value) { this.datediff = value; }
}

