package com.bazaarvoice.shnatyshyn.tictactoe.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;


public class TicTacToe implements EntryPoint {
    private static int[][] winningCombinations = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    Label labels[] = new Label[9];
    boolean playerPlayX = true;

    public void onModuleLoad() {
        Grid playFieldGrid = new Grid(3, 3);
        for (int i = 0; i < 9; i++) {
            final Label label = new Label("");
            label.setWidth("100px");
            playFieldGrid.setWidget(i / 3, i % 3, label);
            label.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    userMove(label);
                }
            });
            label.setStyleName("playFieldCell");
            labels[i] = label;
        }
        playFieldGrid.setStyleName("playField");
        playFieldGrid.setSize("300px", "300px");
        playFieldGrid.setBorderWidth(1);
        RootPanel.get("playFieldID").add(playFieldGrid);

        Button buttonPlayX = new Button("Restart as X");
        Button buttonPlayO = new Button("Restart as O");

        buttonPlayX.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                playerPlayX = true;
                resetField();
            }
        });

        buttonPlayO.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                playerPlayX = false;
                resetField();
            }
        });

        RootPanel.get("buttonXID").add(buttonPlayX);
        RootPanel.get("buttonOID").add(buttonPlayO);
    }

    private void userMove(Label label) {
        if (!label.getText().equals("")) {
            return;
        }
        label.setText(playerPlayX ? "X" : "O");

        if (!checkForGameEnd()) {
            serverMove();
        }
    }

    private void serverMove() {
        TicTacToeService.App.getInstance().nextMove(getMask(labels, "X"), getMask(labels, "O"), !playerPlayX, new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                showDialogBox("Error", "Sorry, some error occurred.", new ResetOnClick());
            }

            @Override
            public void onSuccess(Integer result) {
                if (result >= 0) {
                    labels[result].setText(!playerPlayX ? "X" : "O");
                }
                checkForGameEnd();
            }
        });
    }

    private boolean[] getMask(Label[] labels, String value) {
        boolean[] mask = new boolean[labels.length];
        for (int i = 0; i < labels.length; i++) {
            mask[i] = labels[i].getText().equals(value);
        }
        return mask;
    }

    private void resetField() {
        for (int i = 0; i < 9; i++) {
            labels[i].setText("");
        }
        if (!playerPlayX) {
            serverMove();
        }
    }

    private boolean checkForGameEnd() {
        for (int[] combination : winningCombinations) {
            String userChar = playerPlayX ? "X" : "O";
            String serverChar = !playerPlayX ? "X" : "O";
            if (labels[combination[0]].getText().equals(userChar) && labels[combination[1]].getText().equals(userChar)
                    && labels[combination[2]].getText().equals(userChar)) {
                showDialogBox("Game end.", "You Won!", new ResetOnClick());
                return true;
            } else if (labels[combination[0]].getText().equals(serverChar) && labels[combination[1]].getText().equals(serverChar)
                    && labels[combination[2]].getText().equals(serverChar)) {
                showDialogBox("Game end.", "You Loose!", new ResetOnClick());
                return true;
            }
        }

        boolean end = true;
        for (int i = 0; i < 9; i++) {
            if (labels[i].getText().equals("")) {
                end = false;
                break;
            }
        }
        if (end) {
            showDialogBox("Game end.", "Draw!", new ResetOnClick());
            return true;
        }

        return false;
    }

    private void showDialogBox(String caption, String message, final ClickHandler onClose) {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText(caption);

        VerticalPanel dialogContents = new VerticalPanel();
        dialogContents.setSpacing(4);
        dialogBox.setWidget(dialogContents);

        dialogContents.add(new Label(message));

        Button closeButton = new Button("OK", new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                onClose.onClick(event);
            }
        });
        closeButton.setWidth("100px");
        dialogContents.add(closeButton);

        dialogBox.setGlassEnabled(true);
        dialogBox.setAnimationEnabled(true);
        dialogBox.center();
        dialogBox.show();
    }

    private class ResetOnClick implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            resetField();
        }
    }
}
