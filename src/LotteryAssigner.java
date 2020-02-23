import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class LotteryAssigner {
	
	
	public static void RandomAssign(ArrayList<AbsentTeacher> absentTeachers, ArrayList<SubstituteTeacher> substituteTeachers) {
		
		Random randomGenerator = new Random();
		
		//for each teacher in the absent teacher list
		for(AbsentTeacher teacher : absentTeachers) {
			ArrayList<ShiftProperties> teacherShifts = teacher.getShifts();
			
			//for each shift for one absent teacher - assuming that multiple absent shifts can be assigned to a single teacher
			for(ShiftProperties teacherShift : teacherShifts) {
				
				
				boolean shiftAssigned = false;
				
				//keep randomly picking a substitute teacher until the shift can be assigned.
				while(!shiftAssigned) {
					
					//pick a substitute randomly and check whether or not they have already been assigned that shift.
					int numSubstitutes = substituteTeachers.size();
					int randSubstitute = randomGenerator.nextInt(numSubstitutes - 1);
					SubstituteTeacher substituteTeacher = substituteTeachers.get(randSubstitute);
					
					//get shifts from randomly picked substitute teacher
					ArrayList<ShiftProperties> substituteShifts = substituteTeacher.getShifts();
					
					//only loop through assigned shifts if the teacher has been assigned anything
					if(substituteShifts.size() > 0) {
						for(ShiftProperties substituteShift : substituteShifts) {
							if(substituteShift.getDate().equals(teacherShift.getDate()) && substituteShift.getPeriod().equals(teacherShift.getPeriod())) {
								shiftAssigned = false;
							}
							else {
								substituteTeacher.setShift(teacherShift);
								shiftAssigned = true;
							}
						}
					}
					else {
						substituteTeacher.setShift(teacherShift);
						shiftAssigned = true;
					}
				} //end while loop
				
			} //end loop that goes through each shift assigned to one teacher
		}//end loop that goes through each teacher
	}
}
