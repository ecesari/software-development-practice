package com.swe573.socialhub.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Service {

    public Service() {

    }

    public Service(Long id, String header, String description, String location, LocalDateTime time, int minutes, int quota,int attendingUserCount, User createdUser, Double latitude,Double longitude) {
        this.id = id;
        Header = header;
        Description = description;
        Location = location;
        Time = time;
        Minutes = minutes;
        Quota = quota;
        this.createdUser = createdUser;
        Latitude = latitude;
        Longitude = longitude;
        AttendingUserCount = attendingUserCount;
    }

    private @Id
    @GeneratedValue
    Long id;
    String Header;
    String Description;
    String Location;
    LocalDateTime Time;
    int Minutes;
    int Quota;
    int AttendingUserCount;
    Double Latitude;
    Double Longitude;
    @ManyToOne
    @JoinColumn(name = "createdUser")
    User createdUser;
    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "service_tags",
            joinColumns = { @JoinColumn(name = "service_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    Set<Tag> ServiceTags;
    @OneToMany(mappedBy = "service")
    Set<UserServiceApproval> approvalSet;
    public User getCreatedUser() {
        return createdUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public LocalDateTime getTime() {
        return Time;
    }

    public void setTime(LocalDateTime time) {
        Time = time;
    }

    public int getMinutes() {
        return Minutes;
    }

    public void setMinutes(int minutes) {
        Minutes = minutes;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public int getQuota() {
        return Quota;
    }

    public void setQuota(int quota) {
        Quota = quota;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Set<Tag> getServiceTags() {
        return ServiceTags;
    }

    public void setServiceTags(Set<Tag> serviceTags) {
        ServiceTags = serviceTags;
    }

    public int getAttendingUserCount() {
        return AttendingUserCount;
    }

    public void setAttendingUserCount(int attendingUserCount) {
        AttendingUserCount = attendingUserCount;
    }

    public Set<UserServiceApproval> getApprovalSet() {
        return approvalSet;
    }

    public void setApprovalSet(Set<UserServiceApproval> approvalSet) {
        this.approvalSet = approvalSet;
    }


    @Override
    public String toString() {
        return "Service{" + "id=" + this.id + ", header='" + this.Header + '\'' + '}';
    }

    public void addTag(Tag tag) {
        if (this.ServiceTags == null)
        {
            this.ServiceTags =  new HashSet<Tag>();
        }
        this.ServiceTags.add(tag);
    }
}
