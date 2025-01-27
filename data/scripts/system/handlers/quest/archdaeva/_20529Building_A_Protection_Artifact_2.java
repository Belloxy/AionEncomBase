/*
 * This file is part of Encom.
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
package quest.archdaeva;

import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;

/****/
/** Author (Encom)
/****/

public class _20529Building_A_Protection_Artifact_2 extends QuestHandler
{
    public static final int questId = 20529;
	private final static int[] npcs = {806079, 806298, 806299, 806300, 703325, 731716};
	private final static int[] DF6MissionLightFi75An = {244127}; //๊ทธ๋ฆผ์? ์์นธ ์ ํฌ๋ณ.
	private final static int[] DF6MissionLightWi75An = {244128}; //์๊ด์? ๊ฐ๋์ธ ๋ง๋ฒ๋ณ.
	
    public _20529Building_A_Protection_Artifact_2() {
        super(questId);
    }
	
    @Override
    public void register() {
        for (int npc: npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        } for (int mob: DF6MissionLightFi75An) {
		    qe.registerQuestNpc(mob).addOnKillEvent(questId);
		} for (int mob: DF6MissionLightWi75An) {
		    qe.registerQuestNpc(mob).addOnKillEvent(questId);
		}
		qe.registerOnLevelUp(questId);
		qe.registerOnEnterWorld(questId);
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerQuestNpc(244129).addOnKillEvent(questId); //์ ๋์ฐ์ค.
    }
	
	@Override
	public boolean onZoneMissionEndEvent(QuestEnv env) {
		return defaultOnZoneMissionEndEvent(env);
	}
	
	@Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 20528, true);
    }
	
	@Override
    public boolean onEnterWorldEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (player.getWorldId() == 301690000) { //์ค๋๊ด์ฐ.
			if (qs != null && qs.getStatus() == QuestStatus.START) {
                int var = qs.getQuestVars().getQuestVars();
                if (var == 4) {
                    changeQuestStep(env, 4, 5, false);
				}
			}
		}
        return false;
    }
	
    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        } if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 806079) { //Feregran.
                switch (env.getDialog()) {
                    case START_DIALOG: {
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        } else if (var == 10) {
							return sendQuestDialog(env, 6502);
						}
					} case SELECT_ACTION_1012: {
						if (var == 0) {
							return sendQuestDialog(env, 1012);
						}
					} case SELECT_ACTION_6503: {
						if (var == 10) {
							return sendQuestDialog(env, 6503);
						}
					} case STEP_TO_1: {
                        changeQuestStep(env, 0, 1, false);
						return closeDialogWindow(env);
					} case SET_REWARD: {
						qs.setStatus(QuestStatus.REWARD);
						updateQuestStatus(env);
						return closeDialogWindow(env);
					}
                }
            } if (targetId == 806300) { //๋ท์คํ.
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 1) {
							return sendQuestDialog(env, 1352);
						}
					} case SELECT_ACTION_1353: {
						if (var == 1) {
							return sendQuestDialog(env, 1353);
						}
					} case STEP_TO_2: {
						changeQuestStep(env, 1, 2, false);
						return closeDialogWindow(env);
					}
				}
			} if (targetId == 731716) { //๋ถ์์ง ๋ง์กฑ ํ๋ ์ฅ์น.
                switch (env.getDialog()) {
				    case USE_OBJECT: {
						if (var == 2) {
							return sendQuestDialog(env, 1693);
						} else if (var == 3) {
							return sendQuestDialog(env, 2034);
						}
					} case SELECT_ACTION_1694: {
						if (var == 2) {
							return sendQuestDialog(env, 1694);
						}
					} case SELECT_ACTION_2035: {
						if (var == 3) {
							return sendQuestDialog(env, 2035);
						}
					} case STEP_TO_4: {
						changeQuestStep(env, 3, 4, false);
						removeQuestItem(env, 182216090, 20); //๋ง์กฑ ํ๋ ์ฅ์น ๋ถํ.
						removeQuestItem(env, 182216091, 7); //๋ง์กฑ ํ๋ ๋?๋ ฅ ์ฅ์น.
						removeQuestItem(env, 182216092, 1); //๋ง์กฑ ํ๋ ๋?๋ ฅํต.
						WorldMapInstance AetherMine = InstanceService.getNextAvailableInstance(301690000); //์ค๋๊ด์ฐ.
						InstanceService.registerPlayerWithInstance(AetherMine, player);
						TeleportService2.teleportTo(player, 301690000, AetherMine.getInstanceId(), 323, 267, 259);
						return closeDialogWindow(env);
					} case CHECK_COLLECTED_ITEMS: {
						return checkQuestItems(env, 2, 3, false, 10000, 10001);
					} case FINISH_DIALOG: {
						if (var == 3) {
							defaultCloseDialog(env, 3, 3);
						} else if (var == 2) {
							defaultCloseDialog(env, 2, 2);
						}
					}
				}
            } if (targetId == 806298) { //๋?ฐ์?๋ณด๋ณด.
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 5) {
							return sendQuestDialog(env, 2717);
						}
					} case SELECT_ACTION_2718: {
						if (var == 5) {
							return sendQuestDialog(env, 2718);
						}
					} case STEP_TO_6: {
						changeQuestStep(env, 5, 6, false);
						return closeDialogWindow(env);
					}
				}
			} if (targetId == 703325) { //์ฒ์กฑ ์ฐจ์?์? ์์ฉ๋?์?ด.
                switch (env.getDialog()) {
                    case USE_OBJECT: {
                        if (var == 7) {
							playQuestMovie(env, 878);
                            changeQuestStep(env, 7, 8, false);
							ThreadPoolManager.getInstance().schedule(new Runnable() {
								@Override
								public void run() {
									QuestService.addNewSpawn(301690000, player.getInstanceId(), 244129, (float) 172.000, (float) 156.000, (float) 230.53053, (byte) 96); //์ ๋์ฐ์ค.
									QuestService.addNewSpawn(301690000, player.getInstanceId(), 806299, (float) 173.08958, (float) 153.30316, (float) 230.3820, (byte) 67); //์ฐ๋ฌ์ง ์์?๋ณด๋ณด.
								}
							}, 30000);
							return closeDialogWindow(env);
                        }
					}
                }
            } if (targetId == 806299) { //์ฐ๋ฌ์ง ์์?๋ณด๋ณด.
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 9) {
							return sendQuestDialog(env, 4080);
						}
					} case SELECT_ACTION_4081: {
					    if (var == 9) {
							return sendQuestDialog(env, 4081);
						}
					} case STEP_TO_10: {
						//๊น์? ์ ์? ๋น ์ง ์์?๋ณด๋ณด.
						giveQuestItem(env, 182216108, 1);
						changeQuestStep(env, 9, 10, false);
						TeleportService2.teleportTo(player, 220110000, 228.99796f, 209.33243f, 287.2919f, (byte) 69);
						return closeDialogWindow(env);
					}
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 806079) { //Feregran.
                if (env.getDialog() == QuestDialog.START_DIALOG) {
                    return sendQuestDialog(env, 10002);
				} else if (env.getDialog() == QuestDialog.SELECT_REWARD) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
            }
        }
        return false;
    }
	
	@Override
    public boolean onKillEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();
		if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 6) {
				int[] DF6MissionLightFi75An = {244127}; //์๊ด์? ๊ฐ๋์ธ ์ ํฌ๋ณ.
				int[] DF6MissionLightWi75An = {244128}; //์๊ด์? ๊ฐ๋์ธ ๋ง๋ฒ๋ณ.
				switch (targetId) {
					case 244127: { //์๊ด์? ๊ฐ๋์ธ ์ ํฌ๋ณ.
						return defaultOnKillEvent(env, DF6MissionLightFi75An, 0, 7, 1);
					} case 244128: { //์๊ด์? ๊ฐ๋์ธ ๋ง๋ฒ๋ณ.
						qs.setQuestVar(7);
					    updateQuestStatus(env);
						return defaultOnKillEvent(env, DF6MissionLightWi75An, 0, 3, 2);
					}
				}
            } else if (var == 8) {
				switch (targetId) {
                    case 244129: { //์ ๋์ฐ์ค.
						qs.setQuestVar(9);
						updateQuestStatus(env);
						return true;
					}
                }
			}
		}
		return false;
    }
}