package com.swe573.socialhub.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Service {

        public Service() {

        }public Service(Long id, String header, String description, String location, LocalDateTime time, int minutes, int quota, User createdUser) {
                this.id = id;
                Header = header;
                Description = description;
                Location = location;
                Time = time;
                Minutes = minutes;
                Quota = quota;
                CreatedUser = createdUser;
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

        @ManyToOne
        @JoinColumn(name = "created_user_id")
        User CreatedUser;
        public User getCreatedUser() {
                return CreatedUser;
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
                CreatedUser = createdUser;
        }

        public int getQuota() {
                return Quota;
        }

        public void setQuota(int quota) {
                Quota = quota;
        }

        @Override
        public String toString() {
                return "Service{" + "id=" + this.id + ", header='" + this.Header + '\'' +'}';
        }
}
