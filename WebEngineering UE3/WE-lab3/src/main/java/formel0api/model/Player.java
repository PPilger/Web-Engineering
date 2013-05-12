/**
 * <copyright>
 *
 * Copyright (c) 2010 http://www.big.tuwien.ac.at All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * </copyright>
 */
package formel0api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a player playing in a {@link Game}.
 */
public class Player implements Serializable {
    private String name;
    private int lastResult;
    private int position;
    private int nextPosition;
    
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
        return nextPosition;
    }

    public void setNextPosition(int nextPosition) {
        this.nextPosition = Math.min(nextPosition, 6);
    }
}
