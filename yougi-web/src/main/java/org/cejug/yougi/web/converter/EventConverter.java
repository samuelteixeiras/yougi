/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cejug.yougi.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.cejug.yougi.event.entity.Event;

/**
 * @author Hildeberto Mendonca - http://www.hildeberto.com
 */
@FacesConverter(value="eventConverter")
public class EventConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.isEmpty()) {
            return null;
        }
        return new Event(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (!(value instanceof Event)) {
            return null;
        }
        Event event = (Event) value;
        return event.getId();
    }
}