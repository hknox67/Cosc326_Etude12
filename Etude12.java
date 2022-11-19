import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class Test {
/**
 * This program takes in a file of IBM360 floating point numbers in either
 * single or double precision form. And it coverts them into a IEEE 754
 * floating point form in either single or double precision form,
 * and stores this IEEE 754 number into a file that the user names.
 * @param args
 */
    public static void main(final String[] args) {
        int inputPrecision = 0; //variable that holds the value for the input/IBM360 floating point precision
        int outputPrecision = 0; //variable that holds the value for the output/IEEE 754 floating point precision
        String outputFileName; //variable that holds the name of the output file that the user names
        Scanner userIn = new Scanner(System.in); //Scanner implementation
        String inputFileName; //variable that holds the name of the input file

        if (args.length == 4) { //reads for args or the System.in for input and output files
            inputFileName = args[0];
            inputPrecision = Integer.parseInt(args[1]);
            outputFileName = args[2];
            outputPrecision = Integer.parseInt(args [3]);
        } else{ // if doesn't read from args, reads from the scanner
            System.out.println("Please enter the input file name: "); //Prompts user to enter file name
            inputFileName = userIn.next();
            System.out.println("Please enter a number which chooses single (1) or double precision (2): "); //Prompts user to select input precision number
            inputPrecision = userIn.nextInt();
            System.out.println("Please enter an output file name: ");//Prompts user to write to a file 
            outputFileName = userIn.next();
            System.out.println("Please enter a number which chooses single (1) or double precision (2): ");//Prompts user to choose precision number for output
            outputPrecision = userIn.nextInt();

        }
/**
 * Checks that the precision for both the input and output files are either single or double precision
 */
        if (inputPrecision < 1 || inputPrecision > 2){
            System.err.println("Error: Please enter either single (1) or double (2) precision.");
            System.exit(-1);
        } else if (outputPrecision < 1 || outputPrecision > 2){
            System.out.println();
            System.err.println("Error: Please enter either single (1) or double (2) precision.");
            System.exit(-1);
        }
/**
 * Reads in the data from the input stream of the file
 */
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(inputFileName));
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(outputFileName))) {
            while (dataInputStream.available() > 3 || dataInputStream.available() > 7){
                String line = "";
                float floatingPoint = 1;
                double doublePoint = 0.1;
                if (inputPrecision == 1){
                    floatingPoint = dataInputStream.readFloat();
                    int binStr = Float.floatToRawIntBits(floatingPoint);
                    line = String.format("%32s", Integer.toBinaryString(binStr)).replace(' ', '0');
                    System.out.println("inputSingle: " + floatingPoint);
                } else if (inputPrecision == 2){
                    doublePoint = dataInputStream.readDouble();
                    long binLong = Double.doubleToRawLongBits(doublePoint);
                    line = String.format("%64s", Long.toBinaryString(binLong)).replace(' ', '0');
                    System.out.println("inputDouble: " + doublePoint);
                }
                if (outputPrecision == 1){
                    float temporary = floatingNumber(line);
                    if (temporary != 0){
                        System.out.println("outputSingle: " + temporary);
                        dataOutputStream.writeFloat(temporary);
                    } else if (floatingPoint == 0 || doublePoint == 0.0){
                        System.out.println("outputSingle: " + 0);
                        dataOutputStream.writeFloat(0);
                    } else {
                        double res = singleNumber(line);
                        System.out.println("outputSingle: " + res);
                        dataOutputStream.writeFloat((float)res);
                    }
                    } else if (outputPrecision == 2){
                        double temporary = doubleNumber(line);
                        if (temporary != 0.0) {
                            System.out.println("outputDouble: " + temporary);
                            dataOutputStream.writeDouble(temporary);
                        } else if (floatingPoint == 0 || doublePoint == 0.0){
                            System.out.println("outputDouble: " + 0.0);
                            dataOutputStream.writeDouble(0.0);
                        } else {
                            double res = singleNumber(line);
                            System.out.println("outputDouble: " + res);
                            dataOutputStream.writeDouble(res);
                        }
                    }
                }
            } catch (final IOException e){
                System.err.println("Error: The input file cannot be found");
            }
        }
/**
 * This method breaks up the input into the S E F format of the IEEE 754
 * @param IBMin
 * @return
 */
      public static double singleNumber(final String IBMin){
            final String sign = IBMin.substring(0, 1); //sets the sign
            final String exponential = IBMin.substring(1, 8); //sets the exponential
            final String fraction = IBMin.substring(8, IBMin.length()); //sets the fraction
            long fractionLong = Long.parseLong(fraction, 2);
            String fractionString = Long.toHexString(fractionLong);
            String mantissa = "0." + fractionString;
            mantissa += "p0";
            mantissa = "0x" + mantissa;
            double mantissaDouble = Double.valueOf(mantissa);
            int exponentialInt = Integer.parseInt(exponential, 2);
            double output = mantissaDouble * Math.pow(16, (exponentialInt - 64)); //creates the double
            if (sign.equals("1")){
                output *= -1;
            }
            return output;
        }
/**
 * This method checks if the double in the file is in the correct format to be converted
 * @param line
 * @return
 */
        public static double doubleNumber(String line){
            if (line.substring(1, 8).equals("1111111")){
                if (line.substring(8, line.length()).contains("1")){
                    return Double.NaN;
                } else if (line.substring(0, 1).equals("0")){
                    return Double.POSITIVE_INFINITY;
                } else if (line.substring(0, 1).equals("1")){
                    return Double.NEGATIVE_INFINITY;
                }
            }
            return 0;
        }
/**
 * This method checks if the float in the file is in the correct format to be converted
 * @param line
 * @return
 */
    public static float floatingNumber(String line) {
        if (line.substring(1, 8).contains("1111111")) {
            if (line.substring(8, line.length()).contains("1")){
                return Float.NaN;
            } else if (line.substring(0, 1).equals("0")){
                return Float.POSITIVE_INFINITY;
            } else if (line.substring(0, 1).equals("1")){
                return Float.NEGATIVE_INFINITY;
            }
        }
        return 0;
    }
}
