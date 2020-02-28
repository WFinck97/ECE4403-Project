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
			String name = record.get("name");
			for(SubstituteTeacher sub : substituteTeachers) {
				if(sub.getName().equals(name)) { 
					sub.setUnavailableShift(shift);
				}
			}
			
			substituteTeachers.add(substituteTeacher);
		}
		
		substitutesParser.close();
		
		
		CSVParser unavailabilitiesParser = new CSVParser(new FileReader("unavailabilities.csv"), CSVFormat.EXCEL.withFirstRecordAsHeader());

		for (CSVRecord record : unavailabilitiesParser) {
			SubstituteTeacher substituteTeacher = new SubstituteTeacher();
			String name = record.get("name");
			String date = record.get("date");
			String blacklist = record.get("blacklist");
			
			String period = record.get("period");
			String teacher = record.get("teacher");
			String location = record.get("location");
			String teachables = record.get("teachables");
			
			
			substituteTeacher.setName(name);
			substituteTeachers.add(substituteTeacher);
		}
		
		substitutesParser.close();
		
		
		
		
		
		// call the lottery function and assign shifts to substitutes
		// take the assignments and put in a csv output file
		
		LotteryAssigner.RandomAssign(absentTeachers, substituteTeachers);
		
		CSVPrinter csvPrinter = new CSVPrinter(new FileWriter("assignments.csv"), CSVFormat.EXCEL.withHeader("substitute teacher", "date", "period", "location"));
		
		//basically for each assigned shift make an entry (rather than only show a name once and all the shifts to that person
		for(SubstituteTeacher subTeacher : substituteTeachers) {
			
			for(ShiftProperties shift : subTeacher.getShifts()) {
				
				csvPrinter.printRecord(subTeacher.getName(), shift.getDate(), shift.getPeriod(), shift.getLocation());
			}
		}
		
		csvPrinter.close();
	}
	

}
