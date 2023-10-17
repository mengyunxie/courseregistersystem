/**
 * File: SqliteDatabase.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a SqliteDatabase class as a bridge between the java program and the database. 
 * The SqliteDatabase class contains a number of methods, which are similar to the API.
 */

package courseregistersystem.main.database;

import courseregistersystem.main.model.*;
import courseregistersystem.main.ui.GUI;
import courseregistersystem.main.ui.Toast;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SqliteDatabase {

	private static SqliteDatabase database;
	private GUI gui;

	/**
	 * This is the constructor of SqliteDatabase.
	 * <p>
	 * It is a private constructor
	 * 
	 * @param _gui it is the main container, an instance of GUI class
	 */
	private SqliteDatabase(GUI _gui) {
		gui = _gui;
		createUserTables();
		createCourseTables();
		createRequestedTable();
		createEnrolledTable();
	}

	/**
	 * This method is used to call the constructor.
	 * 
	 * @param _gui it is the main container, an instance of GUI class
	 */
	public static SqliteDatabase instance(GUI _gui) {
		if (database == null) {
			database = new SqliteDatabase(_gui);
		}
		return database;
	}

	/**
	 * Create connection between the program and database.
	 */
	private Connection createConnection() {

		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection("jdbc:sqlite:db/courseRegistrationSystem.db");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CreateConnection fail!");
			Toast.show(gui, "CreateConnection fail!", Color.RED);
		}
		return null;
	}

	/**
	 * Create userTable database.
	 */
	private void createUserTables() {
		String query = "CREATE TABLE IF NOT EXISTS userTable ( " + "user_name TEXT NOT NULL, "
				+ "password TEXT NOT NULL, " + "email TEXT NOT NULL, " + "role TEXT NOT NULL, "
				+ "first_name TEXT NOT NULL, " + "last_name TEXT NOT NULL, " + "address TEXT NOT NULL, "
				+ "birthday TIMESTAMP NOT NULL, " + "create_time TIMESTAMP NOT NULL, "
				+ "update_time TIMESTAMP NOT NULL);";

		try {
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("CreateUserTables fail!");
			Toast.show(gui, "CreateUserTables fail!", Color.RED);
		}

	}

	/**
	 * Login
	 * 
	 * @param form the form of the current user
	 * @return the current user's id
	 */
	public long userLogin(Map<String, String> form) {

		long userId = -1; // -1 means it is not exist

		String query = "SELECT rowid, * FROM userTable WHERE user_name=? AND password=?";
		try {

			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			// Parameters
			statement.setString(1, form.get("username"));
			statement.setString(2, form.get("password"));

			// Execute
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				userId = resultSet.getLong("rowid");
			} else {
				Toast.show(gui, "This user is not exist!", Color.RED);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Login fail!");
			Toast.show(gui, "Login fail!", Color.RED);
		}
		return userId;
	}

	/**
	 * Create a new user
	 * 
	 * @param form the form of the user
	 * @return the new user's id
	 */
	public long createUser(Map<String, String> form) {
		long userId = -1; // -1 means it is not exist
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String ts = sdf.format(timestamp);

		String query = "INSERT INTO userTable (user_name, password, email, role, first_name, last_name, address, birthday, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			// Parameters
			statement.setString(1, form.get("username"));
			statement.setString(2, form.get("password"));
			statement.setString(3, form.get("email"));
			statement.setString(4, form.get("role"));
			statement.setString(5, "");
			statement.setString(6, "");
			statement.setString(7, "");
			statement.setString(8, "");
			statement.setString(9, ts);
			statement.setString(10, ts);

			// Execute
			statement.executeUpdate();
			ResultSet res = statement.getGeneratedKeys();
			if (res.next()) {
				userId = res.getInt(1);
			} else {
				Toast.show(gui, "Create a account fail!", Color.RED);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Create a account fail");
			Toast.show(gui, "Create a account fail!", Color.RED);
		}
		return userId;
	}

	/**
	 * Update the information of the user
	 * 
	 * @param userid the current user's id
	 * @param form   the form of the user
	 */
	public void updateUser(long userid, Map<String, String> form) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String ts = sdf.format(timestamp);
		String query = "UPDATE userTable SET first_name = ?, last_name = ?, address = ?, birthday = ?, update_time = ? WHERE rowid = ?;";
		try {
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			// Parameters
			statement.setString(1, form.get("first_name"));
			statement.setString(2, form.get("last_name"));
			statement.setString(3, form.get("address"));
			statement.setString(4, form.get("birthday"));
			statement.setString(5, ts);
			statement.setLong(6, userid);

			// Execute
			statement.executeUpdate();
			statement.close();
			Toast.show(gui, "Update successfully!", Color.GREEN);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("UpdateUser fail!");
			Toast.show(gui, "UpdateUser fail!", Color.RED);
		}
	}

	/**
	 * Get a data of the user
	 * 
	 * @param userid the current user's id
	 * @return the user entity
	 */
	public UserEntry getUser(long userid) {
		String selectStatement = "SELECT rowid, * FROM userTable WHERE rowid =" + userid + ";";
		UserEntry userEntry = null;
		try {
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectStatement);
			if (resultSet.next()) {
				userEntry = new UserEntry(resultSet.getLong("rowid"), resultSet.getString("user_name"),
						resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("role"),
						resultSet.getString("first_name"), resultSet.getString("last_name"),
						resultSet.getString("address"), resultSet.getString("birthday"),
						resultSet.getString("create_time"), resultSet.getString("update_time"));
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("GetUser fail!");
			Toast.show(gui, "GetUser fail!", Color.RED);
		}
		return userEntry;
	}

	/**
	 * Get a list of user
	 * <p>
	 * This method is used to select students who belong to a course
	 * 
	 * @param courseid the current course's id
	 * @return the list of user entity
	 */
	public List<UserEntry> getUserListByCourseId(long courseid) {
		String query = "SELECT t1.rowid, * FROM userTable t1 INNER JOIN (SELECT \"2\" action_state, * FROM enrolledTable WHERE course_id = ? UNION SELECT  \"1\" action_state, * FROM requestedTable WHERE course_id = ?) t2 ON t1.rowid = t2.student_id;";
		List<UserEntry> userEntries = new ArrayList<>();

		try {
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			// Parameters
			statement.setLong(1, courseid);
			statement.setLong(2, courseid);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				userEntries.add(new UserEntry(resultSet.getLong("rowid"), resultSet.getString("user_name"),
						resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("role"),
						resultSet.getString("first_name"), resultSet.getString("last_name"),
						resultSet.getString("address"), resultSet.getString("birthday"),
						resultSet.getString("create_time"), resultSet.getString("update_time"),
						resultSet.getString("action_state"), resultSet.getString("action_create_time")));
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("GetUserList fail!");
			Toast.show(gui, "GetUserList fail!", Color.RED);
		}
		return userEntries;
	}

	/**
	 * Create courseTable database.
	 */
	private void createCourseTables() {
		String query = "CREATE TABLE IF NOT EXISTS courseTable ( " + "course_name TEXT NOT NULL, "
				+ "course_hours TEXT NOT NULL, " + "course_type TEXT NOT NULL, " + "course_building TEXT NOT NULL, "
				+ "instructor_id INTEGER NOT NULL, " + "create_time TIMESTAMP NOT NULL, "
				+ "update_time TIMESTAMP NOT NULL);";

		try {
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("CreateCourseTables fail!");
			Toast.show(gui, "CreateCourseTables fail!", Color.RED);
		}
	}

	/**
	 * Create requestedTable database.
	 */
	private void createRequestedTable() {
		String query = "CREATE TABLE IF NOT EXISTS requestedTable ( " + "student_id INTEGER NOT NULL, "
				+ "course_id INTEGER NOT NULL, " + "action_create_time TIMESTAMP NOT NULL);";

		try {
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("CreateRequestedTable fail!");
			Toast.show(gui, "CreateRequestedTable fail!", Color.RED);
		}
	}

	/**
	 * Create enrolledTable database.
	 */
	private void createEnrolledTable() {
		String query = "CREATE TABLE IF NOT EXISTS enrolledTable ( " + "student_id INTEGER NOT NULL, "
				+ "course_id INTEGER NOT NULL, " + "action_create_time TIMESTAMP NOT NULL);";

		try {
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("CreateEnrolledTable fail!");
			Toast.show(gui, "CreateEnrolledTable fail!", Color.RED);
		}
	}

	/**
	 * Create a new course
	 * 
	 * @param instructorid the id of an instructor
	 * @param form         the form of the course
	 * @return the new course's id
	 */
	public long createCourse(long instructorid, Map<String, String> form) {
		long courseId = -1; // -1 means it is not exist
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String ts = sdf.format(timestamp);

		String query = "INSERT INTO courseTable (course_name, course_hours, course_type, instructor_id, course_building, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?);";
		try {
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			// Parameters
			statement.setString(1, form.get("course_name"));
			statement.setString(2, form.get("course_hours"));
			statement.setString(3, form.get("course_type"));
			statement.setLong(4, instructorid);
			statement.setString(5, form.get("course_building"));
			statement.setString(6, ts);
			statement.setString(7, ts);

			// Execute
			statement.executeUpdate();
			ResultSet res = statement.getGeneratedKeys();
			if (res.next()) {
				courseId = res.getInt(1);
				Toast.show(gui, "CreateCourse successfully!", Color.GREEN);
			} else {
				Toast.show(gui, "CreateCourse fail!", Color.RED);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("CreateCourse  fail!");
			Toast.show(gui, "CreateCourse fail!", Color.RED);
		}
		return courseId;
	}

	/**
	 * Get a data of the course
	 * 
	 * @param courseid the current course's id
	 * @return the course entity
	 */
	public CourseEntry getCourse(long courseid) {
		String selectStatement = "SELECT rowid, * FROM courseTable WHERE rowid =" + courseid + ";";
		CourseEntry courseEntry = null;
		try {
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectStatement);
			if (resultSet.next()) {
				courseEntry = new CourseEntry(resultSet.getLong("rowid"), resultSet.getString("course_name"),
						resultSet.getString("course_hours"), resultSet.getString("course_type"),
						resultSet.getLong("instructor_id"), resultSet.getString("course_building"),
						resultSet.getString("create_time"), resultSet.getString("update_time"));
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("GetCourse fail!");
			Toast.show(gui, "GetCourse fail!", Color.RED);
		}
		return courseEntry;
	}

	/**
	 * Get all course list
	 * <p>
	 * This method is used for the student's course register request
	 * 
	 * @param studentid the student's id
	 * @return the list of course entity
	 */
	public List<CourseEntry> getCourseList(long studentid) {

		String queryRequestedTable = "SELECT t1.rowid, * FROM courseTable t1 INNER JOIN (SELECT * FROM requestedTable WHERE student_id = "
				+ studentid + ") t2 ON t1.rowid = t2.course_id;";
		String queryEnrolledTable = "SELECT t1.rowid, * FROM courseTable t1 INNER JOIN (SELECT * FROM enrolledTable WHERE student_id = "
				+ studentid + ") t2 ON t1.rowid = t2.course_id;";
		String queryAll = "SELECT rowid, * FROM courseTable;";

		List<CourseEntry> courseEntries = new ArrayList<>();
		try {
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryRequestedTable);

			Set<String> requestedSet = new HashSet<>();
			while (resultSet.next()) {
				requestedSet.add(resultSet.getString("course_id"));
			}

			resultSet = statement.executeQuery(queryEnrolledTable);
			Set<String> enrolledSet = new HashSet<>();
			while (resultSet.next()) {
				enrolledSet.add(resultSet.getString("course_id"));
			}

			resultSet = statement.executeQuery(queryAll);
			while (resultSet.next()) {
				String rowid = String.valueOf(resultSet.getLong("rowid"));
				String type = "0";
				if (requestedSet.contains(rowid)) {
					type = "1";
				}
				if (enrolledSet.contains(rowid)) {
					type = "2";
				}
				courseEntries.add(new CourseEntry(resultSet.getLong("rowid"), resultSet.getString("course_name"),
						resultSet.getString("course_hours"), resultSet.getString("course_type"),
						resultSet.getLong("instructor_id"), resultSet.getString("course_building"),
						resultSet.getString("create_time"), resultSet.getString("update_time"), type, ""));
			}

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("GetCourseList fail!");
			Toast.show(gui, "GetCourseList fail!", Color.RED);
		}
		return courseEntries;

	}

	/**
	 * Get a course list of an instructor
	 * 
	 * @param instructorid the instructor's id
	 * @return the list of course entity
	 */
	public List<CourseEntry> getInstructorCourseList(long instructorid) {
		String query = "SELECT rowid, * FROM courseTable WHERE instructor_id =" + instructorid + ";";
		List<CourseEntry> courseEntries = new ArrayList<>();
		try {
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				courseEntries.add(new CourseEntry(resultSet.getLong("rowid"), resultSet.getString("course_name"),
						resultSet.getString("course_hours"), resultSet.getString("course_type"),
						resultSet.getLong("instructor_id"), resultSet.getString("course_building"),
						resultSet.getString("create_time"), resultSet.getString("update_time")));
			}

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("GetInstructorCourseList fail!");
			Toast.show(gui, "GetInstructorCourseList fail!", Color.RED);
		}
		return courseEntries;

	}

	/**
	 * Get a course list of a student
	 * 
	 * @param studentid the student's id
	 * @return the list of course entity
	 */
	public List<CourseEntry> getStudentCourseList(long studentid) {

		String query = "SELECT t1.rowid, * FROM courseTable t1 INNER JOIN (SELECT \"2\" action_state, * FROM enrolledTable WHERE student_id = ? UNION SELECT  \"1\" action_state, * FROM requestedTable WHERE student_id = ?) t2 ON t1.rowid = t2.course_id;";

		List<CourseEntry> courseEntries = new ArrayList<>();
		try {

			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			// Parameters
			statement.setLong(1, studentid);
			statement.setLong(2, studentid);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				courseEntries.add(new CourseEntry(resultSet.getLong("rowid"), resultSet.getString("course_name"),
						resultSet.getString("course_hours"), resultSet.getString("course_type"),
						resultSet.getLong("instructor_id"), resultSet.getString("course_building"),
						resultSet.getString("create_time"), resultSet.getString("update_time"),
						resultSet.getString("action_state"), resultSet.getString("action_create_time")));
			}

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("GetStudentCourseList fail!");
			Toast.show(gui, "GetStudentCourseList fail!", Color.RED);
		}
		return courseEntries;

	}

	/**
	 * Update the information of the course
	 * 
	 * @param courseid the current course's id
	 * @param form     the form of the course
	 */
	public void updateCourse(long courseid, Map<String, String> form) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String ts = sdf.format(timestamp);

		String query = "UPDATE courseTable SET course_name = ?, course_hours = ?, course_type = ?, course_building = ?, update_time = ? WHERE rowid = ?;";

		try {
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			// Parameters
			statement.setString(1, form.get("course_name"));
			statement.setString(2, form.get("course_hours"));
			statement.setString(3, form.get("course_type"));
			statement.setString(4, form.get("course_building"));
			statement.setString(5, ts);
			statement.setLong(6, courseid);

			// Execute
			statement.executeUpdate();
			statement.close();
			Toast.show(gui, "UpdateCourse successfully!", Color.GREEN);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("UpdateCourse  fail!");
			Toast.show(gui, "UpdateCourse fail!", Color.RED);
		}
	}

	/**
	 * Request for Course Registration
	 * <p>
	 * Add a data to the Requested table.
	 * 
	 * @param studentid the student's id
	 * @param courseid  the current course's id
	 */
	public void registerCourse(long studentid, long courseid) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String ts = sdf.format(timestamp);

		String query = "INSERT INTO requestedTable (student_id, course_id, action_create_time) VALUES (?, ?, ?);";
		try {
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			// Parameters
			statement.setLong(1, studentid);
			statement.setLong(2, courseid);
			statement.setString(3, ts);

			// Execute
			statement.executeUpdate();
			statement.close();
			Toast.show(gui, "RegisterCourse successfully!", Color.GREEN);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("RegisterCourse  fail!");
			Toast.show(gui, "RegisterCourse fail!", Color.RED);
		}
	}

	/**
	 * Drop a course
	 * <p>
	 * Delete a data from the Enrolled table.
	 * 
	 * @param studentid the student's id
	 * @param courseid  the current course's id
	 */
	public void dropCourse(long studentid, long courseid) {
		String query = "DELETE FROM enrolledTable WHERE student_id = ? AND course_id = ?;";
		try {
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			// Parameters
			statement.setLong(1, studentid);
			statement.setLong(2, courseid);

			// Execute
			statement.executeUpdate();
			statement.close();
			Toast.show(gui, "DropCourse successfully!", Color.GREEN);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DropCourse  fail!");
			Toast.show(gui, "DropCourse fail!", Color.RED);
		}
	}

	/**
	 * Approve a student's course register request
	 * <p>
	 * Move a data from the Requested table to the Enrolled table.
	 * 
	 * @param studentid the student's id
	 * @param courseid  the current course's id
	 */
	public void approveCourseRequest(long studentid, long courseid) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String ts = sdf.format(timestamp);
		String deleteQuery = "DELETE FROM requestedTable WHERE student_id = ? AND course_id = ?;";
		String query = "INSERT INTO enrolledTable (student_id, course_id, action_create_time) VALUES (?, ?, ?);";
		try {
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(deleteQuery);
			// Parameters
			statement.setLong(1, studentid);
			statement.setLong(2, courseid);

			// Execute
			statement.executeUpdate();

			statement = connection.prepareStatement(query);
			// Parameters
			statement.setLong(1, studentid);
			statement.setLong(2, courseid);
			statement.setString(3, ts);
			// Execute
			statement.executeUpdate();
			statement.close();
			Toast.show(gui, "ApproveCourseRequest successfully!", Color.GREEN);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ApproveCourseRequest  fail!");
			Toast.show(gui, "ApproveCourseRequest fail!", Color.RED);
		}
	}

	/**
	 * Decline a student's course register request
	 * <p>
	 * Delete a data from the Requested table.
	 * 
	 * @param studentid the student's id
	 * @param courseid  the current course's id
	 */
	public void declineCourseRequest(long studentid, long courseid) {
		String query = "DELETE FROM requestedTable WHERE student_id = ? AND course_id = ?;";
		try {
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			// Parameters
			statement.setLong(1, studentid);
			statement.setLong(2, courseid);

			// Execute
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DeclineCourseRequest  fail!");
		}
	}
}
