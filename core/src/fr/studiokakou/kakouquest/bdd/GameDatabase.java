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

	public void savePlayerStats(Player player) {
		loadDatabase();
		String sql = "INSERT INTO player(partie_id, player_name, hp, stamina, strength, speed, player_level, player_score) VALUES(?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setInt(1, player.getPartie_id());
			pstmt.setString(2, loadUsername());
			pstmt.setInt(3, player.getHp());
			pstmt.setInt(4, (int) player.getStamina());
			pstmt.setInt(5, player.getStrength());
			pstmt.setInt(6, (int) player.getSpeed());
			pstmt.setInt(7, player.getPlayerLevel());
			pstmt.setInt(8, player.getPlayerScore());


			System.out.println("player.getPartie_id() : " + player.getPartie_id());
			System.out.println("player.getPlayerName() : " + loadUsername());
			System.out.println("player.getHp() : " + player.getHp());
			System.out.println("player.getStamina() : " + player.getStamina());
			System.out.println("player.getStrength() : " + player.getStrength());
			System.out.println("player.getSpeed() : " + player.getSpeed());
			System.out.println("player.getPlayerLevel() : " + player.getPlayerLevel());
			System.out.println("player.getPlayerScore() : " + player.getPlayerScore());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void saveWeaponStats(Player player) {
		String sql = "INSERT INTO armes(id, name, dommage, durabilite, rarete) VALUES(?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setInt(1, player.getCurrentWeapon().getWeapon_id());
			pstmt.setString(2, player.getCurrentWeapon().getWeapon_name());
			pstmt.setInt(3, player.getCurrentWeapon().getWeapon_damage());
			pstmt.setInt(4, player.getCurrentWeapon().getWeapon_durability());
			pstmt.setInt(5, player.getCurrentWeapon().getRarety());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void saveAmeliorationStats(Player player) {
		String sql = "INSERT INTO ameliorations(max_hp, max_stamina, strength, speed, partie_id) VALUES(?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setInt(1, player.getMax_hp());
			pstmt.setString(2, String.valueOf(player.getMax_stamina()));
			pstmt.setInt(3, player.getStrength());
			pstmt.setInt(4, (int) player.getSpeed());
			pstmt.setInt(5, player.getPartie_id());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void saveGameStats(Player player) {
		String sql = "INSERT INTO Game(partie_id, game_level, game_score) VALUES(?, ?, ?)";

		try {
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setInt(1, player.getPartie_id());
			pstmt.setInt(2, InGameScreen.deepestLevel);
			pstmt.setInt(3, (int) player.getGameScore());
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
