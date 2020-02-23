import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class LotteryAssigner {
	
	
	public static void RandomAssign(ArrayList<AbsentTeacher> absentTeachers, ArrayList<SubstituteTeacher> substituteTeachers) {
		
		int numSubstitutes;
		int randSubstitute;
		
		Random randomGenerator = new Random();
		
		//for each teacher in the absent teacher list
		for(AbsentTeacher teacher : absentTeachers) {
			ArrayList<ShiftProperties> teacherShifts = teacher.getShifts();
			System.out.println("teacher: " + teacher.getName());
			
			//for each shift for one absent teacher - assuming that multiple absent shifts can be assigned to a single teacher
			for(ShiftProperties teacherShift : teacherShifts) {
				
				
				boolean shiftAssigned = false;
				
				//keep randomly picking a substitute teacher until the shift can be assigned.
				while(!shiftAssigned) {
					
					//pick a substitute randomly and check whether or not they have already been assigned that shift.
					numSubstitutes = substituteTeachers.size();
					randSubstitute = randomGenerator.nextInt(numSubstitutes);
					SubstituteTeacher substituteTeacher = substituteTeachers.get(randSubstitute);
					System.out.println("Random sub: "+substituteTeacher.getName());
					
					//get shifts from randomly picked substitute teacher
					ArrayList<ShiftProperties> substituteShifts = substituteTeacher.getShifts();
					System.out.println("subshift: " + substituteShifts.size());
					
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
					else if(substituteShifts.size() == 1) {
						System.out.println("true");
						substituteTeacher.setShift(teacherShift);
						shiftAssigned = true;
						
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
