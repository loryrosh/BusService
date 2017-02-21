/**
 * @author http://twin-persona.org
 *
 * Bus service for clients.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.services;

import com.google.gson.Gson;
import org.twin_persona.busservice.db.models.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ClientManager extends UserManager
{
    public ClientManager(){}

    /**
     * Gets the list of all stations.
     *
     * @return list of stations
     */
    public List<Station> getAllStations()
    {
        return stationsDAO.getAll();
    }

    /**
    * Gets timetables by station.
    *
    * @param title  station title
    * @return json with the list of of timetables
    */
    public String getTimetablesByStation( String title )
    {
        try
        {
            List<Timetable> timetables = timetablesDAO.getAllByStation( title );
            return new Gson().toJson( timetables );
        } catch( Exception ex ){ return null; }
    }

    /**
     * Gets bus by stations and time.
     *
     * @param stationFrom   initial station
     * @param stationTo   destination station
     * @param hourFrom  left time boundary
     * @param hourTo    right time boundary
     * @return json with the list of buses
     */
    public String getBuses( String stationFrom, String stationTo, String hourFrom, String hourTo )
    {
        try
        {
            // getting stations by their ids
            int stationFromId = stationsDAO.getByTitle( stationFrom ).getId();
            int stationToId = stationsDAO.getByTitle( stationTo ).getId();

            // getting buses ids that satisfy the search criteria
            List busIds = timetablesDAO.getBusesByStationAndTime
                    ( stationFromId, stationToId, LocalTime.parse( hourFrom ), LocalTime.parse( hourTo ) );

            // getting bus objects by found ids
            List<Bus> buses = new ArrayList<>();
            for( Object id : busIds )
            {
                Bus curBus = busesDAO.getById( ( Integer )id, Bus.class );
                Bus newBus = new Bus( curBus.getNumber(), curBus.getSeatsNum() );

                // setting the number of seats left
                newBus.setSeatsNum( _getTicketsLeftNum( curBus ) );
                buses.add( newBus );
            }
            return new Gson().toJson( buses );
        } catch( Exception ex ){ return null; }
    }

    /**
     * Adds ticket.
     *
     * @param station   station of departure
     * @param name  passenger name
     * @param surname   passenger surname
     * @param busNum    bus number
     * @return flag os success
     */
    public boolean buyTicket( String station, String name, String surname, String busNum )
    {
        try
        {
            // saving new passenger
            Passenger curPassenger = passengersDAO.add( new Passenger( name, surname ) );
            Bus curBus = busesDAO.getByNumber( Integer.parseInt( busNum ) );

            if( _getTicketsLeftNum( curBus ) > 0 && curBus != null && curPassenger != null )
            {
                Ticket ticket = ticketsDAO.getByBusAndPassenger( curBus.getId(), curPassenger.getId() );
                if( ticket == null )
                {
                    if( ticketsDAO.add( new Ticket( curBus, curPassenger ) ) != null )
                        return true;
                }
            }
        } catch( Exception ex ){ return false; }
        return false;
    }

/******************************************** private **********************************************/

    private int _getTicketsLeftNum( Bus curBus )
    {
        List<Ticket> tickets = ticketsDAO.getByBusId( curBus.getId() );
        return curBus.getSeatsNum() - tickets.size();
    }
}
