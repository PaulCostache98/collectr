package com.gamification.collectr.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name= "type")
    private String type;

    @OneToMany(mappedBy = "game")
    @ToString.Exclude
    private Set<Badge> badges;

    @Column(name = "image")
    private String imgSource;

    public Game(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.type = game.getType();
        this.imgSource = game.getImgSource();
        this.badges = game.getBadges();
    }

}
