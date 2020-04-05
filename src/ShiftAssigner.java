import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class ShiftAssigner {
	
	public static void oncallAssign(ArrayList<AbsentTeacher> absentTeachers, ArrayList<SubstituteTeacher> substituteTeachers) {
		// run through the substitute teachers and check if they have any on call locations, if they do, check the list of absence, assign, and remove that shift from the list of absent shifts.
		
		
		for(SubstituteTeacher sub : substituteTeachers) {
			for(String oncallLocation : sub.getOncallLocations()) {
				// check if there are any shifts at this location
				for(AbsentTeacher absentTeacher : absentTeachers) {
					
					ArrayList<Integer> indexOfCoveredShift = new ArrayList<Integer>();
					ArrayList<ShiftProperties> shifts = absentTeacher.getShifts();
					for(ShiftProperties shift : shifts) {
						if(oncallLocation.equals(shift.getLocation())){
							sub.setShift(shift);
							
							// keep track of the indices of the shifts have have been taken by on call substitutes
							indexOfCoveredShift.add(shifts.indexOf(shift));
						}
					}
					
					
					//remove the shifts that have been covered by on call substitute from list of absent shifts (no longer an absent shift)
					for(int index : indexOfCoveredShift) {
						absentTeacher.removeShift(index);
					}
					
					
				}
			}
		}
		
		
	}
	
	
	public static void randomAssign(ArrayList<AbsentTeacher> absentTeachers, ArrayList<SubstituteTeacher> substituteTeachers) {
		
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
					boolean assign = true; //if this is true at the end of the if's, automatically assign the shift
										
					//pick a substitute randomly and check whether or not they have already been assigned that shift.
					numSubstitutes = substituteTeachers.size();
					randSubstitute = randomGenerator.nextInt(numSubstitutes);
					substituteTeacher = substituteTeachers.get(randSubstitute);
					
					System.out.println("");
					System.out.println("Sub teacher: " +  substituteTeacher.getName());
					System.out.println("Shift: " + teacherShift.getDate());
					
					//get shifts from randomly picked substitute teacher
					substituteShifts = substituteTeacher.getShifts();
					
					// get unavailabilities shifts from availabilities 
					unavailSubstituteShifts = substituteTeacher.getUnavailableShifts();	
					//only loop through assigned shifts if the teacher has been assigned anything

					//only loop if substitute has previously assigned shifts or unavailabilites
					if(substituteShifts.size() > 0 || unavailSubstituteShifts.size() > 0 ) {
						
						
						//only loop through assigned shifts if the teacher has been assigned anything				
						if(substituteShifts.size() > 0) {
							
							System.out.println("Sub has previous shifts");
							
							// assume able to assign shift
							shiftAssigned = true;
															
							for(int j = 0; j < substituteShifts.size(); j++) {
										
								// If the sub teach has unavailabilities then do not assign
								if((substituteShifts.get(j).getDate().equals(teacherShift.getDate())) && (substituteShifts.get(j).getPeriod().equals(teacherShift.getPeriod())) ) {
									shiftAssigned = false;										
									System.out.println("Shift not assigned due to prev shifts");
								}	
							}
						} 
						
						//only enter loop if sub has unavailabilities
						if(unavailSubstituteShifts.size() > 0) {
							
							System.out.println("Sub has unavails");
						
							for(int j = 0; j < unavailSubstituteShifts.size(); j++) {
								// If the sub teach has unavailabilities then do not assign and break loop
								if((unavailSubstituteShifts.get(j).getDate().equals(teacherShift.getDate())) && (unavailSubstituteShifts.get(j).getPeriod().equals(teacherShift.getPeriod())) ) {
									shiftAssigned = false;
									assign = false;
									System.out.println("Shift not assigned due to unavails");
									break;
								}
								else {
									// tentatively assign shift
									System.out.println("Shift Assigned in unavailability Loop");
									shiftAssigned = true;
								}
							}
						}
					}
					// enter if location is on substitute teacher's blacklist

					System.out.println(shiftAssigned);
					System.out.println("Location of shift: "+ teacherShift.getLocation());
					System.out.println("Location of blacklist: "+ substituteTeacher.getBlacklist());

/*					if(teacherShift.getLocation().equals(substituteTeacher.getBlacklist())) {
						System.out.println("made it");
						shiftAssigned = false;
						assign = false;
					}
					*/
					// only enter if all cases above have been tested and proven false
					if(shiftAssigned || assign) {
						if(teacherShift.getLocation().equals(substituteTeacher.getBlacklist())) {
							System.out.println("made it");
							shiftAssigned = false;
							assign = false;
						}	
						else
						{
							System.out.println("Assigned");
								substituteTeacher.setShift(teacherShift);
							shiftAssigned = true;
							break;
						}
					}
				} //end while loop	
			} //end loop that goes through each shift assigned to one teacher
		} //end loop that goes through each teacher
	}
	
}
