package com.swe573.socialhub.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    public User(Long id, String username, String email, String bio, Set<Tag> tags)
    {
        this.id = id;
        this.bio = bio;
        this.username = username;
        this.email = email;
        this.tags = tags;
    }

    public User() {

    }


    private @Id
    @GeneratedValue
    Long id;
    private String username;
    private String email;
    private String bio;
    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "user_tags",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    private Set<Tag> tags;

    @OneToMany(mappedBy = "createdUser")
    private Set<Service> services;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    public void addTag(Tag tag) {
        if (this.tags == null)
        {
            this.tags =  new HashSet<Tag>();
        }
        this.tags.add(tag);
    }


    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }


    @Override
    public String toString() {
        return "User{" + "id=" + this.id + ", username='" + this.username + '\'' +'}';
    }
}
