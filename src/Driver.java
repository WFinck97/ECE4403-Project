import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

// The "*" tells Java to import all of the Classes included in the library.
import org.apache.commons.csv.*;


public class Driver {
	
	//private ArrayList<AbsentTeacher> absentTeachers;
	//private ArrayList<SubstituteTeacher> substituteTeachers = new ArrayList<SubstituteTeacher>();
	
	
	public static void main(String[] args) throws Exception {
		
		ArrayList<AbsentTeacher> absentTeachers = new ArrayList<AbsentTeacher>();
		ArrayList<SubstituteTeacher> substituteTeachers = new ArrayList<SubstituteTeacher>();
		
		CSVParser absencesParser = new CSVParser(new FileReader("absences.csv"), CSVFormat.EXCEL.withFirstRecordAsHeader());

		for (CSVRecord record : absencesParser) {
			AbsentTeacher absentTeacher = new AbsentTeacher();
			ShiftProperties shift = new ShiftProperties();
			String date = record.get("date");
			String period = record.get("period");
			String teacher = record.get("teacher");
			String location = record.get("location");
			String teachables = record.get("teachables");
			
			absentTeacher.setName(teacher);
			shift.setDate(date);
			shift.setPeriod(period);
			shift.setLocation(location);
			
			absentTeacher.setShift(shift);
			absentTeachers.add(absentTeacher);
		}

		absencesParser.close();
		
		CSVParser substitutesParser = new CSVParser(new FileReader("substitutes.csv"), CSVFormat.EXCEL.withFirstRecordAsHeader());

		for (CSVRecord record : substitutesParser) {
			SubstituteTeacher substituteTeacher = new SubstituteTeacher();
			String name = record.get("name");
			String teachables = record.get("teachables");
			String blacklist = record.get("blacklist");
			
			substituteTeacher.setName(name);
			substituteTeachers.add(substituteTeacher);
		}
		
		substitutesParser.close();
		
		// call the lottery function and assign shifts to substitutes
		// take the assignments and put in a csv output file
		
		LotteryAssigner.RandomAssign(absentTeachers, substituteTeachers);
		
		
	}
	

}
