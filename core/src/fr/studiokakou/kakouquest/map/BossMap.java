package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.entity.Boss;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.interactive.Stairs;
import fr.studiokakou.kakouquest.screens.InGameScreen;

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

        Room bossRoom = new Room(10, 10, this.map_width - 10, this.map_height - 10, true);
        this.rooms.add(bossRoom);

        this.genFloors();
        this.genWalls();

        this.spawnBossMonster();
    }

    public void spawnBossMonster() {
        Monster bossMonster = Boss.createSlimeBoss(InGameScreen.currentLevel);
        bossMonster.place(new Point(300, 1000));
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
     * Obtient le point de spawn du joueur pour la carte du boss.
     *
     * @return Le point de spawn du joueur pour la carte du boss
     */
    @Override
    public Point getPlayerSpawn() {
        // Pour la carte du boss, nous pouvons choisir un emplacement spécifique,
        // comme le centre de la salle du boss.
        // Vous pouvez ajuster cette logique selon vos besoins.

        if (!this.rooms.isEmpty()) {
            // Nous supposons que la salle du boss est la première salle dans la liste des salles.
            Room bossRoom = this.rooms.get(0);
            return new Point(300, 500);
        } else {
            // Si la liste des salles est vide, nous n'avons pas de point de spawn.
            // Vous devrez ajuster cette logique selon votre implémentation.
            // Par exemple, vous pourriez générer une exception ou retourner un point par défaut.
            return new Point(300, 500); // Point de spawn par défaut (300, 500)
        }
    }

}
