package me.chasertw123.minigames.waterwars.game.votes;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.maps.BaseMap;
import me.chasertw123.minigames.waterwars.users.User;

import java.util.Random;

/**
 * Created by Chase on 2/18/2017.
 */
public class VoteManager {

    private Random random = new Random();
    private boolean votingActive = true;

    public int getVotes(BaseMap map) {
        int count = 0;
        for(User user : Main.getUserManager().toCollection())
            if(user.hasVoted() && user.getVotedMap().equalsIgnoreCase(map.getName()))
                count++;

        return count;
    }

    public boolean isVotingActive() {
        return votingActive;
    }

    public void setVotingActive(boolean votingActive) {
        this.votingActive = votingActive;
    }

    public String getWinner(){

        setVotingActive(false);

        int highestCount = 0;
        String highestName = Main.getMapManager().getMaps().get(random.nextInt(Main.getMapManager().getMaps().size())).getName();
        for (BaseMap s : Main.getMapManager().getMaps())
            if (getVotes(s) > highestCount){
                highestCount = getVotes(s);
                highestName = s.getName();
            }

        return highestName;
    }

}
