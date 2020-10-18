package com.pradipta.gamificationproducer.models.score;

import com.pradipta.gamificationproducer.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public Optional<Score> findByUser(User user) {
        return Optional.ofNullable(scoreRepository.findByUser(user.getId()));
    }

    public Score save(Score score) {
        return scoreRepository.save(score);
    }
}
