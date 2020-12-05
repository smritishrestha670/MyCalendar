

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MyCalendar 
{
	//Using hashmap to store the localDate as the key and Arraylist to list out all the events
	TreeMap<LocalDate, ArrayList<EventClass>> scheduleEvents = new TreeMap<LocalDate, ArrayList<EventClass>>();
	LocalDate currentDate= LocalDate.now();
   /**
   * Prints all events that are scheduled the calendar 
   */
   public void printEvents() 
   {
     
       DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("EEEE, MMMM d ");
       
       int year = scheduleEvents.firstKey().getYear();
       System.out.println(year);
       
       for (Entry<LocalDate, ArrayList<EventClass>> entry :scheduleEvents.entrySet()) {
          
           Collections.sort(entry.getValue());
           
           if (entry.getKey().getYear() != year) 
           {
               year = entry.getKey().getYear();
               System.out.println(year);
           }
          
           for (EventClass e : entry.getValue()) 
           {
               System.out.print(" " + dateformatter.format(entry.getKey()) + " ");
               e.printEvent();
           }
       }
       System.out.println();
   }
   /**
	 * 
	 * @param currentDate-sets the current date on the calendar 
	 */

   public void setCurrentCalendarAt(LocalDate currentDate) {
       this.currentDate = currentDate;
   }
   /**
	 * 
	 * @return-this returns the currents date form the calendar 
	 */

   public LocalDate getCurrentCalendarAt() 
   {
       return currentDate;
   }
   /**
    * 
    * @param days- refers to seven days in a week 
    * @param startDate - start date of the event being scheduled 
    * @param endDate - end date of the event being scheduled 
    * @return - dates corresponding to the days 
    */
   public ArrayList<LocalDate> getSpecificDates(String days, LocalDate startDate, LocalDate endDate) {
       ArrayList<DayOfWeek> TotalDays_week = new ArrayList<DayOfWeek>();
       for (char day : days.toCharArray()) {
           if (day == 'M') {
               TotalDays_week.add(DayOfWeek.MONDAY);
           } else if (day == 'T') {
               TotalDays_week.add(DayOfWeek.TUESDAY);
           } else if (day == 'W') {
               TotalDays_week.add(DayOfWeek.WEDNESDAY);
           } else if (day == 'R') {
               TotalDays_week.add(DayOfWeek.THURSDAY);
           } else if (day == 'F') {
               TotalDays_week.add(DayOfWeek.FRIDAY);
           } else if (day == 'S') {
               TotalDays_week.add(DayOfWeek.SATURDAY);
           } else if (day == 'U') {
               TotalDays_week.add(DayOfWeek.SUNDAY);
           }
       }
       
       ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
       
       for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) 
       {
           if (TotalDays_week.contains(d.getDayOfWeek())) 
           {
               dates.add(d);
           }
       }
       return dates;
   }
   /**
    * adds one time event to the treemap by getting the date and time 
    * @param event- one time event being added to the treemap 
    */
  
   public void addSingleOccasion(EventClass event) 
   {
	   
       LocalDate start = event.getTime().getStarts();
       // Check whether the event overlaps with any other events
       if (!intervalOverlap(start, event)) 
       {
        
    	   scheduleEvents.putIfAbsent(start, new ArrayList<EventClass>());
    	   scheduleEvents.get(start).add(event);
           System.out.println("Event " + event.getName() + " has been added!");
       }
       else 
       {
           System.out.println(" Time Conflict! Could not add the event" + event.getName());

       }
       
   }
   /**
    * adds recurring event  event to the treemap by getting the date and time 
    * @param event- recurring event added to the treemap
    * @param days - days the recurring event occurs 
    */
   
   public void addRecurringOccasion(EventClass event, String days) 
   {
       
       ArrayList<LocalDate> dates = getSpecificDates(days, event.getTime().getStarts(), event.getTime().getEnds());
       for (LocalDate date : dates) 
       {
          
           if (!intervalOverlap(date, event)) 
           {
               
        	   scheduleEvents.putIfAbsent(date, new ArrayList<EventClass>());
        	   scheduleEvents.get(date).add(event);
        	   
           }
           else 
           {
               System.out.println(" Time Conflict! Could not add the event" + event.getName());

           }
           
       }
       System.out.println("Event " + event.getName() + " has been added!");
   }

  /**
   * 
   * @param date- date of an event 
   * @param e - refers to the event being checked for overlapping with other events 
   * @return- true or false for the overlapping of an events 
   */
   public boolean intervalOverlap(LocalDate date, EventClass e) {
       ArrayList<EventClass> dayEvents = scheduleEvents.get(date);
       if (dayEvents != null) 
       { 
           for (EventClass event : dayEvents) 
           {
               
               if (event.intervalOverlap(e) == true) 
               {
                   return true;
               }
           }
       }
       return false;
   }
   /**
    * 
    * @param date- date input by user to delete particular event
    * @param name - name of the event in that particular date to be deleted 
    */
   public void remove(LocalDate date, String name) {
       EventClass eventFound;
       int size = scheduleEvents.get(date).size();
       
       for (int i = 0; i < size; i++) {
           eventFound = scheduleEvents.get(date).get(i);
           // If find the event with name given, remove from ArrayList
           if (eventFound.checkEventsimilarity(name) == true) {
        	   scheduleEvents.get(date).remove(i);
               break;
           }
       }
       
       if (scheduleEvents.get(date).size() == size) 
       {
           throw new IllegalArgumentException("No such event in the calendar");
       }
   }
   /**
    * when user inputs the date this method clears all the event in that given date
    * @param date- date for which all the vents will be deleted 
    */
   public void removeAllEvents(LocalDate date) {
       // deletes all events on the date inputed by the user 
       if (scheduleEvents.get(date) != null) {
    	   scheduleEvents.get(date).clear();
       }
   }
   public void removeRecurring(LocalDate date, String name) {
       EventClass eventFound;
       int size = scheduleEvents.get(date).size();
       
       for (int i = 0; i < size; i++) 
       {
           eventFound = scheduleEvents.get(date).get(i);
           // If find the event with name given, remove from ArrayList
           if (eventFound.checkEventsimilarity(name) == true) {
        	   scheduleEvents.get(date).remove(i);
               break;
           }
       }
   }
   /**
    * 
    * @param countDays- days by which the calendar will be advanced
    */
   public void increase_Day(int countDays) 
   {
	   currentDate = currentDate.plusDays(countDays);
   }
   /**
    * 
    * @param countMonths- month by which the calendar will be advanced
    */
   public void increase_Month(int countMonths) {
	   currentDate = currentDate.plusMonths(countMonths);
   }
   /**
    * Prints events that are scheduled on the current day the calendar is at
    *
    */
    public void printDayCalendar() {
        // Formats Day in wanted format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d, yyyy");
        System.out.println(" " + formatter.format(currentDate));
        // Get events at current day
        ArrayList<EventClass> dayEvents = scheduleEvents.get(currentDate);
        if (dayEvents != null) 
        {
            // Sort events in order of time
            Collections.sort(dayEvents);
            // Print event details
            for (EventClass event : dayEvents) 
            {
                event.printEvent();
            }
        }
        System.out.println();
    }
    /**
     * Prints events that are scheduled on the current month the calendar is at
     */
   public void printMonthlyCalendar() 
   {
	  // event in a month
       String month = currentDate.getMonth().toString();
       
       month = month.charAt(0) + month.toLowerCase().substring(1, month.length());
       System.out.println(" " + month + " " + currentDate.getYear());
       
      
        //Print Days in a corresponding order
       System.out.println("Su Mo Tu We Th Fr Sa");
      
     LocalDate current = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
     String indentFirstWeek = "";
      
       for (int index = 0; index <current.getDayOfWeek().getValue(); index++) 
       {
    	   //System.out.print("  "); 
    	  indentFirstWeek += "   ";
       }
       System.out.print(indentFirstWeek);

       
       for (int i = 0; i <currentDate.lengthOfMonth(); i++) 
       {
           if (current.equals(LocalDate.now())) 
           {
               System.out.printf("[%2s] ", current.getDayOfMonth());
           } 
           else 
           {
               System.out.printf("%2s ", current.getDayOfMonth());
           }
           
           current = current.plusDays(1);
           if (current.getDayOfWeek()==DayOfWeek.SUNDAY) 
          {
               System.out.print("\n");
           }
          
       }
       System.out.println();
     
      // e.printEvents();
   }
  /* public void printMonthlyCalendar(EventClass event) 
   {
	  // event in a month
       String month = currentDate.getMonth().toString();
       
       month = month.charAt(0) + month.toLowerCase().substring(1, month.length());
       System.out.println(" " + month + " " + currentDate.getYear());
       
      
        //Print Days in a corresponding order
       System.out.println("Su Mo Tu We Th Fr Sa");
      
     LocalDate current = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
     String indentFirstWeek = "";
      
       for (int index = 0; index <current.getDayOfWeek().getValue(); index++) 
       {
    	   //System.out.print("  "); 
    	  indentFirstWeek += "   ";
       }
       System.out.print(indentFirstWeek);

       
       for (int i = 0; i <currentDate.lengthOfMonth(); i++) 
       {
    	  
           if  (event.getTime().getStarts()== currentDate) 
           {
               System.out.printf("[%2s] ", current.getDayOfMonth());
           } 
           else 
           {
               System.out.printf("%2s ", current.getDayOfMonth());
           }
           
           current = current.plusDays(1);
           if (current.getDayOfWeek()==DayOfWeek.SUNDAY) 
          {
               System.out.print("\n");
           }
          
       }
       System.out.println();
      // if(e.)
      // e.printEvents();
   }*/
  
  
}





