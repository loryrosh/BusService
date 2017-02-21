/**
 * @author http://twin-persona.org
 *
 * Class to implement DAOable interface for the "tickets" table.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.db.dao.impl;

import org.twin_persona.busservice.db.dao.DAOable;
import org.twin_persona.busservice.db.models.Ticket;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

public class TicketsDAO extends BaseEntityDAO<Ticket> implements DAOable<Ticket>
{
    public TicketsDAO( EntityManagerFactory factory ) { super( factory ); }

    @Override
    public Ticket add( Ticket ticket )
    {
        if( getByBusAndPassenger( ticket.getBus().getId(),
                                  ticket.getPassenger().getId() ) == null )
            return super.add( ticket );
        return null;
    }

    /**
     * Gets tickets by bus id.
     *
     * @param busId bus id
     * @return list of tickets
     */
    public List<Ticket> getByBusId( int busId ) throws PersistenceException
    {
        TypedQuery<Ticket> namedQuery = getEntityManager()
                .createNamedQuery( "Tickets.getByBusId", Ticket.class );
        namedQuery.setParameter( "bus_id", busId );

        try
        {
            return namedQuery.getResultList();
        } catch( Exception ex ){}
        return null;
    }

    /**
     * Gets ticket by bus id and passenger id.
     *
     * @param busId bus id
     * @param passengerId passenger id
     * @return Ticket
     */
    public Ticket getByBusAndPassenger( int busId, int passengerId ) throws PersistenceException
    {
        TypedQuery<Ticket> namedQuery = getEntityManager()
                .createNamedQuery( "Tickets.getByBusAndPassenger", Ticket.class );
        namedQuery.setParameter( "bus_id", busId );
        namedQuery.setParameter( "passenger_id", passengerId );

        try
        {
            return namedQuery.getSingleResult();
        } catch( Exception ex ){}
        return null;
    }
}
