package cl.ucn.disc.pdbp.tdd.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Clase Validation.
 * @author Gerald Lopez
 */
public class Validation {

  /**
   * Patron que sigue el rut.
   */
  private static final Pattern pattern = Pattern.compile("^[0-9]+[0-9kK]{1}$");

  /**
   * Valida que el rut ingresado sea correcto.
   * @param rut valido
   */
  public static boolean isRutValid(String rut) {

    if (rut == null) {
      return false;
    }

    Matcher matcher = pattern.matcher(rut);

    if (matcher.matches() == false) {
      return false;
    }
    String digVerificador = Character.toString(rut.charAt(rut.length() - 1));

    String rutAux = rut.substring(0, rut.length() - 1);

    return digVerificador.toLowerCase().equals(Validation.digitoVerificador(rutAux));

  }

  /**
   * Valida el dígito verificador.
   * @param rut valido
   */
  private static String digitoVerificador(String rut) {
    int m = 0;
    int s = 1;
    int t = Integer.parseInt(rut);
    for (; t != 0; t = (int) Math.floor(t /= 10)) {
      s = (s + t % 10 * (9 - m++ % 6)) % 11;
    }

    return (s > 0) ? String.valueOf(s - 1) : "k";

  }
}
