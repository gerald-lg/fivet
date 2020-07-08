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
import java.util.Collections;
import java.util.List;

/**
 * Clase Control.
 *
 * @author Gerald Lopez
 */
@DatabaseTable(tableName = "Control")
public class Control {

  /**
   * Id de control.
   */
  @DatabaseField(generatedId = true)
  private Long id;

  /**
   * Fecha de control.
   */
  @DatabaseField(persisterClass = ZonedDateTimeType.class, canBeNull = false)
  private ZonedDateTime fecha;

  /**
   * Fecha del proximo control.
   */
  @DatabaseField(persisterClass = ZonedDateTimeType.class)
  private ZonedDateTime proximoControl;

  /**
   * Temperatura, no puede ser negativa.
   */
  @DatabaseField(canBeNull = false)
  private Float temperatura;

  /**
   * Peso (KG), no puede ser negativo.
   * MIN: 1
   * MAX: 1000
   */
  @DatabaseField(canBeNull = false)
  private Float peso;

  /**
   * Altura (cm) , no puede ser negativa.
   * MIN: 1
   * MAX: 200
   */
  @DatabaseField(canBeNull = false)
  private Float altura;

  /**
   * Diagnostico del paciente.
   */
  @DatabaseField(canBeNull = false)
  private String diagnostico;

  /**
   * Veterinario.
   */
  @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
  private Persona veterinario;

  /**
   * Ficha.
   */
  @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Ficha ficha;

  @ForeignCollectionField(eager = true)
  private ForeignCollection<Examen> examenes;

  /**
   * Constructor vacio.
   */
  Control() {
    //Nada aqui.
  }

  /**
   * Constructor de Control.
   * @param fecha del control
   * @param proximoControl fecha del proximo control
   * @param temperatura del paciente que le corresponde el control
   * @param peso del paciente que le corresponde el control
   * @param altura del paciente que le corresponde el control
   * @param diagnostico del paciente.
   * @param veterinario asociado.
   * @param ficha asociada al control.
   */
  public Control(ZonedDateTime fecha, ZonedDateTime proximoControl, Float temperatura, Float peso,
                 Float altura, String diagnostico, Persona veterinario, Ficha ficha) {

    if (fecha.equals(null)) {
      throw new NullPointerException("Este campo no puede estar vacio");
    }

    if (fecha.getDayOfYear() != ZonedDateTime.now().getDayOfYear()) {
      throw new RuntimeException("Fecha invalida");
    }

    this.fecha = fecha;

    if (proximoControl != null && proximoControl.isBefore(ZonedDateTime.now())) {
      throw new RuntimeException("Fecha de proximo control invalida.");
    }

    this.proximoControl = proximoControl;

    if (temperatura == null) {
      throw new NullPointerException("No puede ser null");
    }
    if (temperatura < 0 && temperatura > 50) {
      throw new RuntimeException("Temperatura invalida");
    }
    this.temperatura = temperatura;

    if (peso == null) {
      throw new NullPointerException("No puede ser null");
    }
    if (peso < 0 && peso > 1000) {
      throw new RuntimeException("Peso invalido");
    }
    this.peso = peso;

    if (altura == null) {
      throw new NullPointerException("No puede ser null");
    }
    if (altura < 0 && altura > 200) {
      throw new RuntimeException("Altura invalida");
    }
    this.altura = altura;

    if (diagnostico.equals(null)) {
      throw new NullPointerException("El paciente debe tener un diagnostico");
    }

    this.diagnostico = diagnostico;

    if (veterinario.equals(null)) {
      throw new NullPointerException("El paciente debe ser atendido por un veterinario");
    }

    this.veterinario = veterinario;

    if (ficha.equals(null)) {
      throw new NullPointerException("El control debe estar asociado a una ficha!");
    }
    this.ficha = ficha;

  }

  /**
   * Obtiene la fecha del control.
   * @return fecha.
   */
  public ZonedDateTime getFecha() {
    return this.fecha;
  }

  /**
   * Obtiene la fecha del proximo control.
   * @return proximo control.
   */
  public ZonedDateTime getProximoControl() {
    return this.proximoControl;
  }

  /**
   * Obtiene la temperatura del paciente.
   * @return temperatura
   */
  public Float getTemperatura() {
    return this.temperatura;
  }

  /**
   * Obtiene el peso del paciente.
   * @return peso
   */
  public Float getPeso() {
    return this.peso;
  }

  /**
   * Obtiene la altura del paciente.
   * @return altura.
   */
  public Float getAltura() {
    return this.altura;
  }

  /**
   * Obtiene el diagnostico.
   * @return diagnostico
   */
  public String getDiagnostico() {
    return this.diagnostico;
  }

  /**
   * Obtiene el veterinario asociado al control.
   * @return veterinario.
   */
  public Persona getVeterinario() {
    return this.veterinario;
  }

  /**
   * Obtiene el id autogenerado.
   * @return id.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Obtiene la ficha asociada al control.
   * @return Ficha.
   */
  public Ficha getFicha() {
    return this.ficha;
  }

  /**
   * Obtiene los examenes de un control.
   * @return {@link List} de {@link Examen}.
   */
  public List<Examen> getExamenes() {
    return Collections.unmodifiableList(new ArrayList<>(examenes));
  }

  /**
   * Agrega un nuevo examen.
   */
  public void createExamen(Examen examen) {
    this.examenes.add(examen);
  }

}
