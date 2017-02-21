/**
 * @author http://twin-persona.org
 *
 * Class for Passenger JPA entity.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.db.models;

import javax.persistence.*;

@Entity
@Table( name = "passengers" )
@NamedQueries
({
    @NamedQuery( name = "Passengers.getAll", query = "SELECT p from Passenger p" ),
    @NamedQuery( name = "Passengers.getByInfo",
            query = "SELECT p from Passenger p where p.name = :name and p.surname = :surname" )
})
public class Passenger extends BaseEntity
{
    @Basic
    @Column
    private String name;

    @Basic
    @Column
    private String surname;

    public Passenger(){}

    public Passenger( String name, String surname )
    {
        this.name = name;
        this.surname = surname;
    }

    public String getName() { return name; }
    public void setName( String name ) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname( String surname ) { this.surname = surname; }
}
