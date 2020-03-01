import java.util.ArrayList;

public class SubstituteTeacher {
	private String name;
	private String teachable;
	private String blacklist;
	private ArrayList<ShiftProperties> shifts = new ArrayList<ShiftProperties>();
	private ArrayList<ShiftProperties> unavailableShifts = new ArrayList<ShiftProperties>();
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setShift(ShiftProperties shift) {
		this.shifts.add(shift);
	}
	
	public void setTeachable(String teachable) {
		this.teachable = teachable;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTeachable() {
		return teachable;
	}
	
	public String getBlacklist() {
		return blacklist;
	}
	
	public ArrayList<ShiftProperties> getShifts() {
		return shifts;
	}
	
	public void setUnavailableShift(ShiftProperties shift) {
		this.unavailableShifts.add(shift);
	}
	
	public ArrayList<ShiftProperties> getUnavailableShift() {
		return unavailableShifts;
	}


	
}
