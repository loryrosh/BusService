/**
 * @author http://twin-persona.org
 *
 * Class for Ticket JPA entity.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.db.models;

import javax.persistence.*;

@Entity
@Table( name = "tickets" )
@NamedQueries
({
    @NamedQuery( name = "Tickets.getByBusId",
            query = "SELECT t from Ticket t WHERE t.bus.id = :bus_id" ),
    @NamedQuery( name = "Tickets.getByBusAndPassenger",
        query = "SELECT t from Ticket t WHERE t.bus.id = :bus_id AND t.passenger.id = :passenger_id" )
})
public class Ticket extends BaseEntity
{
    @OneToOne
    @JoinColumn( name = "bus_id" )
    private Bus bus;

    @OneToOne
    @JoinColumn( name = "passenger_id" )
    private Passenger passenger;

    public Ticket() {}

    public Ticket( Bus bus, Passenger passenger )
    {
        this.bus = bus;
        this.passenger = passenger;
    }

    public Bus getBus() { return bus; }
    public void setBus( Bus bus ) { this.bus = bus; }

    public Passenger getPassenger() { return passenger; }
    public void setPassenger( Passenger passenger ) { this.passenger = passenger; }
}
