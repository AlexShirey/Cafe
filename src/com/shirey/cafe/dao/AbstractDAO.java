package com.shirey.cafe.dao;

import com.shirey.cafe.db.ProxyConnection;
import com.shirey.cafe.entity.Entity;
import com.shirey.cafe.exception.DAOException;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@code AbstractDAO} class
 * is a superclass for other DAO classes,
 * provides access to the database. *
 *
 * @author Alex Shirey
 */

public abstract class AbstractDAO<K, T extends Entity> {

    /**
     * Gets a row from the table using id,
     * builds and returns {@code Entity} object that represents this id.
     *
     * @param id a id of the entity object
     * @return a {@code Entity}, or null if id is not founded.
     * @throws DAOException if a database access error occurs
     */
    public abstract T findEntityById(K id) throws DAOException;

    /**
     * Gets all rows from the table which represents one of the entity and
     * returns them as a list of {@code Entity} objects
     *
     * @return a list contains {@code Entity}, not null
     * @throws DAOException if a database access error occurs
     */
    public abstract List<T> findAll() throws DAOException;

    /**
     * Returns the acquired ProxyConnection to the connection pool.
     * Sets auto commit to {@code true} before the return.
     *
     * @throws DAOException if SQLException occurs
     */
    void returnConnection(ProxyConnection proxyConnection) throws DAOException {
        try {
            if (proxyConnection != null) {
                proxyConnection.setAutoCommit(true);
                proxyConnection.close();
            }
        } catch (SQLException e) {
            throw new DAOException("problems with returning proxy connection to connection pool, connection wasn't returned.", e);
        }
    }

}


