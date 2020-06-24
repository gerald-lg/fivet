package cl.ucn.disc.pdbp.tdd.model;

import java.time.ZonedDateTime;

/**
 * Clase Control.
 *
 * @author Gerald Lopez
 */
public class Control {

  /**
   * Fecha de control.
   */
  private final ZonedDateTime fecha;

  /**
   * Fecha del proximo control.
   */
  private final ZonedDateTime proximoControl;

  /**
   * Temperatura, no puede ser negativa.
   */
  private final Float temperatura;

  /**
   * Peso (KG), no puede ser negativo.
   * MIN: 1
   * MAX: 1000
   */
  private final Float peso;

  /**
   * Altura (cm) , no puede ser negativa.
   * MIN: 1
   * MAX: 200
   */
  private final Float altura;

  /**
   * Diagnostico del paciente.
   */
  private final String diagnostico;

  /**
   * Veterinario.
   */
  private final Persona veterinario;

  /**
   * Constructor de Control.
   * @param fecha del control
   * @param proximoControl fecha del proximo control
   * @param temperatura del paciente que le corresponde el control
   * @param peso del paciente que le corresponde el control
   * @param altura del paciente que le corresponde el control
   * @param diagnostico del paciente.
   * @param veterinario asociado.
   */
  public Control(ZonedDateTime fecha, ZonedDateTime proximoControl, Float temperatura, Float peso, Float altura,
                 String diagnostico, Persona veterinario) {

    if (fecha.equals(null)) {
      throw new NullPointerException("Este campo no puede estar vacio");
    }

    if (fecha.getDayOfYear() != ZonedDateTime.now().getDayOfYear()) {
      throw new RuntimeException("Fecha invalida");
    }

    this.fecha = fecha;

    if (!proximoControl.isAfter(ZonedDateTime.now())) {
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
  }

  public ZonedDateTime getFecha() {
    return this.fecha;
  }

  public ZonedDateTime getProximoControl() {
    return this.proximoControl;
  }

  public Float getTemperatura() {
    return this.temperatura;
  }

  public Float getPeso() {
    return this.peso;
  }

  public Float getAltura() {
    return this.altura;
  }

  public String getDiagnostico() {
    return this.diagnostico;
  }

  public Persona getVeterinario() {
    return this.veterinario;
  }
}
