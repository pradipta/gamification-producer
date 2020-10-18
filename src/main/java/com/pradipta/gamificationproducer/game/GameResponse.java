package com.pradipta.gamificationproducer.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResponse {
    private Integer totalScore;
    private Integer thisScore;
    private Integer level;

    public GameResponse(Integer totalScore, Integer thisScore, Integer level) {
        this.thisScore = thisScore;
        this.totalScore = totalScore;
        this.level = level;
    }
}
