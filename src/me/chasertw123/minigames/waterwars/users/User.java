package me.chasertw123.minigames.waterwars.users;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import me.chasertw123.minigames.core.database.NoSQLDatabase;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.wws.unlocks.cages.Cage;
import me.chasertw123.minigames.wws.unlocks.kits.Kit;
import me.chasertw123.minigames.wws.users.WaterWarsUser;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chase on 2/18/2017.
 */
public class User implements WaterWarsUser {

    private me.chasertw123.minigames.core.user.User user;

    private boolean dead, loaded = false;
    private int kills = 0, endingPlace = 0, lives = 3, island = 0;
    private long joinTime, lastWaterDamage = 0;

    private List<Location> chestsOpened = new ArrayList<>();
    private List<Kit> ownedKits = new ArrayList<>();
    private List<Cage> ownedCages = new ArrayList<>();

    private Cage selectedCage = Cage.GLASS;
    private Kit selectedKit = Kit.WOODSMAN;

    private String votedMap = null;

    @SuppressWarnings("unchecked")
    public User(me.chasertw123.minigames.core.user.User user) {
        this.user = user;

        Main.newChain()
                .asyncFirst(() -> {

                    if (me.chasertw123.minigames.core.Main.getNoSQLDatabase().containsUser(me.chasertw123.minigames.core.Main.getNoSQLDatabase().getCollection(ServerGameType.WATER_WARS), user.getUUID()))
                        return me.chasertw123.minigames.core.Main.getNoSQLDatabase().getCollection(ServerGameType.WATER_WARS).find(Filters.eq("uuid", user.getUUID().toString())).first();

                    else
                        return null;
                })
                .syncLast((document) -> {

                    if (document != null) {
                        ((List<String>) document.get("kits")).forEach(obj -> this.ownedKits.add(Kit.valueOf(obj)));
                        ((List<String>) document.get("cages")).forEach(obj -> this.ownedCages.add(Cage.valueOf(obj)));
                        this.selectedKit = Kit.valueOf(document.getString("kit"));
                        this.selectedCage = Cage.valueOf(document.getString("cage"));
                    }

                    else {
                        this.ownedKits.add(Kit.WOODSMAN);
                        this.ownedCages.add(Cage.GLASS);
                    }
                }).execute(() -> loaded = true);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void sendMessage(String message) {
        getPlayer().sendMessage(message);
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public long getJoinTime(){
        return joinTime;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Player getPlayer(){
        return user.getPlayer();
    }

    public boolean hasVoted(){
        return votedMap != null;
    }

    public String getVotedMap(){
        return votedMap;
    }

    public void setIsland(int island) {
        this.island = island;
    }

    public int getIsland() {
        return island;
    }

    public void setVotedMap(String votedMap){
        this.votedMap = votedMap;
    }

    public me.chasertw123.minigames.core.user.User getCoreUser() {
        return user;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setCageType(Cage cageType){
        this.selectedCage = cageType;
    }

    public void setPlace(int place){
        this.endingPlace = place;
    }

    public int getPlace(){
        return this.endingPlace;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public long getLastWaterDamage() {
        return lastWaterDamage;
    }

    public boolean isFullDead(){
        return isDead() && lives <= 0;
    }

    public void setLastWaterDamage(long lastWaterDamage) {
        this.lastWaterDamage = lastWaterDamage;
    }

    public List<Location> getChestsOpened() {
        return chestsOpened;
    }

    public Kit getSelectedKit() {
        return selectedKit;
    }

    public void setSelectedKit(Kit selectedKit) {
        this.selectedKit = selectedKit;

        if (!ownsKit(selectedKit))
            ownedKits.add(selectedKit);
    }

    public boolean ownsKit(Kit kit) {
        return ownedKits.contains(kit);
    }

    public Cage getSelectedCage() {
        return selectedCage;
    }

    public void setSelectedCage(Cage selectedCage) {
        this.selectedCage = selectedCage;

        if (!ownsCage(selectedCage))
            ownedCages.add(selectedCage);
    }

    public boolean ownsCage(Cage cage) {
        return ownedCages.contains(cage);
    }

    public List<Kit> getOwnedKits() {
        return ownedKits;
    }

    public List<Cage> getOwnedCages() {
        return ownedCages;
    }

    public boolean save() {

        if (!loaded)
            return true;

        Document wwData = new Document();
        List<String> wwKits = new ArrayList<>(), wwCages = new ArrayList<>();

        this.getOwnedKits().forEach(obj -> wwKits.add(obj.toString()));
        this.getOwnedCages().forEach(obj -> wwCages.add(obj.toString()));
        wwData.append("uuid", getPlayer().getUniqueId().toString())
                .append("kit", this.getSelectedKit().toString())
                .append("cage", this.getSelectedCage().toString())
                .append("kits", wwKits)
                .append("cages", wwCages);

        UpdateResult result = NoSQLDatabase.getNoSQLDatabase().getCollection(ServerGameType.WATER_WARS)
                .replaceOne(Filters.eq("uuid", getPlayer().getUniqueId().toString()), wwData, new UpdateOptions().upsert(true));

        return result.getModifiedCount() >= result.getMatchedCount();
    }
}
