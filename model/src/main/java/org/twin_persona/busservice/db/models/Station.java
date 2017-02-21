/**
 * @author http://twin-persona.org
 *
 * Class for Station JPA entity.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.db.models;

import javax.persistence.*;

@Entity
@Table( name = "stations" )
@NamedQueries
({
    @NamedQuery( name = "Stations.getAll", query = "SELECT s from Station s" ),
    @NamedQuery( name = "Stations.getByTitle", query = "SELECT s from Station s WHERE s.title = :title" )
})
public class Station extends BaseEntity
{
    @Basic
    @Column
    private String title;

    public Station() {}

    public Station( String title )
    {
        this.title = title;
    }

    public String getTitle() { return title; }
    public void setTitle( String title ) { this.title = title; }
}
