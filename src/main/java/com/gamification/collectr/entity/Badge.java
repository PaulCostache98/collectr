package com.gamification.collectr.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String badgeName;

    @ManyToMany(mappedBy = "badges")
    @JsonIgnore
    private Set<MyUser> users;

    @Column(name = "image_source")
    private String imgSource;

    public Badge(Badge badge) {
        this.id = badge.getId();
        this.badgeName = badge.getBadgeName();
        this.users = badge.getUsers();
        this.imgSource = badge.getImgSource();
    }


}
