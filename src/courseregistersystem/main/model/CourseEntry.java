/**
 * File: CourseEntry.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a CourseEntry class, it encapsulates key information about a course 
 * and the corresponding methods for getting and setting that information.
 */

package courseregistersystem.main.model;

public class CourseEntry {

	/**
	 * Key information about a course
	 */
	private final long id;
	private String courseName;
	private String courseHours;
	private String courseType;
	private long instructorId;
	private String building;
	private String createTime;
	private String updateTime;

	/**
	 * actionState is a flag, including "0", "1" and "2" three values,
	 * <p>
	 * "0" This course instance is just associated with the courseTable database.
	 * <p>
	 * "1" This data is from the courseTable database and requestedTable database.
	 * <p>
	 * "2" This data is from the courseTable database and enrolledTable database.
	 */
	private String actionState = "0";
	private String actionCreateTime;

	/**
	 * This is the constructor of CourseEntry.
	 * 
	 * @param id            id, it is a courseId
	 * @param name          courseName
	 * @param hours         courseHours
	 * @param type          courseType
	 * @param instructor_id instructorId
	 * @param building      building
	 * @param create_time   createTime
	 * @param update_time   updateTime
	 */
	public CourseEntry(long id, String name, String hours, String type, long instructor_id, String building,
			String create_time, String update_time) {
		this.id = id;
		this.courseName = name;
		this.courseHours = hours;
		this.courseType = type;
		this.instructorId = instructor_id;
		this.building = building;
		this.createTime = create_time;
		this.updateTime = update_time;
	}

	/**
	 * This is the second constructor of CourseEntry.
	 * <p>
	 * This constructor is for creating course instance with a special action, it is
	 * a flag, including "0", "1" and "2" three values.
	 * <p>
	 * "0" This course instance is just associated with the courseTable database.
	 * <p>
	 * "1" This data is from the courseTable database and requestedTable database.
	 * <p>
	 * "2" This data is from the courseTable database and enrolledTable database.
	 * 
	 * @param id                 id, it is a courseId
	 * @param name               courseName
	 * @param hours              courseHours
	 * @param type               courseType
	 * @param instructor_id      instructorId
	 * @param building           building
	 * @param create_time        createTime
	 * @param update_time        updateTime
	 * @param action_state       actionState
	 * @param action_create_time actionCreateTime
	 */
	public CourseEntry(long id, String name, String hours, String type, long instructor_id, String building,
			String create_time, String update_time, String action_state, String action_create_time) {
		this.id = id;
		this.courseName = name;
		this.courseHours = hours;
		this.courseType = type;
		this.instructorId = instructor_id;
		this.building = building;
		this.createTime = create_time;
		this.updateTime = update_time;

		this.actionState = action_state;
		this.actionCreateTime = action_create_time;
	}

	/**
	 * Returns the courseName attribute.
	 * 
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Returns the courseHours attribute.
	 * 
	 * @return courseHours
	 */
	public String getCourseHours() {
		return courseHours;
	}

	/**
	 * Returns the courseType attribute.
	 * 
	 * @return courseType
	 */
	public String getCourseType() {
		return courseType;
	}

	/**
	 * Returns the building attribute.
	 * 
	 * @return building
	 */
	public String getBuilding() {
		return building;
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
	 * @param index from 0 to 4
	 * @return attribute
	 */
	public Object getValueByIndex(int index) {
		switch (index) {
		case 0:
			return id;
		case 1:
			return courseName;
		case 2:
			return courseHours;
		case 3:
			return courseType;
		case 4:
			return building;
		}
		return null;
	}

	/**
	 * Set the courseName attribute.
	 * 
	 * @param _courseName
	 */
	public void setCourseName(String _courseName) {
		courseName = _courseName;
	}

	/**
	 * Set the courseHours attribute.
	 * 
	 * @param _courseHours
	 */
	public void setCourseHours(String _courseHours) {
		courseHours = _courseHours;
	}

	/**
	 * Set the courseType attribute.
	 * 
	 * @param _courseType
	 */
	public void setCourseType(String _courseType) {
		courseType = _courseType;
	}

	/**
	 * Set the building attribute.
	 * 
	 * @param _building the address of the course
	 */
	public void setCourseBuilding(String _building) {
		building = _building;
	}
}
