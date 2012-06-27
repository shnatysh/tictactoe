package com.bazaarvoice.shnatyshyn.tictactoe.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.bazaarvoice.shnatyshyn.tictactoe.client.TicTacToeService;

public class TicTacToeServiceImpl extends RemoteServiceServlet implements TicTacToeService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}