package com.yashshree.intuit.demo.Intuit.demo.entity;

import com.yashshree.intuit.demo.Intuit.demo.services.logic.LeaderBoardLogic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenerationTime;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "score_table")
public class ScoreBoard implements Comparable<ScoreBoard> {
    @Id
    private String username;
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    //@Column(name = "id", insertable = true)
    private int id;
    private int score;


    @Override
    public boolean equals(Object o) {
        return this.username.equals(((ScoreBoard)o).getUsername())
                && this.score == ((ScoreBoard)o).getScore();
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ScoreBoard that = (ScoreBoard) o;
//        return id == that.id && score == that.score && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, id, score);
    }

    public int compareTo(ScoreBoard s){
        if(this.score==s.getScore()){
            return this.username.compareTo(s.getUsername());
        }
        return Integer.compare(this.score,s.getScore());
    }
}
