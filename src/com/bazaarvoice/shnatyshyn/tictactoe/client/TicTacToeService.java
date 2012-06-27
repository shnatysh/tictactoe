package com.bazaarvoice.shnatyshyn.tictactoe.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("tictactoeService")
public interface TicTacToeService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    int nextMove(boolean[] xMap, boolean[] oMap, boolean playForX);
    /**
     * Utility/Convenience class.
     * Use TicTacToeService.App.getInstance() to access static instance of tictactoeServiceAsync
     */
    public static class App {
        private static TicTacToeServiceAsync ourInstance = GWT.create(TicTacToeService.class);

        public static synchronized TicTacToeServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
