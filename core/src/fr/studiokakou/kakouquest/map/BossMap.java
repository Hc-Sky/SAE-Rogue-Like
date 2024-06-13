package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.entity.Boss;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.screens.InGameScreen;

public class BossMap extends Map {

    public BossMap(int width, int height) {
        super(width, height);
        this.generateBossRoom();
    }

    public void generateBossRoom() {
        this.bridges.clear();
        this.rooms.clear();
        this.chests.clear();
        monsters.clear();

        // Create a large room for the boss
        Room bossRoom = new Room(0, 0, 15, 15, true);
        this.rooms.add(bossRoom);

        // Spawn the boss monster
        this.spawnBossMonster();
    }

    public void spawnBossMonster() {
        Monster bossMonster = Boss.createSlimeBoss(InGameScreen.currentLevel);
        // Place the boss in the center of the boss room
        Point bossSpawnPoint = new Point(300, 300); // Adjust to the center of the room
        bossMonster.place(bossSpawnPoint);
        monsters.add(bossMonster);
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
        for (Monster monster : monsters) {
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
            return new Point(150, 150); // Adjust to the center of the room
        } else {
            // Default spawn point if no rooms are generated
            return new Point(150, 150); // Adjust to a default point
        }
    }

    /**
     * Generates floors for the boss room.
     */
    public void genFloors() {
        // Iterate over the area of the boss room and set floor tiles
        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 30; y++) {
                this.floors.add(new Floor(x, y));
            }
        }
        for (int x = 12; x <= 18; x++) {
            for (int y = 34; y < 40; y++) {
                this.floors.add(new Floor(x, y));
            }
        }
        for (int y = 31; y < 34; y++) {
            this.floors.add(new Floor(15, y));
        }
    }

    /**
     * Generates walls for the boss room.
     */
    public void genWalls() {
        int startX = 0;
        int startY = 0;
        int endX = 30;
        int endY = 29;

        // Top and bottom walls
        for (int x = startX; x < endX; x++) {
            this.walls.add(new Wall(new Point(x, startY - 1), "assets/map/wall_mid.png")); // Top wall
            this.walls.add(new Wall(new Point(x, startY), "assets/map/wall_top_mid.png")); // Row above top wall

            this.walls.add(new Wall(new Point(x, endY + 1), "assets/map/wall_right.png"));   // Bottom wall
            this.walls.add(new Wall(new Point(x, endY + 2), "assets/map/wall_top_mid.png")); // Row below bottom wall
        }

        // Left and right walls
        for (int y = startY-1; y < endY+2; y++) {
            this.walls.add(new Wall(new Point(startX - 1, y), "assets/map/wall_outer_mid_left.png")); // Left wall
            this.walls.add(new Wall(new Point(endX, y), "assets/map/wall_outer_mid_right.png"));   // Right wall
        }

        // Add additional walls at the corners to complete the frame
        //this.walls.add(new Wall(new Point(startX - 1, startY - 1), "assets/map/wall_outer_top_left.png")); // Top left corner
        //this.walls.add(new Wall(new Point(endX, startY - 1), "assets/map/wall_outer_top_right.png")); // Top right corner
    }
}
