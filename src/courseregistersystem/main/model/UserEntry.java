/**
 * File: UserEntry.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a UserEntry class, it encapsulates key information about a user 
 * and the corresponding methods for getting and setting that information.
 */

package courseregistersystem.main.model;

public class UserEntry {

	/**
	 * Key information about a user
	 */
	private final long id;
	private String password;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String role;
	private String address;
	private String birthday;
	private String createTime;
	private String updateTime;

	/**
	 * actionState is flag, including "0", "1" and "2" three values,
	 * <p>
	 * "0" This user instance is just associated with the userTable database.
	 * <p>
	 * "1" This data is from the userTable database and requestedTable database.
	 * <p>
	 * "2" This data is from the userTable database and enrolledTable database.
	 */
	private String actionState = "0";
	private String actionCreateTime;

	/**
	 * This is the constructor of UserEntry.
	 * 
	 * @param id          id, it is a userId
	 * @param name        userName
	 * @param password    password
	 * @param email       email
	 * @param role        role
	 * @param first_name  firstName
	 * @param last_name   lastName
	 * @param address     address
	 * @param birthday    birthday
	 * @param create_time createTime
	 * @param update_time updateTime
	 */
	public UserEntry(long id, String name, String password, String email, String role, String first_name,
			String last_name, String address, String birthday, String create_time, String update_time) {

		this.id = id;
		this.userName = name;
		this.password = password;
		this.email = email;
		this.role = role;
		this.firstName = first_name;
		this.lastName = last_name;
		this.address = address;
		this.birthday = birthday;
		this.createTime = create_time;
		this.updateTime = update_time;
	}

	/**
	 * This is the second constructor of UserEntry.
	 * <p>
	 * This constructor is for creating user instance with a special action, it is a
	 * flag, including "0", "1" and "2" three values.
	 * <p>
	 * "0" This user instance is just associated with the userTable database.
	 * <p>
	 * "1" This data is from the userTable database and requestedTable database.
	 * <p>
	 * "2" This data is from the userTable database and enrolledTable database.
	 * 
	 * @param id                 id, it is a userId
	 * @param name               userName
	 * @param password           password
	 * @param email              email
	 * @param role               role
	 * @param first_name         firstName
	 * @param last_name          lastName
	 * @param address            address
	 * @param birthday           birthday
	 * @param create_time        createTime
	 * @param update_time        updateTime
	 * @param action_state       actionState
	 * @param action_create_time actionCreateTime
	 */
	public UserEntry(long id, String name, String password, String email, String role, String first_name,
			String last_name, String address, String birthday, String create_time, String update_time,
			String action_state, String action_create_time) {
		this.id = id;
		this.userName = name;
		this.password = password;
		this.email = email;
		this.role = role;
		this.firstName = first_name;
		this.lastName = last_name;
		this.address = address;
		this.birthday = birthday;
		this.createTime = create_time;
		this.updateTime = update_time;
		this.actionState = action_state;
		this.actionCreateTime = action_create_time;
	}

	/**
	 * Returns the user's id attribute.
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the userName attribute.
	 * 
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Returns the firstName attribute.
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the lastName attribute.
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the email attribute.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the role attribute.
	 * 
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Returns the address attribute.
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Returns the birthday attribute.
	 * 
	 * @return birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * Returns the actionState attribute.
	 * 
	 * @return actionState
	 */
	public String getActionState() {
		return actionState;
	}

	/**
	 * Get the attribute by the index.
	 * 
	 * @param index from 0 to 2
	 * @return attribute
	 */
	public Object getValueByIndex(int index) {
		switch (index) {
		case 0:
			return id;
		case 1:
			return userName;
		case 2:
			return email;
		}
		return null;
	}

	/**
	 * Set the firstName attribute.
	 * 
	 * @param _firstName
	 */
	public void setFirstName(String _firstName) {
		firstName = _firstName;
	}

	/**
	 * Set the lastName attribute.
	 * 
	 * @param _lastName
	 */
	public void setLastName(String _lastName) {
		lastName = _lastName;
	}

	/**
	 * Set the address attribute.
	 * 
	 * @param _address
	 */
	public void setAddress(String _address) {
		address = _address;
	}

	/**
	 * Set the birthday attribute.
	 * 
	 * @param _birthday
	 */
	public void setBirthday(String _birthday) {
		birthday = _birthday;
	}
}
