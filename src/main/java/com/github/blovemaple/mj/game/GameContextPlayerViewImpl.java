package com.github.blovemaple.mj.game;

import static com.github.blovemaple.mj.action.standard.StandardActionType.*;

import java.util.List;

import com.github.blovemaple.mj.action.Action;
import com.github.blovemaple.mj.action.ActionAndLocation;
import com.github.blovemaple.mj.object.MahjongTablePlayerView;
import com.github.blovemaple.mj.object.PlayerInfo;
import com.github.blovemaple.mj.object.PlayerLocation;
import com.github.blovemaple.mj.object.Tile;
import com.github.blovemaple.mj.rule.GameStrategy;
import com.github.blovemaple.mj.rule.TimeLimitStrategy;

/**
 * {@link GameContextPlayerView}的实现。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class GameContextPlayerViewImpl implements GameContextPlayerView {

	private final GameContext gameContext;
	private final PlayerLocation myLocation;

	public GameContextPlayerViewImpl(GameContext gameContext, PlayerLocation myLocation) {
		this.gameContext = gameContext;
		this.myLocation = myLocation;
	}

	@Override
	public MahjongTablePlayerView getTableView() {
		return gameContext.getTable().getPlayerView(myLocation);
	}

	@Override
	public GameStrategy getGameStrategy() {
		return gameContext.getGameStrategy();
	}

	@Override
	public TimeLimitStrategy getTimeLimitStrategy() {
		return gameContext.getTimeLimitStrategy();
	}

	@Override
	public PlayerLocation getMyLocation() {
		return myLocation;
	}

	@Override
	public PlayerInfo getMyInfo() {
		return gameContext.getPlayerInfoByLocation(myLocation);
	}

	@Override
	public PlayerLocation getZhuangLocation() {
		return gameContext.getZhuangLocation();
	}

	@Override
	public ActionAndLocation getLastActionAndLocation() {
		return gameContext.getLastActionAndLocation();
	}

	@Override
	public Action getLastAction() {
		return gameContext.getLastAction();
	}

	@Override
	public PlayerLocation getLastActionLocation() {
		return gameContext.getLastActionLocation();
	}

	@Override
	public Tile getJustDrawedTile() {
		ActionAndLocation laa = getLastActionAndLocation();
		if (laa.getLocation() != myLocation) {
			return null;
		}
		if (!DRAW.matchBy(laa.getAction().getType())) {
			return null;
		}
		return getMyInfo().getLastDrawedTile();
	}

	@Override
	public List<ActionAndLocation> getDoneActions() {
		return gameContext.getDoneActions();
	}

	@Override
	public GameResult getGameResult() {
		return gameContext.getGameResult();
	}

}