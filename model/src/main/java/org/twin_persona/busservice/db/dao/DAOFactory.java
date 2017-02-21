/**
 * @author http://twin-persona.org
 *
 * Creates DAO models for DB entities.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.db.dao;

import org.twin_persona.busservice.db.dao.impl.*;
import org.twin_persona.busservice.db.models.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class DAOFactory
{
    private static DAOFactory _instance = new DAOFactory();
    private static EntityManagerFactory factory = null;

    private DAOFactory(){}

    /**
     * Provides reference to DAOFactory.
     *
     * @return Instance of DAOFactory.
     */
    public static DAOFactory getInstance() throws PersistenceException
    {
        if( factory == null )
            factory = Persistence.createEntityManagerFactory( "busservice" );
        return _instance;
    }

    /**
     * Gets the necessary DAO model.
     *
     * @param entityType    class of the entity
     * @return Ref to the specific BaseEntityDAO.
     */
    public BaseEntityDAO getModel( Class entityType )
    {
        if( entityType.equals( Passenger.class ) )
            return factory != null ? new PassengersDAO( factory ) : null;
        else if( entityType.equals( Bus.class ) )
            return factory != null ? new BusesDAO( factory ) : null;
        else if( entityType.equals( Station.class ) )
            return factory != null ? new StationsDAO( factory ) : null;
        else if( entityType.equals( Timetable.class ) )
            return factory != null ? new TimetablesDAO( factory ) : null;
        else if( entityType.equals( Ticket.class ) )
            return factory != null ? new TicketsDAO( factory ) : null;

        return null;
    }

    /**
     * Closes DB connection.
     */
    public static void closeConnection()
    {
        if( factory != null )
            factory.close();
    }
}
