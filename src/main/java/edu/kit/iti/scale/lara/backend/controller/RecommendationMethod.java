package edu.kit.iti.scale.lara.backend.controller;

/**
 * Describes different ways the to get the recommendations for a research
 *
 * @author uefiv
 */
public enum RecommendationMethod {
    /**
     * Algorithm of the remote API based on added and hidden papers
     */
    ALGORITHM,
    /**
     * Citations of added papers
     */
    CITATIONS,
    /**
     * References of added papers
     */
    REFERENCES
}
