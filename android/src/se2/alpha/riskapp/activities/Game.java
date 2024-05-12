package se2.alpha.riskapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import se2.alpha.riskapp.RiskGame;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.service.LobbyService;

import javax.inject.Inject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class Game extends AndroidApplication {
    @Inject
    BackendService backendService;
    @Inject
    GameService gameService;
    @Inject
    LobbyService lobbyService;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
//        backendService.checkBackendReachability(isReachable -> {
//            if (!isReachable) {
//                showToast("Backend is unreachable");
//                Log.d(TAG, "Backend is unreachable");
//
//            }else {
//                showToast("Backend is reachable!");
//                Log.d(TAG, "Backend is reachable!");
//            }
//
//
//
//        });
//        initialize(new RiskGame(), config);
//    }

        try {
            ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);

            backendService.checkBackendReachability(isReachable -> {
                if (!isReachable) {
                    showToast("Backend is unreachable");
                    Log.d(TAG, "Backend is unreachable");
                } else {
                    showToast("Backend is reachable!");
                    Log.d(TAG, "Backend is reachable!");
                }
            });

            initialize(new RiskGame(), config);
        } catch (Exception e) {
            Log.e(TAG, "Error during onCreate in Game activity", e);
            showToast("Error initializing the game: " + e.getMessage());
        }
    }


    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Game.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
