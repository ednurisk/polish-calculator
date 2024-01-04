import java.util.*;

public class PolishCalculatorr {

    public static boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    public static int whichOperator(char c) {
        if (c == '+') {
            return 0;
        } else if (c == '-') {
            return 1;
        } else if (c == '*') {
            return 2;
        } else if (c == '/') {
            return 3;
        } else {
            return -1;
        }
    }

    public static double doIt(char c, double val1, double val2) {
        int oprt = whichOperator(c);
        if (oprt == 0) { // addition
            return val1 + val2;
        } else if (oprt == 1) { // subtraction
            return val1 - val2;
        } else if (oprt == 2) { // multiplication
            return val1 * val2;
        } else if (oprt == 3) { // division
            return val1 / val2;
        } else {
            System.out.println("Value not an operator.");
            return -1;
        }
    }

    public static void main(String[] args) {
        Stack<Character> operatorStack = new Stack<>();
        List<Double> operandList = new ArrayList<>();
        List<Double> fifoList = new ArrayList<>();
        String lastOperand = "";
        double result = -1;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Polish Notation: ");
        String notation = scanner.nextLine();
        //String[] generalList = notation.split(" ");

        boolean isFine = false;

        for (int i = 0; i < notation.length(); i++) {
            char character = notation.charAt(i);
            if (character == ' ') {
                continue; // Boşluğu atla
            }

            if (isOperator(character)) {
                isFine = true;
                lastOperand = String.valueOf(character);
                operatorStack.push(character);
            } else {
                operandList.add(Double.parseDouble(String.valueOf(character)));
                if (operandList.size() == 2) {
                    if (isFine) {
                        result = doIt(operatorStack.peek(), operandList.get(0), operandList.get(1));
                        operandList.clear();
                        operatorStack.pop();
                        fifoList.add(result);
                        isFine = false;
                    } else {
                        fifoList.addAll(operandList);
                        operandList.clear();
                    }
                } else if (i == notation.length() - 1) {
                    fifoList.add(Double.parseDouble(String.valueOf(character)));
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            result = doIt(operatorStack.peek(), fifoList.get(0), fifoList.get(1));
            operatorStack.pop();
            fifoList.subList(0, 2).clear();
            fifoList.add(0, result);
        }

        System.out.println("Result: " + fifoList.get(0));
    }
}
