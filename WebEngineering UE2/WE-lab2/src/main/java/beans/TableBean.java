package beans;

import java.util.Date;

/**
 *
 * @author Peter
 */
public class TableBean {

    private String leader;
    private int round;
    private Date startTime = new Date();
    private Player player1;
    private Player player2;

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
        Date currentTime = new Date();
        int time = (int) ((currentTime.getTime() - startTime.getTime()) / 1000);
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
