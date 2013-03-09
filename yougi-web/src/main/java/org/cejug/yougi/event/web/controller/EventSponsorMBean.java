/* Yougi is a web application conceived to manage user groups or
 * communities focused on a certain domain of knowledge, whose members are
 * constantly sharing information and participating in social and educational
 * events. Copyright (C) 2011 Ceara Java User Group - CEJUG.
 *
 * This application is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This application is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * There is a full copy of the GNU Lesser General Public License along with
 * this library. Look for the file license.txt at the root level. If you do not
 * find it, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA.
 * */
package org.cejug.yougi.event.web.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.cejug.yougi.event.business.EventBean;
import org.cejug.yougi.event.business.EventSponsorBean;
import org.cejug.yougi.event.entity.Event;
import org.cejug.yougi.event.entity.EventSponsor;
import org.cejug.yougi.partnership.business.PartnerBean;
import org.cejug.yougi.partnership.entity.Partner;

/**
 * @author Hildeberto Mendonca  - http://www.hildeberto.com
 */
@ManagedBean
@RequestScoped
public class EventSponsorMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private EventSponsorBean eventSponsorBean;

    @EJB
    private EventBean eventBean;

    @EJB
    private PartnerBean partnerBean;

    @ManagedProperty(value = "#{param.id}")
    private String id;

    @ManagedProperty(value = "#{param.eventId}")
    private String eventId;

    private Event event;

    private EventSponsor eventSponsor;

    private List<EventSponsor> eventSponsors;

    private List<Event> events;

    private String selectedEvent;

    private List<Partner> partners;

    private String selectedSponsor;

    public EventSponsorMBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventSponsor getEventSponsor() {
        return eventSponsor;
    }

    public void setEventSponsor(EventSponsor eventSponsor) {
        this.eventSponsor = eventSponsor;
    }

    public List<EventSponsor> getEventSponsors() {
        if (eventSponsors == null) {
            this.eventSponsors = eventSponsorBean.findEventSponsors(this.event);
        }
        return this.eventSponsors;
    }

    public BigDecimal getSumAmounts() {
        BigDecimal sum = new BigDecimal(0);
        List<EventSponsor> es = getEventSponsors();
        for(EventSponsor sponsor: es) {
            sum = sum.add(sponsor.getAmount());
        }
        return sum;
    }

    public String getSelectedEvent() {
        return this.selectedEvent;
    }

    public void setSelectedEvent(String selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public List<Event> getEvents() {
        if (this.events == null) {
            this.events = eventBean.findEvents();
        }
        return this.events;
    }

    public String getSelectedSponsor() {
        return this.selectedSponsor;
    }

    public void setSelectedSponsor(String selectedSponsor) {
        this.selectedSponsor = selectedSponsor;
    }

    public List<Partner> getPartners() {
        if (this.partners == null) {
            this.partners = partnerBean.findPartners();
        }
        return this.partners;
    }

    @PostConstruct
    public void load() {
        if (this.eventId != null && !this.eventId.isEmpty()) {
            this.event = eventBean.findEvent(eventId);
            this.selectedEvent = this.event.getId();
        }

        if (this.id != null && !this.id.isEmpty()) {
            this.eventSponsor = eventSponsorBean.findEventSponsor(id);
            this.selectedEvent = this.eventSponsor.getEvent().getId();
            this.selectedSponsor = this.eventSponsor.getPartner().getId();
        } else {
            this.eventSponsor = new EventSponsor();
        }
    }

    public String save() {
        Event evt = eventBean.findEvent(selectedEvent);
        this.eventSponsor.setEvent(evt);

        Partner spon = partnerBean.findPartner(selectedSponsor);
        this.eventSponsor.setPartner(spon);

        eventSponsorBean.save(this.eventSponsor);
        return "sponsors?faces-redirect=true&eventId=" + evt.getId();
    }

    public String remove() {
        eventSponsorBean.remove(this.eventSponsor.getId());
        return "sponsors?faces-redirect=true&eventId=" + this.event.getId();
    }
}