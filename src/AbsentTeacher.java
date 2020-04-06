import java.util.ArrayList;
import java.util.Arrays;

public class AbsentTeacher {
	private String name;
	private String teachable
	private String preferredSub;
	private ArrayList<ShiftProperties> shifts = new ArrayList<ShiftProperties>();
	private ArrayList<String> teachableList;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setShift(ShiftProperties shift) {
		this.shifts.add(shift);
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<ShiftProperties> getShifts() {
		return shifts;
	}
	
	public void removeShift(int index) {
		this.shifts.remove(index);
	}

	public void setTeachable(String teachable) {
		teachableList = new ArrayList<String>(Arrays.asList(teachable.split("\n")));

	}
	public ArrayList<String> getTeachable() {
		return teachableList;
	}
	
	public void setPreferredSub(String subName) {
		this.preferredSub = subName;
	}

	public String getPreferredSub() {
		return this.preferredSub;
	}
}
