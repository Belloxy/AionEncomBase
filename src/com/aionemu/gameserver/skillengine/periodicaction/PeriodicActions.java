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
package com.aionemu.gameserver.skillengine.periodicaction;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

 /**
 * @Rework MATTY (ADev.Team)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PeriodicActions", propOrder = "periodicActions")
public class PeriodicActions {

	@XmlElements({ @XmlElement(name = "hpuse", type = HpUsePeriodicAction.class), @XmlElement(name = "mpuse", type = MpUsePeriodicAction.class), @XmlElement(name = "dpuse", type = DpUsePeriodicAction.class) })
	protected List<PeriodicAction> periodicActions;
	@XmlAttribute(name = "checktime")
	protected int checktime;

	public List<PeriodicAction> getPeriodicActions() {
		return periodicActions;
	}

	public int getChecktime() {
		return checktime;
	}
}