<%@ page import="java.util.List" %>
<%@ page import="org.twin_persona.busservice.services.EmployeeManager" %>
<%@ page import="org.twin_persona.busservice.db.models.Bus" %>
<%@ include file="/WEB-INF/templates/header.jsp" %>

<%
    EmployeeManager employeeManager = new EmployeeManager();
    List<Bus> buses = employeeManager.getAllBuses();
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
                <li role="presentation" class="active"><a href="#new_station" aria-controls="new_station" role="tab" data-toggle="tab">Add new station</a></li>
                <li role="presentation"><a href="#new_bus" aria-controls="new_bus" role="tab" data-toggle="tab">Add new bus</a></li>
                <li role="presentation"><a href="#all_passengers" aria-controls="all_passengers" role="tab" data-toggle="tab">Show all passengers by bus</a></li>
                <li role="presentation"><a href="#all_buses" aria-controls="all_buses" role="tab" data-toggle="tab">Show all buses</a></li>
            </ul><br/>
            <ul class="nav navbar-nav navbar-right">
                <li>Manage tickets booking system</li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="jumbotron">
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="new_station">
                <form id="add_station_form">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-xs-5">
                                <label for="station_title">Station name</label>
                                <input type="text" class="form-control" name="station_title" id="station_title"><br/>
                            </div>
                        </div>
                            <button type="submit" class="btn btn-primary" name="add_station" id="add_station">Submit</button>
                    </div>
                </form>
            </div>
            <div role="tabpanel" class="tab-pane" id="new_bus">
                <form method="post">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-xs-5">
                                <label for="bus_number">Bus number</label>
                                <input type="number" class="form-control" name="bus_number" id="bus_number"><br/>
                            </div>
                            <div class="col-xs-5">
                                <label for="seats_number">Number of seats</label>
                                <input type="number" class="form-control" name="seats_number" id="seats_number"><br/>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary" name="add_bus" id="add_bus">Submit</button>
                    </div>
                </form>
            </div>
            <div role="tabpanel" class="tab-pane" id="all_passengers">
                <form method="post">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-xs-5">
                                <label for="bus_num_select">Choose bus number</label>
                                <select class="form-control" name="bus_num_select" id="bus_num_select">
                                        <option></option>
                                    <%for( Bus bus : buses ) {%>
                                        <option><%= bus.getNumber() %></option>
                                    <%}%>
                                </select><br/>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary" name="get_passengers_bus" id="get_passengers_bus">Submit</button>
                    </div>
                </form>
                <div class="content_result">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Passengers</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="all_buses">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Bus number</th>
                            <th>Amount of seats</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for( Bus bus : buses ) {%>
                            <tr>
                                <td><%= bus.getNumber() %></td>
                                <td><%= bus.getSeatsNum() %></td>
                            </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
        <div id="status_message" class="bg-danger"></div>
    </div>
</div>

<%@ include file="/WEB-INF/templates/footer.jsp" %>
