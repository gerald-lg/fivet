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


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementacion generica de {@link Repository}.
 * @author Gerald Lopez
 * @param <T> tipo de dato
 * @param <K> tipo del Id.
 */
public final class RepositoryOrmLite<T, K> implements Repository<T, K> {

  private final Dao<T, K> theDao;

  /**
   * Constructor.
   * @param connectionSource conexion con  ORM.
   * @param theClazz para usar como fuente.
   */
  public RepositoryOrmLite(ConnectionSource connectionSource, Class<T> theClazz) {

    try {
      theDao = DaoManager.createDao(connectionSource, theClazz);
    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }

  }

  /**
   * Obtiene el listado de todos los T en el repositorio.
   *
   * @return la lista de T
   */
  @Override
  public List<T> findAll() {

    try {
      return theDao.queryForAll();
    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }
  }

  /**
   * Obtiene un T a traves de su identificador.
   *
   * @param id a buscar
   * @return T con la id.
   */
  @Override
  public T findById(K id) {

    try {
      return theDao.queryForId(id);
    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }

  }

  /**
   * Obtiene un List filtrado por "key".
   *
   * @param key   que se busca
   * @param value que se busca
   * @return Lista de T filtrada por key
   */
  @Override
  public List<T> findAll(String key, Object value) {
    try {
      return theDao.queryForEq(key,value);
    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }
  }

  /**
   * Permite construir consultas al repositorio de forma generica.
   *
   * @return la {@link QueryBuilder}
   */
  @Override
  public QueryBuilder<T, K> getQuery() {
    return theDao.queryBuilder();
  }

  /**
   * Crea un T en el repositorio.
   *
   * @param objeto a crear
   * @return true si se creo correctamente.
   */
  @Override
  public boolean create(T objeto) {

    try {
      return theDao.create(objeto) == 1;
    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }

  }

  /**
   * Actualiza los datos de T en el repositorio.
   *
   * @param objeto a actualizar
   * @return true si se actualizo satisfactoriamente.
   */
  @Override
  public boolean update(T objeto) {

    try {
      return theDao.update(objeto) == 1;
    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }

  }

  /**
   * Borra un T dado su identificador.
   *
   * @param id de T a eliminar.
   * @return true si se elimino correctamente.
   */
  @Override
  public boolean delete(K id) {

    try {
      return theDao.deleteById(id) == 1;
    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }

  }
}
