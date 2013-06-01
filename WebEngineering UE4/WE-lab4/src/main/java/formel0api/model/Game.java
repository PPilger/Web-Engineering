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
package formel0api.model;

import formel0api.beans.MyResourceBundle;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Peter
 */
public class Game implements Serializable {
    private Player leader;
    
    private int round;
    
    private long spentTime;
	
	private String twitterStatus;
    
    private Player player1;
    private Player player2;

    public String getLeader() {
        if(leader == null) {
            return MyResourceBundle.getString("tableMultiple");
        }
        return leader.getName();
    }

    public Player getLeadingPlayer() {
        return leader == null ? player1 : leader;
    }

    public void setLeadingPlayer(Player leader) {
        this.leader = leader;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public long getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(long spentTime) {
        this.spentTime = spentTime;
    }

    public String getTime() {
        long time = spentTime;
        long sec = time % 60;
        long min = time / 60;

        return String.format("%02d:%02d", min, sec);
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
	
	public String getTwitterStatus() {
		return twitterStatus;
	}

	public void setTwitterStatus(String twitterStatus) {
		this.twitterStatus = twitterStatus;
	}
}
