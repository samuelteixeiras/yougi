/* Yougi is a web application conceived to manage user groups or
 * communities focused on a certain domain of knowledge, whose members are
 * constantly sharing information and participating in social and educational
 * events. Copyright (C) 2011 Hildeberto Mendonça.
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
package org.cejug.yougi.entity;

import java.io.Serializable;
import java.util.TimeZone;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Due to the user-unfriendly Java Timezone implementation, this class was
 * created to represent friendly timezones to end-users.
 *
 * @author Hildeberto Mendonca - http://www.hildeberto.com
 */
@Entity
public class Timezone implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String sign;

    @Column(name = "offset_hour")
    private Integer offsetHour;

    @Column(name = "offset_minute")
    private Integer offsetMinute;

    private String label;

    @Column(name = "default_tz")
    private Boolean defaultTz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getOffsetHour() {
        return offsetHour;
    }

    public void setOffsetHour(Integer offsetHour) {
        this.offsetHour = offsetHour;
    }

    public Integer getOffsetMinute() {
        return offsetMinute;
    }

    public void setOffsetMinute(Integer offsetMinute) {
        this.offsetMinute = offsetMinute;
    }

    public String getOffset() {
        return sign + String.format("%02d", this.offsetHour) + ":" + String.format("%02d", this.offsetMinute);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getDefaultTz() {
        return this.defaultTz;
    }

    public void setDefaultTz(Boolean defaultTz) {
        this.defaultTz = defaultTz;
    }

    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone(this.id);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Timezone)) {
            return false;
        }
        Timezone other = (Timezone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.label;
    }
}