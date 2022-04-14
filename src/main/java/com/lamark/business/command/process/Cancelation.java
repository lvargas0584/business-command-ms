package com.lamark.business.command.process;

import com.lamark.business.command.core.pattern.dto.DataProcess;

public abstract class Cancelation {
    public abstract  void process(DataProcess dataProcess)  ;
}
