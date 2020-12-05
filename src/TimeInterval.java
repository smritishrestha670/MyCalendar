import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeInterval implements Comparable <TimeInterval>
{
	//this class gets the start and end date, and start and end time and compares two time interval to check overlap
	private LocalDate starts;
	private LocalDate ends;
	private LocalTime startTime;
	private LocalTime endTime;
	
	/**
	 * This constructor is a time interval for the event that only occurs once when start date is equal to end date
	 * @param date- start and the end date of the event
	 * @param startTime- Time the one day event starts 
	 * @param endTime-Time the one day event ends 
	 */
	public TimeInterval(LocalDate date, LocalTime startTime, LocalTime endTime)
	{
		this.starts =this.ends= date;
		this.startTime=startTime;
		this.endTime= endTime;
		 
	}
	/**
	 * This constructor is for time interval for recurring events 
	 * @param starts- starting date of the particular event
	 * @param ends- ending time date the particular event 
	 * @param startTime-starting time of the particular event
	 * @param endTime-ending time of the particular event 
	 */
	public TimeInterval(LocalDate starts, LocalDate ends, LocalTime startTime, LocalTime endTime)
	{
		this.starts = starts;
		this.ends= ends;
		this.startTime=startTime;
		this.endTime= endTime;
	}
	/** 
	 * This method gets the starting date of the event from the object LocalDate
	 * @return- this returns the start date of the time interval when the event starts
	 */
	public LocalDate getStarts()
	{
		return starts;
	}
	/**
	 * This method gets the ending date of the event from the object LocalDate
	 * @return- this returns the date of the time interval when the event ends 
	 */
	public LocalDate getEnds()
	{
		return ends;
	}
	
	public LocalTime getStartTime()
	{
		return startTime;
	}
	public LocalTime getEndTime()
	{
		return endTime;
	}
	/**
	 * This method checks if there is any overlap in the time interval
	 * @param timeInterval- time period in which the event occurs 
	 * @return -true if it overlaps and false if it doesn't 
	 */
	public boolean intervalOverlap(TimeInterval timeInterval)
	{
		if(this.getStarts().equals(timeInterval.getStarts()))
		{
			return this.getStartTime().isBefore(timeInterval.getEndTime())&&timeInterval.getStartTime().isBefore(this.getEndTime());
		}
		return false;
	}
	/**
	 * compares the starting time for the event
	 */
	public int compareTo(TimeInterval nextOne) 
	{
	       return this.getStartTime().compareTo(nextOne.getStartTime());
	}
	
}
