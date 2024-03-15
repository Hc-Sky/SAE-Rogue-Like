package fr.studiokakou.kakouquest.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.screens.InGameScreen;

public class Utils {
    public static Animation<TextureRegion> getAnimation(String textureName, int FRAME_COLS, int FRAME_ROWS){
        Texture texture = new Texture(textureName);

        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        TextureRegion[] textureRegions = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                textureRegions[index++] = tmp[i][j];
            }
        }

        return new Animation<TextureRegion>(InGameScreen.FRAME_DURATION, textureRegions);
    }

    public static int getAnimationWidth(Animation<TextureRegion> animation){
        TextureRegion currentFrame = animation.getKeyFrame(0f, true);
        return currentFrame.getRegionWidth();
    }

    public static int getAnimationHeight(Animation<TextureRegion> animation){
        TextureRegion currentFrame = animation.getKeyFrame(0f, true);
        return currentFrame.getRegionHeight();
    }

    public static Point getUnprojectPos(Point p, OrthographicCamera camera){
        Vector3 camPosVect = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Point(camPosVect.x, camPosVect.y);
    }

    public static void markPoint(Point position, SpriteBatch batch){
        batch.draw(new Texture("assets/mark.png"), position.x, position.y);
    }
}
