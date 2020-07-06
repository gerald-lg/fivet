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

package cl.ucn.disc.pdbp.tdd;

import cl.ucn.disc.pdbp.tdd.model.Control;
import cl.ucn.disc.pdbp.tdd.model.Ficha;
import cl.ucn.disc.pdbp.tdd.model.Persona;
import java.util.List;

/**
 * Contratos del Sistema.
 * @author Gerald Lopez
 */
public interface Contratos {

  /**
   * Contrato: C01-Registrar los datos de un paciente.
   * @param ficha a insertar
   * @return {@link Ficha} en backend.
   */
  Ficha registrarPaciente(Ficha ficha);

  /**
   * Contrato: C02-Registrar los datos de una Persona.
   * @param persona a insertar
   * @return {@link Persona} en backend.
   */
  Persona registrarPersona(Persona persona);

  /**
   * Contrato: C03-Buscar Ficha.
   * @param query a buscar
   * @return {@link List} de {@link Ficha}
   */
  List<Ficha> buscarFicha(String query);

  /**
   * Registra un control asociado a una {@link Ficha}.
   * @param control nuevo a registrar.
   * @return {@link Control} en backend.
   */
  Control registrarControl(Control control);

  /**
   * Obtiene una lista de todas las fichas.
   * @return {@link List} of {@link Ficha}
   */
  List<Ficha> getAllFichas();

  /**
   * Obtiene el id de una persona.
   * @param id a buscar.
   * @return id of {@link Persona}
   */
  Persona getId(Long id);

  /**
   * Obtiene una lista de todas las personas.
   * @return {@link List} of {@link Persona}
   */
  List<Persona> getAllPersonas();

  /**
   * Obtiene un listado de los controles.
   * @param numeroFicha de los controles.
   * @return {@link List} de {@link Control}
   */
  List<Control> getControles(Integer numeroFicha);

  /**
   * Obtiene el duenio de un paciente por su numero de ficha.
   * @param numeroFicha buscada.
   * @return duenio de la Ficha.
   */
  Persona getDuenioOfFicha(Integer numeroFicha);
}
