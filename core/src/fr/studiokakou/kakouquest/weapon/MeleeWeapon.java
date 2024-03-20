package fr.studiokakou.kakouquest.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class MeleeWeapon {

	public String name;

	//weapon texture
	public Texture texture;
	public Sprite sprite;

	//weapon stats
	public int damage;
	public int resistance;
	public float attackRange;
	public float attackSpeed;

	//weapon dimensions
	public float size;
	public float height;
	public float width;

	public MeleeWeapon(String name, String texturePath, int damage, int resistance, float attackRange, float attackSpeed, float size) {
		//weapon stats
		this.name = name;
		this.damage = damage;
		this.resistance = resistance;
		this.attackRange = attackRange;
		this.attackSpeed = attackSpeed;
		this.size = size;

		//weapon texture
		this.texture = new Texture(texturePath);
		this.sprite = new Sprite(this.texture);
		this.sprite.setScale(this.size);
		this.height = this.sprite.getHeight();
		this.width = this.sprite.getWidth();
		this.sprite.setOrigin(this.width/2, 0);
	}

	public static MeleeWeapon ANIME_SWORD () {return new MeleeWeapon("Anime sword", "assets/weapon/weapon_anime_sword.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon BATON_WITH_SPIKES () {return new MeleeWeapon("Baton with spikes", "assets/weapon/weapon_baton_with_spikes.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon BIG_HAMMER () {return new MeleeWeapon("Big hammer", "assets/weapon/weapon_big_hammer.png", 20, 0, 120, 0.3f, 3);}
	public static MeleeWeapon CLEAVER () {return new MeleeWeapon("Cleaver", "assets/weapon/weapon_cleaver.png", 15, 0, 90, 0.5f, 1);}
	public static MeleeWeapon DOUBLE_AXE () {return new MeleeWeapon("Double axe", "assets/weapon/weapon_double_axe.png", 20, 0, 90, 0.5f, 1);}
	public static MeleeWeapon DUEL_SWORD () {return new MeleeWeapon("Duel sword", "assets/weapon/weapon_duel_sword.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon GOLDEN_SWORD () {return new MeleeWeapon("Golden sword", "assets/weapon/weapon_golden_sword.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon HAMMER () {return new MeleeWeapon("Hammer", "assets/weapon/weapon_hammer.png", 15, 0, 90, 0.5f, 1);}
	public static MeleeWeapon KATANA () {return new MeleeWeapon("Katana", "assets/weapon/weapon_katana.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon KNIFE () {return new MeleeWeapon("Knife", "assets/weapon/weapon_knife.png", 5, 0, 90, 0.5f, 1);}
	public static MeleeWeapon KNIGHT_SWORD () {return new MeleeWeapon("Knight sword", "assets/weapon/weapon_knight_sword.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon LAVISH_SWORD () {return new MeleeWeapon("Lavish sword", "assets/weapon/weapon_lavish_sword.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon MACE () {return new MeleeWeapon("Mace", "assets/weapon/weapon_mace.png", 15, 0, 90, 0.5f, 1);}
	public static MeleeWeapon MACHETE () {return new MeleeWeapon("Machete", "assets/weapon/weapon_machete.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon RED_GEM_SWORD () {return new MeleeWeapon("Red gem sword", "assets/weapon/weapon_red_gem_sword.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon REGULAR_SWORD () {return new MeleeWeapon("Regular sword", "assets/weapon/weapon_regular_sword.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon RUSTY_SWORD () {return new MeleeWeapon("Rusty sword", "assets/weapon/weapon_rusty_sword.png", 10, -1, 90, 0.5f, 1);}
	public static MeleeWeapon SAW_SWORD () {return new MeleeWeapon("Saw sword", "assets/weapon/weapon_saw_sword.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon THROWING_AXE () {return new MeleeWeapon("Throwing axe", "assets/weapon/weapon_throwing_axe.png", 10, 0, 90, 0.5f, 1);}
	public static MeleeWeapon WARAXE () {return new MeleeWeapon("Waraxe", "assets/weapon/weapon_waraxe.png", 15, 0, 90, 0.5f, 1);}

	//dev weapon
	public static MeleeWeapon DEV_SWORD () {return new MeleeWeapon("Dev sword", "assets/weapon/weapon_golden_sword.png", 50, -1, 120, 0.7f, 1);}

}
