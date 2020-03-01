import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class LotteryAssigner {
	
	
	public static void RandomAssign(ArrayList<AbsentTeacher> absentTeachers, ArrayList<SubstituteTeacher> substituteTeachers) {
		
		int numSubstitutes;
		int randSubstitute;
		SubstituteTeacher substituteTeacher;
		ArrayList<ShiftProperties> substituteShifts;
		ArrayList<ShiftProperties> unavailSubstituteShifts;
		
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
					numSubstitutes = substituteTeachers.size();
					randSubstitute = randomGenerator.nextInt(numSubstitutes);
					substituteTeacher = substituteTeachers.get(randSubstitute);
					
					//get shifts from randomly picked substitute teacher
					substituteShifts = substituteTeacher.getShifts();
					
					// get unavailabilities shifts from availabilities 
					unavailSubstituteShifts = substituteTeacher.getUnavailableShift();
					
					
					// check if the teacherShift matches the substitutes unavailable list
									
					//only loop through assigned shifts if the teacher has been assigned anything
					if(substituteShifts.size() > 0) {
						
						// assume able to assign shift
						shiftAssigned = true;
						
						for(int i = 0; i < substituteShifts.size(); i++) {
							
								for(int j = 0; j < unavailSubstituteShifts.size(); i++) {
									
									// If the sub teach has unavailabilities then do not assign
									if((unavailSubstituteShifts.get(j).getDate().equals(teacherShift.getDate())) && (unavailSubstituteShifts.get(j).getPeriod().equals(teacherShift.getPeriod())) ) {
										shiftAssigned = false;
									}	
								}
							// If sub and teach have coinciding availabilities then 
							if(substituteShifts.get(i).getDate().equals(teacherShift.getDate()) && substituteShifts.get(i).getPeriod().equals(teacherShift.getPeriod())) {
								shiftAssigned = false;
							}
							else {
								
							}
						}
						
						if(shiftAssigned) {
							substituteTeacher.setShift(teacherShift);

						}
					}
					else {
						substituteTeacher.setShift(teacherShift);
						shiftAssigned = true;
						break;
					}
					
					//substituteShifts.clear();
				} //end while loop
				
			} //end loop that goes through each shift assigned to one teacher
		}//end loop that goes through each teacher
	}
}
