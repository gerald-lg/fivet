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

package cl.ucn.disc.pdbp.tdd.model;

import cl.ucn.disc.pdbp.tdd.utils.Validation;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase Persona.
 * @author Gerald Lopez
 */
@DatabaseTable(tableName = "Persona")
public final class Persona {

  /**
   * Id.
   */
  @DatabaseField(generatedId = true)
  private Long id;

  /**
   * El Nombre.
   */
  @DatabaseField(canBeNull = false)
  private String nombre;
  /**
   * El Apellido.
   */
  @DatabaseField(canBeNull = false)
  private String apellido;
  /**
   * El RUT.
   */
  @DatabaseField(canBeNull = false, index = true)
  private String rut;

  /**
   * Direccion.
   */
  @DatabaseField(canBeNull = false)
  private String direccion;

  /**
   * Telefono fijo.
   * Formato: YY XX-XXXX, para cualquier telefono fijo del pais.
   */
  @DatabaseField(canBeNull = false)
  private Integer telefonoFijo;

  /**
   * Telefono movil.
   * Formato: 9 XXXX-XXXX.
   */
  @DatabaseField(canBeNull = false)
  private Integer telefonoMovil;

  /**
   * Email.
   */
  @DatabaseField(canBeNull = false)
  private String email;

  /**
   * Patrón de un telefono fijo valido.
   */
  private final Pattern telFijoValido = Pattern.compile("^[0-9]{8}$");
  /**
   * Patrón de un telefono movil valido.
   */
  private final Pattern telMovilValido = Pattern.compile("^9[0-9]{8}$");
  /**
   * Patrón de un correo electronico valido.
   */
  private final Pattern emailValido = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$");

  /**
   * Constructor vacio.
   */
  Persona() {
    //Nada aquí
  }

  /**
   * Constructor de la clase.
   * @param nombre valido a usar
   * @param apellido valido a utilizar
   * @param rut valido
   * @param direccion valida
   * @param telefonoFijo valido a usar
   * @param telefonoMovil valido a utilizar
   * @param email valido
   */
  public Persona(String nombre, String apellido, String rut, String direccion, Integer telefonoFijo,
                 Integer telefonoMovil, String email) {

    if (nombre.equals(null) || apellido.equals(null) || rut.equals(null)) {
      throw new NullPointerException("Dato invalido");
    }

    if (nombre.length() < 2) {
      throw new RuntimeException("Nombre invalido");
    }

    this.nombre = nombre;

    if (apellido.length() < 3) {
      throw new RuntimeException("Apellido invalido");
    }

    this.apellido = apellido;

    if (!Validation.isRutValid(rut)) {
      throw new RuntimeException("Rut invalido");
    }

    this.rut = rut;

    if (direccion.equals(null)) {
      throw new NullPointerException("No puede ser null");
    }

    if (direccion.length() < 2) {
      throw new RuntimeException("Direccion invalida");
    }

    this.direccion = direccion;

    Matcher fijoMatch = telFijoValido.matcher(Integer.toString(telefonoFijo));

    if (telefonoFijo.equals(null)) {
      throw new NullPointerException("No puede ser null");
    }

    if (fijoMatch.matches() == false) {
      throw new RuntimeException("Telefono fijo invalido");
    }

    this.telefonoFijo = telefonoFijo;

    Matcher movilMatch = telMovilValido.matcher(Integer.toString(telefonoMovil));

    if (telefonoMovil.equals(null)) {
      throw new NullPointerException("No puede ser null");
    }

    if (movilMatch.matches() == false) {
      throw new RuntimeException("Telefono movil invalido");
    }

    this.telefonoMovil = telefonoMovil;

    Matcher emailMatch = emailValido.matcher(email);

    if (email.equals(null)) {
      throw new NullPointerException("No puede ser null");
    }

    if (emailMatch.matches() == false) {
      throw new RuntimeException("Email invalido");
    }

    this.email = email;

  }

  /**
   * Obtiene el nombre de la persona.
   * @return nombre
   */
  public String getNombre() {
    return this.nombre;
  }

  /**
   * Obtiene el apellido de la persona.
   * @return apellido
   */
  public String getApellido() {
    return this.apellido;
  }

  /**
   * Obtiene el nombre completo de la persona.
   * @return el nombre y apellido
   */
  public String getNombreApellido() {
    return this.nombre + " " + this.apellido;
  }

  public String getRut() {
    return this.rut;
  }

  public String getDireccion() {
    return this.direccion;
  }

  public Integer getTelefonoFijo() {
    return this.telefonoFijo;
  }

  public Integer getTelefonoMovil() {
    return this.telefonoMovil;
  }

  public String getEmail() {
    return this.email;
  }

  /**
   * Obtiene el id autogenerado.
   * @return id
   */
  public Long getId() {
    return id;
  }
}
