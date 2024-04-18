package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.GameSpace;
import static fr.studiokakou.kakouquest.utils.Utils.getAnimationHorizontal;

public class HelpScreen implements Screen {
    /**
     * Espace de jeu.
     */
    GameSpace game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    public Animation<TextureRegion> interactKeyAnimation;

    public HelpScreen(GameSpace game){
        this.game = game;
        batch = new SpriteBatch();
        font = game.font;
        this.interactKeyAnimation = getAnimationHorizontal("assets/keys/animated/ARROWLEFT.png", 2, 1, 1f);
        // Taille du texte
        font.getData().setScale(1.5f);
        // Couleur du texte
        font.setColor(Color.WHITE);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        // Retour au menu principal en appuyant sur Echap
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            game.setScreen(new MenuScreen(game));
        }
        /*
        Coordonnées pour le texte :
         */
        int xposText = 60;
        int yposText = Gdx.graphics.getHeight() - 60;
        /*
          Initialisation de l'écran
         */
        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        // Texte des règles du jeu
        // ATTENTION AUX CARACTERES SPECIAUX COMME LES ACCENTS!
        font.draw(game.batch, "# Principe du jeu\n\n" +
                "Le but du jeu Kakou Quest est, \u00E0 la mani\u00E8re d'un Roguelike, de s'am\u00E9liorer en tant que joueur en combattant des monstres. Il faut descendre d'\u00E9tages en \u00E9tages et aller le plus \n" +
                "bas possible tout en am\u00E9liorant son joueur et son \u00E9quipement et combattant des monstres et des boss. \n\n" +
                "Le jeu utilise un syst\u00E8me de mort permanente qui fait recommencer au premier niveau le joueur \u00E0 chaque fois qu'il meurt et lui fait perdre son \u00E9quipement mais lui laisse tout \n" +
                " de même ses améliorations de statistiques faites durant la partie. Ceci permettra au joueur de s'améliorer à chaque mort et lui permettra aussi d'avancer de plus en plus loin dans \n" +
                " un jeu qui se veut difficile.\n\n" +
                "\u00C0 chaque fois que le joueur tues un monstre ou passe un \u00E9tage, il r\u00E9cup\u00E8re des points d'exp\u00E9rience qui lui permettent de passer des niveau et donc d'am\u00E9liorer une statistique \n" +
                " au choix parmi sa vie, sa force, sa vitesse et son \u00E9nergie.\n\n" +
                "Les combats de boss, quant \u00E0 eux, permettent au joueur de gagner beaucoup plus de points d'exp\u00E9rience et d'\u00E9quipement rare que le reste des coffres et des monstres mais \n" +
                "seront bien plus dur a terminer. \n\n\n\n\n" +
                "# D\u00E9roulement d'une partie\n\n" +
                "Apr\u00E8s avoir choisit, \u00E0 l'aide du menu principal, de commencer une nouvelle partie en solo ou de rejoindre une partie multijoueur, le ou les joueurs commencent au premier \n" +
                "\u00E9tage (qui est l'\u00E9tage le plus haut) avec uniquement l'arme de d\u00E9part qui est la moins forte (Rusty Sword) et avec uniquement les stats de base (voir partie Statistiques). \n\n" +
                "Ils apparaissent donc dans la premi\u00E8re salle de la map g\u00E9n\u00E9r\u00E9e al\u00E9atoirement et doivent la parcourir afin de trouver l'\u00E9chelle pour atteindre le prochain \u00E9tage. Au cours de \n" +
                "leur trajet, les joueurs devront donc tuer des monstres et peuvent trouver des coffres contenant des armes plus puissantes que celle de d\u00E9part mais qui ne poss\u00E8dent pas une \n" +
                "r\u00E9sistance illimit\u00E9e. \n\nLe joueur poss\u00E8de aussi un arc qu'il peut utiliser pour attaquer des monstres de loin mais ses fl\u00E8ches ne sont pas illimit\u00E9es non plus. Il peut cependant \n" +
                "en trouver dans des coffres ou en acheter \u00E0 des marchands.\n\nDe plus, le joueur poss\u00E8de aussi des sorts qu'il peut utiliser pour effectuer diff\u00E9rentes actions. Enfin, le joueur \n" +
                "peut trouver des potions dans des coffres qu'il peut utiliser pour toutes sortes de choses comme r\u00E9g\u00E9n\u00E9rer sa vie ou son \u00E9nergie.\n\n\n" +
                "Appuyez sur Echap pour revenir au menu principal...", xposText, yposText);

        TextureRegion currentKeyFrame = this.interactKeyAnimation.getKeyFrame(InGameScreen.stateTime, true);
        game.batch.draw(currentKeyFrame, 620, 50, 70, 70);
        if (Gdx.input.getX() < 620 + 70 &&
                Gdx.input.getX() > 620 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < 50 + 70 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > 50) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }

        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
