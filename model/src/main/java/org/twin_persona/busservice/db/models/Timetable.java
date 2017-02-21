/**
 * @author http://twin-persona.org
 *
 * Class for Timetable JPA entity.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.busservice.db.models;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table( name = "timetables" )
@NamedQueries
({
    @NamedQuery( name = "Timetable.getAllByStation",
            query = "SELECT t from Timetable t WHERE t.station.title = :station_title" )
})
public class Timetable extends BaseEntity
{
    @Basic
    @Column
    private LocalTime time;

    @OneToOne
    @JoinColumn( name = "bus_id" )
    private Bus bus;

    @ManyToOne
    @JoinColumn( name = "station_id" )
    private Station station;

    public Timetable() {}

    public Timetable( LocalTime time, Bus bus, Station station )
    {
        this.time = time;
        this.bus = bus;
        this.station = station;
    }

    public LocalTime getTime() { return time; }
    public void setTime( LocalTime time ) { this.time = time; }

    public Bus getBus() { return bus; }
    public void setBus( Bus bus ) { this.bus = bus; }

    public Station getStation() { return station; }
    public void setStation( Station station ) { this.station = station; }
}
