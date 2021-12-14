package com.ng.xerathlib.scene;

public class TransformExtension {
    public RunModel runModel;

    public RunVariant runVariant;

    public TransformExtension() {
        this.runVariant = RunVariant.DEBUG;
        this.runModel = RunModel.ANALYSE;
    }

    @Override
    public String toString() {
        return "NoahTransformExtension{" +
                "runModel=" + runModel +
                ", runVariant=" + runVariant +
                '}';
    }
}
