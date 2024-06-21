package fr.studiokakou.kakouquest.bdd;

import fr.studiokakou.kakouquest.GetCoreProperties;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.InGameScreen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDatabase {
	private Connection connexion;

	private void loadDatabase() {
		// Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		try {
			connexion = DriverManager.getConnection("jdbc:mysql://165.232.124.186:3306/studio_kakou", "root", "StudioKakou06!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void savePlayerStats(Player player, int id) {
		loadDatabase();
		String sql = "INSERT INTO player(id, partie_id, player_name, hp, stamina, strength, speed, player_level, player_score) VALUES(?, ?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setInt(2, id);
			pstmt.setString(3, loadUsername());
			pstmt.setInt(4, player.getHp());
			pstmt.setInt(5, (int) player.getStamina());
			pstmt.setInt(6, player.getStrength());
			pstmt.setInt(7, (int) player.getSpeed());
			pstmt.setInt(8, player.getPlayerLevel());
			pstmt.setInt(9, player.getPlayerScore());


			//System.out.println("player.getPartie_id() : " + id);
			//System.out.println("player.getPlayerName() : " + loadUsername());
			//System.out.println("player.getHp() : " + player.getHp());
			//System.out.println("player.getStamina() : " + player.getStamina());
			//System.out.println("player.getStrength() : " + player.getStrength());
			//System.out.println("player.getSpeed() : " + player.getSpeed());
			//System.out.println("player.getPlayerLevel() : " + player.getPlayerLevel());
			//System.out.println("player.getPlayerScore() : " + player.getPlayerScore());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void saveWeaponStats(Player player, int id) {
		String sql = "INSERT INTO armes(player_id, name, dommage, durabilite, rarete) VALUES(?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, player.getCurrentWeapon().getWeapon_name());
			pstmt.setInt(3, player.getCurrentWeapon().getWeapon_damage());
			pstmt.setInt(4, player.getCurrentWeapon().getWeapon_durability());
			pstmt.setInt(5, player.getCurrentWeapon().getRarety());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void saveAmeliorationStats(Player player, int id) {
		String sql = "INSERT INTO ameliorations(max_hp, max_stamina, strength, speed, player_id) VALUES(?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setInt(1, player.getMax_hp());
			pstmt.setString(2, String.valueOf(player.getMax_stamina()));
			pstmt.setInt(3, player.getStrength());
			pstmt.setInt(4, (int) player.getSpeed());
			pstmt.setInt(5, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void saveGameStats(Player player, int id) {
		String sql = "INSERT INTO game(id, game_level, game_score) VALUES(?, ?, ?)";

		try {
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setInt(2, InGameScreen.deepestLevel);
			pstmt.setInt(3, InGameScreen.score);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void closeConnection() {
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private String loadUsername() {
		if (GetCoreProperties.getStringProperty("USERNAME") == null || GetCoreProperties.getStringProperty("USERNAME").isEmpty()) {
			return "guest";
		}
		return GetCoreProperties.getStringProperty("USERNAME");
	}


}
