package com.bazaarvoice.shnatyshyn.tictactoe.client;

import com.bazaarvoice.shnatyshyn.tictactoe.client.components.PlayFieldComponent;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TicTacToe implements EntryPoint {
    private static int[][] WINNING_COMBINATIONS = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private PlayFieldComponent playFieldComponent;

    public void onModuleLoad() {
        playFieldComponent = new PlayFieldComponent(new UserMoveListerImpl(), new NewGameListenerImpl());
        RootPanel.get().add(playFieldComponent);
    }

    private class UserMoveListerImpl implements PlayFieldComponent.UserMoveListener {
        @Override
        public void onUserMove(boolean playerPlayX, boolean[] xMask, boolean[] oMask) {
            if (!checkForGameEnd(playerPlayX, xMask, oMask)) {
                serverMove(playerPlayX, xMask, oMask);
            }
        }
    }

    private class NewGameListenerImpl implements PlayFieldComponent.NewGameListener {
        @Override
        public void onNewGame(boolean playerPlayX) {
            playFieldComponent.resetFields();
            if (!playerPlayX) {
                serverMove(playerPlayX, new boolean[9], new  boolean[9]);
            }
        }
    }

    private void serverMove(final boolean playerPlayX, final boolean[] xMask, final boolean[] oMask) {
        TicTacToeService.App.getInstance().nextMove(xMask, oMask, !playerPlayX, new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                playFieldComponent.showEndDialogBox("Error", "Sorry, some error occurred.");
            }

            @Override
            public void onSuccess(Integer result) {
                if (result >= 0) {
                    playFieldComponent.doServerMove(result);
                    if (playerPlayX) {
                        oMask[result] = true;
                        checkForGameEnd(playerPlayX, xMask, oMask);
                    } else {
                        xMask[result] = true;
                        checkForGameEnd(playerPlayX, xMask, oMask);
                    }
                } else {
                    onFailure(null);
                }
            }
        });
    }

    private boolean checkForGameEnd(boolean playerPlayX, boolean[] xMask, boolean[] oMask) {
        for (int[] combination : WINNING_COMBINATIONS) {
            if (xMask[combination[0]] && xMask[combination[1]] && xMask[combination[2]]) {
                playFieldComponent.showEndDialogBox("Game end.", playerPlayX ? "You Won!" : "You Loose!");
                return true;
            } else if (oMask[combination[0]] && oMask[combination[1]] && oMask[combination[2]]) {
                playFieldComponent.showEndDialogBox("Game end.", !playerPlayX ? "You Won!" : "You Loose!");
                return true;
            }
        }

        boolean end = true;
        for (int i = 0; i < 9; i++) {
            if (!xMask[i] && !oMask[i]) {
                end = false;
                break;
            }
        }
        if (end) {
            playFieldComponent.showEndDialogBox("Game end.", "Draw!");
            return true;
        }

        return false;
    }
}
