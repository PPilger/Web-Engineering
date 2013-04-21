<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<jsp:useBean id="info" class="beans.TableBean" scope="session" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="de" xml:lang="de">
    <head>
        <title xml:lang="de">Formel 0 - Spielen</title>
        <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
        <link rel="stylesheet" type="text/css" href="styles/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="container">
            <div id="bordercontainer">
                <div id="header">
                    <div id="header_left"><h1 class="accessibility">Formel 0</h1></div>
                    <div id="header_right"></div>
                </div>
                <div id="navigation">
                    <h2 class="accessibility">Navigation</h2>
                    <ul title="Navigation">
                        <form method="post">
                            <li><a id="startNewGame" href="Table" tabindex="1">Neues Spiel</a></li>
                            <!--<li><input id="startNewGame" class="menuButton" name="newGame" type="submit" value="Neues Spiel" tabindex="1" /></li>-->
                            <!--<li><input id="startNewGame" class="menuButton" name="newGame" type="submit" value="Neues Spiel" tabindex="1" /></li>-->
                            <li><a href="#" tabindex="2">Ausloggen</a></li>
                        </form>
                    </ul>
                </div>
                <div id="main-area">
                    <div class="info">
                        <h2>Spielinformationen</h2>
                        <table summary="Diese Tabelle zeigt Informationen zum aktuellen Spiel">
                            <tr><th id="leaderLabel" class="label">F&uuml;hrender</th><td id="leader" class="data"><%=info.getLeader()%></td></tr>
                            <tr><th id="roundLabel" class="label">Runde</th><td id="round" class="data"><%=info.getRound()%></td></tr>
                            <tr><th id="timeLabel" class="label">Zeit</th><td id="time" class="data"><%=info.getTime()%></td></tr>
                            <tr><th id="computerScoreLabel" class="label">W&uuml;rfelergebnis <em><%=info.getPlayer2().getName()%></em></th><td id="computerScore" class="data"><%=info.getPlayer2().getLastResult()%></td></tr>
                        </table>  
                        <h2>Spieler</h2>
                        <table summary="Diese Tabelle listet die Namen der Spieler auf">
                            <tr><th id="player1NameLabel" class="label">Spieler 1</th><td id="player1Name" class="data"><%=info.getPlayer1().getName()%></td></tr>
                            <tr><th id="player2NameLabel" class="label">Spieler 2</th><td id="player2Name" class="data"><%=info.getPlayer2().getName()%></td></tr>
                        </table>    	  
                    </div>
                    <div class="field">
                        <h2 class="accessibility">Spielbereich</h2>
                        <ol id="road">
                            <li id="start_road">
                                <span class="accessibility">Startfeld</span>
                            </li>
                            <li class="empty_road" id="road_1">
                                <span class="accessibility">Feld 2</span>
                            </li>
                            <li class="oil_road" id="road_2">
                                <span class="accessibility">Feld 3</span>
                                <span id="player1">
                                    <span class="accessibility"><em>Spieler 1</em></span>
                                </span>
                            </li>
                            <li class="empty_road" id="road_3">
                                <span class="accessibility">Feld 4</span>
                            </li>
                            <li class="empty_road" id="road_4">
                                <span class="accessibility">Feld 5</span>
                                <span id="player2">
                                    <span class="accessibility"><em>Spieler 2</em></span>
                                </span>
                            </li>
                            <li class="oil_road" id="road_5">
                                <span class="accessibility">Feld 6</span>
                            </li>
                            <li id="finish_road">
                                <span class="accessibility">Zielfeld</span>
                            </li>
                        </ol>
                    </div>
                    <div class="player">
                        <h2 class="accessibility">W&uuml;rfelbereich</h2>
                        <span class="accessibility">An der Reihe ist</span><div id="currentPlayerName"><%=info.getPlayer1().getName()%></div>
                        <a id="dice" href="Table" tabindex="4">
                            <img id="diceImage" src="img/wuerfel<%=info.getPlayer1().getLastResult()%>.png" alt="W&uuml;rfel mit einer Eins" />	
                        </a>
                    </div>
                </div>
            </div>
            <div id="footer">
                &copy; 2013 Formel 0
            </div>
        </div>

        <script type="text/javascript">
            //<![CDATA[

            // call this function once before starting the animations
            function prepareAnimation() {
                $("#animationDone").remove();
            }

            // call this function once after all animations have finished
            function completeAnimation() {
                var div = $(document.createElement('div'));
                div.attr('id', 'animationDone');
                div.addClass('hide');
                $("body").append(div);
            }

            function moveTo(player, position) {
                if (position <= 0) {
                    player.appendTo("#start_road");
                } else if (position >= 6) {
                    player.appendTo("#finish_road");
                } else {
                    player.appendTo("#road_" + position);
                }
            }

            function animateFromTo(player, oldPos, newPos, callback) {
                oldPos = Math.min(oldPos, 6);
                newPos = Math.min(newPos, 6);
                
                if (oldPos === newPos) {
                    moveTo(player, oldPos);
                    callback();
                } else {
                    moveTo(player, oldPos);
                    player.fadeOut(700, function() {
                        moveTo(player, newPos);
                        player.fadeIn(700, callback);
                    });
                }
            }

            function animation(player, oldPos, lastResult, newPos, callback) {
                animateFromTo(player, oldPos, oldPos + lastResult, function() {
                    animateFromTo(player, oldPos + lastResult, newPos, callback);
                });
            }
            
            moveTo($("#player1"), <%=info.getPlayer1().getPosition()%>);
            moveTo($("#player2"), <%=info.getPlayer2().getPosition()%>);
            
            prepareAnimation();
            animation($("#player1"),
                      <%=info.getPlayer1().getPosition()%>,
                      <%=info.getPlayer1().getLastResult()%>,
                      <%=info.getPlayer1().getNextPosition()%>,
                      function() {
                animation($("#player2"),
                          <%=info.getPlayer2().getPosition()%>,
                          <%=info.getPlayer2().getLastResult()%>,
                          <%=info.getPlayer2().getNextPosition()%>,
                          completeAnimation);
            });

            $("#startNewGame").click(function() {
                $.post("Table", {newGame: "true"})
                        .done(function(data) {
                    $("body").html(data);
                });
            });

            $("#dice").click(function() {
                $.post("Table", {rollDice: "true"})
                        .done(function(data) {
                    $("body").html(data);
                });
            });
            //]]>
        </script>

    </body>
</html>