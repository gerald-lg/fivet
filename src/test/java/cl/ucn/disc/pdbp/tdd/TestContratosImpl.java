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

import checkers.units.quals.A;
import cl.ucn.disc.pdbp.tdd.model.Ficha;
import cl.ucn.disc.pdbp.tdd.model.Persona;
import cl.ucn.disc.pdbp.tdd.model.Sexo;
import cl.ucn.disc.pdbp.tdd.model.Tipo;
import cl.ucn.disc.pdbp.tdd.utils.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Test Contratos del sistema
 * @author Gerald Lopez
 */
public class TestContratosImpl {

  /**
   * Logger
   */
  private static final Logger log = LoggerFactory.getLogger(TestContratosImpl.class);

  private static String databaseUrl = "jdbc:h2:mem:fivet_db";

  private static final Contratos contratos = new ContratosImpl(databaseUrl);

  /**
   * Testing del C01: Registrar un paciente.
   */
  @Test
  public void testRegistrarPaciente() {

    //Datos del paciente
    Long numeroFicha = 123L;
    String nombre = "Calcetin";
    String especie = "Felino";
    ZonedDateTime fechaNacimiento = ZonedDateTime.now();
    String raza = "Angora";
    Sexo sexo = Sexo.MACHO;
    String color = "negro";
    Tipo tipo = Tipo.EXTERNO;

    //Instancia del duenio
    Persona duenio = new Persona("Dylan","Frost", "170800516","Fake 541", 55229988,
            998761234,"dfrost@gmail.com");

    contratos.registrarPersona(duenio);

    //Instancia de ficha del paciente.
    Ficha ficha = new Ficha(numeroFicha, nombre, especie, fechaNacimiento, raza, sexo, color, tipo, duenio);

    //Insercion en la base de datos.
    Ficha fichaDb = contratos.registrarPaciente(ficha);

    //Los atributos de la ficha creada deberian ser los mismos que la ficha almacenada en la BD
    Assertions.assertEquals(fichaDb.getId(),ficha.getId());
    Assertions.assertEquals(fichaDb.getNumero(),ficha.getNumero());
    Assertions.assertEquals(fichaDb.getNombre(),ficha.getNombre());
    Assertions.assertEquals(fichaDb.getEspecie(),ficha.getEspecie());
    Assertions.assertEquals(fichaDb.getRaza(),ficha.getRaza());
    Assertions.assertEquals(fichaDb.getSexo(),ficha.getSexo());
    Assertions.assertEquals(fichaDb.getColor(),ficha.getColor());
    Assertions.assertEquals(fichaDb.getTipo(),ficha.getTipo());
    Assertions.assertEquals(fichaDb.getDuenio().getRut(),ficha.getDuenio().getRut());


  }

  /**
   * Testing C02: Registrar una persona.
   */
  @Test
  public void testRegistrarPersona() {

    //Atributos de Persona.
    String nombre = "Gerald";
    String apellido = "Lopez";
    String rut = "170800516";
    String direccion = "Fake 123";
    Integer telefonoFijo = 55221234;
    Integer telefonoMovil = 977233877;
    String email = "glopez@gmail.com";

    //Intancia de la persona.
    Persona persona = new Persona(nombre,apellido,rut, direccion,telefonoFijo,telefonoMovil,email);

    //Insercion en la base de datos.
    Persona personaDb= contratos.registrarPersona(persona);

    Assertions.assertEquals(personaDb.getId(),persona.getId());
    Assertions.assertEquals(personaDb.getRut(),persona.getRut());
    Assertions.assertEquals(personaDb.getNombre(),persona.getNombre());


  }

  /**
   * Testing C03-Buscar una ficha.
   */
  @Test
  public void testBuscarFicha() {

    //Datos de duenios

    Persona duenio1 = new Persona("Dylan","Frost", "247305335","Fake 541", 55229988,
            998761234,"dfrost@gmail.com");

    Persona duenio2 = new Persona("Brenda", "Lopez","191468694","Fake 653", 55218877,
            963293074,"blopez@hotmail.com");

    Persona duenio3 = new Persona("Mauro","Fuentes","198774081","Fake 123", 55225544,
            948931276, "mfuentes@gmail.com");

    contratos.registrarPersona(duenio1);
    contratos.registrarPersona(duenio2);
    contratos.registrarPersona(duenio3);

    //Instancia de fichas.

    Ficha ficha1 = new Ficha(23L,"Harry","Felino",ZonedDateTime.now(),"American shorthair",
            Sexo.MACHO, "Amarillo", Tipo.EXTERNO, duenio2);

    Ficha ficha2 = new Ficha(22L,"Artemi","Felino",ZonedDateTime.now(),"Siberiano",Sexo.HEMBRA,
            "Gris", Tipo.INTERNO, duenio3);

    Ficha ficha3 = new Ficha(404L,"Askar","Canino", ZonedDateTime.now(),"Pastor belga",
             Sexo.MACHO,"Negro", Tipo.EXTERNO, duenio1);

    //Registro en BD.
    contratos.registrarPaciente(ficha1);
    contratos.registrarPaciente(ficha2);
    contratos.registrarPaciente(ficha3);

    //Busqueda por numero
    {
      List<Ficha> fichas = contratos.buscarFicha("19");

      Assertions.assertEquals(fichas.get(0).getId(), ficha1.getId());

      Assertions.assertEquals(fichas.get(1).getId(), ficha2.getId());
    }

    //Filtro por nombre de Paciente
    {
      List<Ficha> fichas = contratos.buscarFicha("Artemi");

      Assertions.assertEquals(fichas.get(0).getId(), ficha2.getId());
    }
    //Filtro por nombre de duenio
    {
      List<Ficha> fichas = contratos.buscarFicha("Dylan");

      Assertions.assertEquals(fichas.get(0).getId(), ficha3.getId());
    }

  }

}
