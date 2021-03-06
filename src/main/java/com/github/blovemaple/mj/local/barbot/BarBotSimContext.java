package com.github.blovemaple.mj.local.barbot;

import java.util.ArrayList;
import java.util.List;

import com.github.blovemaple.mj.action.Action;
import com.github.blovemaple.mj.action.ActionAndLocation;
import com.github.blovemaple.mj.game.GameContext;
import com.github.blovemaple.mj.game.GameContextPlayerView;
import com.github.blovemaple.mj.game.GameContextPlayerViewImpl;
import com.github.blovemaple.mj.game.GameResult;
import com.github.blovemaple.mj.object.MahjongTable;
import com.github.blovemaple.mj.object.PlayerInfo;
import com.github.blovemaple.mj.object.PlayerLocation;
import com.github.blovemaple.mj.rule.GameStrategy;
import com.github.blovemaple.mj.rule.TimeLimitStrategy;
import com.github.blovemaple.mj.utils.MyUtils;

/**
 * 模拟选择时执行动作使用的GameContext。因为是机器人任务使用，所以只能从GameContext.PlayerView构建，
 * 只允许获取PlayerView能看到的一些信息。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class BarBotSimContext implements GameContext {
	private GameStrategy gameStrategy;
	private TimeLimitStrategy timeLimitStrategy;
	
	private GameContextPlayerView contextView;
	private ActionAndLocation lastAction;
	private PlayerInfo myInfo;

	public BarBotSimContext(GameContextPlayerView contextView, ActionAndLocation lastAction, PlayerInfo myInfo) {
		this.gameStrategy = contextView.getGameStrategy();
		this.timeLimitStrategy = contextView.getTimeLimitStrategy();
		this.contextView = contextView;
		this.lastAction = lastAction;
		this.myInfo = myInfo;
	}

	@Override
	public MahjongTable getTable() {
		throw new UnsupportedOperationException();
	}

	@Override
	public GameStrategy getGameStrategy() {
		return gameStrategy;
	}

	@Override
	public TimeLimitStrategy getTimeLimitStrategy() {
		return timeLimitStrategy;
	}

	@Override
	public PlayerInfo getPlayerInfoByLocation(PlayerLocation location) {
		if (location == contextView.getMyLocation())
			return myInfo;
		else
			throw new UnsupportedOperationException();
	}

	@Override
	public PlayerLocation getZhuangLocation() {
		return contextView.getZhuangLocation();
	}

	@Override
	public void setZhuangLocation(PlayerLocation zhuangLocation) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void actionDone(Action action, PlayerLocation location) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ActionAndLocation getLastActionAndLocation() {
		if (lastAction == null)
			return contextView.getLastActionAndLocation();
		else
			return lastAction;
	}

	@Override
	public Action getLastAction() {
		if (lastAction == null)
			return contextView.getLastAction();
		else
			return lastAction.getAction();
	}

	@Override
	public PlayerLocation getLastActionLocation() {
		if (lastAction == null)
			return contextView.getLastActionLocation();
		else
			return lastAction.getLocation();
	}

	private List<ActionAndLocation> doneActions;

	@Override
	public List<ActionAndLocation> getDoneActions() {
		if (doneActions == null) {
			if (lastAction == null)
				doneActions = contextView.getDoneActions();
			else
				doneActions = MyUtils.merged(ArrayList<ActionAndLocation>::new, contextView.getDoneActions(),
						lastAction);
		}
		return doneActions;
	}

	@Override
	public GameResult getGameResult() {
		return contextView.getGameResult();
	}

	@Override
	public void setGameResult(GameResult gameResult) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GameContextPlayerView getPlayerView(PlayerLocation location) {
		if (location == contextView.getMyLocation())
			return new GameContextPlayerViewImpl(this, location);
		else
			throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "[last action=" + getLastActionAndLocation() + ", alive tiles=" + myInfo.getAliveTiles()
				+ "]";
	}

}
