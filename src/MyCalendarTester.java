import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * This program is implemented to schedule an event in the calendar allowing user to view, create, remove and print events.
 * @author smritishrestha
 * CS 151
 * 09/30/20
 * Professor Kim
 */

public class MyCalendarTester 
{
	   public static void main(String[] args)
	   {
	       MyCalendar calendar = new MyCalendar();
	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
	       loadEvents(calendar, formatter);
	       calendar.printMonthlyCalendar();
	       Scanner input = new Scanner(System.in);
	       String strDate = null;
	       LocalDate date = null;

	       String UserChoice = "";
	       // While the user does not want to quit
	       while (!UserChoice.equals("Q")) {
	           System.out.println("\nSelect one of the following options:");
	           // Display options
	           System.out.print("[V]iew by, [C]reate, [G]o to, [E]vent list, [D]elete, [Q]uit : ");
	           UserChoice = input.nextLine().toUpperCase(); // Read user input
	           switch (UserChoice) 
	           { 
	                               
	           case "V":
	        	   System.out.println("--View--");
	        	   calendar.setCurrentCalendarAt(LocalDate.now());
	               System.out.print("[D]ay view or [M]view ? : "); // Ask user if want to see calendar by Day or Month
	                                                               
	               String view = input.nextLine().toUpperCase(); // gets user input D/M
	               System.out.println();
	               String option = "";
	               int val = 0; 
	               while (!option.equals("G")) 
	               {
	                   if (view.equals("D")) 
	                   { 
	                       calendar.increase_Day(val);                                                                               
	                       calendar.printDayCalendar(); 
	                       
	                   } 
	                   else if (view.equals("M")) 
	                   { 
	                	  
	                       calendar.increase_Month(val);
	                       
	                       calendar.printMonthlyCalendar(); 
	                      
	                    
	                                                    
	                   } 
	               	   else 
	                   {
	                       try {
	                           throw new Exception("Invalid input. Try again");
	                       } catch (Exception e) {
	                           System.out.println(e.getMessage() + " - Invalid date");
	                       }
	                   }
	                   System.out.print("\n[P]revious or [N]ext or [G]o back to main menu ? : ");
	                   option = input.nextLine().toUpperCase(); //gets the user input
	                   if (option.equals("P")) 
	                   { 
	                       val = -1; // Go one step backwards in the calendar
	                   } 
	                   else if (option.equals("N")) 
	                   { 
	                       val = 1; // Go one step ahead in the calendar
	                       
	                   } 
	                   else if (!option.equals("G")) 
	                   { 
	                                                       
	                       try {
	                           throw new Exception("Invalid Input. Try again");
	                       } catch (Exception e) {
	                           System.out.println(e.getMessage() + " Date cannot be recognised");
	                       }
	                   }
	                  
	               }

	               break;
	           case "C":
	        	   System.out.println("--Create an Event--");
	               System.out.print("Name of Event: ");
	               String name = input.nextLine();

	               try {
	                   // Get date of event to create a new one
	                   System.out.print("Date of Event in MM/DD/YYYY: ");
	                   strDate = input.nextLine();
	                   formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	                   date = LocalDate.parse(strDate, formatter);
	                   formatter = DateTimeFormatter.ofPattern("H:mm");
	                   // Asking user to input start and end time of the new event being created in correct format
	                   System.out.print("Events starts at : ");
	                   String startTime = input.nextLine();
	                   System.out.print("Events ends at : ");
	                   String endTime = input.nextLine();
	                   // Add one time event to the calendar
	                   calendar.addSingleOccasion(new EventClass(name, date, date, LocalTime.parse(startTime, formatter), LocalTime.parse(endTime, formatter), EventChoice.singleOccasion));
	                   
	               } catch (Exception e) {
	                   System.out.println(e.getMessage() + " - Invalid date");
	               }

	               break;
	           case "G": 
	        	   System.out.println("--Go to--");
	               System.out.print("Enter a date see events occuring on that day:");
	               strDate = input.nextLine();
	               formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	               try 
	               {
	                   
	                   date = LocalDate.parse(strDate, formatter);
	                   calendar.setCurrentCalendarAt(date);
	                   calendar.printDayCalendar();
	                  
	               } 
	               catch (Exception e) 
	               {
	                   System.out.println(e.getMessage() + " - Invalid date");
	               }

	               break;
	           case "E": 
	        	   System.out.println("--Event List--");
	               calendar.printEvents();
	               break;
	               
	           case "D": 
	               System.out.println("--Delete--");
	               //Select option in order to delete particular event- one time, all or reoccurring 
	               System.out.print("\n[S]elected or [A]ll or [DR] ? ");
	               option = input.nextLine().toUpperCase(); 
	               System.out.print("Date of Event in MM/DD/YYYY: ");
	               strDate = input.nextLine();
	               formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	               try {
	                   
	                   date = LocalDate.parse(strDate, formatter);
	                   calendar.setCurrentCalendarAt(date);
	                   switch(option)
	                   {
	                   case "S":
	                   {
	                       calendar.printDayCalendar();
	                       System.out.print("Enter name of Event to delete: ");
	                       String eventName = input.nextLine();
	                      
	                           
	                          calendar.remove(date, eventName);
	                           System.out.println("Event has been deleted. Currently scheduled events:");	                        
	                         
	                   break;
	                   }
	                   case "DR":
	                   {
	                       calendar.printDayCalendar();
	                       System.out.print("Enter name of Event to delete: ");
	                       String eName = input.nextLine();
	                     
	                           
	                           calendar.removeRecurring(date, eName);
	                           System.out.println("Event Deleted. Currently scheduled events:");
	                           // Print new calendar
	                          
	                           break;
	                   }
	                   case "A":
	                   {
	                       // Remove all events on given date
	                	   calendar.printDayCalendar();
	                       calendar.removeAllEvents(date);
	                       
	                       System.out.println("All Events on the given date are Deleted! ");
	                       break;
	                   }
	                   }
	               }
	             
	               catch (Exception e) 
	               {
	                   System.out.println(e.getMessage() + " - Invalid date");
	               }

	               break;
	           case "Q": 
	               System.out.println("Quit");
	               System.out.println("Good Bye!");
	               break;
	           default:
	               System.out.println("Invalid input. Try again");
	               break;
	           }
	       }

	       // Print calendar to 'output.txt'
	       getEvents(calendar);
	       input.close();
	   }

	   /**
	    * 
	    * @param calendar- loads one time and reccuring events to the calendar 
	    * @param formatDate- formats the date in the given required format 
	    */

	   public static void loadEvents(MyCalendar calendar, DateTimeFormatter formatDate) {
	       String file = "events.txt";
	       // Read in file
	       
	       try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	           String line;
	           // Read file line by line
	           while ((line = br.readLine()) != null) {
	               // First line contains name
	               String name = line;
	               line = br.readLine();
	               char firstChar = line.charAt(0);
	               String index[] = line.split("\\s+");
	               
	               if (firstChar >= 'A' && firstChar <= 'Z') {
	                   // Split line by white space and parse into respective
	                   // elements
	            	   
	            	   formatDate = DateTimeFormatter.ofPattern("M/d/yy");
	                   LocalDate startDate = LocalDate.parse(index[3], formatDate);
	                   LocalDate endDate = LocalDate.parse(index[4], formatDate);
	                   formatDate = DateTimeFormatter.ofPattern("H:mm");
	                   // Add event to the calendar
	                   calendar.addRecurringOccasion(new EventClass(name, startDate, endDate, LocalTime.parse(index[1], formatDate), LocalTime.parse(index[2], formatDate), EventChoice.recurringOccasion),
	                           index[0]);
	               } 
	               
	               else 
	               {
	            	 
	                   // One Time event
	                   formatDate = DateTimeFormatter.ofPattern("M/d/yy");
	                   LocalDate startDate, endDate;
	                   startDate = endDate = LocalDate.parse(index[0], formatDate);
	                   formatDate = DateTimeFormatter.ofPattern("H:mm");
	                   // Add event to the calendar
	                   calendar.addSingleOccasion(new EventClass(name, startDate, endDate, LocalTime.parse(index[1], formatDate), LocalTime.parse(index[2], formatDate), EventChoice.singleOccasion));
	               }
	           }
	           // If file not found, throw exception
	       } catch (FileNotFoundException e) {
	           System.out.println("File not found " + e.getMessage());
	       } catch (IOException e) {
	           System.out.println("Error " + e.getMessage());
	       }
	       System.out.println("\nLoading is done!\n");
	   }
		/**
		 * 
		 * @param calendar-from the data inputed by user the event gets scheduled in the calendar and stored in output.txt
		 */
	   public static void getEvents(MyCalendar calendar) {
	       // Creating a File object that represents the disk file.
	       PrintStream printData = null;
	       try {
	           printData = new PrintStream(new File("output.txt"));
	       } catch (FileNotFoundException e) {
	           System.out.println("Error " + e.getMessage());
	       }

	       // Assign printer to output stream
	       System.setOut(printData);
	       calendar.printEvents();
	   }
}
