import java.math.BigInteger;

/***************************************************************************
 *  Developer:          Nicholas Haley
 *  Student Number:     150108595
 *  Date:               03/12/2017
 *  Purpose:            Linear equation solver in the group Zn
 ***************************************************************************/

public class Exercise1 {

    public static void main(String[] args) {

        BigInteger n;
        n = new BigInteger("643808006803554439230129854961492699151386107534013432918073439524138264842370630061369715394739134090922937332590384720397133335969549256322620979036686633213903952966175107096769180017646161851573147596390153");
        //n = new BigInteger("7");
        //n = new BigInteger("342487235325934582350235837853456029394235268328294285895982432387582570234238487625923289526382379523573265963293293839298595072093573204293092705623485273893582930285732889238492377364284728834632342522323422");
        //n = new BigInteger("43");

        BigInteger n1;
        //n1 = new BigInteger("643808006803554439230129854961492699151386107534013432918073439524138264842370630061369715394739134090922937332590384720397133335969549256322620979036686633213903952966175107096769180017646161851573147596390153");
        n1 = new BigInteger("342487235325934582350235837853456029394235268328294285895982432387582570234238487625923289526382379523573265963293293839298595072093573204293092705623485273893582930285732889238492377364284728834632342522323422");


        BigInteger a;
        //a = new BigInteger("34325464564574564564768795534569998743457687643234566579654234676796634378768434237897634345765879087764242354365767869780876543424");
        //a = new BigInteger("3");
        a = new BigInteger("34325464564574564564768795534569998743457687643234566579654234676796634378768434237897634345765879087764242354365767869780876543424");
        //a = new BigInteger("17");

        BigInteger b;
        //b = new BigInteger("45292384209127917243621242398573220935835723464332452353464376432246757234546765745246457656354765878442547568543334677652352657235");
        //b = new BigInteger("2");
        b = new BigInteger("24243252873562935279236582385723952735639239823957923562835832582635283562852252525256882909285959238420940257295265329820035324646");
        //b = new BigInteger("1");

        System.out.println("x = " + solve(a, b, n));
        evaluateN(n, n1);
        isNPrime(n);
        System.out.println("\n");
        isNPrime(n1);
    }

    public static BigInteger solve(BigInteger a, BigInteger b, BigInteger n) {
        BigInteger x;                           //Initialised variable x
        BigInteger coprime = new BigInteger("1");

        BigInteger modB = b.mod(n);             //finding b (mod N)
        System.out.println("b (mod N) = " + modB);   //Print out statement for modB

        BigInteger GCD = a.gcd(b);           //Calculating the gcd(a,b)
        System.out.println("GCD = " + GCD);     //Print out statement for GCD

        if (GCD.equals(coprime)) {              //If clause to calculate x if gcd(a,b) is coprime

            BigInteger aModInverse = a.modInverse(n);   //Inverse of mod calculated to calculate x
            System.out.println("aModInverse = " + aModInverse); //Print out statement for aModInverse

            x = (modB.multiply(aModInverse).mod(n));    //The final calculation for x
            return x;                                   //Returning the value for x
        }
        return null;    // if gcd is not coprime, result will be null, therefore unsolvable
    }

    public static void evaluateN(BigInteger n, BigInteger n1) {
        BigInteger[] result;
        String s = null;

        if (n.compareTo(n1) == 0) {
            System.out.println("n.compareTo(n1) == 0");
            result = n.divideAndRemainder(n1);
        } else if (n.compareTo(n1) == 1) {
            System.out.println("n.compareTo(n1) == 1");
            result = n.divideAndRemainder(n1);
        } else {
            System.out.println("n.compareTo(n1) != 0 || != 1");
            result = n1.divideAndRemainder(n1);
        }
        System.out.println("Division result");
        System.out.println("Quotient is " + result[0]);
        System.out.println("Remainder is " + result[1]);
    }

    public static void isNPrime(BigInteger n) {
        // create 3 Boolean objects
        Boolean b1, b2, b3;

        // perform isProbablePrime on bi1, bi2
        b1 = n.isProbablePrime(1);
        b2 = n.isProbablePrime(2);
        b3 = n.isProbablePrime(-1);

        String str1 = "N is prime with certainity 1 is " + b1;
        String str2 = "N is prime with certainity 2 is " + b2;
        String str3 = "N is prime with certainity -1 is " + b3;

        // print b1, b2, b3 values
        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
    }
}

