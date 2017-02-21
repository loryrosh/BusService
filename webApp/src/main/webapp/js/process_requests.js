/**
 * @author http://twin-persona.org
 *
 * JS functions to process forms.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

$( document ).ready( function()
{
    var statusField = $( '#status_message' );
    var modalWinStatusField = $( '#modal_status_message' );
    var contentField = $( '.content_result' );

    function hideMsgs()
    {
        statusField.toggle( false );
        contentField.toggle( false );
        modalWinStatusField.toggle( false );
    }

    function prepareContentField()
    {
        contentField.toggle( true );

        var contentBody = contentField.find( 'tbody' );
        contentBody.empty();
        return contentBody;
    }

    function show_alert( msg, msgField, isSuccess )
    {
        if( isSuccess )
            msgField.removeClass( 'bg-danger' ).addClass( 'bg-success' );
        else
            msgField.removeClass( 'bg-success' ).addClass( 'bg-danger' );
        msgField.toggle( true ).text( msg );
    }

    function has_empty_fields( data, msgField )
    {
        var res = false;
        $.each( data, function( i, field )
        {
            //alert( field.name + ' ' + field.value + ' ' + field.type );
            if ( field.value == '' && field.type != 'submit' )
            {
                show_alert( "Please fill in all the fields!", msgField, false );
                res = true;
                return false;
            }
        });
        return res;
    }

    function getFormArray( elem, msgField )
    {
        // serializing all elements of the form
        var form_data = elem.closest( 'form' ).serializeArray();

        // adding button to the array of form fields
        form_data.push({ name: elem.attr( 'name' ) });

        if( has_empty_fields( form_data, msgField ) )
            return '';
        return form_data;
    }

    function showResponse( resp, msgField )
    {
        if( resp == "false" )
            show_alert( "Unsuccessful attempt. Please try once again.", msgField, false );
        else
            show_alert( resp, msgField, true );
    }

    /************************************************* general settings *************************************************/

    // stop submit requests
    $( 'form' ).submit( function( e )
    {
        e.preventDefault();
    });

    $( 'button' ).click( function()
    {
        hideMsgs();
    });

    // hide error message when switching between tabs
    $( '.navbar-nav a, #navbar' ).click( function()
    {
        hideMsgs();
    });

    $.ajaxSetup(
    {
        url: "service",
        type: "POST"
    });

/************************************************* client forms *****************************************************/

    // process form for searching buses
    $( '#get_bus' ).click( function()
    {
        var form_data = getFormArray( $( this ), statusField );
        if( form_data != '' )
        {
            if( $( '#station_from' ).val() == $( '#station_to' ).val() )
            {
                show_alert( "Please choose different stations!", statusField, false );
                return false;
            }

            $.ajax(
            {
                data: form_data,
                dataType: 'json',
                success: function ( resp )
                {
                    if( resp == "false" )
                    {
                        showResponse( "Nothing found." );
                        return false;
                    }

                    var contentBody = prepareContentField();
                    $.each( resp, function( index, value )
                    {
                        var htmlContent = "<tr><td>" + value.number;

                        if( value.seatsNum != '0' )
                            htmlContent += "<span class='delimeter'></span><button type='button' " +
                                "class='btn btn-success btn-sm role' data-toggle='modal' " +
                                "id='" + value.number + "' " +
                                "data-target='#ticketBuyWindow'>Buy ticket</button>";

                        htmlContent += "</td><td>" + value.seatsNum + "</td></tr>";
                        contentBody.append( $( htmlContent ) );
                    });
                }
            });
        }
        else
            return false;
    });

    // populate hidden fields of the tickets ordering modal window
    $( '#ticketBuyWindow' ).on( 'shown.bs.modal', function( e )
    {
        hideMsgs();
        $( '#bus_num' ).val( e.relatedTarget.id );
        $( '#station_name' ).val( $( '#station_from' ).val() );
    });

    // register passenger for the bus
    $( '#get_ticket' ).click( function()
    {
        var form_data = getFormArray( $( this ), modalWinStatusField );
        if( form_data != '' )
        {
            $.ajax(
            {
                data: form_data,
                success: function ( resp )
                {
                    if( resp != 'false' )
                    {
                        ( $( '#ticketBuyWindow' ).find( '.close' ) ).click();
                        showResponse( resp, statusField  );
                    }
                    else
                        showResponse( resp, modalWinStatusField);
                }
            });
        }
        else
            return false;
    });

    // process form for showing timetables
    $( '#get_timetable' ).click( function()
    {
        var form_data = getFormArray( $( this ), statusField );
        if( form_data != '' )
        {
            $.ajax(
            {
                data: form_data,
                dataType: 'json',
                success: function ( resp )
                {
                    if( resp == "false" )
                    {
                        showResponse( resp, statusField );
                        return false;
                    }

                    var contentBody = prepareContentField();
                    $.each( resp, function( index, value )
                    {
                        // '00' is reduced to single '0' when transferred from the server
                        if( value.time.minute == '0' )
                            value.time.minute = '00';

                        var row = $( "<tr><td>" + value.time.hour + ":" + value.time.minute +
                            "</td><td>" + value.bus.number + "</td></tr>" );
                        contentBody.append( row );
                    });
                }
            });
        }
        else
            return false;
    });

/************************************************* employee forms ***************************************************/

    // process employee password
    $( '#check_passwd' ).click( function()
    {
        var form_data = getFormArray( $( this ), statusField );
        if( form_data != '' )
        {
            $.ajax(
            {
                data: form_data,
                success: function ( resp )
                {
                    if( resp == "false" )
                        show_alert( "Incorrect password!", statusField, false );
                    else
                        location.href = "service";
                }
            });
        }
        else
            return false;
    });

    // process form for adding new stations and buses
    $( "#add_station, #add_bus" ).click( function()
    {
        var form_data = getFormArray( $( this ), statusField );
        if( form_data != '' )
        {
            $.ajax(
            {
                data: form_data,
                success: function ( resp ) { showResponse( resp, statusField ); }
            });
        }
        else
            return false;
    });

    // process form for showing all passengers
    $( "#get_passengers_bus" ).click( function()
    {
        var form_data = getFormArray( $( this ), statusField );
        if( form_data != '' )
        {
            $.ajax(
            {
                data: form_data,
                dataType: 'json',
                success: function ( resp )
                {
                    if( resp == "false" )
                    {
                        showResponse( resp, statusField );
                        return false;
                    }

                    var contentBody = prepareContentField();
                    $.each( resp, function( index, value )
                    {

                        var row = $( "<tr><td>" + value + "</td></tr>" );
                        contentBody.append( row );
                    });
                }
            });
        }
        else
            return false;
    });
});
