import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLParser {



public static String parseOuterJoins(String SQL) {
        String result = SQL;

        // Patrón: exp1 *= exp2 → exp1(+)=exp2
        String pattern1 = "(\\b(\\w+\\.\\w+)\\s*\\*=\\s*(\\w+\\.\\w+))";
        Pattern p1 = Pattern.compile(pattern1);
        Matcher m1 = p1.matcher(result);
        StringBuffer sb1 = new StringBuffer();
        while (m1.find()) {
            String exp1 = m1.group(2);
            String exp2 = m1.group(3);
            m1.appendReplacement(sb1, exp1 + "(+)=" + exp2);
        }
        m1.appendTail(sb1);
        result = sb1.toString();

        // Patrón: exp1 =* exp2 → exp1=exp2(+)
        String pattern2 = "(\\b(\\w+\\.\\w+)\\s*=\\*\\s*(\\w+\\.\\w+))";
        Pattern p2 = Pattern.compile(pattern2);
        Matcher m2 = p2.matcher(result);
        StringBuffer sb2 = new StringBuffer();
        while (m2.find()) {
            String exp1 = m2.group(2);
            String exp2 = m2.group(3);
            m2.appendReplacement(sb2, exp1 + "=" + exp2 + "(+)");
        }
        m2.appendTail(sb2);
        result = sb2.toString();

        // Reemplazo de #
        result = result.replace("#", "TT_");

        return result;
    }



    public static void main(String[] args) {
        String sql = "Rescates.EstadoRescate = EstadoRescate AND Rescates.CP *= CATCP.cp AND Rescates.Cuenta *= grabacion.F_Proc AND #tmpProductoFinal.Cuenta =* Rescates.Cuenta";
        System.out.println(parseOuterJoins(sql));
    }
}
