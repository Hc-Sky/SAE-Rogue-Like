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
 * Classe utilitaire.
 * Cette classe contient des méthodes utilitaires pour diverses tâches.
 *
 * @version 1.0
 */
public class Utils {

    /**
     * Récupère une animation à partir d'une sprite sheet.
     *
     * @param textureName Le nom de la texture
     * @param FRAME_COLS  Le nombre de colonnes de la sprite sheet
     * @param FRAME_ROWS  Le nombre de lignes de la sprite sheet
     * @return L'animation générée
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
     * Récupère une animation à partir d'une sprite sheet avec une durée de frame personnalisée.
     *
     * @param textureName    Le nom de la texture
     * @param FRAME_COLS     Le nombre de colonnes de la sprite sheet
     * @param FRAME_ROWS     Le nombre de lignes de la sprite sheet
     * @param frame_duration La durée de chaque frame
     * @return L'animation générée
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
     * Récupère une animation horizontale à partir d'une sprite sheet.
     *
     * @param textureName    Le nom de la texture
     * @param FRAME_COLS     Le nombre de colonnes de la sprite sheet
     * @param FRAME_ROWS     Le nombre de lignes de la sprite sheet
     * @param frame_duration La durée de chaque frame
     * @return L'animation générée
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
     * Récupère une animation inversée à partir d'une sprite sheet.
     *
     * @param textureName Le nom de la texture
     * @param FRAME_COLS  Le nombre de colonnes de la sprite sheet
     * @param FRAME_ROWS  Le nombre de lignes de la sprite sheet
     * @return L'animation inversée générée
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
     * Récupère la largeur d'une animation.
     *
     * @param animation L'animation
     * @return La largeur de l'animation
     */
    public static int getAnimationWidth(Animation<TextureRegion> animation){
        TextureRegion currentFrame = animation.getKeyFrame(0f, true);
        return currentFrame.getRegionWidth();
    }

    /**
     * Récupère la hauteur d'une animation.
     *
     * @param animation L'animation
     * @return La hauteur de l'animation
     */
    public static int getAnimationHeight(Animation<TextureRegion> animation){
        TextureRegion currentFrame = animation.getKeyFrame(0f, true);
        return currentFrame.getRegionHeight();
    }

    /**
     * Transforme les coordonnées d'un point en coordonnées non projetées.
     *
     * @param p      Le point à transformer
     * @param camera La caméra utilisée
     * @return Le point non projeté
     */
    public static Point getUnprojectPos(Point p, OrthographicCamera camera){
        Vector3 camPosVect = camera.unproject(new Vector3(p.x, p.y, 0));
        return new Point(camPosVect.x, camPosVect.y);
    }

    /**
     * Marque un point sur l'écran.
     *
     * @param position La position du point à marquer
     * @param batch    Le SpriteBatch utilisé pour le rendu
     */
    public static void markPoint(Point position, SpriteBatch batch){
        batch.draw(new Texture("assets/mark.png"), position.x, position.y);
    }

    /**
     * Marque un point sur l'écran de manière plus large.
     *
     * @param position La position du point à marquer
     * @param batch    Le SpriteBatch utilisé pour le rendu
     */
    public static void bigMarkPoint(Point position, SpriteBatch batch){
        batch.draw(new Texture("assets/mark.png"), position.x, position.y);
        batch.draw(new Texture("assets/mark.png"), position.x+1, position.y);
        batch.draw(new Texture("assets/mark.png"), position.x, position.y+1);
        batch.draw(new Texture("assets/mark.png"), position.x+1, position.y+1);
    }

    /**
     * Récupère la position de la souris non projetée.
     *
     * @param camera La caméra utilisée
     * @return La position de la souris non projetée
     */
    public static Point mousePosUnproject(OrthographicCamera camera){
        Vector3 camPosVect = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Point(camPosVect.x, camPosVect.y);
    }

    /**
     * Récupère la direction entre deux points.
     *
     * @param p1    Le premier point
     * @param p2    Le deuxième point
     * @param speed La vitesse
     * @return La direction entre les deux points
     */
    public static Point getPointDirection(Point p1, Point p2, float speed){
        float totalDistance = (float) Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
        float ratio = speed / totalDistance;
        float x = ratio * p2.x + (1 - ratio) * p1.x;
        float y = ratio * p2.y + (1 - ratio) * p1.y;
        return new Point(x, y);
    }

    /**
     * Calcule la distance entre deux points.
     *
     * @param p1 Le premier point
     * @param p2 Le deuxième point
     * @return La distance entre les deux points
     */
    public static float getDistance(Point p1, Point p2){
        return (float) Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    /**
     * Génère un nombre aléatoire dans un intervalle donné.
     *
     * @param min La borne inférieure de l'intervalle
     * @param max La borne supérieure de l'intervalle
     * @return Le nombre aléatoire généré
     */
    public static int randint(int min, int max){
        return min+(int)(Math.random() * ((max - min) + 1));
    }

    /**
     * Calcule l'angle entre deux points en degrés.
     *
     * @param p1 Le premier point
     * @param p2 Le deuxième point
     * @return L'angle entre les deux points en degrés
     */
    public static float getAngleWithPoint(Point p1, Point p2){
        return (float) Math.toDegrees(Math.atan2(p2.y - p1.y, p2.x - p1.x));
    }

    /**
     * Calcule la distance entre une position et la position du joueur.
     *
     * @param pos       La position
     * @param playerPos La position du joueur
     * @return La distance entre la position et la position du joueur
     */
    public static int distance(Point pos, Point playerPos) {
        return (int) Math.sqrt(Math.pow(playerPos.x - pos.x, 2) + Math.pow(playerPos.y - pos.y, 2));
    }
}
