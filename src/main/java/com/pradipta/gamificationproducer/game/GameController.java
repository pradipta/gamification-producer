package com.pradipta.gamificationproducer.game;

import com.pradipta.gamificationproducer.security.CurrentUser;
import com.pradipta.gamificationproducer.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {
    @Autowired
    private GetGameHandler getGameHandler;

    @GetMapping("/roll")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> rollDice(@CurrentUser UserPrincipal currentUser) {
        return getGameHandler.rollDice(currentUser);
    }
}
