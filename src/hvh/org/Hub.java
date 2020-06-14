package hvh.org;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Leee Leee
 */
public class Hub {
    
    List<Game> games;
    Location spawn;
    
    public Hub(Plugin plugin, Location spawn) {
        this.spawn = spawn;
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> tryStartGames(), 50l, 1000l);
	games = new ArrayList();
    }

    private boolean tryStartGames() {
    	games
    }

    public void joinGame(Player player, String team) {
	Game game = new Game();
	

	games.add(game);
    }
    
    public boolean joinGame(Player player, String team) {
    	Game game = findGame(player, team);
	
	// if none found, create
	if (game == null) {
	    game = createGame(player, team);
	    games.add(game);
	}

	// add player to game
	switch (team) {
	    case "hunted":
		game.addHunted(player);
		break;
	    case "hunter":
		game.addHunter(player);
		break;
            default:
		player.sendMessage("you must join the team either 'hunter' or 'hunted'");
		break;
	}
	
    }

    public boolean findGame(Player player, String team) {
        switch (team) {
            case "hunted":

		// find a game
                for (Game game : games) {
                    if (game.addHunted(player)) {
                        return true;
                    }
                }

                break;
            case "hunter":
                
		for (Game game : games) {
                    if (game.addHunter(player)) {
                        return true;
                    }
                }
		
                break;
            default:
                return false;
        }

        return true;
    }
    
    public boolean parseCommand(Player player, List<String> args) {
        String cmd = args.remove(0);
        
        switch (cmd) {
            case "join":
		if (args.size() > 0) {
                    return joinGame(player, args.get(0));
		} else {
		    player.sendMessage("cmd: /hvh join [hunter/hunted]");
		}
            case "setspawn":
		player.sendMessage("setting hub spawn");
                setSpawn(player.getLocation());
                break;
            case "spawn":
		player.sendMessage("teleporting to hub");
                spawn(player);
                break;
            default:
		player.sendMessage("cmds: join, spawn, setspawn");
                return false;
        }
        
        return true;
    }
    
    public void setSpawn(Location location) {
        spawn = location;
    }
    
    public void spawn(Player player) {
	player.teleport(spawn);
    }

}