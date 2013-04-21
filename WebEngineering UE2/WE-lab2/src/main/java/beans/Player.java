package beans;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Peter
 */
public class Player {
    private String name="asdf";
    private int lastResult;
    private int position;
    
    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLastResult() {
        return lastResult;
    }

    public void setLastResult(int lastResult) {
        this.lastResult = lastResult;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = Math.min(position, 6);
    }
    
    public int getNextPosition() {
        return Math.min(position + lastResult, 6);
    }
}
