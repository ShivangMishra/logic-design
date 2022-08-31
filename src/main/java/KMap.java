import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KMap {
    private final List<String> minTerms;
    private final int[][] matrix;
    private final int rVars;
    private final int cVars;

    private final String[] rGrayCodes;
    private final String[] cGrayCodes;

    public static void main(String[] args) {
        List<Integer> minTermInts = List.of(1, 2, 3, 25, 10);
        KMap map = new KMap(minTermInts);
        System.out.println("m" + minTermInts);
        map.draw();
    }

    public KMap(List<Integer> minTermInts)  {
        this.minTerms = minTermInts.stream()
                .map(Integer::toBinaryString)
                .collect(Collectors.toList());
        int nVars = this.minTerms.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("")
                .length();

        List<String> terms = this.minTerms;
        for (int i = 0; i < terms.size(); i++) {
            String minTerm = terms.get(i);
            int padding = nVars - minTerm.length();
            minTerm = ("0".repeat(Math.max(0, padding))) + minTerm;
            this.minTerms.set(i, minTerm);
        }
        rVars = nVars / 2;
        cVars = nVars - rVars;
        rGrayCodes = grayCodes(rVars);
        cGrayCodes = grayCodes(cVars);
        this.matrix = toMatrix();
    }

    public int[][] toMatrix() {

        int rows = (int) Math.pow(2, rVars);
        int cols = (int) Math.pow(2, cVars);
        int[][] matrix = new int[rows][cols];


        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                StringBuilder binary = new StringBuilder();
                binary.append(rGrayCodes[row]);

                binary.append(cGrayCodes[col]);
                matrix[row][col] = minTerms.contains(binary.toString()) ? 1 : 0;
//                System.out.print(binary + " ");
            }
//            System.out.println();
        }
        return matrix;
    }

    private static String[] grayCodes(int n) {
        int nCodes = (int) Math.pow(2, n);
        String[] grayCodes = new String[nCodes];
        for (int i = 0; i < grayCodes.length; i++) {
            int val = i ^ (i >> 1);
            String grayCode = Integer.toBinaryString(val);
            grayCodes[i] = "0".repeat(Math.max(0, n - grayCode.length())) + grayCode;
        }
        return grayCodes;
    }

    public void draw() {
        System.out.println("\n" +
                " ".repeat(cVars + (cGrayCodes.length * cVars) / 2) + "K-Map");
        char var = 64;
        for (int i = 1; i <= rVars; i++) {
            var++;
            System.out.print(var + "");
        }
        System.out.print("\\");
        for (int i = 1; i <= cVars; i++) {
            var++;
            System.out.print(var + "");
        }
        System.out.print(" ");
        for (String code : cGrayCodes) {
            System.out.print(code + " ");
        }
        System.out.println();

//        String offset = " ".repeat((var - 63) - rVars);
        for (int row = 0; row < matrix.length; row++) {
            System.out.print(rGrayCodes[row] + "  " + " ".repeat(cVars));
            for (int col = 0; col < matrix[row].length; col++) {
                int anInt = matrix[row][col];
                System.out.print(anInt + " " + " ".repeat(cVars - 1));
            }
            System.out.println();
        }
    }

    private String[] reduce() {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] copyMatrix = new int[rows][cols];

        for(int row = 0; row < rows;row++) {
            System.arraycopy(matrix[row], 0, copyMatrix[row], 0, cols);
        }

        return null;
    }
}
