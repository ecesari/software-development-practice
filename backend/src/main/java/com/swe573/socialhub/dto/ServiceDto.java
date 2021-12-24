package com.swe573.socialhub.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ServiceDto implements Serializable {
    private final Long id;
    private final String Header;
    private final String Description;
    private final String Location;
    private final LocalDateTime Time;
    private final int Minutes;
    private final int Quota;
    private final Long CreatedUserIdId;
    private final String CreatedUserName;

    public ServiceDto(Long id, String header, String description, String location, LocalDateTime time, int minutes, int quota, Long createdUserIdId, String createdUserName) {
        this.id = id;
        Header = header;
        Description = description;
        Location = location;
        Time = time;
        Minutes = minutes;
        Quota = quota;
        CreatedUserIdId = createdUserIdId;
        CreatedUserName = createdUserName;
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


}
