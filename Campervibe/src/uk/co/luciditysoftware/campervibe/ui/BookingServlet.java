package uk.co.luciditysoftware.campervibe.ui;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.co.luciditysoftware.campervibe.domain.entities.Booking;
import uk.co.luciditysoftware.campervibe.domain.entities.Depot;
import uk.co.luciditysoftware.campervibe.domain.entities.Vehicle;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;

@WebServlet(name = "bookingServlet", urlPatterns = { "/booking" }, loadOnStartup = 1)
@MultipartConfig(fileSizeThreshold = 5_242_880, // 5MB
maxFileSize = 20_971_520L, // 20MB
maxRequestSize = 41_943_040L // 40MB
)
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Map<UUID, Booking> bookingDatabase = new LinkedHashMap<>();
    private Map<UUID, Vehicle> vehicleDatabase = new LinkedHashMap<>();
    
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookingServlet() {
		super();
		
		Depot depot = new Depot();
		depot.setId(UUID.randomUUID());
		depot.setName("Manchester");
		depot.setCode("MAN01");
		
		Vehicle vehicle1 = new Vehicle(){{
			setId(UUID.randomUUID());
			setName("Archie");
			setMake("Volkswagen");
			setModel("T1");
			setHomeDepot(depot);
		}};
		
		Vehicle vehicle2 = new Vehicle(){{
			setId(UUID.randomUUID());
			setName("Barry");
			setMake("Volkswagen");
			setModel("T2");
			setHomeDepot(depot);
		}};
		
		vehicleDatabase.put(vehicle1.getId(), vehicle1);
		vehicleDatabase.put(vehicle2.getId(), vehicle2);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");

		if (action == null)
			action = "list";

		switch (action) {
		case "make":
			this.showMakeBookingForm(request, response);
			break;
		case "view":
			// this.viewTicket(request, response);
			break;
		case "download":
			// this.downloadAttachment(request, response);
			break;
		case "list":
		default:
			this.listBookings(request, response);
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
        if(action == null)
            action = "list";
        
        switch(action)
        {
            case "create":
                this.makeBooking(request, response);
                break;
            case "list":
            default:
                response.sendRedirect("tickets");
                break;
        }
	}

	private void listBookings(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setAttribute("bookingDatabase", this.bookingDatabase);
		request.getRequestDispatcher("/WEB-INF/jsp/view/listBookings.jsp").forward(request, response);
	}
	
	private void showMakeBookingForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setAttribute("vehicleDatabase", this.vehicleDatabase);
		request.getRequestDispatcher("/WEB-INF/jsp/view/makeBookingForm.jsp").forward(request, response);
	}
	
	private void makeBooking(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/view/listBookings.jsp").forward(request, response);
	}
}
