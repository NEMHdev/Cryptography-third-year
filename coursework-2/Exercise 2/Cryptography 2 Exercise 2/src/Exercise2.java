import java.io.IOException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.util.*;

/***************************************************************************
 *  Developer:          Nicholas Haley
 *  Student Number:     150108595
 *  Date:               05/12/2017
 *  Purpose:            Demonstrate Diffie-Hellman key exchange for a Cryptography coursework
 ***************************************************************************/

public class Exercise2 {

    private static Person a;
    private static Person b;
    private static Person Ea;
    private static Person Eb;
    private static Modulus modulus;

    public static void main(String[] args) throws IOException {

        modulus = new Modulus();
        modulus.setMod(BigInteger.probablePrime(1024, new Random()));

        a = new Person();
        //a.setSecretKey(new BigInteger("4"));
        a.setSecretKey(BigInteger.probablePrime(1024, new Random()));

        if (a.getSecretKey().compareTo(modulus.getMod()) == 1 && a.getSecretKey().gcd(modulus.getMod()).equals(1) == false) {
            a.setSecretKey(a.getSecretKey().subtract(modulus.getMod()));
        }
        System.out.println("SecretA = " + a.getSecretKey());

        b = new Person();
        //b.setSecretKey(new BigInteger("3"));
        b.setSecretKey(BigInteger.probablePrime(1024, new Random()));

        if (b.getSecretKey().compareTo(modulus.getMod()) == 1 && b.getSecretKey().gcd(modulus.getMod()).equals(1) == false) {
            b.setSecretKey(b.getSecretKey().subtract(modulus.getMod()));
        }
        System.out.println("SecretB = " + b.getSecretKey());

        Ea = new Person();
        //a.setSecretKey(new BigInteger("4"));
        Ea.setSecretKey(BigInteger.probablePrime(1024, new Random()));

        if (Ea.getSecretKey().compareTo(modulus.getMod()) == 1 && Ea.getSecretKey().gcd(modulus.getMod()).equals(1) == false) {
            Ea.setSecretKey(Ea.getSecretKey().subtract(modulus.getMod()));
        }
        System.out.println("SecretEa = " + Ea.getSecretKey());

        Eb = new Person();
        //a.setSecretKey(new BigInteger("4"));
        Eb.setSecretKey(BigInteger.probablePrime(1024, new Random()));

        if (Eb.getSecretKey().compareTo(modulus.getMod()) == 1 && Eb.getSecretKey().gcd(modulus.getMod()).equals(1) == false) {
            Eb.setSecretKey(Eb.getSecretKey().subtract(modulus.getMod()));
        }
        System.out.println("SecretEb = " + Eb.getSecretKey());

        BigInteger[] msgA = computeMsgAToB();
        BigInteger msgB;
        msgB = computeMsgBToA(msgA);
        computeKeyA(msgB);
        computeKeyB(msgA);

        System.out.println("\n\n" + "Eve active Eavesdropper" + "\n\n");

        System.out.println("SecretA = " + a.getSecretKey());
        System.out.println("SecretB = " + b.getSecretKey());
        System.out.println("SecretEa = " + Ea.getSecretKey());
        System.out.println("SecretEb = " + Eb.getSecretKey());
        BigInteger[] msgAtoEve = computeMsgAToB();
        BigInteger[] msgEa;
        BigInteger msgEb;
        BigInteger msgBToEve;

        msgEa = computeMsgEaToBob(msgAtoEve);
        msgBToEve = computeMsgBToA(msgEa);
        msgEb = computeMsgEbToAlice(msgEa);
        computeKeyA(msgEb);
        computeKeyB(msgEa);
        computeKeyEAlice(msgAtoEve);
        computeKeyEBob(msgBToEve);
    }

    public static BigInteger[] computeMsgAToB() {

        BigInteger p;
        p = modulus.getMod();

        BigInteger[] msgA = new BigInteger[3];
        msgA[0] = p;                               //Add p to msgA

        BigInteger base;
        //base = new BigInteger("5");
        base = BigInteger.probablePrime(1024, new Random());

        msgA[1] = base;

        BigInteger valueA;
        valueA = base.modPow(a.getSecretKey(), p);
        msgA[2] = valueA;

        //valueA = g^a (mod p)
        System.out.println("msg1.modulus = " + msgA[0]);
        System.out.println("msg1 base = " + msgA[1]);
        System.out.println("msg1.valueA = " + msgA[2]);
        return msgA;
    }

    public static BigInteger computeMsgBToA(BigInteger[] msgA) {
        BigInteger msgB;
        BigInteger p = msgA[0];
        BigInteger base = msgA[1];

        BigInteger valueB;
        valueB = base.modPow(b.getSecretKey(), p);
        msgB = valueB;

        System.out.println("msg2.valueB = " + msgB);
        return msgB;
    }

    public static BigInteger computeKeyA(BigInteger msg) {
        BigInteger keyA;

        keyA = msg.modPow(a.getSecretKey(), modulus.getMod());
        System.out.println("keyA = " + keyA);

        return keyA;
    }

    public static BigInteger computeKeyB(BigInteger[] msg) {
        BigInteger keyB;
        BigInteger p = msg[0];
        BigInteger valueA = msg[2];

        keyB = valueA.modPow(b.getSecretKey(), p);

        System.out.println("keyB = " + keyB);

        return keyB;
    }

    public static BigInteger[] computeMsgEaToBob(BigInteger[] msgA) {
        BigInteger msgEa[] = new BigInteger[3];
        BigInteger p = msgA[0];
        BigInteger base = msgA[1];

        msgEa[0] = p;
        msgEa[1] = base;

        BigInteger valueEa;
        valueEa = base.modPow(Ea.getSecretKey(), p);
        msgEa[2] = valueEa;

        System.out.println("msgEa.valueEa = " + msgEa[2]);
        return msgEa;
    }

    public static BigInteger computeMsgEbToAlice(BigInteger[] msgA) {
        BigInteger msgEb;
        BigInteger p = msgA[0];
        BigInteger base = msgA[1];

        BigInteger valueEb;
        valueEb = base.modPow(Eb.getSecretKey(), p);
        msgEb = valueEb;

        System.out.println("msgEb.valueEb = " + msgEb);
        return msgEb;
    }

    public static BigInteger computeKeyEAlice(BigInteger[] msg) {
        BigInteger keyE;
        BigInteger p = msg[0];
        BigInteger valueE = msg[2];

        keyE = valueE.modPow(Eb.getSecretKey(), p);

        System.out.println("keyE With Alice = " + keyE);

        return keyE;
    }

    public static BigInteger computeKeyEBob(BigInteger msg) {
        BigInteger keyE;

        keyE = msg.modPow(Ea.getSecretKey(), modulus.getMod());

        System.out.println("keyE with Bob = " + keyE);

        return keyE;
    }


}
