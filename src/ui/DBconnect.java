package ui;

import java.awt.BorderLayout;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
/**
 * A class that deals with the database and shows the high score repository
 * @author Yuan Tang
 * 
 */
public class DBconnect {
	
	static Connection connection = null;
	// select DB2 type 2 driver
	static final String dbDriver = "com.mysql.jdbc.Driver";
	static final String dbUrl = "jdbc:mysql://127.0.0.1/Records";
	// use the Records database
	static final String dbName = "Records";
	// set dbUser and dbPassword to any user on my system
	static final String dbUser = "root";
	static final String dbPassword = "password";
	
	/*
	 * method to open the connection
	 */
	public Connection getConnection(){
		if(connection != null)
			return connection;
		try{
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		}catch(SQLException e){
			System.err.println(
					"Cannot connect to database."
					+  "Check whether your database is running and that the Records database exists.");
		}
		return connection;
	}
	/*
	 * retrieve all the data from MySQL
	 */
	public ResultSet getAllRecordsInfo(){
		ResultSet rs = null;
		try{
			Statement statement = getConnection().createStatement();
			String sql = "SELECT * FROM scores";
			System.out.println(sql);
			statement.executeQuery(sql);
			rs = statement.getResultSet();
			while(rs.next()){
				System.out.println("initial: " + rs.getString("initial") + "  "
								+ "score: " + rs.getInt("score"));
			}
		}catch(SQLException e){
			System.out.println("SQLException " + e.getMessage());
		}
		return rs;
		
	}
	
	/*
	 * add a record into the database
	 */
	public void addRecord(String initial, int score) throws SQLException{
		Statement statement = getConnection().createStatement();
		String sql = "INSERT INTO scores VALUES("
					 + "'" + initial + "'," + score + ")";
		System.out.println(sql);
		int val = statement.executeUpdate(sql);
		if(val == 1)
			System.out.println("Successfully inserted record");
	}
	
	/*
	 * this function updates the record of the winner that has record here before
	 */
	public void updateRecord(String initial, int score) throws SQLException{
		Statement statement = getConnection().createStatement();
		String sql = "UPDATE scores SET score=" + score + " WHERE initial='" + initial + "'";
		System.out.println(sql);
		int val = statement.executeUpdate(sql);
		if(val == 1)
			System.out.println("Successfully updated record");
	}
	
	/*
	 * retrieve the score of an old player
	 */
	@SuppressWarnings("finally")
	public int getScore(String init, Connection connection){
		ResultSet rs = null;
		int score = 0;
		try{
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM scores WHERE initial='" + init + "'";
			System.out.println(sql);
			// something's wrong here
			statement.executeQuery(sql);
			while(rs.next()){
				rs = statement.getResultSet();
				score = rs.getInt("score");
				System.out.println(score);
			}
		}catch(SQLException e){
			System.out.println("SQLException " + e.getMessage());
		}
		finally{
			System.out.println(score);
			return score;
		}
	}
	
	/*
	 * this function gives the winner options to choose as well as showing his/her the
	 * high score repository
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void runProcess(String[] args){
		JFrame frame = new JFrame();
		DBconnect dbmain = new DBconnect();
		ArrayList<String> columnNames = new ArrayList<String>();
        ArrayList data = new ArrayList();
		
		//Custom button text
		Object[] options = {"Yes, I have",
		                    "No, I haven't"};
		int choice = JOptionPane.showOptionDialog(frame,
		    "Have you had a record here before? ",
		    "You won this game!",
		    JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[1]);
		
		System.out.println("Getting Database Driver");
		try{
			Class.forName(dbDriver);
		}catch(ClassNotFoundException e){
			System.out.println("Cannot load database driver");
		}
		
		System.out.println("Getting database connection");
		Connection connection = dbmain.getConnection();
		if(connection == null)
			System.exit(0);
		System.out.println("Database ready");
		
		try{
			// print info from the driver metadata
			DatabaseMetaData md = connection.getMetaData();
			System.out.println("Product name: "
					+ md.getDatabaseProductName());
			System.out.println("Driver name: "
					+ md.getDriverName());
			
			// if yes->updateRecord(), if no->addRecord()
			if(choice == JOptionPane.YES_OPTION){
				// update a recordString initial = null;
				String initial = null;
				do{
					initial = JOptionPane.showInputDialog(frame, "Please type your initials: ");
				}while(initial == null);
				int scoreBefore = dbmain.getScore(initial,connection);
				dbmain.updateRecord(initial, ++scoreBefore);
			}else if(choice == JOptionPane.NO_OPTION){
				String initial = null;
				do{
					initial = JOptionPane.showInputDialog(frame, "Please type your initials: ");
				}while(initial == null);
				dbmain.addRecord(initial, 1);
			}
								
			// select and show data from the database
			dbmain.getAllRecordsInfo();
			// show data in a table(high score repository)
			ResultSet rs = null;
			try{
				Statement statement = connection.createStatement();
				String sql = "SELECT * FROM scores ORDER BY score DESC";
				System.out.println("Retrieving data from MySQL");
				statement.executeQuery(sql);
				rs = statement.getResultSet();
				columnNames.add("initial");
				columnNames.add("score");
				while(rs.next()){
					ArrayList<Object> row = new ArrayList<Object>(2);
					for(int i = 1; i <= 2; i++){
						row.add(rs.getObject(i));
					}
					data.add(row);
				}
			}catch(SQLException e){
				System.out.println("SQLException " + e.getMessage());
			}
			
			Vector<String> columnNamesVector = new Vector<String>();
	        Vector dataVector = new Vector();
	        
	        for (int i = 0; i < data.size(); i++)
	        {
	            ArrayList subArray = (ArrayList)data.get(i);
	            Vector subVector = new Vector();
	            for (int j = 0; j < subArray.size(); j++)
	            {
	                subVector.add(subArray.get(j));
	            }
	            dataVector.add(subVector);
	        }
			
	        for (int i = 0; i < columnNames.size(); i++ )
	            columnNamesVector.add(columnNames.get(i));

	        //  Create table with database data    
	        JTable table = new JTable(dataVector, columnNamesVector)
	        {
				private static final long serialVersionUID = 1L;

				public Class getColumnClass(int column)
	            {
	                for (int row = 0; row < getRowCount(); row++)
	                {
	                    Object o = getValueAt(row, column);

	                    if (o != null)
	                    {
	                        return o.getClass();
	                    }
	                }

	                return Object.class;
	            }
	        };

	        JScrollPane scrollPane = new JScrollPane( table );
	        frame.getContentPane().add( scrollPane );

	        JPanel buttonPanel = new JPanel();
	        frame.getContentPane().add( buttonPanel, BorderLayout.SOUTH );
	        frame.setTitle("High Score Repository");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.pack();
	        frame.setVisible(true);
			
			// close the connection when done
			connection.close();
		}catch(Exception e){
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
	}
	/*
	 * this function shows the high score repository to the user if user chooses to do 
	 * so at the begining stage of the game
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void viewHighScore(String[] args){
		JFrame frame = new JFrame();
		DBconnect dbmain = new DBconnect();
		ArrayList<String> columnNames = new ArrayList<String>();
        ArrayList data = new ArrayList();
		
		
		System.out.println("Getting Database Driver");
		try{
			Class.forName(dbDriver);
		}catch(ClassNotFoundException e){
			System.out.println("Cannot load database driver");
		}
		
		System.out.println("Getting database connection");
		Connection connection = dbmain.getConnection();
		if(connection == null)
			System.exit(0);
		System.out.println("Database ready");
		
		try{
			// print info from the driver metadata
			DatabaseMetaData md = connection.getMetaData();
			System.out.println("Product name: "
					+ md.getDatabaseProductName());
			System.out.println("Driver name: "
					+ md.getDriverName());
								
			// show data in a table(high score repository)
			ResultSet rs = null;
			try{
				Statement statement = connection.createStatement();
				String sql = "SELECT * FROM scores ORDER BY score DESC";
				System.out.println("Retrieving data from MySQL");
				statement.executeQuery(sql);
				rs = statement.getResultSet();
				columnNames.add("initial");
				columnNames.add("score");
				while(rs.next()){
					ArrayList<Object> row = new ArrayList<Object>(2);
					for(int i = 1; i <= 2; i++){
						row.add(rs.getObject(i));
					}
					data.add(row);
				}
			}catch(SQLException e){
				System.out.println("SQLException " + e.getMessage());
			}
			
			Vector<String> columnNamesVector = new Vector<String>();
	        Vector dataVector = new Vector();
	        
	        for (int i = 0; i < data.size(); i++)
	        {
	            ArrayList subArray = (ArrayList)data.get(i);
	            Vector subVector = new Vector();
	            for (int j = 0; j < subArray.size(); j++)
	            {
	                subVector.add(subArray.get(j));
	            }
	            dataVector.add(subVector);
	        }
			
	        for (int i = 0; i < columnNames.size(); i++ )
	            columnNamesVector.add(columnNames.get(i));

	        //  Create table with database data    
	        JTable table = new JTable(dataVector, columnNamesVector)
	        {
				private static final long serialVersionUID = 1L;

				public Class getColumnClass(int column)
	            {
	                for (int row = 0; row < getRowCount(); row++)
	                {
	                    Object o = getValueAt(row, column);

	                    if (o != null)
	                    {
	                        return o.getClass();
	                    }
	                }

	                return Object.class;
	            }
	        };

	        JScrollPane scrollPane = new JScrollPane( table );
	        frame.getContentPane().add( scrollPane );

	        JPanel buttonPanel = new JPanel();
	        frame.getContentPane().add( buttonPanel, BorderLayout.SOUTH );
	        frame.setTitle("High Score Repository");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.pack();
	        frame.setVisible(true);
			
			// close the connection when done
			connection.close();
		}catch(Exception e){
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
	}
}

















