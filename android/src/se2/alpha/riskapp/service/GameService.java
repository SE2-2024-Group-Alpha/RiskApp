package se2.alpha.riskapp.service;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import lombok.Getter;
import lombok.Setter;
import se2.alpha.riskapp.RiskGame;
import se2.alpha.riskapp.dol.Player;

import javax.inject.Inject;
import java.util.*;


@Getter
public class GameService {
    @Setter
    private UUID sessionId = null;
    @Setter
    private RiskGame riskGame = RiskGame.getInstance();

    private final SecurePreferencesService securePreferencesService;

    private final MutableLiveData<Map<String, Boolean>> userStates = new MutableLiveData<>(new HashMap<>());
    private MutableLiveData<Boolean> gameStarted = new MutableLiveData<>();
    private MutableLiveData<List<Player>> players = new MutableLiveData<>();
    private MutableLiveData<Player> activePlayer = new MutableLiveData<>();

    private String playerName = "";

    @Inject
    public GameService(Context context, SecurePreferencesService securePreferencesService) {
        this.securePreferencesService = securePreferencesService;
    }

    public void updateUsers(Map<String, Boolean> newUserStates){
        userStates.postValue(newUserStates);
    }

    public void updatePlayers(List<Player> newPlayers){
        players.postValue(newPlayers);
    }

    public void setActivePlayer(Player newActivePlayer){
        activePlayer.postValue(newActivePlayer);
        checkIfActivePlayer();
    }

    public void checkIfActivePlayer(){
        if (activePlayer.getValue() != null){
            riskGame.setActive(activePlayer.getValue().getName().equals(playerName));
        }
    }

    public RiskGame startGame(){
        playerName = securePreferencesService.getPlayerName();
        riskGame = RiskGame.getInstance();
        riskGame.setPlayers(players.getValue());
        riskGame.setPlayerName(playerName);
        checkIfActivePlayer();
        return riskGame;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public UUID getSessionId() {
        return sessionId;
    }
}
