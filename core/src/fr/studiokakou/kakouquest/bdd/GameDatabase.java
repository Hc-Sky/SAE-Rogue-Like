package fr.studiokakou.kakouquest.bdd;

import fr.studiokakou.kakouquest.player.Player;

import java.sql.*;

public class GameDatabase {
	private Connection connexion;

	private void loadDatabase() {
		// Chargement du driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}

		try {
			connexion = DriverManager.getConnection("jdbc:mysql://165.232.124.186:3306/studio_kakou", "root", "********");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void savePlayerStats(Player player) {
		loadDatabase();
		String sql = "INSERT INTO player(partie_id, hp, stamina, strength, speed, player_level, player_score) VALUES(?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setInt(1, player.getPartie_id());
			pstmt.setInt(2, player.getHp());
			pstmt.setInt(3, (int) player.getStamina());
			pstmt.setInt(4, player.getStrength());
			pstmt.setInt(5, (int) player.getSpeed());
			pstmt.setInt(6, player.getPlayerLevel());
			pstmt.setInt(7, player.getPlayerScore());

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



}
