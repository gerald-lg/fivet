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

import cl.ucn.disc.pdbp.tdd.model.Persona;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.json.JavalinJson;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Clase Main Application.
 *
 * @author Gerald Lopez
 */
public final class Application {

  /**
   * Logger.
   */
  private static final Logger log = LoggerFactory.getLogger(Application.class);

  /**
   * Constructor privado.
   */
  private Application() {
    //Nada aqui.
  }

  /**
   * Main.
   * @param args desde consola
   */
  public static void main(String[] args) {

    //Configuracion Gson
    //Persona <-> Json via libreria Gson
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    JavalinJson.setFromJsonMapper(gson::fromJson);
    JavalinJson.setToJsonMapper(gson::toJson);

    //Servidor de Javalin
    log.debug("Starting Javalin ..");
    Javalin javalin = Javalin.create(config -> {

      config.enableDevLogging();
      //Configuracion del Logger
      config.requestLogger((ctx, executionTimeMs) -> {
        log.info("Served {} in {} ms.", ctx.fullUrl(), executionTimeMs);
        ctx.header("Server-Timing", "total;dur=" + executionTimeMs);
      });

      //Muestra todas las rutas.
      config.registerPlugin(new RouteOverviewPlugin("/routes"));
    }).start(7000);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      log.debug("Stopping the server .. ");
      javalin.stop();
      log.debug("The end.");
    }));

    //Peticion a la raiz del servidor, se obtiene la fecha.
    javalin.get("/", ctx -> {
      ctx.result("The Date: " + ZonedDateTime.now());
    });

    javalin.get("/personas/", ctx -> {

      String nombre = "Andrea";
      String apellido = "Contreras";
      String rutOk = "152532873";
      String direccion = "Falsa 123";
      Integer telefonoFijo = 55221234;
      Integer telefonoMovil = 912345678;
      String email = "andrea.contreras@gmail.com";

      Persona persona = new Persona(nombre, apellido, rutOk, direccion, telefonoFijo, telefonoMovil, email);

      ctx.json(persona);
    });

  }

}
