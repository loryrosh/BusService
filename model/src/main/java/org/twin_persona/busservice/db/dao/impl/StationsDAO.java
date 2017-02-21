/**
 * @author http://twin-persona.org
 *
 * Class to implement DAOable interface for the "stations" table.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.db.dao.impl;

import org.twin_persona.busservice.db.dao.DAOable;
import org.twin_persona.busservice.db.models.Station;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

public class StationsDAO extends BaseEntityDAO<Station> implements DAOable<Station>
{
    public StationsDAO( EntityManagerFactory factory ) {
        super( factory );
    }

    @Override
    public Station add( Station station )
    {
        if( getByTitle( station.getTitle() ) == null )
            return super.add( station );
        return null;
    }

    /**
     * Reads all Station objects.
     *
     * @return List of all stations.
     */
    public List<Station> getAll() throws PersistenceException
    {
        TypedQuery<Station> namedQuery = getEntityManager()
                .createNamedQuery( "Stations.getAll", Station.class );

        try
        {
            return namedQuery.getResultList();
        } catch( Exception ex ){}
        return null;
    }

    /**
     * Gets station by its title.
     *
     * @param title station title
     * @return Station
     */
    public Station getByTitle( String title ) throws PersistenceException
    {
        TypedQuery<Station> namedQuery = getEntityManager()
                .createNamedQuery( "Stations.getByTitle", Station.class );
        namedQuery.setParameter( "title", title );

        try
        {
            return namedQuery.getSingleResult();
        } catch( Exception ex ){}
        return null;
    }
}
