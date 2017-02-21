/**
 * @author http://twin-persona.org
 *
 * Class to implement DAOable interface for the "passengers" table.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.db.dao.impl;

import org.twin_persona.busservice.db.dao.DAOable;
import org.twin_persona.busservice.db.models.Passenger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

public class PassengersDAO extends BaseEntityDAO<Passenger> implements DAOable<Passenger>
{
    public PassengersDAO( EntityManagerFactory factory ) {
        super( factory );
    }

    @Override
    public Passenger add( Passenger passenger )
    {
        if( getByInfo( passenger.getName(), passenger.getSurname() ) == null )
            return super.add( passenger );
        return getByInfo( passenger.getName(), passenger.getSurname() );
    }

    /**
     * Reads all Passenger objects.
     *
     * @return List of all passengers.
     */
    public List<Passenger> getAll() throws PersistenceException
    {
        TypedQuery<Passenger> namedQuery = getEntityManager()
                .createNamedQuery( "Passengers.getAll", Passenger.class );

        try
        {
            return namedQuery.getResultList();
        } catch( Exception ex ){}
        return null;
    }

    /**
     * Gets passenger by name and surname
     *
     * @param name passenger name
     * @param surname passenger surname
     * @return passenger
     */
    public Passenger getByInfo( String name, String surname ) throws PersistenceException
    {
        TypedQuery<Passenger> namedQuery = getEntityManager()
                .createNamedQuery( "Passengers.getByInfo", Passenger.class );
        namedQuery.setParameter( "name", name );
        namedQuery.setParameter( "surname", surname );

        try
        {
            return namedQuery.getSingleResult();
        } catch( Exception ex ){}
        return null;
    }
}
