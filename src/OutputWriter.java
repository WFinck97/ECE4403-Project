import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// The "*" tells Java to import all of the Classes included in the library.
import org.apache.commons.csv.*;

public class OutputWriter {
	
	public static void assignmentCSVOut(String assignmentsCSV, ArrayList<SubstituteTeacher> substituteTeachers) throws FileNotFoundException, IOException {
		
		CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(assignmentsCSV), CSVFormat.EXCEL.withHeader("substitute teacher", "date", "period", "location"));
		
		//basically for each assigned shift make an entry (rather than only show a name once and all the shifts to that person
		for(SubstituteTeacher subTeacher : substituteTeachers) {
			
			for(ShiftProperties shift : subTeacher.getShifts()) {
				
				csvPrinter.printRecord(subTeacher.getName(), shift.getDate(), shift.getPeriod(), shift.getLocation());
			}
		}
		
		csvPrinter.close();
	}
}
