package com.yashshree.intuit.demo.Intuit.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "score_table")
public class ScoreBoard implements Comparable<ScoreBoard> {
    @Id
    private String username;
    private int id;
    private int score;


    @Override
    public boolean equals(Object o) {
        return this.username.equals(((ScoreBoard) o).getUsername())
                && this.score == ((ScoreBoard) o).getScore();
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, id, score);
    }

    public int compareTo(ScoreBoard s) {
        if (this.score == s.getScore()) {
            return this.username.compareTo(s.getUsername());
        }
        return Integer.compare(this.score, s.getScore());
    }
}
