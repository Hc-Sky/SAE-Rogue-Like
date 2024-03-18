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

import java.util.Random;

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

    public static Animation<TextureRegion> getAnimation(String textureName, int FRAME_COLS, int FRAME_ROWS, float frame_duration){
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

        return new Animation<TextureRegion>(frame_duration, textureRegions);
    }

    public static Animation<TextureRegion> getAnimationHorizontal(String textureName, int FRAME_COLS, int FRAME_ROWS, float frame_duration){
        Texture texture = new Texture(textureName);

        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        TextureRegion[] textureRegions = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
            for (int j = 0; j < FRAME_ROWS; j++) {
                textureRegions[index++] = tmp[j][i];
            }
        }

        return new Animation<TextureRegion>(frame_duration, textureRegions);
    }

    public static Animation<TextureRegion> getAnimationRevert(String textureName, int FRAME_COLS, int FRAME_ROWS){
        Texture texture = new Texture(textureName);

        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        TextureRegion[] textureRegions = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = FRAME_ROWS-1; i >= 0; i--) {
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
        Vector3 camPosVect = camera.unproject(new Vector3(p.x, p.y, 0));
        return new Point(camPosVect.x, camPosVect.y);
    }

    public static void markPoint(Point position, SpriteBatch batch){
        batch.draw(new Texture("assets/mark.png"), position.x, position.y);
    }

    public static Point mousePosUnproject(OrthographicCamera camera){
        Vector3 camPosVect = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Point(camPosVect.x, camPosVect.y);
    }

    public static Point getPointDirection(Point p1, Point p2, float speed){
        float totalDistance = (float) Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
        float ratio = speed / totalDistance;
        float x = ratio * p2.x + (1 - ratio) * p1.x;
        float y = ratio * p2.y + (1 - ratio) * p1.y;
        return new Point(x, y);
    }

    public static float getDistance(Point p1, Point p2){
        return (float) Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    public static int randint(int min, int max){
        return min+(int)(Math.random() * ((max - min) + 1));
    }

    public static float getAngleWithPoint(Point p1, Point p2){
        //retourne un angle en degree (float) qu'il y a du point p1 au point p2 par l'axe des abscisses dans le sens des aiguilles d'une montre
        return (float) Math.toDegrees(Math.atan2(p2.y - p1.y, p2.x - p1.x));
    }
}
