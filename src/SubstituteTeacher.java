import java.util.ArrayList;

public class SubstituteTeacher {
	private String name;
	private ArrayList<ShiftProperties> shifts = new ArrayList<ShiftProperties>();
	private ArrayList<ShiftProperties> unavailableShifts = new ArrayList<ShiftProperties>();
	
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
	
	/* Unavailable Add-on XOXO */
	public ArrayList<ShiftProperties> unavailableShifts(){
		return unavailableShifts;
	}
	
	
	public void setUnavailableShift(ShiftProperties shift) {
		this.unavailableShifts.add(shift);
	}
	

	
}
