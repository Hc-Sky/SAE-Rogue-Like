package fr.studiokakou.kakouquest.interactive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.GetProperties;
import fr.studiokakou.kakouquest.map.Floor;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;

/**
 * La classe Stairs représente un objet qui permet de changer de niveau dans le jeu.
 * Lorsque le joueur interagit avec les escaliers, le niveau actuel est augmenté et une nouvelle carte est générée,
 * tout en restaurant le joueur à l'endroit approprié.
 */
public class Stairs {
    /** Position des escaliers sur la carte. */
    public Point pos;

    /** Indique si le joueur peut interagir avec les escaliers. */
    boolean canInteract = false;

    /** Texture représentant les escaliers. */
    Texture texture;

    /** Référence à l'écran de jeu dans lequel les escaliers sont utilisés. */
    InGameScreen gameScreen;

    //interact var
    /** Touche pour interagir avec les escaliers. */
    String interactKey;

    /** Code de la touche pour interagir avec les escaliers. */
    int interactKeyCode;

    /** Animation de la touche pour interagir avec les escaliers. */
    Animation<TextureRegion> interactKeyAnimation;

    /**
     * Constructeur de la classe Stairs.
     * @param pos Position des escaliers sur la carte.
     * @param gameScreen Référence à l'écran de jeu dans lequel les escaliers sont utilisés.
     */
    public Stairs(Point pos, InGameScreen gameScreen){
        this.pos = pos;
        this.gameScreen = gameScreen;
        this.texture = new Texture("assets/map/floor_ladder.png");

        this.interactKeyCode = GetProperties.getIntProperty("KEY_INTERRACT");
        this.interactKey = Input.Keys.toString(this.interactKeyCode);

        this.interactKeyAnimation = Utils.getAnimationHorizontal("assets/keys/animated/"+this.interactKey+".png", 2, 1, 1f);
    }

    /**
     * Met à jour la possibilité d'interaction avec les escaliers en fonction de la position du joueur.
     * @param player Joueur actuel.
     * @param isClosest Indique si le joueur est le plus proche des escaliers.
     */
    public void refreshInteract(Player player, boolean isClosest){

        if (this.canInteract && Gdx.input.isKeyJustPressed(this.interactKeyCode)){
            this.interact();
        }

        if (Utils.getDistance(this.pos, player.pos) <= 40 &&  isClosest){
            this.canInteract = true;
        } else {
            this.canInteract = false;
        }
    }

    /** Effectue l'interaction avec les escaliers, déclenchant le passage au niveau suivant. */
    public void interact(){
        if (this.canInteract){
            this.gameScreen.nextLevel();
        }
    }

    /**
     * Dessine les escaliers sur l'écran de jeu.
     * @param batch Batch pour dessiner les textures.
     */
    public void draw(SpriteBatch batch){
        if (canInteract){
            TextureRegion currentKeyFrame = this.interactKeyAnimation.getKeyFrame(InGameScreen.stateTime, true);
            batch.draw(currentKeyFrame, this.pos.x, this.pos.y+20, Floor.TEXTURE_WIDTH, Floor.TEXTURE_HEIGHT);
        }

        batch.draw(this.texture, this.pos.x, this.pos.y);
    }
}
