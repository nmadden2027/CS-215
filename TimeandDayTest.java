import java.time.*;

public class TimeandDayTest {
   public static void main(String args[]) {
      // Get the current date and time
      LocalDateTime currentTime = LocalDateTime.now();
      System.out.println("Current DateTime: " + currentTime);
        
      LocalDate curdate = currentTime.toLocalDate();
      System.out.println("Current Date: " + curdate);
        
      Month curmonth = currentTime.getMonth();
      int curday = currentTime.getDayOfMonth();
      int curseconds = currentTime.getSecond();
        
      System.out.println("Month: " + curmonth +", day: " + curday +", seconds: " + curseconds);
        
      LocalDateTime anotherdate = currentTime.withDayOfMonth(5).withYear(2010);
      System.out.println("Another Date: " + anotherdate);
    
      LocalDate yetanotherdate = LocalDate.of(2013, Month.JUNE, 9);
      System.out.println("Yet Another Date: " + yetanotherdate );
        
      LocalTime atime = LocalTime.of(5, 10);
      System.out.println("A Time: " + atime);
        
      LocalTime anothertime = LocalTime.parse("08:40:10");
      System.out.println("Another Time: " + anothertime);
   
      //Use zoned times instead
      System.out.println(ZonedDateTime.now());
      System.out.println(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
      
      
    
    }
   
}