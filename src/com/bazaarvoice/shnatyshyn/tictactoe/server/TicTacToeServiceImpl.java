package com.bazaarvoice.shnatyshyn.tictactoe.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.bazaarvoice.shnatyshyn.tictactoe.client.TicTacToeService;

import java.util.Random;

public class TicTacToeServiceImpl extends RemoteServiceServlet implements TicTacToeService {
    private Random random = new Random();

    @Override
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    @Override
    public int nextMove(boolean[] xMap, boolean[] oMap, boolean playForX) {
        // very sophisticated backend logic ))
        for (int i = 0; i < 1000; i++) {
            int result = Math.abs(random.nextInt()) % 9;
            if (!xMap[result] && !oMap[result]) {
                return result;
            }
        }
        return -1;
    }
}