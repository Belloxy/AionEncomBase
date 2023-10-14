/*

 *
 *  Encom is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Encom is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with Encom.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.dataholders;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.aionemu.gameserver.model.templates.towerofeternity.TowerOfEternityTemplate;
import com.aionemu.gameserver.model.towerofeternity.TowerOfEternityLocation;

import javolution.util.FastMap;

/**
 * Created by Wnkrz on 22/08/2017.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tower_of_eternity")
public class TowerOfEternityData
{
    @XmlElement(name = "tower_location")
    private List<TowerOfEternityTemplate> towerOfEternityTemplates;
	
    @XmlTransient
    private FastMap<Integer, TowerOfEternityLocation> towerOfEternity = new FastMap<Integer, TowerOfEternityLocation>();
	
    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (TowerOfEternityTemplate template : towerOfEternityTemplates) {
            towerOfEternity.put(template.getId(), new TowerOfEternityLocation(template));
        }
    }
	
    public int size() {
        return towerOfEternity.size();
    }
	
    public FastMap<Integer, TowerOfEternityLocation> getTowerOfEternityLocations() {
        return towerOfEternity;
    }
}