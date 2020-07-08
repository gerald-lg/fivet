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

import cl.ucn.disc.pdbp.tdd.dao.Repository;
import cl.ucn.disc.pdbp.tdd.dao.RepositoryOrmLite;
import cl.ucn.disc.pdbp.tdd.model.Control;
import cl.ucn.disc.pdbp.tdd.model.Examen;
import cl.ucn.disc.pdbp.tdd.model.Ficha;
import cl.ucn.disc.pdbp.tdd.model.Persona;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementacion de los contratos.
 * @author Gerald Lopez
 */
public class ContratosImpl implements Contratos {

  /**
   * Logger.
   */
  private static final Logger log = LoggerFactory.getLogger(ContratosImpl.class);

  /**
   * Fuente de conexion.
   */
  private ConnectionSource connectionSource;

  /**
   * {@link Repository} de {@link Control}.
   */
  private Repository<Control, Long> repoControl;

  /**
   * {@link Repository} de {@link Examen}.
   */
  private Repository<Examen, Long> repoExamen;

  /**
   * {@link Repository} de {@link Ficha}.
   */
  private Repository<Ficha, Long> repoFicha;

  /**
   * {@link Repository} de {@link Persona}.
   */
  private Repository<Persona, Long> repoPersona;

  /**
   * Constructor de la clase.
   * @param databaseUrl a utilizar para la conexion.
   */
  public ContratosImpl(String databaseUrl) {

    if (databaseUrl == null) {
      throw new IllegalArgumentException("No es posible crear los contratos con databaseUrl null");
    }

    try {
      //Conexion
      this.connectionSource = new JdbcConnectionSource(databaseUrl);

      //Creacion de tablas.
      TableUtils.createTableIfNotExists(connectionSource, Control.class);
      TableUtils.createTableIfNotExists(connectionSource, Examen.class);
      TableUtils.createTableIfNotExists(connectionSource, Ficha.class);
      TableUtils.createTableIfNotExists(connectionSource, Persona. class);

      this.repoControl = new RepositoryOrmLite<>(connectionSource, Control.class);
      this.repoExamen = new RepositoryOrmLite<>(connectionSource, Examen.class);
      this.repoFicha = new RepositoryOrmLite<>(connectionSource, Ficha.class);
      this.repoPersona = new RepositoryOrmLite<>(connectionSource, Persona.class);

    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }


  }

  /**
   * Contrato: C01-Registrar los datos de un paciente.
   *
   * @param ficha a insertar
   * @return {@link Ficha} en backend.
   */
  @Override
  public Ficha registrarPaciente(Ficha ficha) {
    if (ficha == null) {
      throw new NullPointerException("Ficha invalida!");
    }
    this.repoFicha.create(ficha);
    return this.repoFicha.findById(ficha.getId());
  }

  /**
   * Contrato: C02-Registrar los datos de una Persona.
   *
   * @param persona a insertar
   * @return {@link Persona} en backend.
   */
  @Override
  public Persona registrarPersona(Persona persona) {
    if (persona == null) {
      throw  new NullPointerException("Persona invalida!");
    }
    this.repoPersona.create(persona);
    return this.repoPersona.findById(persona.getId());
  }

  /**
   * Contrato: C03-Buscar Ficha.
   *
   * @param query a buscar
   * @return {@link List} de {@link Ficha}
   */
  @Override
  public List<Ficha> buscarFicha(String query) {

    //Nullity
    if (query == null) {
      throw new IllegalArgumentException("Query was null");
    }

    //The result: List of Ficha
    List<Ficha> fichas = new ArrayList<>();
    QueryBuilder<Persona, Long> queryPersona = this.repoPersona.getQuery();

    try {
      //1. Buscar por numero.
      if (StringUtils.isNumeric(query)) {

        //Todas las fichas con el numero buscado.
        log.debug("Finding Fichas with numero ..");
        fichas.addAll(this.repoFicha.findAll("numero", query));

        //2. Buscar por el rut del duenio.
        log.debug("Finding Fichas with rut of Duenio ..");
        queryPersona.where().like("rut", "%" + query + "%");

        //Join between Duenio and Ficha.
        fichas.addAll(this.repoFicha.getQuery().join(queryPersona).query());
      }
      //3.Busqueda por nombre del paciente
      log.debug("Finding Fichas with nombre of Paciente ..");
      fichas.addAll(this.repoFicha.getQuery().where().like("nombre", "%" + query + "%").query());

      //4.Busqueda por nombre del Duenio.
      log.debug("Finding Fichas with nombre of Duenio ..");
      queryPersona.where().like("nombre", query);
      fichas.addAll(this.repoFicha.getQuery().join(queryPersona).query());

    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }

    return fichas;
  }

  /**
   * Registra un control asociado a una {@link Ficha}.
   *
   * @param control nuevo a registrar.
   * @return {@link Control} en backend.
   */
  @Override
  public Control registrarControl(Control control) {

    if (control == null) {
      throw  new NullPointerException("Control invalido!");
    }

    this.repoControl.create(control);

    control.getFicha().createControl(control);
    //Cuando se hace la relacion con el control, se debe actualizar la ficha en el repositorio.
    this.repoFicha.update(control.getFicha());

    return this.repoControl.findById(control.getId());

  }

  /**
   * Registra un examen asociado a un {@link Control}.
   *
   * @param examen nuevo
   * @return {@link Examen} en backend.
   */
  @Override
  public Examen registrarExamen(Examen examen) {

    if (examen == null) {
      throw  new NullPointerException("Examen invalido");
    }
    this.repoExamen.create(examen);

    examen.getControl().createExamen(examen);

    this.repoControl.update(examen.getControl());

    return this.repoExamen.findById(examen.getId());
  }

  /**
   * Obtiene una lista de todas las fichas.
   *
   * @return {@link List} of {@link Ficha}
   */
  @Override
  public List<Ficha> getAllFichas() {
    return repoFicha.findAll();
  }

  /**
   * Obtiene el id de una persona.
   *
   * @param id a buscar.
   * @return id of {@link Persona}
   */
  @Override
  public Persona getId(Long id) {

    return repoPersona.findById(id);

  }

  /**
   * Obtiene una lista de todas las personas.
   *
   * @return {@link List} of {@link Persona}
   */
  @Override
  public List<Persona> getAllPersonas() {

    return repoPersona.findAll();
  }

  /**
   * Obtiene un listado de los controles.
   * @param numeroFicha de los controles.
   * @return {@link List} de {@link Control}
   */
  @Override
  public List<Control> getControles(Long numeroFicha) {

    Ficha fichaBuscada = this.repoFicha.findAll("numero", numeroFicha).get(0);

    if (fichaBuscada.equals(null)) {
      return null;
    }
    return new ArrayList<>(fichaBuscada.getControles());
  }


  /**
   * Obtiene el duenio de un paciente por su numero de ficha.
   *
   * @param numeroFicha buscada.
   * @return duenio de la Ficha.
   */
  @Override
  public Persona getDuenioOfFicha(Integer numeroFicha) {

    Ficha fichaBuscada = this.repoFicha.findAll("numero", numeroFicha).get(0);

    if (fichaBuscada.equals(null)) {
      return null;
    }

    return fichaBuscada.getDuenio();
  }
}
