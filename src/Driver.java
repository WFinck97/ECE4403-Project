import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

// The "*" tells Java to import all of the Classes included in the library.
import org.apache.commons.csv.*;


public class Driver {
	
	private ArrayList<AbsentTeacher> absentTeachers;
	private ArrayList<SubstituteTeacher> substituteTeachers;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//
		// Reading from a CSV file using the CSVParser class
		//
		// The 2nd argument provided to the constructor indicates that the first line of the file
		// contains the label for each column
		//
		CSVParser csvParser = new CSVParser(new FileReader("absences.csv"), CSVFormat.EXCEL.withFirstRecordAsHeader());

		// After constructed, we can loop through each row of the CSV file using a for-each loop
		// We access the data in each column using the corresponding column label
		for (CSVRecord record : csvParser) {
			String date = record.get("date");
			String period = record.get("period");
			String teacher = record.get("teacher");
			String location = record.get("location");
			String teachables = record.get("teachables");
			System.out.println("CSV Record: " + date + " | " + period + " | " + teacher + " | " + location + " | " + teachables);
		}
		
		// Remember to close all input and output streams when you are done processing them
		csvParser.close();
	}

}
