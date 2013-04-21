package beans;

import java.text.DateFormat;
import java.util.Calendar;

/**
 *
 * @author Peter
 */
public class TableBean {
    private String leader="Julia";
    private int round = 2;
    private int time = 100;
    
    private Player player1=new Player("Julia");
    private Player player2 = new Player("Christian");

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getTime() {
        int sec = time % 60;
        int min = time / 60;
        
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
}
