package org.tesolin.crossover.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.pdfbox.cos.COSInputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tesolin.crossover.dao.FlightDao;
import org.tesolin.crossover.dao.FlightTicketDao;
import org.tesolin.crossover.entity.Flight;
import org.tesolin.crossover.entity.FlightTicket;
import org.tesolin.crossover.entity.TypePerson;

@RestController
public class TicketService {
	
	@Autowired
	private FlightDao flightDao;
	
	@Autowired
	private FlightTicketDao flightTicketDao;
	
	@RequestMapping("/bookFlight")
	public Collection<FlightTicket> bookFlight(Long onewayFlight, Long roundTripFlight, Integer numAdult, Integer numYouth, Integer numChildren) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Flight flight1 = flightDao.getById(onewayFlight);
		
		Flight flight2 = null;
		if (roundTripFlight!=null) {
			flight2 = flightDao.getById(roundTripFlight);
		}
		
		Collection<FlightTicket> tickets = new ArrayList<FlightTicket>();
		
		FlightTicket flightTicket = new FlightTicket();
		flightTicket.setUser(authentication.getName());
		flightTicket.setFlight(flight1.getId());
		flightTicket.setPrice(flight1.getPrice().multiply(flight1.getPriceFactor()));
		flightTicket.setSeat("16A");
		flightTicket.setType(TypePerson.ADULT);
		tickets.add(flightTicketDao.save(flightTicket));
		
		if (flight2!=null){
			flightTicket = new FlightTicket();
			flightTicket.setUser(authentication.getName());
			flightTicket.setFlight(flight2.getId());
			flightTicket.setPrice(flight2.getPrice().multiply(flight2.getPriceFactor()));
			flightTicket.setSeat("16A");
			flightTicket.setType(TypePerson.ADULT);
			tickets.add(flightTicketDao.save(flightTicket));
		}
		
		return tickets;
	}
	
	@RequestMapping(path = "/printVoucher.pdf", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<?> print() {
		
		PDDocument doc = new PDDocument(); // creating instance of pdfDoc
		
        doc.addPage(new PDPage()); // adding page in pdf doc file
        
        PDStream ps=new PDStream(doc);
		
		try {
			COSInputStream is = ps.createInputStream();

			return ResponseEntity
		            .ok()
		            .contentLength(ps.getLength())
		            .contentType(
		                    MediaType.parseMediaType("application/octet-stream"))
		            .body(new InputStreamResource(is));
		} catch (IOException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
