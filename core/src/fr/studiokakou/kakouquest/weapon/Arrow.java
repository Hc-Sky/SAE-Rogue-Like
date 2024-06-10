package fr.studiokakou.kakouquest.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.map.Point;

public class Arrow {
    public static Texture arrowTexture;
    Sprite sprite;

    Point startPos;
    float rotation;

    public static final float ARROW_SPEED = 80f;

    public static void initTexture() {
        arrowTexture = new Texture("assets/weapon/weapon_arrow.png");
    }

    public Arrow(Point startPos, float rotation) {
        this.startPos = startPos;
        this.sprite = new Sprite(arrowTexture);
        this.rotation = rotation;
        sprite.setRotation(rotation-90f);
    }

    public void update(float delta, SpriteBatch batch) {
        startPos.x += ARROW_SPEED * delta * (float) Math.cos(Math.toRadians(rotation));
        startPos.y += ARROW_SPEED * delta * (float) Math.sin(Math.toRadians(rotation));
        draw(batch);
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(startPos.x, startPos.y);
        sprite.draw(batch);
    }
}
