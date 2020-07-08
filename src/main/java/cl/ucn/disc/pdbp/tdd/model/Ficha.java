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
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 * Clase Ficha Veterinaria.
 *
 * @author Gerald Lopez
 */
@DatabaseTable(tableName = "Ficha")
public final class Ficha {

  /**
   * Id de la Ficha.
   */
  @DatabaseField(generatedId = true)
  private Long id;

  /**
   * Numero de ficha del paciente.
   */
  @DatabaseField(unique = true, canBeNull = false)
  private Long numero;

  /**
   * Nombre del paciente.
   */
  @DatabaseField(canBeNull = false)
  private String nombre;

  /**
   * Especie del paciente, ej: canino.
   */
  @DatabaseField(canBeNull = false)
  private String especie;

  /**
   * Fecha de nacimiento del paciente.
   */
  @DatabaseField(canBeNull = false, persisterClass = ZonedDateTimeType.class)
  private ZonedDateTime fechaNacimiento;

  /**
   * Raza del paciente.
   */
  @DatabaseField
  private String raza;

  /**
   * Sexo.
   */
  @DatabaseField(canBeNull = false)
  private Sexo sexo;

  /**
   * Color del paciente, ej: rojo cobrizo.
   */
  @DatabaseField(canBeNull = false)
  private String color;

  /**
   * Tipo.
   */
  @DatabaseField(canBeNull = false)
  private Tipo tipo;

  /**
   * Duenio de paciente.
   */
  @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
  private Persona duenio;

  /**
   * Controles del paciente.
   */
  @ForeignCollectionField(eager = true, columnName = "controles")
  private ForeignCollection<Control> controles;

  /**
   * Constructor vacio.
   */
  Ficha() {
    //nada aqui.
  }

  /**
   * Constructor de Ficha.
   * @param numero valido de la ficha a designar.
   * @param nombre valido del paciente a utilizar.
   * @param especie del paciente a ingresar.
   * @param fechaNacimiento estimada valida a utilizar.
   * @param raza del paciente a ingresar.
   * @param sexo del paciente a ingresar.
   * @param color valido a usar.
   * @param tipo de paciente.
   * @param duenio de paciente.
   */
  public Ficha(long numero, String nombre, String especie, ZonedDateTime fechaNacimiento,
               String raza, Sexo sexo, String color, Tipo tipo, Persona duenio) {

    this.numero = numero;
    this.nombre = nombre;
    this.especie = especie;
    this.fechaNacimiento = fechaNacimiento;
    this.raza = raza;
    this.sexo = sexo;
    this.color = color;
    this.tipo = tipo;
    this.duenio = duenio;
  }

  /**
   * Obtiene el id asignado.
   * @return id
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Obtiene el numero de ficha del paciente.
   * @return numero de ficha veterinaria.
   */
  public Long getNumero() {
    return this.numero;
  }

  /**
   * Obtiene el nombre del paciente.
   * @return nombre.
   */
  public String getNombre() {
    return this.nombre;
  }

  /**
   * Obtiene la especie del paciente.
   * @return especie.
   */
  public String getEspecie() {
    return this.especie;
  }

  /**
   * Obtiene la fecha de nacimiento estimada.
   * @return fecha de nacimiento del paciente.
   */
  public ZonedDateTime getFechaNacimiento() {
    return this.fechaNacimiento;
  }

  /**
   * Obtiene la raza.
   * @return raza del paciente
   */
  public String getRaza() {
    return this.raza;
  }

  /**
   * Obtiene el sexo (MACHO/HEMBRA).
   * @return Sexo
   */
  public Sexo getSexo() {
    return this.sexo;
  }

  /**
   * Obtiene el color del paciente.
   * @return color
   */
  public String getColor() {
    return this.color;
  }

  /**
   * Obtiene el tipo de paciente (INTERNO/EXTERNO).
   * @return Tipo
   */
  public Tipo getTipo() {
    return this.tipo;
  }

  /**
   * Obtiene el duenio del paciente.
   * @return duenio
   */
  public Persona getDuenio() {
    return this.duenio;
  }

  /**
   * Obtiene los controles de una ficha.
   * @return controles
   */
  public Collection<Control> getControles() {
    return Collections.unmodifiableList(new ArrayList<>(controles));
  }

  /**
   * Agrega un control a la coleccion asociado a la ficha.
   * @param control a insertar.
   */
  public void createControl(Control control) {
    this.controles.add(control);
  }
}
