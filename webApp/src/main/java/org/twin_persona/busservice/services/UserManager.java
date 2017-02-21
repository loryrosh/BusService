/**
 * @author http://twin-persona.org
 *
 * General class for managing users.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.twin_persona.busservice.FrontController;
import org.twin_persona.busservice.db.DBController;
import org.twin_persona.busservice.db.dao.impl.*;
import org.twin_persona.busservice.db.models.*;

public class UserManager
{
    protected final static Logger logger = LoggerFactory.getLogger( ClientManager.class );

    protected static StationsDAO stationsDAO = null;
    protected static TimetablesDAO timetablesDAO = null;
    protected static BusesDAO busesDAO = null;
    protected static PassengersDAO passengersDAO = null;
    protected static TicketsDAO ticketsDAO = null;

    public UserManager()
    {
        try
        {
            DBController dbController = FrontController.getDbController();

            stationsDAO = ( StationsDAO ) dbController.getModelFactory().getModel( Station.class );
            timetablesDAO = ( TimetablesDAO ) dbController.getModelFactory().getModel( Timetable.class );
            busesDAO = ( BusesDAO ) dbController.getModelFactory().getModel( Bus.class );
            passengersDAO = ( PassengersDAO ) dbController.getModelFactory().getModel( Passenger.class );
            ticketsDAO = ( TicketsDAO ) dbController.getModelFactory().getModel( Ticket.class );
        } catch( Exception ex ){}
    }

    public void closeResources()
    {
        stationsDAO.close();
        timetablesDAO.close();
        busesDAO.close();
        passengersDAO.close();
        ticketsDAO.close();
    }
}
