package me.chasertw123.minigames.waterwars.users;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Chase on 2/18/2017.
 */
public class UserManager {

    private Map<UUID, User> users = new HashMap<>();

    public User get(UUID uuid) {
        return users.get(uuid);
    }

    public boolean has(UUID uuid) {
        return users.containsKey(uuid);
    }

    public void add(User user) {
        users.put(user.getCoreUser().getUUID(), user);
    }

    public Collection<User> toCollection(){
        return users.values();
    }

    public void remove(UUID uuid){
        users.remove(uuid);
    }
}
