package com.pradipta.gamificationproducer.models.score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Query("SELECT v FROM Score v WHERE v.user.id = :userId")
    Score findByUser(@Param("userId") Long userId);
}
