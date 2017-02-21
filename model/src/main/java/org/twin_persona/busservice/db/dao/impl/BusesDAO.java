/**
 * @author http://twin-persona.org
 *
 * Class to implement DAOable interface for the "buses" table.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.db.dao.impl;

import org.twin_persona.busservice.db.dao.DAOable;
import org.twin_persona.busservice.db.models.Bus;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

public class BusesDAO extends BaseEntityDAO<Bus> implements DAOable<Bus>
{
    public BusesDAO( EntityManagerFactory factory ) {
        super( factory );
    }

    @Override
    public Bus add( Bus bus )
    {
        if( getByNumber( bus.getNumber() ) == null )
            return super.add( bus );
        return null;
    }

    /**
     * Reads all Bus objects.
     *
     * @return List of all buses.
     */
    public List<Bus> getAll() throws PersistenceException
    {
        TypedQuery<Bus> namedQuery = getEntityManager()
                .createNamedQuery( "Buses.getAll", Bus.class );

        try
        {
            return namedQuery.getResultList();
        } catch( Exception ex ){}
        return null;
    }

    /**
     * Gets bus by its number.
     *
     * @param number    bus number
     * @return Bus.
     */
    public Bus getByNumber( int number ) throws PersistenceException
    {
        TypedQuery<Bus> namedQuery = getEntityManager()
                .createNamedQuery( "Buses.getByNumber", Bus.class );
        namedQuery.setParameter( "number", number );

        try
        {
            return namedQuery.getSingleResult();
        } catch( Exception ex ){}
        return null;
    }
}
