package com.gamification.collectr.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
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
    @ToString.Exclude
    private Set<MyUser> users;

    @Column(name = "image_source")
    private String imgSource;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column
    private Integer steps;

    @Column
    private Integer defaultSteps;

    public Badge(Badge badge) {
        this.id = badge.getId();
        this.badgeName = badge.getBadgeName();
        this.users = badge.getUsers();
        this.imgSource = badge.getImgSource();
        this.game = badge.getGame();
        this.steps = badge.getSteps();
        this.defaultSteps = badge.getDefaultSteps();
    }


}
