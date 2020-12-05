import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
enum EventChoice
{
	singleOccasion, recurringOccasion;
}

public class EventClass implements Comparable<EventClass> 
{
	String eventName ;
	TimeInterval timePeriod;
	EventChoice TypeOfEvent;
	/**
	 * this constructor takes creates an event object by taking the given data 
	 * @param eventName- name of the event 
	 * @param start_Date- starting date of the event 
	 * @param end_Date- ending date of the event 
	 * @param start_Time- time of the event being started 
	 * @param end_Time- time of the event being ended 
	 * @param TypeOfEvent- type of the event that justifies whether one time or recurring events
	 */
	public EventClass(String eventName, LocalDate starts, LocalDate ends, LocalTime startTime, LocalTime endTime, EventChoice TypeOfEvent) 
	{
		
		this.eventName= eventName;
		timePeriod = new TimeInterval( starts, ends,  startTime,  endTime);
		this.TypeOfEvent = TypeOfEvent;
		
	}
	/**
	 * 
	 * @return-gets and returns the name of the event
	 */
	public String getName()
	{
		return eventName;
	}
	/**
	 * 
	 * @return-gets and returns the time interval
	 */
	public TimeInterval getTime()
	{
		return timePeriod;
	}
	/**
	 * 
	 * @return- the type of the event either one time or recurring 
	 */
	public EventChoice GetTypeOfEvent()
	{
		return TypeOfEvent;
	}
	/**
	 * 
	 * @param eventName-getting the name of the event to check if there are any same events 
	 * @return- returns true if same otherwise false
	 */
	public boolean checkEventsimilarity(String eventName)
	{
		if(this.getName().equalsIgnoreCase(eventName))
		{
			return true;
		}
		return false;
	}
	/**
	 * compares two events by comparing two time intervals
	 */
	@Override
	public int compareTo(EventClass nextOne) 
	{
		return this.getTime().compareTo(nextOne.getTime());
		
	}
	/**
	 * checks whether the time interval between two events overlaps or not 
	 * @param nextOne
	 * @return
	 */
	public boolean intervalOverlap(EventClass nextOne)
	{
		return this.getTime().intervalOverlap(nextOne.getTime());
	}
	/**
	 * prints the event in a given format 
	 */
	public void printEvent()
	{
		DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("H:mm");
		System.out.println(timeformat.format(getTime().getStartTime()) + " - " + timeformat.format(getTime().getEndTime()) + " " + getName());
	}
	

}
