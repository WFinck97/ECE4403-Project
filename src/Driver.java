import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

// The "*" tells Java to import all of the Classes included in the library.
import org.apache.commons.csv.*;


public class Driver {
	
	private static ArrayList<AbsentTeacher> absentTeachers;
	private static ArrayList<SubstituteTeacher> substituteTeachers;
	
	public Driver() {
		absentTeachers = new ArrayList<AbsentTeacher>();
		substituteTeachers = new ArrayList<SubstituteTeacher>();
	}
	
	public static void main(String[] args) throws Exception {
		
		absentTeachers = CSVReader.readAbsences("absences.csv");
		substituteTeachers = CSVReader.readSubstitutes("substitutes.csv");
		
		
		CSVParser unavailabilitiesParser = new CSVParser(new FileReader("unavailabilities.csv"), CSVFormat.EXCEL.withFirstRecordAsHeader());

		for (CSVRecord record : unavailabilitiesParser) { 
			 
			String name = record.get("substitute");
			String date = record.get("date");
			String period = record.get("period");
			
			ShiftProperties unavailableShift = new ShiftProperties();
			unavailableShift.setDate(date);
			unavailableShift.setPeriod(period);
			
			 for (SubstituteTeacher sub : substituteTeachers){
				 if (name.equals(sub.getName())) {
					 sub.setUnavailableShifts(unavailableShift);
				 }
			 }
		}
		
		unavailabilitiesParser.close();
		
		// call the lottery function and assign shifts to substitutes
		// take the assignments and put in a csv output file
		ShiftAssigner.suitablilityAssign(absentTeachers, substituteTeachers);
		
		//LotteryAssigner.RandomAssign(absentTeachers, substituteTeachers);
		
		OutputWriter.assignmentCSVOut("assignments.csv", substituteTeachers);
	
		//System.out.println(substituteTeachers.get(0).getTeachable());
		//System.out.println(absentTeachers.get(0).getTeachable());
	}
	

}
