package fr.studiokakou.kakouquest.weapon;

import java.util.WeakHashMap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class MeleeWeapon {

	public String name;
	public Texture texture;
	public Sprite sprite;
	public int damage;
	public int resistance;
	public float attackRange;
	public float attackSpeed;
	public float size;

	public MeleeWeapon(String name, String texturePath, int damage, int resistance, float attackRange, float attackSpeed, float size) {
		this.name = name;
		this.texture = new Texture(texturePath);
		this.sprite = new Sprite(new TextureRegion(this.texture));
		this.damage = damage;
		this.resistance = resistance;
		this.attackRange = attackRange;
		this.attackSpeed = attackSpeed;
		this.size = size;
	}

	public static MeleeWeapon ANIME_SWORD = new MeleeWeapon("Anime sword", "asset/weapon/weapon_anime_sword.png", 10, 0, 90, 0.5f, 1);	public static Weapon ARROW = new Weapon("Arrow", "asset/weapon/weapon_arrow.png", 5, 0, 180, 0.5f, 1);
	public static MeleeWeapon AXE = new MeleeWeapon("Axe", "asset/weapon/weapon_axe.png", 15, 0, 90, 0.5f, 1);
	public static MeleeWeapon BATON_WITH_SPIKES = new MeleeWeapon("Baton with spikes", "asset/weapon/weapon_baton_with_spikes.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon BIG_HAMMER = new MeleeWeapon("Big hammer", "asset/weapon/weapon_big_hammer.png", 20, 0, 90, 0.5f, 1);
	public static MeleeWeapon CLEAVER = new MeleeWeapon("Cleaver", "asset/weapon/weapon_cleaver.png", 15, 0, 90, 0.5f, 1);
	public static MeleeWeapon DOUBLE_AXE = new MeleeWeapon("Double axe", "asset/weapon/weapon_double_axe.png", 20, 0, 90, 0.5f, 1);
	public static MeleeWeapon DUEL_SWORD = new MeleeWeapon("Duel sword", "asset/weapon/weapon_duel_sword.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon GOLDEN_SWORD = new MeleeWeapon("Golden sword", "asset/weapon/weapon_golden_sword.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon HAMMER = new MeleeWeapon("Hammer", "asset/weapon/weapon_hammer.png", 15, 0, 90, 0.5f, 1);
	public static MeleeWeapon KATANA = new MeleeWeapon("Katana", "asset/weapon/weapon_katana.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon KNIFE = new MeleeWeapon("Knife", "asset/weapon/weapon_knife.png", 5, 0, 90, 0.5f, 1);
	public static MeleeWeapon KNIGHT_SWORD = new MeleeWeapon("Knight sword", "asset/weapon/weapon_knight_sword.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon LAVISH_SWORD = new MeleeWeapon("Lavish sword", "asset/weapon/weapon_lavish_sword.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon MACE = new MeleeWeapon("Mace", "asset/weapon/weapon_mace.png", 15, 0, 90, 0.5f, 1);
	public static MeleeWeapon MACHETE = new MeleeWeapon("Machete", "asset/weapon/weapon_machete.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon RED_GEM_SWORD = new MeleeWeapon("Red gem sword", "asset/weapon/weapon_red_gem_sword.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon REGULAR_SWORD = new MeleeWeapon("Regular sword", "asset/weapon/weapon_regular_sword.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon RUSTY_SWORD = new MeleeWeapon("Rusty sword", "asset/weapon/weapon_rusty_sword.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon SAW_SWORD = new MeleeWeapon("Saw sword", "asset/weapon/weapon_saw_sword.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon THROWING_AXE = new MeleeWeapon("Throwing axe", "asset/weapon/weapon_throwing_axe.png", 10, 0, 90, 0.5f, 1);
	public static MeleeWeapon WARAXE = new MeleeWeapon("Waraxe", "asset/weapon/weapon_waraxe.png", 15, 0, 90, 0.5f, 1);


	//Asset non melleWeapon a Arc et Magie
	/*public static Weapon BOW = new Weapon("Bow", "asset/weapon/weapon_bow.png", 5, 0, 180, 0.5f, 1);
	public static Weapon BOW_2 = new Weapon("Bow 2", "asset/weapon/weapon_bow_2.png", 5, 0, 180, 0.5f, 1);
	public static Weapon SPEAR = new Weapon("Spear", "asset/weapon/weapon_spear.png", 10, 0, 90, 0.5f, 1);
	public static Weapon RED_MAGIC_STAFF = new Weapon("Red magic staff", "asset/weapon/weapon_red_magic_staff.png", 5, 0, 180, 0.5f, 1);
	public static Weapon GREEN_MAGIC_STAFF = new Weapon("Green magic staff", "asset/weapon/weapon_green_magic_staff.png", 5, 0, 180, 0.5f, 1);*/



}
