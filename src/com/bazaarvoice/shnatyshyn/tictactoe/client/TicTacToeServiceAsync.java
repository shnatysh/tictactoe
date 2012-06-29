package com.bazaarvoice.shnatyshyn.tictactoe.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TicTacToeServiceAsync {

    void nextMove(boolean[] xMap, boolean[] oMap, boolean playForX, AsyncCallback<Integer> async);
}
