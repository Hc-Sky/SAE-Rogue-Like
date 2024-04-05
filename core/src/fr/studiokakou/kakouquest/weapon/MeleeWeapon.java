package fr.studiokakou.kakouquest.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;


/**
 * This class represents a MeleeWeapon in the game.
 * A MeleeWeapon has a name, texture, stats, dimensions, and a list of possible MeleeWeapons.
 */
public class MeleeWeapon {

	public String name;

	//weapon texture
	public String texturePath;
	public Texture texture;
	public Sprite sprite;

	//weapon stats
	public int damage;
	public int resistance;
	public float attackRange;
	public float attackSpeed;
	public int maxResistance;

	//weapon dimensions
	public float size;
	public float height;
	public float width;

	public static Dictionary<Integer, ArrayList<MeleeWeapon>> possibleMeleeWeapon = new Hashtable<>();

	/**
	 * Constructs a MeleeWeapon with the given parameters.
	 * @param name The name of the weapon.
	 * @param texturePath The path to the texture of the weapon.
	 * @param damage The damage of the weapon.
	 * @param resistance The resistance of the weapon.
	 * @param attackRange The attack range of the weapon.
	 * @param attackSpeed The attack speed of the weapon.
	 * @param size The size of the weapon.
	 */
	public MeleeWeapon(String name, String texturePath, int damage, int resistance, float attackRange, float attackSpeed, float size) {
		//weapon stats
		this.name = name;
		this.damage = damage;
		this.maxResistance = resistance;
		this.resistance = resistance;
		this.attackRange = attackRange;
		this.attackSpeed = attackSpeed;
		this.size = size;

		//weapon texture
		this.texturePath = texturePath;
		this.texture = new Texture(texturePath);
		this.sprite = new Sprite(this.texture);
		this.sprite.setScale(this.size);
		this.height = this.sprite.getHeight();
		this.width = this.sprite.getWidth();
		this.sprite.setOrigin(this.width/2, 0);
		this.sprite.flip(true, false);
	}

	/**
	 * Returns a new MeleeWeapon with the same parameters as this MeleeWeapon.
	 * @return A new MeleeWeapon.
	 */
	public MeleeWeapon getNew(){
		return new MeleeWeapon(this.name, this.texturePath, damage, maxResistance, attackRange, attackSpeed, size);
	}

	/**
	 * Creates the possible MeleeWeapons and adds them to the possibleMeleeWeapon dictionary.
	 */
	public static void createPossibleMeleeWeapons(){
		possibleMeleeWeapon = new Hashtable<>();
		possibleMeleeWeapon.put(1, new ArrayList<>());
		possibleMeleeWeapon.put(2, new ArrayList<>());
		possibleMeleeWeapon.put(3, new ArrayList<>());
		possibleMeleeWeapon.put(4, new ArrayList<>());
		possibleMeleeWeapon.put(5, new ArrayList<>());
		possibleMeleeWeapon.put(6, new ArrayList<>());
		possibleMeleeWeapon.put(7, new ArrayList<>());
		possibleMeleeWeapon.put(8, new ArrayList<>());
		possibleMeleeWeapon.put(9, new ArrayList<>());
		possibleMeleeWeapon.put(10, new ArrayList<>());

		possibleMeleeWeapon.get(8).add(ANIME_SWORD());
		possibleMeleeWeapon.get(6).add(BATON_WITH_SPIKES());
		possibleMeleeWeapon.get(8).add(BIG_HAMMER());
		possibleMeleeWeapon.get(5).add(CLEAVER());
		possibleMeleeWeapon.get(6).add(DOUBLE_AXE());
		possibleMeleeWeapon.get(4).add(DUEL_SWORD());
		possibleMeleeWeapon.get(10).add(GOLDEN_SWORD());
		possibleMeleeWeapon.get(3).add(HAMMER());
		possibleMeleeWeapon.get(8).add(KATANA());
		possibleMeleeWeapon.get(1).add(KNIFE());
		possibleMeleeWeapon.get(4).add(KNIGHT_SWORD());
		possibleMeleeWeapon.get(10).add(LAVISH_SWORD());
		possibleMeleeWeapon.get(6).add(MACE());
		possibleMeleeWeapon.get(3).add(MACHETE());
		possibleMeleeWeapon.get(9).add(RED_GEM_SWORD());
		possibleMeleeWeapon.get(3).add(REGULAR_SWORD());
		possibleMeleeWeapon.get(4).add(SAW_SWORD());
		possibleMeleeWeapon.get(1).add(THROWING_AXE());
		possibleMeleeWeapon.get(4).add(WARAXE());
	}


	//static weapons for better optimisation
	public static MeleeWeapon ANIME_SWORD () {return new MeleeWeapon("Anime sword", "assets/weapon/weapon_anime_sword.png", 80, 100, 140, 0.6f, 1);}
	public static MeleeWeapon BATON_WITH_SPIKES () {return new MeleeWeapon("Baton with spikes", "assets/weapon/weapon_baton_with_spikes.png", 40, 35, 150, 0.2f, 1.2f);}
	public static MeleeWeapon BIG_HAMMER () {return new MeleeWeapon("Big hammer", "assets/weapon/weapon_big_hammer.png", 70, 55, 160, 0.3f, 1.5f);}
	public static MeleeWeapon CLEAVER () {return new MeleeWeapon("Cleaver", "assets/weapon/weapon_cleaver.png", 20, 60, 100, 0.6f, 1);}
	public static MeleeWeapon DOUBLE_AXE () {return new MeleeWeapon("Double axe", "assets/weapon/weapon_double_axe.png", 50, 60, 180, 0.4f, 1);}
	public static MeleeWeapon DUEL_SWORD () {return new MeleeWeapon("Duel sword", "assets/weapon/weapon_duel_sword.png", 35, 70, 100, 0.9f, 1);}
	public static MeleeWeapon GOLDEN_SWORD () {return new MeleeWeapon("Golden sword", "assets/weapon/weapon_golden_sword.png", 100, 100, 120, 0.6f, 1);}
	public static MeleeWeapon HAMMER () {return new MeleeWeapon("Hammer", "assets/weapon/weapon_hammer.png", 40, 40, 90, 0.5f, 1);}
	public static MeleeWeapon KATANA () {return new MeleeWeapon("Katana", "assets/weapon/weapon_katana.png", 50, 100, 130, 0.7f, 1);}
	public static MeleeWeapon KNIFE () {return new MeleeWeapon("Knife", "assets/weapon/weapon_knife.png", 12, 40, 90, 0.7f, 1);}
	public static MeleeWeapon KNIGHT_SWORD () {return new MeleeWeapon("Knight sword", "assets/weapon/weapon_knight_sword.png", 35, 80, 110, 0.5f, 1);}
	public static MeleeWeapon LAVISH_SWORD () {return new MeleeWeapon("Lavish sword", "assets/weapon/weapon_lavish_sword.png", 90, 120, 120, 0.8f, 1);}
	public static MeleeWeapon MACE () {return new MeleeWeapon("Mace", "assets/weapon/weapon_mace.png", 60, 30, 180, 0.3f, 1);}
	public static MeleeWeapon MACHETE () {return new MeleeWeapon("Machete", "assets/weapon/weapon_machete.png", 20, 40, 90, 0.5f, 1);}
	public static MeleeWeapon RED_GEM_SWORD () {return new MeleeWeapon("Red gem sword", "assets/weapon/weapon_red_gem_sword.png", 70, 120, 100, 0.5f, 1);}
	public static MeleeWeapon REGULAR_SWORD () {return new MeleeWeapon("Regular sword", "assets/weapon/weapon_regular_sword.png", 25, 70, 110, 0.5f, 1);}
	public static MeleeWeapon RUSTY_SWORD () {return new MeleeWeapon("Rusty sword", "assets/weapon/weapon_rusty_sword.png", 10, -1000, 90, 0.5f, 1);}
	public static MeleeWeapon SAW_SWORD () {return new MeleeWeapon("Saw sword", "assets/weapon/weapon_saw_sword.png", 65, 80, 120, 0.5f, 1);}
	public static MeleeWeapon THROWING_AXE () {return new MeleeWeapon("Throwing axe", "assets/weapon/weapon_throwing_axe.png", 15, 40, 90, 0.7f, 1);}
	public static MeleeWeapon WARAXE () {return new MeleeWeapon("Waraxe", "assets/weapon/weapon_waraxe.png", 45, 60, 120, 0.4f, 1.3f);}

	//dev weapon
	public static MeleeWeapon DEV_SWORD () {return new MeleeWeapon("Dev sword", "assets/weapon/weapon_golden_sword.png", 200, -1, 120, 0.7f, 1);}

}