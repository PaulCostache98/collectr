package com.gamification.collectr.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

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
    private List<Integer> steps;

    @Column
    private Integer defaultSteps;

    @Column
    private Integer cost;

    public Badge(Badge badge) {
        this.id = badge.getId();
        this.badgeName = badge.getBadgeName();
        this.users = badge.getUsers();
        this.imgSource = badge.getImgSource();
        this.game = badge.getGame();
        this.steps = badge.getSteps();
        this.defaultSteps = badge.getDefaultSteps();
        this.cost = badge.getCost();
    }

    public HashSet<MyUser> getUsers() {
        List<MyUser> userTemp = new ArrayList<>();
        if(this.users != null) {
            userTemp.addAll(List.copyOf(this.users));
        }
        if(this.users == null) {
            return new HashSet<>();
        }
        userTemp.sort(Comparator.comparing(MyUser::getId));
        return new HashSet<>(userTemp);
    }

    public void setUsers(Set<MyUser> users) {
        List<MyUser> userTemp = new ArrayList<>(List.copyOf(users));
        userTemp.sort(Comparator.comparing(MyUser::getId));
        this.users = new HashSet<>(userTemp);
    }


}
