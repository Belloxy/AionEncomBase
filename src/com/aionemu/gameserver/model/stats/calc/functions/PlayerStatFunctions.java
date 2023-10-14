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
package com.aionemu.gameserver.model.stats.calc.functions;

import java.util.ArrayList;
import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.container.StatEnum;

public class PlayerStatFunctions
{
	private static final List<IStatFunction> FUNCTIONS = new ArrayList<IStatFunction>();
	
	static {
		FUNCTIONS.add(new AttackSpeedFunction());
		FUNCTIONS.add(new PhysicalAttackFunction());
		FUNCTIONS.add(new MagicalAttackFunction());
		FUNCTIONS.add(new BoostCastingTimeFunction());
		FUNCTIONS.add(new PvPAttackRatioFunction());
		FUNCTIONS.add(new PvPDefendRatioFunction());
		FUNCTIONS.add(new PvPPhysicalAttackRatioFunction());
		FUNCTIONS.add(new PvPMagicalAttackRatioFunction());
		FUNCTIONS.add(new PvPPhysicalDefendRatioFunction());
		FUNCTIONS.add(new PvPMagicalDefendRatioFunction());
		FUNCTIONS.add(new PDefFunction());
		FUNCTIONS.add(new MaxHpFunction());
		FUNCTIONS.add(new MaxMpFunction());
		FUNCTIONS.add(new AgilityModifierFunction(StatEnum.BLOCK, 0.25f));
		FUNCTIONS.add(new AgilityModifierFunction(StatEnum.PARRY, 0.25f));
		FUNCTIONS.add(new AgilityModifierFunction(StatEnum.EVASION, 0.3f));
	}
	
	public static final List<IStatFunction> getFunctions() {
		return FUNCTIONS;
	}
	
	public static final void addPredefinedStatFunctions(Player player) {
        player.getGameStats().addEffectOnly(null, FUNCTIONS);
    }
}