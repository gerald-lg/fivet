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

package cl.ucn.disc.pdbp.tdd.dao;

import checkers.units.quals.C;
import cl.ucn.disc.pdbp.tdd.model.Persona;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Storage Test
 *
 * @author Gerald Lopez
 */
public final class StorageTest {

  /**
   * Logger
   */
  private static final Logger log = LoggerFactory.getLogger(StorageTest.class);

  /**
   * Base de datos a usar (Memoria RAM)
   */

  private final String databaseUrl = "jdbc:h2:mem:fivet_db";

  /**
   * Testing de ORMLite + H2(DB)
   */
  @Test
  public void testDataBase() throws SQLException {

    // Conexion
    try (ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl)) {

        //Crear la tabla
        TableUtils.createTableIfNotExists(connectionSource, Persona.class);

        //
        Dao<Persona, Long> daoPersona = DaoManager.createDao(connectionSource, Persona.class);

        Persona persona = new Persona("Gerald", "Lopez", "152532873", "Falsa 123",
                55221234,912345678, "gerald.lopez@gmail.com");

        int tuplas = daoPersona.create(persona);
        log.debug("Id {}", persona.getId());

        Assertions.assertEquals(1,tuplas, "El numero de tuplas es != 1");

        Persona personaDb = daoPersona.queryForId(persona.getId());

        Assertions.assertEquals(persona.getNombre(),personaDb.getNombre(),"No coincide!");
        Assertions.assertEquals(persona.getApellido(),personaDb.getApellido(),"No coincide!");
        Assertions.assertEquals(persona.getRut(),personaDb.getRut(),"No coincide!");

        List<Persona> personaList = daoPersona.queryForEq("rut","152532873");
        Assertions.assertEquals(1,personaList.size(),"Mas de una persona?!");

        Assertions.assertEquals(0,daoPersona.queryForEq("rut","19").size(),"Encontro algo?");

    } catch (IOException e) {
        log.error("Error", e);
    }


  }

  /**
   * Testing the repository.
   */
  @Test
  public void testRepository() {

    try (ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl)) {

      //Crear tabla.
      TableUtils.createTableIfNotExists(connectionSource,Persona.class);

      // Test conexion nula
      Assertions.assertThrows(RuntimeException.class, () -> new RepositoryOrmLite<>(null,Persona.class));

      //Repositorio
      Repository<Persona,Long> theRepo = new RepositoryOrmLite<>(connectionSource,Persona.class);

      //Repositorio vacio.
      {
        List<Persona> personas = theRepo.findAll();
        Assertions.assertEquals(0, personas.size(), "Size != 0");
      }

      //Insertar una persona en el repositorio v1.
      {
        Persona persona = new Persona("Gerald", "Lopez", "152532873", "Falsa 123",
                55221234,912345678, "gerald.lopez@gmail.com");

        if (!theRepo.create(persona)){
          Assertions.fail("No se inserto la persona!");
        }
      }

      //Insertar una persona en el repositorio v2.
      {
        Persona persona = new Persona("Gerald", "Lopez", "152532873", "Falsa 123",
                55221234,912345678, "gerald.lopez@gmail.com");

        Assertions.assertThrows(RuntimeException.class, () -> theRepo.create(persona));
      }

      //Repositorio despues de haber realizado una insercion.
      {
        List<Persona> personas = theRepo.findAll();
        Assertions.assertEquals(1, personas.size(), "Size != 1");
      }


    } catch (IOException | SQLException e){
        throw new RuntimeException(e);
    }

  }


}
