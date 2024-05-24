package fr.studiokakou.kakouquest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;

import java.time.LocalDateTime;

public class Boss extends Monster {
    Animation<TextureRegion> attackAnimation;
    Animation<TextureRegion> hitAnimation;
    Animation<TextureRegion> deathAnimation;
    boolean isAttacking = false;
    boolean isHit = false;

    public Boss(String name, String idleAnimationPath, String runAnimationPath, String attackAnimationPath,
                String hitAnimationPath, String deathAnimationPath, int hp, int damage, float attackPause, float speed,
                int detectRange, int currentLevel) {
        super(name, idleAnimationPath, runAnimationPath, hp, damage, attackPause, speed, detectRange, currentLevel);
        this.idleAnimation = Utils.getAnimation(idleAnimationPath, 6, 1);
        this.runAnimation = Utils.getAnimation(runAnimationPath, 12, 1);
        this.attackAnimation = Utils.getAnimation(attackAnimationPath, 15, 1);
        this.hitAnimation = Utils.getAnimation(hitAnimationPath, 5, 1);
        this.deathAnimation = Utils.getAnimation(deathAnimationPath, 22, 1);
    }

    @Override
    public void move(Player player, Map map) {
        if (isDying || isRed || isAttacking || isHit || !player.hasPlayerSpawn) {
            return;
        }
        Point playerPos = player.pos;
        if (detectPlayer(playerPos)) {
            this.isRunning = true;
            this.getOrientation(player);
            Point orientation = Point.getOrientation(this.pos, playerPos);
            if (canMove(new Point(orientation.x, 0), map)) {
                this.pos = this.pos.add(orientation.x * (this.speed) * Gdx.graphics.getDeltaTime(), 0);
            }
            if (canMove(new Point(0, orientation.y), map)) {
                this.pos = this.pos.add(0, orientation.y * (this.speed) * Gdx.graphics.getDeltaTime());
            }
        } else {
            this.isRunning = false;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame;

        if (isDying) {
            currentFrame = this.deathAnimation.getKeyFrame(InGameScreen.stateTime, false);
            if (this.deathAnimation.isAnimationFinished(InGameScreen.stateTime)) {
                this.isDead = true;
            }
        } else if (isHit) {
            currentFrame = this.hitAnimation.getKeyFrame(InGameScreen.stateTime, false);
            if (this.hitAnimation.isAnimationFinished(InGameScreen.stateTime)) {
                this.isHit = false;
            }
        } else if (isAttacking) {
            currentFrame = this.attackAnimation.getKeyFrame(InGameScreen.stateTime, false);
            if (this.attackAnimation.isAnimationFinished(InGameScreen.stateTime)) {
                this.isAttacking = false;
            }
        } else if (isRunning) {
            currentFrame = this.runAnimation.getKeyFrame(InGameScreen.stateTime, true);
        } else {
            currentFrame = this.idleAnimation.getKeyFrame(InGameScreen.stateTime, true);
        }

        this.sprite = new Sprite(currentFrame);

        this.sprite.setX(this.pos.x);
        this.sprite.setY(this.pos.y);

        this.sprite.flip(this.isFlip, false);

        // Only set red color for a short period to indicate taking damage
        if (isRed) {
            currentFrame = this.hitAnimation.getKeyFrame(InGameScreen.stateTime, false);
            if (this.hitAnimation.isAnimationFinished(InGameScreen.stateTime)) {
                this.isHit = false;
            }
        } else {
            currentFrame = this.idleAnimation.getKeyFrame(InGameScreen.stateTime, true);
        }

        this.sprite.draw(batch);
    }

    @Override
    public void takeDamage(Player player){
        this.hp -= player.currentWeapon.damage*(player.strength/10);
        this.isRed = true;
        if (this.hp <= 0){
            this.isAttacking=false;
            this.isHit=false;
            this.isRunning=false;
            this.isDying=true;
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
        }
    }

    public static Boss createSlimeBoss(int currentLevel) {
        return new Boss("Slime Boss",
                "assets/entities/slime_boss_idle.png",
                "assets/entities/slime_boss_walk.png",
                "assets/entities/slime_boss_attack.png",
                "assets/entities/slime_boss_hit.png",
                "assets/entities/slime_boss_death.png",
                2000, 50, 1000, 30f, 300, currentLevel);
    }
}
