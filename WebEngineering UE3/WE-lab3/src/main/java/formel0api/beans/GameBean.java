/*
 * Copyright 2013 Peter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package formel0api.beans;

import formel0api.model.*;
import java.io.Serializable;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "gameBean")
@SessionScoped
public class GameBean implements Serializable {

    private static final int LAST_FIELD = 6;
    private Random random = new Random();
    private Game game = new Game();
    private boolean gameOver = false;
    private long startTime = System.currentTimeMillis();
    //@ManagedProperty(value = "#{user}")
    private User user;

    public GameBean(User user) {
        this.user = user;
        init();
    }
    
    /*public void setUser(User user) {
        this.user = user;
        init();
    }*/

    public Game getGame() {
        return game;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }

    public void init() {
        if (user == null) {
            game.setPlayer1(new Player("Super Mario"));
        } else {
            game.setPlayer1(new Player(user.getFirstname() + " " + user.getLastname()));
        }
        game.setPlayer2(new Player("Super C"));
        newGame(null);
        refresh();
    }

    public String logout() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.getExternalContext().getSessionMap().containsKey("gameBean")) {
            fc.getExternalContext().getSessionMap().remove("gameBean");
        }
        if (fc.getExternalContext().getSessionMap().containsKey("user")) {
            fc.getExternalContext().getSessionMap().remove("user");
        }
        return "index.xhtml";
    }

    private void refresh() {
        if (game.getPlayer1().getNextPosition() > game.getPlayer2().getNextPosition()) {
            game.setLeader(game.getPlayer1().getName());
        } else if (game.getPlayer1().getNextPosition() < game.getPlayer2().getNextPosition()) {
            game.setLeader(game.getPlayer2().getName());
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle rb = ResourceBundle.getBundle("i18n", context.getExternalContext().getRequestLocale());
            
            game.setLeader(rb.getString("tableMultiple"));
        }
    }

    private void resetPlayer(Player player) {
        player.setPosition(0);
        player.setLastResult(0);
        player.setNextPosition(0);
    }

    private void movePlayer(Player player) {
        player.setLastResult(random.nextInt(3) + 1);
        player.setNextPosition(Math.min(player.getPosition() + player.getLastResult(), LAST_FIELD));

        if (isOil(player.getNextPosition())) {
            player.setNextPosition(0);
        }

        if (player.getNextPosition() == LAST_FIELD) {
            gameOver = true;
        }
    }

    private boolean isOil(int position) {
        return position == 2 || position == 5;
    }

    public void newGame(ActionEvent ae) {
        resetPlayer(game.getPlayer1());
        resetPlayer(game.getPlayer2());
        game.setRound(1);
        game.setSpentTime(0);
        startTime = System.currentTimeMillis();
        gameOver = false;
        
        refresh();
    }

    public void rollDice(ActionEvent ae) {
        game.getPlayer1().setPosition(game.getPlayer1().getNextPosition());
        game.getPlayer2().setPosition(game.getPlayer2().getNextPosition());

        if (!gameOver) {
            movePlayer(game.getPlayer1());
            movePlayer(game.getPlayer2());
            game.setSpentTime((System.currentTimeMillis() - startTime)/1000);
            game.setRound(game.getRound() + 1);
            
            refresh();
        }
    }

    public String getPlayerResultAsString() {
        int num = game.getPlayer1().getLastResult();
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle rb = ResourceBundle.getBundle("i18n", context.getExternalContext().getRequestLocale());
        
        switch (num) {
            case 0:
                return rb.getString("tableCubeNumber0");
            case 1:
                return rb.getString("tableCubeNumber1");
            case 2:
                return rb.getString("tableCubeNumber2");
            case 3:
                return rb.getString("tableCubeNumber3");
            default:
                return Integer.toString(num);
        }
    }
}
