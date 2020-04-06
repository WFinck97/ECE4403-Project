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
							//make sure the oncall sub can be assigned
							if (ShiftAssigner.checkShiftPossibility(shift, sub)){
								sub.setShift(shift);
							}
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
	public static void suitablilityAssign(ArrayList<AbsentTeacher> absentTeachers, ArrayList<SubstituteTeacher> substituteTeachers) {
		// looking at absent teachers and trying to decide which  assign, and remove that shift from the list of absent shifts.
		
		for(AbsentTeacher absentTeacher : absentTeachers) {
		int MAX = 0;
		SubstituteTeacher mostSuitable = null;
		//Get Teachables for teacher. It was assumed teachables applied to the teacher-level and not to the shift
		ArrayList<String> absTeachTeachables = absentTeacher.getTeachable();
		//Get Shifts for teacher that teachables applies too
		ArrayList<ShiftProperties> shifts = absentTeacher.getShifts();
		//Pay attention to covered shifts removed from teacher
		ArrayList<Integer> indexOfCoveredShift = new ArrayList<Integer>();
			for(ShiftProperties shift : shifts) {
				//find sub that can best cover shift
				int count = 0;
				for(SubstituteTeacher sub : substituteTeachers) {
					ArrayList<String> subTeachTeachables = sub.getTeachable();
					// Finding where the max number of overlapping teachables for each absence
					for(String a: absTeachTeachables) {
						for(String s: subTeachTeachables) {
							if(a.equals(s)){
								//count is number of teachables sub has that match up with absent teacher
								count++;
							}
						}
					}					
					if (count>MAX) {
						//Cannot assign most suitable sub if they're not available
						if (ShiftAssigner.checkShiftPossibility(shift, sub)) {
							mostSuitable = sub;
							MAX = count;
						}
					}
				}// End of subs
				//set the most suitable sub if one was found
				if (mostSuitable != null) {
					mostSuitable.setShift(shift);
					indexOfCoveredShift.add(shifts.indexOf(shift));
				}
			} // END of shift
			//remove the shifts that have been covered by on call substitute from list of absent shifts (no longer an absent shift)
			for(int index : indexOfCoveredShift) {
				absentTeacher.removeShift(index);
			}
		}//END of absent teachers
		
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
					
					//get shifts from randomly picked substitute teacher
					substituteShifts = substituteTeacher.getShifts();
					
					// get unavailabilities shifts from availabilities 
					unavailSubstituteShifts = substituteTeacher.getUnavailableShifts();	
					//only loop through assigned shifts if the teacher has been assigned anything

					//only loop if substitute has previously assigned shifts or unavailabilites
					if(substituteShifts.size() > 0 || unavailSubstituteShifts.size() > 0 ) {
						
						
						//only loop through assigned shifts if the teacher has been assigned anything				
						if(substituteShifts.size() > 0) {
								
							// assume able to assign shift
							shiftAssigned = true;
															
							for(int j = 0; j < substituteShifts.size(); j++) {
										
								// If the sub teach has unavailabilities then do not assign
								if((substituteShifts.get(j).getDate().equals(teacherShift.getDate())) && (substituteShifts.get(j).getPeriod().equals(teacherShift.getPeriod())) ) {
									shiftAssigned = false;										
								}	
							}
						} 
						
						//only enter loop if sub has unavailabilities
						if(unavailSubstituteShifts.size() > 0) {
													
							for(int j = 0; j < unavailSubstituteShifts.size(); j++) {
								// If the sub teach has unavailabilities then do not assign and break loop
								if((unavailSubstituteShifts.get(j).getDate().equals(teacherShift.getDate())) && (unavailSubstituteShifts.get(j).getPeriod().equals(teacherShift.getPeriod())) ) {
									shiftAssigned = false;
									assign = false;
									break;
								}
								else {
									// tentatively assign shift
									shiftAssigned = true;
								}
							}
						}
					}

					// only enter if all cases above have been tested and proven false
					if(shiftAssigned || assign) {
						//check to see if location is blacklisted by substitute
						if(teacherShift.getLocation().equals(substituteTeacher.getBlacklist())) {
							shiftAssigned = false;
							assign = false;
						}	
						else
						{
							substituteTeacher.setShift(teacherShift);
							shiftAssigned = true;
							break;
						}
					}
				} //end while loop	
			} //end loop that goes through each shift assigned to one teacher
		} //end loop that goes through each teacher
	}

	static private boolean checkShiftPossibility(ShiftProperties shift, SubstituteTeacher sub) {

		if (shift.getLocation().equals(sub.getBlacklist())) {
			//sub is blacklisted and cannot be assigned
			return false;
		}
		for (ShiftProperties subShift: sub.getShifts()) {
			if (subShift.getDate().equals(shift.getDate()) && subShift.getPeriod().equals(shift.getPeriod())) {
				//sub already has shift assigned at teachers shift date, cannot assign
				return false;
			}
		}
		for (ShiftProperties unavailableShift: sub.getUnavailableShifts()) {
			if (unavailableShift.getDate().equals(shift.getDate())) {
				//sub is unavailable during shift, cannot be assigned
				return false;
			}
		}
		return true;
	}

	public static void preferredAssign(ArrayList<AbsentTeacher> absentTeachers, ArrayList<SubstituteTeacher> substituteTeachers) {
		//run through the list of absent teachers and see if they have preferred subs. If those subs are available assign them
		for (AbsentTeacher teacher : absentTeachers) {
			//check if teacher has preferred sub
			if (teacher.getPreferredSub() != null) {
				//loop through sub teachers to find preferred sub
				for (SubstituteTeacher sub : substituteTeachers) {
					if (sub.getName().equals(teacher.getPreferredSub())) {
						//keep track of assigned shifts from sub so they can be removed later
						ArrayList<Integer> indexOfCoveredShift = new ArrayList<>();
						for (ShiftProperties shift : teacher.getShifts()) {
							//once preferred sub is found, try and assign this sub to teachers shifts

							//check if sub can be assigned to teachers shift
							if (ShiftAssigner.checkShiftPossibility(shift, sub)) {
								//assign sub to teachers shift
								sub.setShift(shift);
								indexOfCoveredShift.add(teacher.getShifts().indexOf(shift));
							}

						}
						//remove the shifts that have been covered by on call substitute from list of absent shifts (no longer an absent shift)
						for (int index : indexOfCoveredShift) {
							teacher.removeShift(index);
						}
					}
				}
			}
		}
	}
}



