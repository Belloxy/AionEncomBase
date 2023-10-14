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
package com.aionemu.gameserver.controllers.attack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aionemu.gameserver.configs.main.PvPConfig;

import javolution.util.FastMap;

/**
 * @author Sarynth
 */
public class KillList {

	private FastMap<Integer, List<Long>> killList;

	public KillList() {
		killList = new FastMap<Integer, List<Long>>();
	}

	/**
	 * @param winnerId
	 * @param victimId
	 * @return killsForVictimId
	 */
	public int getKillsFor(int victimId) {
		List<Long> killTimes = killList.get(victimId);

		if (killTimes == null) {
			return 0;
        }

		long now = System.currentTimeMillis();
		int killCount = 0;

		for (Iterator<Long> i = killTimes.iterator(); i.hasNext();) {
			if (now - i.next().longValue() > PvPConfig.CHAIN_KILL_TIME_RESTRICTION) {
				i.remove();
			} else {
				killCount++;
			}
		}
		return killCount;
	}

	/**
	 * @param victimId
	 */
	public void addKillFor(int victimId) {
		List<Long> killTimes = killList.get(victimId);
		if (killTimes == null) {
			killTimes = new ArrayList<Long>();
			killList.put(victimId, killTimes);
		}

		killTimes.add(System.currentTimeMillis());
	}
}