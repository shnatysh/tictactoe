package com.bazaarvoice.shnatyshyn.tictactoe.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class PlayFieldComponent extends Composite {
    interface TestUiBinder extends UiBinder<HTMLPanel, PlayFieldComponent> {
    }

    private static TestUiBinder ourUiBinder = GWT.create(TestUiBinder.class);

    private UserMoveListener userMoveListener;
    private NewGameListener newGameListener;
    private boolean playerPlayX = true;

    @UiField
    Label cell0;
    @UiField
    Label cell1;
    @UiField
    Label cell2;
    @UiField
    Label cell3;
    @UiField
    Label cell4;
    @UiField
    Label cell5;
    @UiField
    Label cell6;
    @UiField
    Label cell7;
    @UiField
    Label cell8;

    private Label[] cells;

    public PlayFieldComponent(UserMoveListener userMoveListener, NewGameListener newGameListener) {
        this.userMoveListener = userMoveListener;
        this.newGameListener = newGameListener;

        initWidget(ourUiBinder.createAndBindUi(this));
        cells = new Label[]{cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8};
    }

    @UiHandler({"cell0", "cell1", "cell2", "cell3", "cell4", "cell5", "cell6", "cell7", "cell8"})
    void cellClick(ClickEvent event) {
        Label label = (Label) event.getSource();
        if (!label.getText().equals("")) {
            return;
        }
        label.setText(playerPlayX ? "X" : "O");
        userMoveListener.onUserMove(playerPlayX, getMask(cells, "X"), getMask(cells, "O"));
    }

    @UiHandler("buttonPlayX")
    void playX(ClickEvent event) {
        playerPlayX = true;
        newGameListener.onNewGame(playerPlayX);
    }

    @UiHandler("buttonPlayO")
    void playO(ClickEvent event) {
        playerPlayX = false;
        newGameListener.onNewGame(playerPlayX);
    }

    public void resetFields() {
        for (Label label : cells) {
            label.setText("");
        }
    }

    public void doServerMove(int position) {
        cells[position].setText(!playerPlayX ? "X" : "O");
    }

    public void showEndDialogBox(String caption, String message) {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText(caption);

        VerticalPanel dialogContents = new VerticalPanel();
        dialogContents.setSpacing(4);
        dialogBox.setWidget(dialogContents);

        dialogContents.add(new Label(message));

        Button closeButton = new Button("OK", new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                newGameListener.onNewGame(playerPlayX);
            }
        });
        closeButton.setWidth("100px");
        dialogContents.add(closeButton);

        dialogBox.setGlassEnabled(true);
        dialogBox.setAnimationEnabled(true);
        dialogBox.center();
        dialogBox.show();
    }

    private boolean[] getMask(Label[] labels, String value) {
        boolean[] mask = new boolean[labels.length];
        for (int i = 0; i < labels.length; i++) {
            mask[i] = labels[i].getText().equals(value);
        }
        return mask;
    }

    public interface UserMoveListener {
        void onUserMove(boolean playerPlayX, boolean[] xMask, boolean[] oMask);
    }

    public interface NewGameListener {
        void onNewGame(boolean playerPlayX);
    }
}