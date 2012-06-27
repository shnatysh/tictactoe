package com.bazaarvoice.shnatyshyn.tictactoe.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class TicTacToe implements EntryPoint {

    Label labels[] = new Label[9];

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // Create a grid
        Grid playFieldGrid = new Grid(3, 3);
        for (int i = 0; i < 9; i++) {
            final Label label = new Label("O");
            label.setWidth("100px");
            playFieldGrid.setWidget(i / 3, i % 3, label);
            label.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    label.setText(label.getText().equals("O") ? "X" : "O");
                }
            });
            label.setStyleName("playFieldCell");
            labels[i] = label;
        }
        playFieldGrid.setStyleName("playField");
        playFieldGrid.setSize("300px", "300px");
        playFieldGrid.setBorderWidth(1);
        RootPanel.get("playFieldID").add(playFieldGrid);

        final Button button = new Button("Click me");
        final Label label = new Label();

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (label.getText().equals("")) {
                    TicTacToeService.App.getInstance().getMessage("Hello, World!", new MyAsyncCallback(label));
                } else {
                    label.setText("");
                }
            }
        });

        // Assume that the host HTML has elements defined whose
        // IDs are "slot1", "slot2".  In a real app, you probably would not want
        // to hard-code IDs.  Instead, you could, for example, search for all
        // elements with a particular CSS class and replace them with widgets.
        //
        RootPanel.get("buttonXID").add(button);
        RootPanel.get("buttonOID").add(label);
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
