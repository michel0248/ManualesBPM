import java.util.logging.Logger;

public class project {


	public static void main(String[] args) {
		Logger logger = java.util.logging.Logger.getLogger("project");
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<5;i++) {
			sb.append("Logger ->");
			sb.append(i);
			sb.append("\n");
			logger.info(sb.toString());
		}

	}

}
