/**
 * @author http://twin-persona.org
 *
 * Filter to check access info.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.busservice.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter
(
    filterName = "authFilter",
    servletNames = "FrontController"
)
public class AuthFilter implements Filter
{
    private final String _employeePasswd = "serviceAdmin";

    @Override
    public void init( FilterConfig filterConfig ) throws ServletException {}

    @Override
    public final void doFilter( final ServletRequest req, final ServletResponse resp, final FilterChain chain )
    {
        HttpServletRequest request = ( HttpServletRequest ) req;
        HttpSession session = request.getSession();

        try
        {
            if( request.getMethod().equals( "GET" ) )
                chain.doFilter( req, resp );
            else if( request.getParameter( "check_passwd" ) != null )
            {
                Object passwd = request.getParameter( "passwd" );

                if( passwd.toString().equals( _employeePasswd ) )
                {
                    session.setAttribute( "isAuthorized", true );
                    chain.doFilter( req, resp );
                }
                resp.setContentType( "text/plain" );
                resp.getWriter().write( "false" );
            }
            else if( session.getAttribute( "isAuthorized" ) != null
                    && session.getAttribute( "isAuthorized" ).equals( true ) )
                chain.doFilter( req, resp );
            else if( request.getParameter( "get_bus" ) != null ||
                    request.getParameter( "get_timetable" ) != null ||
                    request.getParameter( "get_ticket" ) != null)
                chain.doFilter( req, resp );
        } catch( final Exception ex ) { System.out.println( ex.getMessage() ); }
    }

    @Override
    public void destroy() {}
}
