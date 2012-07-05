package com.bazaarvoice.shnatyshyn.tictactoe.client.components.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;


public interface PlayFieldResources extends ClientBundle {
    public static final PlayFieldResources INSTANCE =  GWT.create(PlayFieldResources.class);

    @Source("PlayField.css")
    public PlayFieldCssResource css();

    @Source("PlayField.txt")
    public TextResource manual();
}
