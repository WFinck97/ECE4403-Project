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
		
		CSVParser oncallParser = new CSVParser(new FileReader("oncalls.csv"), CSVFormat.EXCEL.withFirstRecordAsHeader());

		for (CSVRecord record : oncallParser) { 
			 
			String name = record.get("substitute");
			String location = record.get("location");
			
			 for (SubstituteTeacher sub : substituteTeachers){
				 if (name.equals(sub.getName())) {
					 sub.addOncallLocation(location);
				 }
			 }
		}
		
		oncallParser.close();

		CSVParser preferredParser = new CSVParser(new FileReader("preferred.csv"), CSVFormat.EXCEL.withFirstRecordAsHeader());

		for (CSVRecord record : preferredParser) {

			String teacherName = record.get("teacher");
			String preferredSubName = record.get("substitute");
			for (AbsentTeacher teacher: absentTeachers) {
				if (teacherName.equals(teacher.getName())) {
					teacher.setPreferredSub(preferredSubName);
				}
			}
		}
		// go through and assign based on the on call list, then go and assign the rest randomly
		
		// call the lottery function and assign shifts to substitutes
		// take the assignments and put in a csv output file
		ShiftAssigner.suitablilityAssign(absentTeachers, substituteTeachers);
		
		ShiftAssigner.oncallAssign(absentTeachers, substituteTeachers);

		ShiftAssigner.preferredAssign(absentTeachers,substituteTeachers);

		ShiftAssigner.randomAssign(absentTeachers, substituteTeachers);

		
		OutputWriter.assignmentCSVOut("assignments.csv", substituteTeachers);

	}
	

}
