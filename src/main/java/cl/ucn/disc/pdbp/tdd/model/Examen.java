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

import cl.ucn.disc.pdbp.tdd.dao.ZonedDateTimeType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.ZonedDateTime;

/**
 * Clase Examen.
 * @author Gerald Lopez
 */
@DatabaseTable(tableName = "Examen")
public final class Examen {

  /**
   * Id del examen.
   */
  @DatabaseField(generatedId = true)
  private Long id;

  /**
   * Nombre del Examen.
   */
  @DatabaseField(canBeNull = false)
  private String nombre;

  /**
   * Fecha del Examen.
   */
  @DatabaseField(canBeNull = false, persisterClass = ZonedDateTimeType.class)
  private ZonedDateTime fecha;

  /**
   * Control asociado al Examen.
   */
  @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Control control;

  /**
   * Constructor vacio.
   */
  Examen() {
    //Nada aqui.
  }

  /**
   * Constructor.
   * @param nombre del examen.
   * @param fecha del examen.
   * @param control asociado del examen.
   */
  public Examen(String nombre, ZonedDateTime fecha, Control control) {

    if (nombre == null) {
      throw new NullPointerException("No puede ser null !");
    }
    if (nombre.length() < 2) {
      throw new RuntimeException("Nombre de examen invalido!");
    }
    this.nombre = nombre;


    if (fecha.equals(null)) {
      throw new NullPointerException("No puede ser null !");
    }
    //TODO:Consultar sobre la validacion de la fecha.
    this.fecha = fecha;

    if (control.equals(null)) {
      throw new NullPointerException("No puede ser null !");
    }
    this.control = control;
  }

  /**
   * Obtiene el id autogenerado.
   * @return Id.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Obtiene el nombre de {@link Examen}.
   * @return nombre.
   */
  public String getNombre() {
    return this.nombre;
  }

  /**
   * Obtiene el nombre de {@link Examen}.
   * @return fecha del Examen.
   */
  public ZonedDateTime getFecha() {
    return this.fecha;
  }

  /**
   * Obtiene el {@link Control} asociado a {@link Examen}.
   * @return control.
   */
  public Control getControl() {
    return this.control;
  }
}
