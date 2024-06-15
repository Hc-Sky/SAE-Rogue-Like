package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.entity.Boss;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.interactive.Chest;
import fr.studiokakou.kakouquest.interactive.Stairs;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;

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

    public static boolean isBossDefeated() {
        return monsters.isEmpty();
//        for (Monster monster : monsters) {
//            if (monster instanceof Boss && monster.isDying) {
//                return true; // Boss monster is defeated
//            }
//        }
//        return false; // Boss monster is still alive
    }

    @Override
    public void genInteractive(int currentLevel, InGameScreen gameScreen){
        this.stairs = new Stairs(new Point(240,608),gameScreen);
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
            return new Point(240, -125); // Adjust to the center of the room
        } else {
            // Default spawn point if no rooms are generated
            return new Point(240, -125); // Adjust to a default point
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
        for (int x = 12; x <= 18; x++) {
            for (int y = -10; y < -4; y++) {
                this.floors.add(new Floor(x, y));
            }
        }
        for (int y = -4; y < 0; y++) {
            this.floors.add(new Floor(15, y));
        }

        // Add floors on the line of abscissa 15
        for (int y = -5; y < 39; y++) {
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

        // Top and bottom walls for the main room
        for (int x = startX; x < endX; x++) {
            if (x != 15) {
                this.walls.add(new Wall(new Point(x, startY - 1), "assets/map/wall_mid.png")); // Top wall
                this.walls.add(new Wall(new Point(x, startY), "assets/map/wall_top_mid.png")); // Row above top wall

                this.walls.add(new Wall(new Point(x, endY + 1), "assets/map/wall_right.png"));   // Bottom wall
                this.walls.add(new Wall(new Point(x, endY + 2), "assets/map/wall_top_mid.png")); // Row below bottom wall
            }
        }

        // Left and right walls for the main room
        for (int y = startY - 1; y < endY + 2; y++) {
            this.walls.add(new Wall(new Point(startX - 1, y), "assets/map/wall_outer_mid_left.png")); // Left wall
            this.walls.add(new Wall(new Point(endX, y), "assets/map/wall_outer_mid_right.png"));   // Right wall
        }

        // Walls for the small room at the top
        for (int x = 12; x <= 18; x++) {
            if (x != 15) {
                this.walls.add(new Wall(new Point(x, 33), "assets/map/wall_mid.png")); // Bottom wall of the top room
                this.walls.add(new Wall(new Point(x, 34), "assets/map/wall_top_mid.png")); // Row above bottom wall
            }
            this.walls.add(new Wall(new Point(x, 40), "assets/map/wall_right.png")); // Top wall of the top room
            this.walls.add(new Wall(new Point(x, 41), "assets/map/wall_top_mid.png")); // Row below top wall
        }
        for (int y = 33; y <= 40; y++) {
            this.walls.add(new Wall(new Point(11, y), "assets/map/wall_outer_mid_left.png")); // Left wall of the top room
            this.walls.add(new Wall(new Point(19, y), "assets/map/wall_outer_mid_right.png")); // Right wall of the top room
        }

        // Walls for the small room at the bottom
        for (int x = 12; x <= 18; x++) {
            if (x != 15) {
                this.walls.add(new Wall(new Point(x, -4), "assets/map/wall_mid.png")); // Top wall of the bottom room
                this.walls.add(new Wall(new Point(x, -3), "assets/map/wall_top_mid.png")); // Row above top wall
            }
            this.walls.add(new Wall(new Point(x, -11), "assets/map/wall_right.png")); // Bottom wall of the bottom room
            this.walls.add(new Wall(new Point(x, -10), "assets/map/wall_top_mid.png")); // Row below bottom wall
        }
        for (int y = -11; y <= -4; y++) {
            this.walls.add(new Wall(new Point(11, y), "assets/map/wall_outer_mid_left.png")); // Left wall of the bottom room
            this.walls.add(new Wall(new Point(19, y), "assets/map/wall_outer_mid_right.png")); // Right wall of the bottom room
        }

        // Walls for the left and right sides of the small corridors connecting the rooms
        for (int y = 31; y < 34; y++) {
            if (y != 15) {
                this.walls.add(new Wall(new Point(14, y), "assets/map/wall_outer_mid_left.png")); // Left wall of the top corridor
                this.walls.add(new Wall(new Point(16, y), "assets/map/wall_outer_mid_right.png")); // Right wall of the top corridor
            }
        }
        for (int y = -4; y < 0; y++) {
            if (y != 15) {
                this.walls.add(new Wall(new Point(14, y), "assets/map/wall_outer_mid_left.png")); // Left wall of the bottom corridor
                this.walls.add(new Wall(new Point(16, y), "assets/map/wall_outer_mid_right.png")); // Right wall of the bottom corridor
            }
        }
    }
}
