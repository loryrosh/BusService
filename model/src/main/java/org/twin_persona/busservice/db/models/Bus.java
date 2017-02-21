/**
 * @author http://twin-persona.org
 *
 * Class for Bus JPA entity.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.db.models;

import javax.persistence.*;

@Entity
@Table( name = "buses" )
@NamedQueries
({
    @NamedQuery( name = "Buses.getAll", query = "SELECT b from Bus b" ),
    @NamedQuery( name = "Buses.getByNumber", query = "SELECT b from Bus b WHERE b.number = :number" )
})
public class Bus extends BaseEntity
{
    @Basic
    @Column
    private int number;

    @Basic
    @Column( name = "seats_num" )
    private int seatsNum;

    public Bus(){}

    public Bus( int number, int seatsNum )
    {
        this.number = number;
        this.seatsNum = seatsNum;
    }

    public int getNumber() { return number; }
    public void setNumber( int number ) { this.number = number; }

    public int getSeatsNum() { return seatsNum; }
    public void setSeatsNum( int seatsNum ) { this.seatsNum = seatsNum;}
}
