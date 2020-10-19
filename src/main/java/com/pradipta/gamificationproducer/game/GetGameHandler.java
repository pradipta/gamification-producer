package com.pradipta.gamificationproducer.game;

import com.pradipta.gamificationproducer.models.kafka.KafkaUserLevelMessage;
import com.pradipta.gamificationproducer.models.score.Score;
import com.pradipta.gamificationproducer.models.score.ScoreService;
import com.pradipta.gamificationproducer.models.user.User;
import com.pradipta.gamificationproducer.models.user.UserRepository;
import com.pradipta.gamificationproducer.security.UserPrincipal;
import com.pradipta.gamificationproducer.trial.entities.user.TrialUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GetGameHandler {
    private static final Logger logger = LoggerFactory.getLogger(GetGameHandler.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private KafkaTemplate<String, KafkaUserLevelMessage> kafkaTemplateUserLevelMessage;
    private static final String userlevelMessage = "user-level-message";

    public ResponseEntity<?> rollDice(UserPrincipal currentUser) {
        logger.info("User : {} is rolling a dice", currentUser.getEmail());
        Random rand = new Random();
        int scoreRandom = rand.nextInt(7);
        logger.info("User : {} has scored ");
        User user = userRepository.getOne(currentUser.getId());
        Score score = updateScores(user, scoreRandom);
        GameResponse gameResponse = new GameResponse(scoreRandom, score.getScore(), score.getLevel());
        return ResponseEntity.ok(gameResponse);
    }

    private Score updateScores(User user, int scoreRandom) {
        Score score = scoreService.findByUser(user).orElse(new Score());
        int initialLevel = score.getLevel();
        score.increaseScoreBy(scoreRandom);
        int finalLevel = score.getLevel();
        if (finalLevel == 10 && score.getScore() > 100) {
            score.setScore(100);
        }
        score.setUser(user);
        if (finalLevel != initialLevel) {
            sendKafkaMessage(score);
        }
        return scoreService.save(score);
    }

    private void sendKafkaMessage(Score score) {
        KafkaUserLevelMessage message = new KafkaUserLevelMessage();
        message.setEmail(score.getUser().getEmail());
        message.setLevel(score.getLevel());
        //Since my total score is 100 [so, no calculation logic]
        message.setCompletion(score.getScore());
        kafkaTemplateUserLevelMessage.send(userlevelMessage, message);
    }
}
