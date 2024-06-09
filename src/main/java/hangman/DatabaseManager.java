package hangman;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
	private Connection connection = null;
    private Statement statement = null;
    private boolean isLoaded = false;
    
    public DataBaseManager(String db_path) {
    	try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	try {
    		boolean is_exist = new File(db_path).exists();
			connection = DriverManager.getConnection("jdbc:sqlite:" + db_path);
			statement = connection.createStatement();
			if(is_exist == false) {
				createTables();
			}
			isLoaded = true;
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    private void createTables() {
    	String game_info_table = 
    			"CREATE TABLE \"GameInfo\" (" + 
    			"	\"GameID\"	INTEGER," + 
    			"	\"Username\"	TEXT NOT NULL," + 
    			"	\"Word\"	TEXT NOT NULL," + 
    			"	\"WrongGuesses\"	INTEGER NOT NULL," + 
    			"	\"Time\"	INTEGER NOT NULL," + 
    			"	\"Win\"	INTEGER NOT NULL," + 
    			"	PRIMARY KEY(\"GameID\")" + 
    			");";
    	
    	String user_info_table = 
    			"CREATE TABLE \"UserInfo\" (" + 
    			"	\"Name\"	TEXT NOT NULL," + 
    			"	\"Username\"	TEXT," + 
    			"	\"Password\"	TEXT NOT NULL," + 
    			"	PRIMARY KEY(\"Username\")" + 
    			");";
    	
    	try {
			statement.executeUpdate(game_info_table);
			statement.executeUpdate(user_info_table);
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public boolean isAuthenticated(String user_name, String password) {
    	return isUserAvailable(user_name,password,true);
    }
    
    public boolean isUserAvailable(String user_name, String password,Boolean auth) {
		if(isLoaded == true) {
			try {
				String query = "SELECT * FROM UserInfo WHERE Username = '" + user_name + "'";
				if(auth == true) query += " and Password = '" + password + "'";
				ResultSet result = statement.executeQuery(query);
				if(result.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	return false;
    }
    
    public boolean createNewUser(String user_name, String name, String password) {
    	if(isUserAvailable(user_name, password, false) == false) {
    		if(isLoaded == true) {
    			try {
    				String query = "INSERT INTO UserInfo (Name,Username,Password) VALUES ('" + name + "','" + user_name + "','" + password + "')";
					statement.executeUpdate(query);
					return true;
    			} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    	}
    	return false;
    }

    public boolean addGameInfo(String user_name,String word,int wrong_guesses,int time,Boolean win) {
    	if(isLoaded == true) {
    		String query = "INSERT INTO GameInfo(Username,Word,WrongGuesses,Time,Win) VALUES ('" + user_name + "','" + word + "'," + wrong_guesses + "," + time + "," + (win ? 1 : 0) + ")";
    		try {
				statement.executeUpdate(query);
				return true;
    		} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    public boolean addGameInfo(GameInfo game_info) {
    	return addGameInfo(game_info.Username, game_info.Word,game_info.WrongGuesses, game_info.Time, game_info.Win);
    }

	public List<GameInfo> getGameInfos(String user_name) {
    	List<GameInfo> game_inf = new ArrayList<>();
    	if(isLoaded == true) {
    		String query = "SELECT * FROM GameInfo";
    		if(user_name.length() > 0) query += " WHERE Username = '" + user_name + "'";
    		try {
				ResultSet result = statement.executeQuery(query);
				while (result.next()) {
					GameInfo gi = new GameInfo();
					gi.GameId = result.getInt("GameId");
					gi.Username = result.getString("Username");
					gi.Word = result.getString("Word");
					gi.WrongGuesses = result.getInt("WrongGuesses");
					gi.Time = result.getInt("Time");
					gi.Win = result.getBoolean("Win");
					game_inf.add(gi);
				}
    		} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return game_inf;
    }
	
	public List<UserInfo> getLeaderBoard(){
		List<UserInfo> leader_board = new ArrayList<>();
		if(isLoaded == true) {
    		String query = "SELECT Username,sum(Win) as Score from GameInfo GROUP by Username ORDER by score DESC";
    		try {
				ResultSet result = statement.executeQuery(query);
				while (result.next()) {
					UserInfo player = new UserInfo();
					player.Username = result.getString("Username");
					player.Name = getNameByUser(player.Username);
					player.Score = result.getInt("Score");
					leader_board.add(player);
				}
    		} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
		return leader_board;
	}
	
	public String getNameByUser(String user_name) {
		String name = "";
		if(isLoaded == true) {
    		String query = "SELECT Name FROM UserInfo WHERE Username = '"+ user_name + "'";
    		try {
    			Statement state = connection.createStatement();
				ResultSet result = state.executeQuery(query);
				if (result.next()) {
					name = result.getString("Name");
				}
				state.close();
    		} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
		return name;
	}
	
	public static String getPathForDB(String db_name) {
		String path = System.getenv("APPDATA") + "\\HangMan\\";
		File db_file = new File(path);
		if(db_file.exists() == false) {
			if(db_file.mkdir()) {
				return path + db_name;
			}
		}
		return "";
	}
	
	public void close() {
		try {
			if(statement.isClosed() == false) {
				statement.close();
			}
			
			if(connection.isClosed() == false) {
				connection.close();
			}
			
			isLoaded = false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
