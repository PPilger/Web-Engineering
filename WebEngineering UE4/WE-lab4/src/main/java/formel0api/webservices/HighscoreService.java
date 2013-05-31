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
package formel0api.webservices;

import formel0api.beans.MyResourceBundle;
import formel0api.model.Game;
import formel0api.model.Player;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

/**
 *
 * @author Peter
 */
public class HighscoreService implements Serializable {

    private SOAPConnectionFactory connectionFactory;
    private MessageFactory messageFactory;
    private static String url = "http://playground.big.tuwien.ac.at:8080/highscore/PublishHighScoreService";
    private static String dataNS = "http://big.tuwien.ac.at/we/highscore/data";
    private static String tourNS = "http://www.dbai.tuwien.ac.at/education/ssd/SS13/uebung/Tournament";
    private static DateFormat xmlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat xmlDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public HighscoreService() {
        try {
            connectionFactory = SOAPConnectionFactory.newInstance();
            messageFactory = MessageFactory.newInstance();
        } catch (SOAPException ex) {
            ex.printStackTrace();
            throw new WebserviceException("failed to initialize soap connection", ex);
        } catch (UnsupportedOperationException ex) {
            ex.printStackTrace();
            throw new WebserviceException("failed to initialize soap connection", ex);
        }
    }

    public boolean postHighscore(Game game) {
        SOAPConnection con = null;
        try {
            con = connectionFactory.createConnection();
            SOAPMessage msg = messageFactory.createMessage();

            SOAPPart soapPart = msg.getSOAPPart();
            SOAPEnvelope env = soapPart.getEnvelope();
            SOAPBody body = env.getBody();

            fillMessageBody(body, game);
            
            try {
                System.out.println();
                System.out.println("SOAP Message:");
                msg.writeTo(System.out);
                System.out.println();
                System.out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //send message
            SOAPMessage reply = con.call(msg, url);

            try {
                System.out.println();
                System.out.println("SOAP Reply:");
                reply.writeTo(System.out);
                System.out.println();
                System.out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return true;
        } catch (SOAPException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SOAPException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return false;
    }

    private void fillMessageBody(SOAPBody body, Game data) throws SOAPException {
        SOAPElement highScoreRequest = body.addChildElement(new QName(dataNS, "HighScoreRequest", "data"));
        SOAPElement userKey = highScoreRequest.addChildElement(new QName(dataNS, "UserKey", "data"));
        userKey.setValue("34EphAp2C4ebaswu");

        SOAPElement tournament = highScoreRequest.addChildElement(new QName(tourNS, "tournament", "tour"));
        String date = xmlDateFormat.format(new Date());
        String dateTime = xmlDateTimeFormat.format(new Date());
        tournament.setAttribute("start-date", date);
        tournament.setAttribute("end-date", date);
        tournament.setAttribute("registration-deadline", dateTime);

        //players
        {
            SOAPElement players = tournament.addChildElement(new QName(tourNS, "players", "tour"));
            fillPlayer(players, data.getPlayer1());
        }

        //rounds
        {
            SOAPElement rounds = tournament.addChildElement(new QName(tourNS, "rounds", "tour"));

            SOAPElement round = rounds.addChildElement(new QName(tourNS, "round", "tour"));
            round.setAttribute("number", "0");

            SOAPElement game = round.addChildElement(new QName(tourNS, "game", "tour"));
            game.setAttribute("date", date);
            game.setAttribute("status", "finished");
            game.setAttribute("duration", Long.toString(data.getSpentTime()));
            if (data.getLeadingPlayer() == data.getPlayer1()) {
                game.setAttribute("winner", data.getLeadingPlayer().getName());
            } else {
                game.setAttribute("winner", "Computer");
            }

            // players
            SOAPElement players = game.addChildElement(new QName(tourNS, "players", "tour"));
            SOAPElement player = players.addChildElement(new QName(tourNS, "player", "tour"));
            player.setAttribute("ref", data.getPlayer1().getName());

            /* game-history (optional)
             SOAPElement gameHistory = game.addChildElement(new QName(tourNS, "game-history", "tour"));
             SOAPElement move = gameHistory.addChildElement(new QName(tourNS, "move", "tour"));
             move.setAttribute("player", "");
             move.setAttribute("dots", "");
             move.setAttribute("start-point", "");
             move.setAttribute("end-point", ""); */
        }

        /* description (optional)
         {
         SOAPElement description = tournament.addChildElement(new QName(tourNS, "description", "tour"));
         description.setValue("");
         } */
    }

    private void fillPlayer(SOAPElement players, Player playerData) throws SOAPException {
        SOAPElement player = players.addChildElement(new QName(tourNS, "player", "tour"));
        SOAPElement dateOfBirth = player.addChildElement(new QName(tourNS, "date-of-birth", "tour"));
        SOAPElement gender = player.addChildElement(new QName(tourNS, "gender", "tour"));

        if (playerData.getUser() == null) {
            throw new IllegalArgumentException("Player must be connected to a user");
        }

        player.setAttribute("username", playerData.getName());
        
        dateOfBirth.setValue(xmlDateFormat.format(playerData.getUser().getBirthdate()));
        
        if("f".equals(playerData.getUser().getSex())) {
            gender.setValue("FEMALE");
        } else {
            gender.setValue("MALE");
        }
    }
}
