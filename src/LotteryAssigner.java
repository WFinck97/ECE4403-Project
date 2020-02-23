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
			for(ShiftProperties shifts : teacherShifts) {
				
				//pick a substitute randomly and check whether or not they have already been assigned that shift.
				int numSubstitutes = substituteTeachers.size();
				int randSubstitute = randomGenerator.nextInt(numSubstitutes - 1);
				SubstituteTeacher substituteTeacher = substituteTeachers.get(randSubstitute);
				
				//get shifts from randomly picked substitute teacher
				ArrayList<ShiftProperties> absentShifts = substituteTeacher.getShifts();
			}
		}
	}
}
