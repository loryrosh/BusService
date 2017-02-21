/**
 * @author http://twin-persona.org
 *
 * Bus service controller.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.twin_persona.busservice.db.DBController;
import org.twin_persona.busservice.services.ClientManager;
import org.twin_persona.busservice.services.EmployeeManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
(
    name = "frontController",
    urlPatterns = { "/service" }
)
public class FrontController extends HttpServlet
{
    private final static Logger logger = LoggerFactory.getLogger( FrontController.class );

    private EmployeeManager _employeeManager = null;
    private ClientManager _clientManager = null;

    private static DBController _dbController = null;
    public static DBController getDbController() { return _dbController; }

    @Override
    public void init() throws ServletException
    {
        super.init();

        _dbController = new DBController();
        _dbController.openConn();

        _employeeManager = new EmployeeManager();
        _clientManager = new ClientManager();
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws javax.servlet.ServletException, IOException
    {
        if( !_dbController.isConnected() )
            request.getRequestDispatcher("/WEB-INF/jsp/errorPages/error500.jsp").forward( request, response );
        if( request.getParameter( "client" ) != null )
            request.getRequestDispatcher("/WEB-INF/jsp/client.jsp").forward( request, response );
        else
            request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp").forward( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws javax.servlet.ServletException, IOException
    {
        response.setContentType( "text/plain;charset=UTF-8" );

        if( request.getParameter( "add_station" ) != null )
        {
            boolean success = _employeeManager.addNewStation( request.getParameter( "station_title" ) );
            _writeResponse( "New station added!", success, response );
        }
        else if( request.getParameter( "add_bus" ) != null )
        {
            boolean success = _employeeManager.addNewBus(
                    request.getParameter( "bus_number" ), request.getParameter( "seats_number" ) );
            _writeResponse( "New bus added!", success, response );
        }
        else if( request.getParameter( "get_passengers_bus" ) != null )
        {
            String passengers = _employeeManager.getAllPassengersForBus( request.getParameter( "bus_num_select" ) );
            _writeResponse( passengers, ( passengers != null ), response );
        }
        else if( request.getParameter( "get_ticket" ) != null )
        {
            boolean success = _clientManager.buyTicket(
                    request.getParameter( "station_name" ), request.getParameter( "first_name" ),
                    request.getParameter( "surname" ), request.getParameter( "bus_num" ) );
            _writeResponse( "You have successfully purchased the ticket!", success, response );
        }
        else if( request.getParameter( "get_bus" ) != null )
        {
            String buses = _clientManager.getBuses(
                    request.getParameter( "station_from" ), request.getParameter( "station_to" ),
                    request.getParameter( "hour_from" ), request.getParameter( "hour_to" ) );
            _writeResponse( buses, ( buses != null ), response );
        }
        else if( request.getParameter( "get_timetable" ) != null )
        {
            String timetables = _clientManager.
                    getTimetablesByStation( request.getParameter( "station_title" ) );
            _writeResponse( timetables, ( timetables != null ), response );
        }
        else
            _writeResponse( "service", true, response );
    }

    @Override
    public void destroy()
    {
        _dbController.closeConn();
        _clientManager.closeResources();
        super.destroy();
    }

/******************************************** private **********************************************/

    private void _writeResponse( String msg, boolean success, HttpServletResponse response )
            throws IOException
    {
        if( success )
            response.getWriter().write( msg );
        else
            response.getWriter().write( "false" );
    }
}
