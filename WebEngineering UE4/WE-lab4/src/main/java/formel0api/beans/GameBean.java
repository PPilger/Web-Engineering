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

import com.google.api.client.util.DateTime;
import formel0api.model.*;
import formel0api.webservices.HighscoreService;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import tuwien.big.formel0.twitter.TwitterStatusMessage;

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
	private UserData user;

	public GameBean(UserData user) {
		this.user = user;
		init();
	}

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
			game.setPlayer1(new Player(user));
		}
		game.setPlayer2(new Player("Super C"));
		newGame(null);
		game.setTwitterStatus("");
		
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
			game.setLeadingPlayer(game.getPlayer1());
		} else if (game.getPlayer1().getNextPosition() < game.getPlayer2().getNextPosition()) {
			game.setLeadingPlayer(game.getPlayer2());
		} else {
			game.setLeadingPlayer(null);
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
		game.setTwitterStatus("");
		
		refresh();
	}

	public void rollDice(ActionEvent ae) {
		game.getPlayer1().setPosition(game.getPlayer1().getNextPosition());
		game.getPlayer2().setPosition(game.getPlayer2().getNextPosition());

		if (!gameOver) {
			movePlayer(game.getPlayer1());
			movePlayer(game.getPlayer2());
			game.setSpentTime((System.currentTimeMillis() - startTime) / 1000);
			game.setRound(game.getRound() + 1);

			if (gameOver) {
				//Am Highscore-Board posten
                                HighscoreService highscoreService = new HighscoreService();
				String UUID = highscoreService.postHighscore(game);
				
				//Auf Twitter posten
				TwitterStatusMessage twitter = new TwitterStatusMessage(game.getLeader(), UUID, new Date());
				boolean bSucessfull = twitter.postOnTwitter();
				
				if(bSucessfull == true) {
					//Erfolgreich auf Twitter gepostet
					game.setTwitterStatus("UUID " + UUID + " " + MyResourceBundle.getString("twitterOK"));
					System.out.println("Twitter Post successful");
				} else {
					//Nicht erfolgreich auf Twitter gepostet
					game.setTwitterStatus(MyResourceBundle.getString("twitterFail"));
					System.out.println("Twitter fehler");
				}
			}

			refresh();
		}
	}

	public String getPlayerResultAsString() {
		int num = game.getPlayer1().getLastResult();

		switch (num) {
			case 0:
				return MyResourceBundle.getString("tableCubeNumber0");
			case 1:
				return MyResourceBundle.getString("tableCubeNumber1");
			case 2:
				return MyResourceBundle.getString("tableCubeNumber2");
			case 3:
				return MyResourceBundle.getString("tableCubeNumber3");
			default:
				return Integer.toString(num);
		}
	}
}
