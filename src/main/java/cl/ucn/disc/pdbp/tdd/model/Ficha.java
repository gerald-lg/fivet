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

import java.time.ZonedDateTime;

/**
 * Clase Ficha Veterinaria.
 *
 * @author Gerald Lopez
 */
public class Ficha {

  /**
   * Numero de ficha del paciente.
   */
  private final long numero;

  /**
   * Nombre del paciente.
   */
  private final String nombre;

  /**
   * Especie del paciente, ej: canino.
   */
  private final String especie;

  /**
   * Fecha de nacimiento del paciente.
   */
  private final ZonedDateTime fechaNacimiento;

  /**
   * Raza del paciente.
   */
  private final String raza;

  /**
   * Sexo.
   */
  private final Sexo sexo;

  /**
   * Color del paciente, ej: rojo cobrizo.
   */
  private final String color;

  /**
   * Tipo.
   */
  private final Tipo tipo;

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
   */
  public Ficha(long numero, String nombre, String especie, ZonedDateTime fechaNacimiento, String raza, Sexo sexo, String color, Tipo tipo) {
    this.numero = numero;
    this.nombre = nombre;
    this.especie = especie;
    this.fechaNacimiento = fechaNacimiento;
    this.raza = raza;
    this.sexo = sexo;
    this.color = color;
    this.tipo = tipo;
  }

  /**
   * Obtiene el numero de ficha del paciente.
   * @return numero de ficha veterinaria.
   */
  public long getNumero() {
    return numero;
  }

  /**
   * Obtiene el nombre del paciente.
   * @return nombre.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Obtiene la especie del paciente.
   * @return especie.
   */
  public String getEspecie() {
    return especie;
  }

  /**
   * Obtiene la fecha de nacimiento estimada.
   * @return fecha de nacimiento del paciente.
   */
  public ZonedDateTime getFechaNacimiento() {
    return fechaNacimiento;
  }

  /**
   * Obtiene la raza.
   * @return raza del paciente
   */
  public String getRaza() {
    return raza;
  }

  /**
   * Obtiene el sexo (MACHO/HEMBRA).
   * @return Sexo
   */
  public Sexo getSexo() {
    return sexo;
  }

  /**
   * Obtiene el color del paciente.
   * @return color
   */
  public String getColor() {
    return color;
  }

  /**
   * Obtiene el tipo de paciente (INTERNO/EXTERNO).
   * @return Tipo
   */
  public Tipo getTipo() {
    return tipo;
  }
}
