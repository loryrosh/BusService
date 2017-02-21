<%@ page import="org.twin_persona.busservice.services.ClientManager" %>
<%@ page import="org.twin_persona.busservice.db.models.Station" %>
<%@ page import="java.util.List" %>
<%@ include file="/WEB-INF/templates/header.jsp" %>

<%
    ClientManager clientManager = new ClientManager();
    List<Station> stations = clientManager.getAllStations();
%>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="../../index.jsp">Bus service&trade; <span class="delimeter"></span></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav" role="tablist">
                <li role="presentation" class="active"><a href="#find_bus" aria-controls="find_bus" role="tab" data-toggle="tab">Find bus</a></li>
                <li role="presentation"><a href="#timetable" aria-controls="timetable" role="tab" data-toggle="tab">Timetable by station</a></li>
            </ul><br/>
            <ul class="nav navbar-nav navbar-right">
                <li>Book your tickets</li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="jumbotron">
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="find_bus">
                <form method="post">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-xs-5">
                                <label for="station_from">Choose station (to)</label>
                                <select class="form-control" name="station_from" id="station_from">
                                    <option></option>
                                    <%for( Station station : stations ) {%>
                                        <option><%= station.getTitle() %></option>
                                    <%}%>
                                </select><br/>
                            </div>
                            <div class="col-xs-5">
                                <label for="station_to">Choose station (to)</label>
                                <select class="form-control" name="station_to" id="station_to">
                                    <option></option>
                                    <%for( Station station : stations ) {%>
                                        <option><%= station.getTitle() %></option>
                                    <%}%>
                                </select><br/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-5">
                                <label for="hour_from">Hour (from)</label>
                                <input type="time" class="form-control" name="hour_from" id="hour_from"><br/>
                            </div>
                            <div class="col-xs-5">
                                <label for="hour_to">Hour (to)</label>
                                <input type="time" class="form-control" name="hour_to" id="hour_to"><br/>
                            </div>
                            <div class="col-xs-5">
                                <button type="submit" class="btn btn-primary" name="get_bus" id="get_bus">Submit</button>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="content_result">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Bus number</th>
                                <th>Tickets left</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal fade" id="ticketBuyWindow" tabindex="-1" role="dialog" aria-labelledby="ticketBuyWindowLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="ticketBuyWindowLabel">Please provide your personal details:</h4><br/>
                            <strong>Notes:</strong><br/>
                            <ul>
                                <li>We won't be able to issue you a ticket if the bus departure time is less than <strong>10</strong> minutes.</li>
                                <li>For the chosen bus you can buy only <strong>one</strong> ticket for your name.</li>
                            </ul>
                        </div>
                        <div class="modal-body">
                            <form method="post">
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-xs-5">
                                            <label for="first_name">First name</label>
                                            <input type="text" class="form-control" name="first_name" id="first_name"><br/>
                                        </div>
                                        <div class="col-xs-5">
                                            <label for="surname">Surname</label>
                                            <input type="text" class="form-control" name="surname" id="surname"><br/>
                                        </div>
                                    </div>
                                    <input type="hidden" id="station_name" name="station_name" value="">
                                    <input type="hidden" id="bus_num" name="bus_num" value="">
                                    <button type="submit" class="btn btn-primary" id="get_ticket" name="get_ticket">Submit</button>
                                </div>
                            </form>
                        </div>
                        <br/>
                        <div id="modal_status_message" class="bg-danger"></div>
                    </div>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="timetable">
                <form method="post">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-xs-5">
                                <label for="station_title">Choose station</label>
                                <select class="form-control" name="station_title" id="station_title">
                                    <option></option>
                                    <%for( Station station : stations ) {%>
                                        <option><%= station.getTitle() %></option>
                                    <%}%>
                                </select><br/>
                             </div>
                        </div>
                        <button type="submit" class="btn btn-primary" name="get_timetable" id="get_timetable">Submit</button>
                    </div>
                </form>
                <br/>
                <div class="content_result">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Time</th>
                                <th>Bus number</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id="status_message" class="bg-danger"></div>
    </div>
</div>

<%@ include file="/WEB-INF/templates/footer.jsp" %>
