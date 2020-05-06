import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args) {
        String patternString = "(.*?,){13}z";
        Pattern pattern;
        boolean[] results = new boolean[20];
        long[] time = new long[20];
        
        pattern = Pattern.compile(patternString);
        for(int i = 0; i < 20; i++) {
            String text = "1,2,3,4,5,6,7,8,9,10,11,12";
            for(int j = 0; j < i; j++) {
                text += ",1";
            }
            long start = System.nanoTime();
            boolean result = pattern.matcher(text).matches();
            long end = System.nanoTime();
            long elapsed = (end - start) / 1000000;
            results[i] = result;
            time[i] = elapsed;
        }

        long avg = 0;
        System.out.println("Index | Result | Duration (ms)");
        for(int i = 0; i < 20; i++) {
            String text = i + "    ";
            if(i < 10) text += " ";
            text += "| " + results[i] + "  ";
            if(results[i] == true) text += " ";
            text += "| " + time[i];
            System.out.println(text);
            avg += time[i];
        }

        avg /= 20;

        System.out.println("Average Time (ms); " + avg);
    }
}