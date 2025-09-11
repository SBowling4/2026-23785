package org.firstinspires.ftc.teamcode.util;

public class IndexerSlot {
    private Artifact artifact;

    public IndexerSlot(Artifact artifact) {
        this.artifact = artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public enum IndexerPositons {
        INTAKE,
        LEFT,
        SHOOTER,
        RIGHT
    }
}
