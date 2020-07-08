/*
 * MIT License
 *
 * Copyright (c) 2020 Gerald Lopez Gutiérrez <gerald.lopez@alumnos.ucn.cl>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
   * Patron que sigue el formato de foto.
   */
  private static final Pattern formato = Pattern.compile("^[_aA-zZ0-9]+.(jpg|jpeg|png){1}$");

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

  /**
   * Comprueba si el formato de la foto.
   * @param fotoUrl a analizar
   * @return true si el formato es correcto.
   */
  public static boolean formatoFoto(String fotoUrl){

    if (fotoUrl == null){
      return false;
    }

    Matcher match = formato.matcher(fotoUrl);

    if(match.matches() == false ){
      return false;
    }

    return true;
  }
}
