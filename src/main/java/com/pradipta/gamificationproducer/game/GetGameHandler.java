package com.pradipta.gamificationproducer.game;

import com.pradipta.gamificationproducer.models.score.Score;
import com.pradipta.gamificationproducer.models.score.ScoreService;
import com.pradipta.gamificationproducer.models.user.User;
import com.pradipta.gamificationproducer.models.user.UserRepository;
import com.pradipta.gamificationproducer.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GetGameHandler {
    private static final Logger logger = LoggerFactory.getLogger(GetGameHandler.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreService scoreService;

    public ResponseEntity<?> rollDice(UserPrincipal currentUser) {
        logger.info("User : {} is rolling a dice", currentUser.getEmail());
        Random rand = new Random();
        int scoreRandom = rand.nextInt(7);
        logger.info("User : {} has scored ");
        User user = userRepository.getOne(currentUser.getId());
        Score score = scoreService.findByUser(user).orElse(new Score());
        score.increaseScoreBy(scoreRandom);
        score.setUser(user);
        scoreService.save(score);
        GameResponse gameResponse = new GameResponse(scoreRandom, score.getScore(), score.getLevel());
        return ResponseEntity.ok(gameResponse);
    }
}
