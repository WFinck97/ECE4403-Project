import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// The "*" tells Java to import all of the Classes included in the library.
import org.apache.commons.csv.*;

public class CSVReader {
	
	public static ArrayList<AbsentTeacher> readAbsences(String absencesCSV) throws FileNotFoundException, IOException {
		ArrayList<AbsentTeacher> absentTeachers = new ArrayList<AbsentTeacher>();
		
		
		CSVParser absencesParser = new CSVParser(new FileReader(absencesCSV), CSVFormat.EXCEL.withFirstRecordAsHeader());
		
		
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
			
			absentTeacher.setTeachable(teachables);
			absentTeacher.setShift(shift);
			absentTeachers.add(absentTeacher);
		}

		absencesParser.close();
		return absentTeachers;
	}
	
	public static ArrayList<SubstituteTeacher> readSubstitutes(String substitutesCSV) throws FileNotFoundException, IOException {
		ArrayList<SubstituteTeacher> substituteTeachers = new ArrayList<SubstituteTeacher>();
		
		CSVParser substitutesParser = new CSVParser(new FileReader(substitutesCSV), CSVFormat.EXCEL.withFirstRecordAsHeader());

		for (CSVRecord record : substitutesParser) {
			SubstituteTeacher substituteTeacher = new SubstituteTeacher();
			String name = record.get("name");
			String teachable = record.get("teachables");
			String blacklist = record.get("blacklist");
			
			substituteTeacher.setName(name);
			substituteTeacher.setTeachable(teachable);
			substituteTeacher.setBlacklist(blacklist);
			substituteTeachers.add(substituteTeacher);
		}
		
		substitutesParser.close();
		return substituteTeachers;
	}
	
	public static void updateUnavailabilities(String unavailabilitiesCSV, ArrayList<SubstituteTeacher> subTeachers) {
		
	}
}
