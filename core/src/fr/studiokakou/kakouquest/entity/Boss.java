package fr.studiokakou.kakouquest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;

import java.time.LocalDateTime;

public class Boss extends Monster {
    Animation<TextureRegion> attackAnimation;
    Animation<TextureRegion> hitAnimation;
    Animation<TextureRegion> deathAnimation;
    boolean isAttacking = false;
    boolean isHit = false;

    private float stateTime;
    private float hitStateTime;
    private float attackStateTime;
    private float deathStateTime;
    private final float HIT_ANIMATION_DURATION = 0.3f; // durée de l'animation hit en secondes
    private final float ATTACK_ANIMATION_DURATION = 1.0f; // durée de l'animation attack en secondes
    private final float DEATH_ANIMATION_DURATION = 5.0f; // durée de l'animation death en secondes

    public Boss(String name, String idleAnimationPath, String runAnimationPath, String attackAnimationPath,
                String hitAnimationPath, String deathAnimationPath, int hp, int damage, float attackPause, float speed,
                int detectRange, int currentLevel) {
        super(name, idleAnimationPath, runAnimationPath, hp, damage, attackPause, speed, detectRange, currentLevel);
        this.idleAnimation = Utils.getAnimation(idleAnimationPath, 6, 1);
        this.runAnimation = Utils.getAnimation(runAnimationPath, 12, 1);
        this.attackAnimation = Utils.getAnimation(attackAnimationPath, 15, 1);

        // Ajuster la durée de chaque frame pour l'animation hit
        int hitFrameCount = 5;
        this.hitAnimation = new Animation<>(HIT_ANIMATION_DURATION / hitFrameCount, Utils.getAnimation(hitAnimationPath, hitFrameCount, 1).getKeyFrames());

        // Ajuster la durée de chaque frame pour l'animation death
        int deathFrameCount = 22;
        this.deathAnimation = new Animation<>(DEATH_ANIMATION_DURATION / deathFrameCount, Utils.getAnimation(deathAnimationPath, deathFrameCount, 1).getKeyFrames());

        this.sprite = new Sprite(idleAnimation.getKeyFrame(0)); // Initialiser le sprite avec la première frame de l'animation idle
    }

    @Override
    public void move(Player player, Map map) {
        if (isDying || isRed || isAttacking || isHit || !player.hasPlayerSpawn) {
            return;
        }
        Point playerPos = player.pos;
        if (Utils.distance(playerPos, this.pos) <= 20) {
            this.attack(player);
        }
        if (detectPlayer(playerPos)) {
            this.isRunning = true;
            this.getOrientation(player);
            Point orientation = Point.getOrientation(this.pos, playerPos);
            if (canMove(new Point(orientation.x, 0), map)) {
                this.pos = this.pos.add(orientation.x * this.speed * Gdx.graphics.getDeltaTime(), 0);
            }
            if (canMove(new Point(0, orientation.y), map)) {
                this.pos = this.pos.add(0, orientation.y * this.speed * Gdx.graphics.getDeltaTime());
            }

            // Tourner le boss vers le joueur
            this.isFlip = (this.pos.x < player.pos.x);
        } else {
            this.isRunning = false;
        }
    }


    @Override
    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = null;

        if (isDying) {
            deathStateTime += Gdx.graphics.getDeltaTime();
            currentFrame = this.deathAnimation.getKeyFrame(deathStateTime, false);
            if (this.deathAnimation.isAnimationFinished(deathStateTime)) {
                this.isDead = true;
            }
        } else if (isHit) {
            hitStateTime += Gdx.graphics.getDeltaTime();
            currentFrame = this.hitAnimation.getKeyFrame(hitStateTime, false);
            if (this.hitAnimation.isAnimationFinished(hitStateTime)) {
                this.isHit = false;
                this.isRed = false;
            }
        } else if (isAttacking) {
            attackStateTime += Gdx.graphics.getDeltaTime();
            currentFrame = this.attackAnimation.getKeyFrame(attackStateTime, false);
            if (this.attackAnimation.isAnimationFinished(attackStateTime)) {
                this.isAttacking = false;
            }
        } else if (isRunning) {
            currentFrame = this.runAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = this.idleAnimation.getKeyFrame(stateTime, true);
        }

        if (currentFrame != null) {
            this.sprite.setRegion(currentFrame);

            // Centrer le sprite sur les coordonnées du boss
            this.sprite.setX(this.pos.x - this.sprite.getWidth() / 2);
            this.sprite.setY(this.pos.y - this.sprite.getHeight() / 2);

            this.sprite.setFlip(this.isFlip, false); // Utiliser setFlip pour mettre à jour l'orientation

            this.sprite.draw(batch);
        }
    }

    @Override
    public void takeDamage(Player player) {
        if (Utils.getDistance(player.pos, this.pos) < 80 ){
            this.hp -= player.currentWeapon.damage * (player.strength / 10);
            this.isHit = true;
            this.isRed = true; // Activer l'effet rouge
            hitStateTime = 0; // Réinitialiser hitStateTime lors de la prise de dégâts
            if (this.hp <= 0) {
                this.isAttacking = false;
                this.isHit = false;
                this.isRunning = false;
                this.isDying = true;
                deathStateTime = 0; // Réinitialiser deathStateTime lors de la mort
            }
        }
    }

    protected void attack(Player player) {
        if (this.isDying || !player.hasPlayerSpawn) {
            return;
        }
        if (this.currentAttackTime == null || this.currentAttackTime.plusNanos((long) (1000000 * this.attackPause)).isBefore(LocalDateTime.now())) {
            player.takeDamage(this.damage);
            this.currentAttackTime = LocalDateTime.now();
            this.isAttacking = true;
            attackStateTime = 0; // Réinitialiser attackStateTime lors du début de l'attaque

            // Calculer l'orientation du boss par rapport au joueur
            this.isFlip = (this.pos.x + 150 < player.pos.x);
        }
    }



    public static Boss createSlimeBoss(int currentLevel) {
        return new Boss("Slime Boss",
                "assets/entities/slime_boss_idle.png",
                "assets/entities/slime_boss_walk.png",
                "assets/entities/slime_boss_attack.png",
                "assets/entities/slime_boss_hit.png",
                "assets/entities/slime_boss_death.png",
                2000, 50, 300, 30f, 150, currentLevel); // Réduire attackPause à 500 ms pour des attaques plus fréquentes
    }
}
