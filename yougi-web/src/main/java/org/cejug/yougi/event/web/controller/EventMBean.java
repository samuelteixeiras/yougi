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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cejug.yougi.business.ApplicationPropertyBean;
import org.cejug.yougi.business.MessengerBean;
import org.cejug.yougi.business.UserAccountBean;
import org.cejug.yougi.entity.ApplicationProperty;
import org.cejug.yougi.entity.Properties;
import org.cejug.yougi.entity.UserAccount;
import org.cejug.yougi.event.business.AttendeeBean;
import org.cejug.yougi.event.business.EventBean;
import org.cejug.yougi.event.entity.Attendee;
import org.cejug.yougi.event.entity.Event;
import org.cejug.yougi.partnership.business.PartnerBean;
import org.cejug.yougi.partnership.entity.Partner;
import org.cejug.yougi.web.controller.LocationMBean;
import org.cejug.yougi.web.controller.UserProfileMBean;
import org.cejug.yougi.web.report.EventAttendeeCertificate;
import org.cejug.yougi.util.ResourceBundleHelper;
import org.cejug.yougi.util.WebTextUtils;
import org.primefaces.model.chart.PieChartModel;

/**
 * @author Hildeberto Mendonca - http://www.hildeberto.com
 */
@ManagedBean
@RequestScoped
public class EventMBean {

    static final Logger logger = Logger.getLogger(EventMBean.class.getName());

    @EJB
    private EventBean eventBean;

    @EJB
    private AttendeeBean attendeeBean;

    @EJB
    private PartnerBean partnerBean;

    @EJB
    private UserAccountBean userAccountBean;

    @EJB
    private MessengerBean messengerBean;

    @EJB
    private ApplicationPropertyBean applicationPropertyBean;

    @ManagedProperty(value = "#{param.id}")
    private String id;

    @ManagedProperty(value = "#{locationMBean}")
    private LocationMBean locationMBean;

    @ManagedProperty(value = "#{userProfileMBean}")
    private UserProfileMBean userProfileMBean;

    private Event event;

    private Attendee attendee;

    private List<Event> events;

    private List<Event> commingEvents;

    private List<Partner> venues;

    private Long numberPeopleAttending;

    private Long numberPeopleAttended;

    private PieChartModel pieChartModel;

    private String selectedVenue;

    public EventMBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocationMBean getLocationMBean() {
        return locationMBean;
    }

    public void setLocationMBean(LocationMBean locationMBean) {
        this.locationMBean = locationMBean;
    }

    public UserProfileMBean getUserProfileMBean() {
        return userProfileMBean;
    }

    public void setUserProfileMBean(UserProfileMBean userProfileMBean) {
        this.userProfileMBean = userProfileMBean;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Attendee getAttendee() {
        return attendee;
    }

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }

    public String getSelectedVenue() {
        return selectedVenue;
    }

    public void setSelectedVenue(String selectedVenue) {
        this.selectedVenue = selectedVenue;

        Partner venue = partnerBean.findPartner(selectedVenue);

        if (this.event.getAddress() == null && venue.getAddress() != null) {
            this.event.setAddress(venue.getAddress());
        }
        if (this.event.getCountry() == null && venue.getCountry() != null) {
            this.locationMBean.setSelectedCountry(venue.getCountry().getAcronym());
        }
        if (this.event.getProvince() == null && venue.getProvince() != null) {
            this.locationMBean.setSelectedProvince(venue.getProvince().getId());
        }
        if (this.event.getCity() == null && venue.getCity() != null) {
            this.locationMBean.setSelectedCity(venue.getCity().getId());
        }
    }

    /**
     * @return true if the event ocurred on the day before today.
     */
    public Boolean getHappened() {
        TimeZone tz = TimeZone.getTimeZone(userProfileMBean.getTimeZone());
        Calendar today = Calendar.getInstance(tz);

        if(this.event.getStartDate().before(today.getTime())) {
            return true;
        }

        return false;
    }

    /**
     * @return true if the member has the intention to attend the event. It does
     * not mean that s(he) actually attended it.
     */
    public Boolean getIsAttending() {
        if (attendee != null) {
            return true;
        }
        return false;
    }

    /**
     * @return true if the member actually attended the event.
     */
    public Boolean getAttended() {
        if(attendee != null) {
            return attendee.getAttended();
        }
        return Boolean.FALSE;
    }

    public List<Event> getEvents() {
        if (events == null) {
            events = eventBean.findEvents();
        }
        return events;
    }

    public List<Event> getCommingEvents() {
        if (commingEvents == null) {
            commingEvents = eventBean.findCommingEvents();
        }
        return commingEvents;
    }

    public List<Partner> getVenues() {
        if (venues == null) {
            venues = partnerBean.findPartners();
        }
        return venues;
    }

    public Long getNumberPeopleAttending() {
        return numberPeopleAttending;
    }

    public void setNumberPeopleAttending(Long numberPeopleAttending) {
        this.numberPeopleAttending = numberPeopleAttending;
    }

    public void setNumberPeopleAttended(Long numberPeopleAttended) {
        this.numberPeopleAttended = numberPeopleAttended;
    }

    public Long getNumberPeopleAttended() {
        return numberPeopleAttended;
    }

    public PieChartModel getAttendanceRateChartModel() {
        pieChartModel = new PieChartModel();
        pieChartModel.set("Registered", numberPeopleAttending);
        pieChartModel.set("Attended", numberPeopleAttended);
        return pieChartModel;
    }

    public String getFormattedEventDescription() {
        return WebTextUtils.convertLineBreakToHTMLParagraph(event.getDescription());
    }

    public String getFormattedEventDescription(String description) {
        return WebTextUtils.convertLineBreakToHTMLParagraph(description);
    }

    public String getFormattedStartDate() {
        return WebTextUtils.getFormattedDate(event.getStartDate());
    }

    public String getFormattedStartDate(Date startDate) {
        return WebTextUtils.getFormattedDate(startDate);
    }

    public String getFormattedEndDate() {
        return WebTextUtils.getFormattedDate(event.getEndDate());
    }

    public String getFormattedStartTime() {
        return WebTextUtils.getFormattedTime(event.getStartTime(), userProfileMBean.getTimeZone());
    }

    public String getFormattedStartTime(Date startTime) {
        return WebTextUtils.getFormattedTime(startTime, userProfileMBean.getTimeZone());
    }

    public String getFormattedEndTime() {
        return WebTextUtils.getFormattedTime(event.getEndTime(), userProfileMBean.getTimeZone());
    }

    public String getFormattedEndTime(Date endTime) {
        return WebTextUtils.getFormattedTime(endTime, userProfileMBean.getTimeZone());
    }

    public String getFormattedRegistrationDate() {
        if (this.attendee == null) {
            return "";
        }
        return WebTextUtils.getFormattedDate(this.attendee.getRegistrationDate());
    }

    @PostConstruct
    public void load() {
        if (id != null && !id.isEmpty()) {
            this.event = eventBean.findEvent(id);
            this.selectedVenue = this.event.getVenue().getId();

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String username = request.getRemoteUser();
            UserAccount person = userAccountBean.findUserAccountByUsername(username);
            this.attendee = attendeeBean.findAttendee(this.event, person);
            this.numberPeopleAttending = attendeeBean.findNumberPeopleAttending(this.event);
            this.numberPeopleAttended = attendeeBean.findNumberPeopleAttended(this.event);

            locationMBean.initialize();

            if (this.event.getCountry() != null) {
                locationMBean.setSelectedCountry(this.event.getCountry().getAcronym());
            } else {
                locationMBean.setSelectedCountry(null);
            }

            if (this.event.getProvince() != null) {
                locationMBean.setSelectedProvince(this.event.getProvince().getId());
            } else {
                locationMBean.setSelectedProvince(null);
            }

            if (this.event.getCity() != null) {
                locationMBean.setSelectedCity(this.event.getCity().getId());
            } else {
                locationMBean.setSelectedCity(null);
            }
        } else {
            this.event = new Event();
        }
    }

    public String confirmAttendance() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String username = request.getRemoteUser();
        UserAccount person = userAccountBean.findUserAccountByUsername(username);

        this.event = eventBean.findEvent(event.getId());

        Attendee newAttendee = new Attendee();
        newAttendee.setEvent(this.event);
        newAttendee.setAttendee(person);
        newAttendee.setRegistrationDate(Calendar.getInstance().getTime());
        attendeeBean.save(newAttendee);
        ResourceBundleHelper rb = new ResourceBundleHelper();
        messengerBean.sendConfirmationEventAttendance(newAttendee.getAttendee(),
                newAttendee.getEvent(),
                rb.getMessage("formatDate"),
                rb.getMessage("formatTime"),
                userProfileMBean.getTimeZone());
        return "events?faces-redirect=true";
    }

    public String cancelAttendance() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String username = request.getRemoteUser();
        UserAccount person = userAccountBean.findUserAccountByUsername(username);

        this.event = eventBean.findEvent(event.getId());

        Attendee existingAttendee = attendeeBean.findAttendee(event, person);
        attendeeBean.remove(existingAttendee.getId());

        return "events?faces-redirect=true";
    }

    public void getCertificate() {
        if(!this.attendee.getAttended()) {
            return;
        }

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline=filename=file.pdf");

        try {
            Document document = new Document(PageSize.A4.rotate());
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, output);
            document.open();

            ApplicationProperty fileRepositoryPath = applicationPropertyBean.findApplicationProperty(Properties.FILE_REPOSITORY_PATH);

            EventAttendeeCertificate eventAttendeeCertificate = new EventAttendeeCertificate(document);
            StringBuilder certificateTemplatePath = new StringBuilder();
            certificateTemplatePath.append(fileRepositoryPath.getPropertyValue());
            certificateTemplatePath.append("/");
            certificateTemplatePath.append(event.getCertificateTemplate());
            eventAttendeeCertificate.setCertificateTemplate(writer, certificateTemplatePath.toString());

            this.attendee.generateCertificateData();
            this.attendeeBean.save(this.attendee);
            eventAttendeeCertificate.generateCertificate(this.attendee);

            document.close();

            response.getOutputStream().write(output.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            context.responseComplete();
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, ioe.getMessage(), ioe);
        } catch (DocumentException de) {
            logger.log(Level.SEVERE, de.getMessage(), de);
        }
    }

    public String save() {
        Partner venue = partnerBean.findPartner(selectedVenue);
        this.event.setVenue(venue);
        this.event.setCountry(this.locationMBean.getCountry());
        this.event.setProvince(this.locationMBean.getProvince());
        this.event.setCity(this.locationMBean.getCity());

        eventBean.save(this.event);
        return "events?faces-redirect=true";
    }

    public String remove() {
        eventBean.remove(this.event.getId());
        return "events?faces-redirect=true";
    }
}