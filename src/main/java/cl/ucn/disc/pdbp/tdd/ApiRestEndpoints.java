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
import cl.ucn.disc.pdbp.tdd.model.Sexo;
import cl.ucn.disc.pdbp.tdd.model.Tipo;
import io.javalin.http.Context;
import java.time.ZonedDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase intermediaria entre web service y contratos.
 *
 * @author Gerald Lopez
 */
public class ApiRestEndpoints {

  /**
   * Logger.
   */
  private static final Logger log = LoggerFactory.getLogger(ApiRestEndpoints.class);

  /**
   * Contratos usando SQLite.
   */
  private static final Contratos CONTRATOS = new ContratosImpl("jdbc:sqlite:fivet.db");

  /**
   * Contructor privado.
   */
  private ApiRestEndpoints() {
    //nada aqui.
  }

  /**
   * Metodo que busca todas las fichas que existen en la DB.
   * @param ctx the Javalin {@link Context}
   */
  public static void getAllFichas(Context ctx) {

    log.debug("Obteniendo todas las fichas ..");
    List<Ficha> fichas = CONTRATOS.getAllFichas();
    ctx.json(fichas);
  }

  /**
   * Busca las fichas segun una query.
   * @param ctx the Javalin {@link Context}
   */
  public static void findFichas(Context ctx) {

    String query = ctx.pathParam("query");
    log.debug("Buscando fichas consultada <{}> ..", query);

    List<Ficha> fichas = CONTRATOS.buscarFicha(query);
    ctx.json(fichas);

  }

  /**
   * Crea una ficha.
   * @param ctx the Javalin {@link Context}
   */
  public static void createFicha(Context ctx) {

    //Atributos de la Ficha

    Integer numero = Integer.parseInt(ctx.formParam("numeroFicha"));
    String nombrePaciente = ctx.formParam("nombrePaciente");
    String especie = ctx.formParam("especie");
    String raza = ctx.formParam("raza");
    String color = ctx.formParam("color");
    ZonedDateTime fechaNacimiento = ZonedDateTime.parse(ctx.formParam("fechaNacimiento"));

    Sexo sexo;
    if (ctx.formParam("sexo").equalsIgnoreCase("hembra")) {
      sexo = Sexo.HEMBRA;
    } else {
      sexo = Sexo.MACHO;
    }

    Tipo tipo;

    if (ctx.formParam("tipo").equalsIgnoreCase("interno")) {
      tipo = Tipo.INTERNO;
    } else {
      tipo = Tipo.EXTERNO;
    }

    //Obtiene la id del duenio del paciente.
    Long duenioId = Long.parseLong(ctx.formParam("duenio"));
    Persona duenio = CONTRATOS.getId(duenioId);

    //Crea la ficha y la inserta en la BD.
    Ficha ficha = new Ficha(numero, nombrePaciente, especie, fechaNacimiento, raza, sexo, color,
            tipo, duenio);
    CONTRATOS.registrarPaciente(ficha);

  }

  /**
   * Obtiene un listado de personas.
   * @param ctx the Javalin {@link Context}
   */
  public static void getAllPersonas(Context ctx) {

    log.debug("Listado de personas en el sistema ..");

    List<Persona> personas = CONTRATOS.getAllPersonas();
    ctx.json(personas);

  }

  /**
   * Crear una Persona.
   * @param ctx the Javalin {@link Context}
   */
  public static void createPersona(Context ctx) {

    log.debug("Create a Persona");
    String nombre = ctx.pathParam("nombre");
    String apellido = ctx.pathParam("apellido");
    String rut = ctx.pathParam("rut");
    String direccion = ctx.pathParam("direccion");
    Integer telefonoFijo = Integer.parseInt(ctx.formParam("telefonoFijo"));
    Integer telefonoMovil = Integer.parseInt(ctx.formParam("telefonoMovil"));
    String email = ctx.pathParam("email");

    Persona persona = new Persona(nombre, apellido, rut, direccion, telefonoFijo, telefonoMovil,
            email);
    CONTRATOS.registrarPersona(persona);
    ctx.json(persona);

  }

  /**
   * Obtiene los controles por numero de ficha.
   * @param ctx the Javalin {@link Context}
   */
  public static void getControles(Context ctx) {

    Integer numeroFicha = Integer.parseInt(ctx.formParam("numeroFicha"));
    List<Control> controles = CONTRATOS.getControles(numeroFicha);
    ctx.json(controles);


  }

  /**
   * Crea un control.
   * @param ctx the Javalin {@link Context}
   */
  public static void createControl(Context ctx) {

    log.debug("Add control .. ");
    //Atributos del control.
    ZonedDateTime fecha = ZonedDateTime.parse(ctx.pathParam("fecha"));
    ZonedDateTime proximoControl = ZonedDateTime.parse(ctx.formParam("proximoControl"));
    Float temperatura = Float.valueOf(ctx.formParam("temperatura"));
    Float peso = Float.valueOf(ctx.formParam("peso"));
    Float altura = Float.valueOf(ctx.formParam("altura"));
    String diagnostico = ctx.formParam("diagnostico");

    //Obtener el veterinario
    Long veterinarioId = Long.valueOf(ctx.formParam("veterinario"));
    Persona veterinario = CONTRATOS.getId(veterinarioId);

    //Ficha asociada al control
    Long numeroFicha = Long.valueOf(ctx.pathParam("numeroFicha"));
    Ficha ficha = CONTRATOS.buscarFicha(numeroFicha.toString()).get(0);

    Control control = new Control(fecha, proximoControl, temperatura, peso, altura, diagnostico,
            veterinario, ficha);
    CONTRATOS.registrarControl(control);
    ctx.json(control);


  }

  /**
   * Obtiene el duenio de una ficha.
   * @param ctx the Javalin {@link Context}
   */
  public static void getDuenioOfFicha(Context ctx) {

    Integer numeroFicha = Integer.parseInt(ctx.formParam("numeroFicha"));
    Persona duenio = CONTRATOS.getDuenioOfFicha(numeroFicha);
    ctx.json(duenio);

  }


}

