package fr.studiokakou.kakouquest.interactive;

//en t'aider de la classe Chest créer un class Stairs Lorsque l’on interagit avec, elle augmente le currentLevel de InGameScreen et appel la fonction nextLevel de InGameScreen qui s’occupe de générer une nouvelle map et de faire respawn le player au bon endroit

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

// La position des stairs doit etre au centre de la dernière room dans la liste des rooms de map. sa Texture est l’échelle qui rentre dans le sol (assets/map/floor_ladder.png).

public class Stairs {
	Point pos;

	boolean canInteract = false;

	TextureRegion stairs;

	//interact var
	String interactKey;
	int interactKeyCode;
	Animation<TextureRegion> interactKeyAnimation;

	public Stairs(Point pos){
		this.pos = pos;

		this.stairs = new TextureRegion(new Texture("assets/map/floor_ladder.png"));

		this.interactKeyCode = GetProperties.getIntProperty("KEY_INTERRACT");
		this.interactKey = Input.Keys.toString(this.interactKeyCode);

		this.interactKeyAnimation = Utils.getAnimationHorizontal("assets/keys/animated/"+this.interactKey+".png", 2, 1, 40f);
	}

	public void refreshInteract(Player player){
		if (!this.canInteract){
			if (player.pos.equals(this.pos)){
				this.canInteract = true;
			}
		}else{
			if (!player.pos.equals(this.pos)){
				this.canInteract = false;
			}
		}
	}

	public void interact(Player player){
		if (this.canInteract){
			InGameScreen.currentLevel++;
			InGameScreen.nextLevel();
		}
	}

	public void draw(SpriteBatch batch){
		if (canInteract){
			TextureRegion currentKeyFrame = this.interactKeyAnimation.getKeyFrame(InGameScreen.stateTime, true);
			batch.draw(currentKeyFrame, this.pos.x, this.pos.y+20, Floor.TEXTURE_WIDTH, Floor.TEXTURE_HEIGHT);
		}
	}
}



