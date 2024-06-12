package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.entity.Boss;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.map.Point;

public class BossMap extends Map {

    public BossMap(int width, int height) {
        super(width, height);
        this.generateBossRoom();
    }

    public void generateBossRoom() {
        this.floors.clear();
        this.bridges.clear();
        this.rooms.clear();
        this.chests.clear();
        this.monsters.clear();
        this.walls.clear();

        // Create a large room for the boss
        Room bossRoom = new Room(10, 10, this.map_width - 20, this.map_height - 20, true);
        this.rooms.add(bossRoom);

        // Generate floors and walls for the boss room
        this.genFloors();
        this.genWalls();

        // Spawn the boss monster
        this.spawnBossMonster();
    }

    public void spawnBossMonster() {
        Monster bossMonster = Boss.createSlimeBoss(InGameScreen.currentLevel);
        // Place the boss in the center of the boss room
        Point bossSpawnPoint = new Point(600, 500);
        bossMonster.place(bossSpawnPoint);
        this.monsters.add(bossMonster);
    }

    @Override
    public void drawMap(SpriteBatch batch) {
        super.drawMap(batch);
        // Additional drawing for boss map (e.g., boss room indicators)
        // Add your additional drawing code here
    }

    @Override
    public void drawMonsters(SpriteBatch batch) {
        super.drawMonsters(batch);
        // Additional drawing for boss map monsters
        // Add your additional drawing code here
    }

    @Override
    public void drawInteractive(SpriteBatch batch) {
        super.drawInteractive(batch);
        // Additional drawing for boss map interactive objects
        // Add your additional drawing code here
    }

    public boolean isBossDefeated() {
        for (Monster monster : this.monsters) {
            if (monster instanceof Boss && monster.isDead) {
                return true; // Boss monster is defeated
            }
        }
        return false; // Boss monster is still alive
    }

    /**
     * Obtains the player's spawn point for the boss map.
     *
     * @return The player's spawn point for the boss map
     */
    @Override
    public Point getPlayerSpawn() {
        if (!this.rooms.isEmpty()) {
            // Spawn the player at the center of the boss room
            return new Point(300,500);
        } else {
            // Default spawn point if no rooms are generated
            return new Point(300, 500);
        }
    }

    /**
     * Generates floors for the boss room.
     */
    public void genFloors() {
        // Iterate over the area of the boss room and set floor tiles
        for (int x = 10; x < this.map_width - 10; x++) {
            for (int y = 10; y < this.map_height - 10; y++) {
                this.floors.add(new Floor(x, y));
            }
        }
    }

    /**
     * Generates walls for the boss room.
     */
    public void genWalls() {
        // Iterate over the boundary of the boss room and set wall tiles
        for (int x = 9; x <= this.map_width - 9; x++) {
            this.walls.add(new Wall(new Point(x,9), "assets/map/wall_edge_left.png"));
            this.walls.add(new Wall(new Point(x, this.map_height - 9),"assets/map/wall_edge_left.png"));
        }
        for (int y = 9; y <= this.map_height - 9; y++) {
            this.walls.add(new Wall(new Point(9, y), "assets/map/wall_edge_left.png"));
            this.walls.add(new Wall(new Point(this.map_width - 9, y),"assets/map/wall_edge_left.png"));
        }
    }
}
