import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final Set<String> arabicNumbers = Set.of(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10"
    );

    private static final Set<String> romanNumbers = Set.of(
            "I",
            "II",
            "III",
            "IV",
            "V",
            "VI",
            "VII",
            "VIII",
            "IX",
            "X"
    );

    private final static Map<Character, Integer> converterMap = new HashMap<>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};

    private final static Map<Integer, String> convertMapReverse = new HashMap<>() {{
        put(1, "I");
        put(2, "II");
        put(3, "III");
        put(4, "IV");
        put(5, "V");
        put(6, "VI");
        put(7, "VII");
        put(8, "VIII");
        put(9, "IX");
        put(10, "X");
        put(50, "L");
        put(100, "C");
    }};

    public static int toArabic(String romanNotation) {
        final int length = romanNotation.length() - 1;
        int result = converterMap.get(romanNotation.charAt(length));
        for (int i = 0; i < length; ) {
            int left = converterMap.get(romanNotation.charAt(i));
            int right = converterMap.get(romanNotation.charAt(++i));
            if (right > left) {
                result -= left;
            } else {
                result += left;
            }
        }
        return result;
    }

    private static String toRoman(String arabicNotation) throws Exception { // 64
        final int length = arabicNotation.length() - 1;
        String[] array = arabicNotation.split("");
        String result = "";
        if (array[0].equals("-")) {
            throw new Exception();
        } else {
            if (convertMapReverse.containsKey(Integer.parseInt(arabicNotation))) {
                return convertMapReverse.get(Integer.parseInt(arabicNotation));
            } else {
                int firstNumber = Integer.parseInt(array[0]) * 10;
                int secondNumber = Integer.parseInt(array[1]);
                if (firstNumber < 50) {
                    while (firstNumber >= 10) { // 1 2 3
                        result += convertMapReverse.get(10); //XXXX
                        firstNumber -= 10;
                    }
                    return result += convertMapReverse.get(secondNumber);
                } else {
                    while (firstNumber >= 10) {
                        if (firstNumber >= 50) {
                            result += convertMapReverse.get(50);
                            firstNumber -= 50;
                        } else {
                            result += convertMapReverse.get(10); //XXXX
                            firstNumber -= 10;
                        }
                    }
                    return result += convertMapReverse.get(secondNumber);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(calc(new Scanner(System.in).nextLine()));
    }

    public static String calc(String input) throws Exception {
        String[] values = input.split(" ");
        if (values.length != 3) {
            throw new Exception();
        }
        boolean isArabicNumbers = arabicNumbers.contains(values[0]) && arabicNumbers.contains(values[2]);
        boolean isRomanNumbers = romanNumbers.contains(values[0]) && romanNumbers.contains(values[2]);
        if (isArabicNumbers || isRomanNumbers) {
            int firstArg = isArabicNumbers ? Integer.parseInt(values[0]) : toArabic(values[0]);
            int secondArg = isArabicNumbers ? Integer.parseInt(values[2]) : toArabic(values[2]);
            if (secondArg == 0 && values[1] == "/") {
                throw new Exception();
            }
            String result = String.valueOf(switch (values[1]) {
                case "+" -> firstArg + secondArg;
                case "-" -> firstArg - secondArg;
                case "*" -> firstArg * secondArg;
                case "/" -> firstArg / secondArg;
                default -> throw new Exception();
            });
            return isArabicNumbers ? result : toRoman(result);
        } else {
            throw new Exception();
        }
    }

}