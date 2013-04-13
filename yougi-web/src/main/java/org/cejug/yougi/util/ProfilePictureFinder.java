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
package org.cejug.yougi.util;

import de.bripkens.gravatar.Gravatar;
import de.bripkens.gravatar.Rating;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author Hildeberto Mendonça
 */
@ManagedBean
@RequestScoped
public class ProfilePictureFinder {

    public String getPictureFromEmail(String email) {
        return this.validateUrl(new Gravatar()
                .setSize(85)
                .setHttps(true)
                .setRating(Rating.PARENTAL_GUIDANCE_SUGGESTED)
                .getUrl(email));
    }
    
    private String validateUrl(String url) {
        String urlErro = "https://secure.gravatar.com/avatar/9dd4e461268c8034f5c8564e155c67a6.jpg?s=85&r=pg&";
        return url.equalsIgnoreCase(urlErro) ? getDefaultAvatar() : url; 
    }
    
    private String getDefaultAvatar() {
        return "/images/logo.png";
    }
}
