/**
 * @author http://twin-persona.org
 *
 * Bus service for employees.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.services;

import com.google.gson.Gson;
import org.twin_persona.busservice.db.models.Bus;
import org.twin_persona.busservice.db.models.Station;
import org.twin_persona.busservice.db.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager extends UserManager
{
    public EmployeeManager(){}

    /**
     * Adds new Station.
     *
     * @param title Station name
     * @return flag of success
    */
    public boolean addNewStation( String title )
    {
        if( stationsDAO.add( new Station( title ) ) != null )
            return true;
        return false;
    }

    /**
     * Adds new Bus.
     *
     * @param busNum    bus number
     * @param seatsAmount    amount of seats
     * @return flag of success
    */
    public boolean addNewBus( String busNum, String seatsAmount )
    {
        try
        {
            if( busesDAO.add( new Bus( Integer.parseInt( busNum ), Integer.parseInt( seatsAmount ) ) ) != null )
                return true;
        } catch( Exception ex ){}
        return false;
    }

    /**
     * Gets the list of all passengers registered for the bus.
     *
     * @param busNumber bus number
     * @return json with the list of passengers
    */
    public String getAllPassengersForBus( String busNumber )
    {
        try
        {
            // getting the list of tickets for the current bus
            Bus curBus = busesDAO.getByNumber( Integer.parseInt( busNumber ) );
            List<Ticket> ticketsForCurBus = ticketsDAO.getByBusId( curBus.getId() );

            // getting the list of names/surnames of passengers
            List<String> passengers = new ArrayList<>();
            for( Ticket ticket : ticketsForCurBus )
                passengers.add( ticket.getPassenger().getName() + " " + ticket.getPassenger().getSurname() );

            return new Gson().toJson( passengers );
        } catch( Exception ex ){ return null; }
    }

    /**
     * Gets the list of all buses.
     *
     * @return list of buses
     */
    public List<Bus> getAllBuses()
    {
        return busesDAO.getAll();
    }
}
