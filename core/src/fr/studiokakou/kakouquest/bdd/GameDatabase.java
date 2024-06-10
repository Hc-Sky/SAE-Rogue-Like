package fr.studiokakou.kakouquest.bdd;

import fr.studiokakou.kakouquest.player.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDatabase {
	private Connection connexion;

	private void loadDatabase() {
		// Chargement du driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}

		try {
			connexion = DriverManager.getConnection("jdbc:mysql://165.232.124.186:3306/studio_kakou", "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void savePlayerStats(Player player) {
		String sql = "INSERT INTO Player(hp, maxHp, strengh, speed, stamina, maxStamina, currentWeapon, currentLevel) VALUES(?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setInt(1, player.getHp());
			pstmt.setInt(2, player.getMax_hp());
			pstmt.setInt(3, player.getStrength());
			pstmt.setInt(4, (int) player.getSpeed());
			pstmt.setInt(5, (int) player.getStamina());
			pstmt.setInt(6, player.getMax_stamina());
			pstmt.setString(7, String.valueOf(player.getCurrentWeapon()));

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
