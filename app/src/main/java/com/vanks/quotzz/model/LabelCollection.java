package com.vanks.quotzz.model;

import java.util.ArrayList;
import java.util.Arrays;

public class LabelCollection {
    public ArrayList<Label> labels = new ArrayList<Label>(Arrays.asList(
            new Label("Top World Headlines", 0),
            new Label("Local News", 1),
            new Label("Business", 2),
            new Label("Technology", 3)
    ));
}

