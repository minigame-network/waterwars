package me.chasertw123.minigames.waterwars.game.loops;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameState;

/**
 * Created by Scott Hiett on 4/22/2017.
 */
public class Loop_Game extends GameLoop {

    public Loop_Game(){
        super(60 * 10, 20L);
    }

    @Override
    public void run() {

        if (Main.getGameManager().inCages())
            return;

        if (Main.getGameManager().getGameState() == GameState.GAME)
            Main.getGameManager().checkGame();

        if (--interval == 0) {
            Main.getGameManager().startDeathmatch();

            this.cancel();
        }
    }

    public int getInterval() {
        return interval;
    }
}
