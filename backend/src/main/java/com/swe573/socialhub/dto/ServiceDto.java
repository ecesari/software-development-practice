package com.swe573.socialhub.dto;

import com.swe573.socialhub.enums.ServiceStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class ServiceDto implements Serializable {
    private final Long id;
    private final String Header;
    private final String Description;
    private final String Location;
    private final LocalDateTime Time;
    private final int Minutes;
    private final int Quota;
    private final int AttendingUserCount;
    private final Long CreatedUserIdId;
    private final String CreatedUserName;
    private final Double Latitude;
    private final Double Longitude;
    private final List<TagDto> ServiceTags;
    private final ServiceStatus Status;
    private final String TimeString;
    private final Boolean ShowServiceOverButton;

    public ServiceDto(Long id, String header, String description, String location, LocalDateTime time, int minutes, int quota, int attendingUserCount, Long createdUserIdId, String createdUserName, Double latitude, Double longitude, List<TagDto> serviceTags, ServiceStatus status) {
        this.id = id;
        Header = header;
        Description = description;
        Location = location;
        Time = time;
        Minutes = minutes;
        Quota = quota;
        AttendingUserCount = attendingUserCount;
        CreatedUserIdId = createdUserIdId;
        CreatedUserName = createdUserName;
        Latitude = latitude;
        Longitude = longitude;
        ServiceTags = serviceTags;
        Status = status;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm");

        String formattedDateTime = time.format(formatter); // "1986-04-08 12:30"
        TimeString = formattedDateTime;
        ShowServiceOverButton = time.isBefore(LocalDateTime.now()) && status == ServiceStatus.ONGOING;
    }

    public Long getId() {
        return id;
    }

    public String getHeader() {
        return Header;
    }

    public String getDescription() {
        return Description;
    }

    public String getLocation() {
        return Location;
    }

    public LocalDateTime getTime() {
        return Time;
    }

    public int getMinutes() {
        return Minutes;
    }

    public int getQuota() {
        return Quota;
    }

    public Long getCreatedUserIdId() {
        return CreatedUserIdId;
    }

    public String getCreatedUserName() {
        return CreatedUserName;
    }

    public ServiceStatus getStatus() {
        return Status;
    }

    public String getTimeString() {
        return TimeString;
    }

    public Boolean getShowServiceOverButton() {
        return ShowServiceOverButton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDto entity = (ServiceDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.Header, entity.Header) &&
                Objects.equals(this.Description, entity.Description) &&
                Objects.equals(this.Location, entity.Location) &&
                Objects.equals(this.Time, entity.Time) &&
                Objects.equals(this.Minutes, entity.Minutes) &&
                Objects.equals(this.Quota, entity.Quota) &&
                Objects.equals(this.CreatedUserIdId, entity.CreatedUserIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Header, Description, Location, Time, Minutes, Quota, CreatedUserIdId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "Header = " + Header + ", " +
                "Description = " + Description + ", " +
                "Location = " + Location + ", " +
                "Time = " + Time + ", " +
                "Minutes = " + Minutes + ", " +
                "Quota = " + Quota + ", " +
                "CreatedUserIdId = " + CreatedUserIdId + ")";
    }


    public Double getLatitude() {
        return Latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public List<TagDto> getServiceTags() {
        return ServiceTags;
    }

    public int getAttendingUserCount() {
        return AttendingUserCount;
    }

}
