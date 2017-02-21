/**
 * @author http://twin-persona.org
 *
 * Class to implement DAOable interface for the "timetables" table.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.db.dao.impl;

import org.twin_persona.busservice.db.dao.DAOable;
import org.twin_persona.busservice.db.models.Timetable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalTime;
import java.util.List;

public class TimetablesDAO extends BaseEntityDAO<Timetable> implements DAOable<Timetable>
{
    public TimetablesDAO( EntityManagerFactory factory ) {
        super( factory );
    }

    /**
     * Reads all Timetables objects by station title.
     *
     * @param stationTitle  station title
     * @return List of all timetables.
     */
    public List<Timetable> getAllByStation( String stationTitle ) throws PersistenceException
    {
        TypedQuery<Timetable> namedQuery = getEntityManager()
                .createNamedQuery( "Timetable.getAllByStation", Timetable.class );
        namedQuery.setParameter( "station_title", stationTitle );

        try
        {
            return namedQuery.getResultList();
        } catch( Exception ex ){}
        return null;
    }

    /**
     * Gets all buses by route and time.
     *
     * @param stationFrom   initial station
     * @param stationTo   destination station
     * @param hourFrom  left time boundary
     * @param hourTo    right time boundary
     * @return List of all found buses ids
     */
    public List getBusesByStationAndTime( int stationFrom, int stationTo, LocalTime hourFrom, LocalTime hourTo )
    {
        // departure time should be more than 10 minutes from now()
        LocalTime departureTime = LocalTime.now().plusMinutes( 10 );

        Query q = this.getEntityManager().createNativeQuery(
                "SELECT first.bus_id\n" +
                "FROM timetables first, timetables second\n" +
                "WHERE first.bus_id = second.bus_id and first.station_id=" + stationFrom +
                    " and second.station_id=" + stationTo + " and first.time > '" + hourFrom +
                    "' and first.time < '" + hourTo + "' and first.time < second.time" +
                    " and first.time > '" + departureTime + "';" );
        try
        {
            return q.getResultList();
        } catch( Exception ex ){}
        return null;
    }
}
