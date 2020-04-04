import java.util.ArrayList;

public class AbsentTeacher {
	private String name;
	private ArrayList<ShiftProperties> shifts = new ArrayList<ShiftProperties>();
	
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
}
