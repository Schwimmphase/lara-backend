package edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper;

/**
 * Represents the different types of a SavedPaper
 *
 * @author ukgcc
 * @version 1.0
 */
public enum SaveState {
    /**
     * the SavedPaper was added to its Research
     */
    ADDED,
    /**
     * the SavedPaper got enqueued for its Research
     */
    ENQUEUED,
    /**
     * the SavedPaper is hidden for its Research
     */
    HIDDEN
}
