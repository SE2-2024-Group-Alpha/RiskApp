package se2.alpha.riskapp.data;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.service.LobbyService;
import se2.alpha.riskapp.service.SecurePreferencesService;

import javax.inject.Singleton;

@Module
public class RiskAppModule {

    private final Context applicationContext;

    public RiskAppModule(Context context) {
        this.applicationContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return applicationContext;
    }

    @Provides
    @Singleton
    public SecurePreferencesService provideSecurePreferencesService(Context context) {
        return new SecurePreferencesService(context);
    }

    @Provides
    @Singleton
    public BackendService provideBackendService(Context context, SecurePreferencesService securePreferences, GameService gameService) {
        return new BackendService(context, securePreferences, gameService);
    }

    @Provides
    @Singleton
    public GameService provideGameService(Context context, SecurePreferencesService securePreferences){
        return new GameService(context, securePreferences);
    }

    @Provides
    @Singleton
    public LobbyService provideLobbyService(Context context, GameService gameService, BackendService backendService){
        return new LobbyService(context, gameService, backendService);
    }
}
