package cl.ucn.disc.pdbp.tdd.model;

import cl.ucn.disc.pdbp.tdd.utils.Validation;

/**
 * Clase Persona.
 * @author Gerald Lopez
 */
public class Persona {

  /**
   * El Nombre.
   */
  private final String nombre;
  /**
   * El Apellido.
   */
  private final String apellido;
  /**
   * El RUT.
   */
  private final String rut;

  /**
   * Constructor de la clase.
   * @param nombre valido a usar
   * @param apellido valido a utilizar
   * @param rut valido
   */
  public Persona(String nombre, String apellido, String rut) {

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
}
