/*
 * =====================================================================================*
 * This file is part of Aion-Unique (Aion-Unique Home Software Development)             *
 * Aion-Unique Development is a closed Aion Project that use Old Aion Project Base      *
 * Like Aion-Lightning, Aion-Engine, Aion-Core, Aion-Extreme, Aion-NextGen, ArchSoft,   *
 * Aion-Ger, U3J, Encom And other Aion project, All Credit Content                      *
 * That they make is belong to them/Copyright is belong to them. And All new Content    *
 * that Aion-Unique make the copyright is belong to Aion-Unique                         *
 * You may have agreement with Aion-Unique Development, before use this Engine/Source   *
 * You have agree with all of Term of Services agreement with Aion-Unique Development   *
 * =====================================================================================*
 */
package quest.daevanion;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/****/
/** Author Ghostfur & Unknown (Aion-Unique)
/****/

public class _2990Making_The_Daevanion_Weapon extends QuestHandler
{
	private final static int questId = 2990;
	private final static int[] npc_ids = {204146};
	
	public _2990Making_The_Daevanion_Weapon() {
		super(questId);
	}
	
	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 2989, true);
	}
	
	@Override
	public void register() {
		qe.registerOnLevelUp(questId);
		qe.registerQuestNpc(204146).addOnQuestStart(questId);
		qe.registerQuestNpc(253696).addOnKillEvent(questId);
		qe.registerQuestNpc(253720).addOnKillEvent(questId);
		qe.registerQuestNpc(253721).addOnKillEvent(questId);
		qe.registerQuestNpc(253682).addOnKillEvent(questId);
		qe.registerQuestNpc(253684).addOnKillEvent(questId);
		qe.registerQuestNpc(253685).addOnKillEvent(questId);
		for (int npc_id: npc_ids) {
		    qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
		}
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 204146) {
				if (env.getDialog() == QuestDialog.START_DIALOG) {
					int plate = player.getEquipment().itemSetPartsEquipped(9);
					int chain = player.getEquipment().itemSetPartsEquipped(8);
					int leather = player.getEquipment().itemSetPartsEquipped(7);
					int cloth = player.getEquipment().itemSetPartsEquipped(6);
					int gunslinger = player.getEquipment().itemSetPartsEquipped(378);
					if (plate != 5 && chain != 5 && leather != 5 && cloth != 5 && gunslinger != 5)
						return sendQuestDialog(env, 4848);
					else
						return sendQuestDialog(env, 4762);
				} else
					return sendQuestStartDialog(env);
			}
		}
		if (qs == null)
			return false;
		int var = qs.getQuestVarById(0);
		int var1 = qs.getQuestVarById(1);
		if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 204146) {
				switch (env.getDialog()) {
					case START_DIALOG:
						if (var == 0)
							return sendQuestDialog(env, 1011);
						if (var == 1)
							return sendQuestDialog(env, 1352);
						if (var == 2 && var1 == 60)
							return sendQuestDialog(env, 1693);
						if (var == 3 && player.getInventory().getItemCountByItemId(186000040) > 0)
							return sendQuestDialog(env, 2034);
					case CHECK_COLLECTED_ITEMS:
						if (var == 0) {
							if (QuestService.collectItemCheck(env, true)) {
								qs.setQuestVarById(0, var + 1);
								updateQuestStatus(env);
								return sendQuestDialog(env, 10000);
							} else
								return sendQuestDialog(env, 10001);
						}
						break;
					case SELECT_ACTION_1352:
						if (var == 0)
							return sendQuestDialog(env, 1352);
					case SELECT_ACTION_2035:
					if (var == 3) {
						if (player.getCommonData().getDp() == 4000 && player.getInventory().getItemCountByItemId(186000040) > 0) {
							removeQuestItem(env, 186000040, 1);
							player.getCommonData().setDp(0);
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							return sendQuestDialog(env, 5);
						} else
							return sendQuestDialog(env, 2120);
					}
					break;
					case STEP_TO_2:
					if (var == 1) {
						qs.setQuestVarById(0, var + 1);
						updateQuestStatus(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
						return true;
					}
					break;
					case STEP_TO_3:
					if (var == 2) {
						qs.setQuestVar(3);
						updateQuestStatus(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
						return true;
					}
					break;
				}
			}
			return false;
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 204146) {
				return sendQuestEndDialog(env);
			}
			return false;
		}
		return false;
	  }
	
	@Override
    public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() != QuestStatus.START) {
			return false;
		}
		int var1 = qs.getQuestVarById(1);
		int var2 = qs.getQuestVarById(2);
		int var3 = qs.getQuestVarById(3);
		int targetId = env.getTargetId();
        if ((targetId == 253696 || //Smoking Ember.
	        targetId == 253720 || //Lava Hoverstone.
		    targetId == 253721 || //Lava Hoverstone.
		    targetId == 253682 || //Sulfur Oculazen.
		    targetId == 253684 || //Sulfur Oculazen.
		    targetId == 253685) && qs.getQuestVarById(0) == 2) {
			switch (targetId) {
				case 253696: //Smoking Ember.
				    if (var1 >= 0 && var1 < 30) {
					    ++var1;
					    qs.setQuestVarById(1, var1 + 1);
					    updateQuestStatus(env);
				    }
				break;
                case 253720: //Lava Hoverstone.
			    case 253721: //Lava Hoverstone.
                    if (var2 >= 0 && var2 < 30) {
					    ++var2;
					    qs.setQuestVarById(2, var2 + 3);
					    updateQuestStatus(env);
                    }
                break;
                case 253682: //Sulfur Oculazen.
			    case 253684: //Sulfur Oculazen.
			    case 253685: //Sulfur Oculazen.
                    if (var3 >= 0 && var3 < 30) {
					    ++var3;
					    qs.setQuestVarById(3, var3 + 7);
					    updateQuestStatus(env);
                    }
				break;
			}
		} if (qs.getQuestVarById(0) == 2 && var1 == 30 && var2 == 30 && var3 == 30) {
			qs.setQuestVarById(1, 60);
			updateQuestStatus(env);
			return true;
		}
		return false;
	}
}