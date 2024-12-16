import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestSFDF {
	
	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
		System.out.println(df.format(new Date()));
	}

}
