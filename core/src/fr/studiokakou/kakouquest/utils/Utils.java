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

/**
 * The Utils class.
 * Used to store every utility static functions that does not link to a specific class
 * @version 1.0
 */
public class Utils {

    /**
     * Retrieves the animation from a sprite sheet. The function cuts the sprite sheet into multiple frames.
     *
     * @param textureName the texture name
     * @param FRAME_COLS  the frame cols
     * @param FRAME_ROWS  the frame rows
     * @return the animation
     */
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

    /**
     * Retrieves an animation with a texture name, number of columns, number of rows, and frame duration.
     *
     * @param textureName    the texture name
     * @param FRAME_COLS     the frame cols
     * @param FRAME_ROWS     the frame rows
     * @param frame_duration the frame duration
     * @return the animation
     */
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

    /**
     * Retrieves the horizontal of an animation.
     *
     * @param textureName    the texture name
     * @param FRAME_COLS     the frame cols
     * @param FRAME_ROWS     the frame rows
     * @param frame_duration the frame duration
     * @return the animation
     */
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

    /**
     * Retrieves an animation in reverse order.
     *
     * @param textureName the texture name
     * @param FRAME_COLS  the frame cols
     * @param FRAME_ROWS  the frame rows
     * @return the animation
     */
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

    /**
     * Retrieves the width of an animation.
     *
     * @param animation the animation
     * @return the int
     */
    public static int getAnimationWidth(Animation<TextureRegion> animation){
        TextureRegion currentFrame = animation.getKeyFrame(0f, true);
        return currentFrame.getRegionWidth();
    }

    /**
     * Retrieves the height of an animation.
     *
     * @param animation the animation
     * @return the int
     */
    public static int getAnimationHeight(Animation<TextureRegion> animation){
        TextureRegion currentFrame = animation.getKeyFrame(0f, true);
        return currentFrame.getRegionHeight();
    }

    /**
     * Get unproject pos point.
     *
     * @param p      the p
     * @param camera the camera
     * @return the point
     */
    public static Point getUnprojectPos(Point p, OrthographicCamera camera){
        Vector3 camPosVect = camera.unproject(new Vector3(p.x, p.y, 0));
        return new Point(camPosVect.x, camPosVect.y);
    }

    /**
     * Mark point.
     *
     * @param position the position
     * @param batch    the batch
     */
    public static void markPoint(Point position, SpriteBatch batch){
        batch.draw(new Texture("assets/mark.png"), position.x, position.y);
    }

    public static void bigMarkPoint(Point position, SpriteBatch batch){
        batch.draw(new Texture("assets/mark.png"), position.x, position.y);
        batch.draw(new Texture("assets/mark.png"), position.x+1, position.y);
        batch.draw(new Texture("assets/mark.png"), position.x, position.y+1);
        batch.draw(new Texture("assets/mark.png"), position.x+1, position.y+1);
    }

    /**
     * The mouse position.
     *
     * @param camera the camera
     * @return the point
     */
    public static Point mousePosUnproject(OrthographicCamera camera){
        Vector3 camPosVect = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Point(camPosVect.x, camPosVect.y);
    }

    /**
     * The direction between two points.
     *
     * @param p1    the p 1
     * @param p2    the p 2
     * @param speed the speed
     * @return the point
     */
    public static Point getPointDirection(Point p1, Point p2, float speed){
        float totalDistance = (float) Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
        float ratio = speed / totalDistance;
        float x = ratio * p2.x + (1 - ratio) * p1.x;
        float y = ratio * p2.y + (1 - ratio) * p1.y;
        return new Point(x, y);
    }

    /**
     * The distance between two points.
     *
     * @param p1 the p 1
     * @param p2 the p 2
     * @return the float
     */
    public static float getDistance(Point p1, Point p2){
        return (float) Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    /**
     * Random int.
     *
     * @param min the min
     * @param max the max
     * @return the int
     */
    public static int randint(int min, int max){
        return min+(int)(Math.random() * ((max - min) + 1));
    }

    /**
     * Retrieves the angle in degrees between two points.
     *
     * @param p1 the p 1
     * @param p2 the p 2
     * @return the float
     */
    public static float getAngleWithPoint(Point p1, Point p2){
        return (float) Math.toDegrees(Math.atan2(p2.y - p1.y, p2.x - p1.x));
    }

    /**
     * Retrieves the distance between a position and the player.
     *
     * @param pos       the pos
     * @param playerPos the player pos
     * @return the int
     */
    public static int distance(Point pos, Point playerPos) {
        return (int) Math.sqrt(Math.pow(playerPos.x - pos.x, 2) + Math.pow(playerPos.y - pos.y, 2));
    }
}
